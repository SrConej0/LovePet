package com.davidchura.sistema1232

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color4
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import org.json.JSONArray

class ParqueDetalleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val parkId = intent.getStringExtra("id")
        readService(parkId)
    }

    private fun readService(parkId: String?) {
        val url = "http://10.0.2.2/servicio/parquesdetalle.php?id=$parkId"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                val jsonArray = JSONArray(response)
                val park = jsonArray.getJSONObject(0)
                val details = hashMapOf(
                    "nombre" to park.getString("nombre"),
                    "latitud" to park.getString("latitud"),
                    "longitud" to park.getString("longitud"),
                    "foto" to "http://10.0.2.2/imagenes/${park.getString("foto")}"
                )
                drawParkDetails(details)
            },
            { error -> Log.e("VOLLEY", error.toString()) }
        )
        queue.add(stringRequest)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun drawParkDetails(details: HashMap<String, String>) {
        setContent {
            val ubicacion = LatLng(details["latitud"]?.toDouble() ?: 0.0, details["longitud"]?.toDouble() ?: 0.0)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(ubicacion, 16f)
            }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color1,
                            titleContentColor = Color4
                        ),
                        title = { Text(stringResource(R.string.parques)) },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color4)
                            }
                        }
                    )
                }
            ) { paddingValues ->
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(position = ubicacion),
                        title = details["nombre"],
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                    )

                    Circle(
                        center = ubicacion,
                        radius = 100.0, // 100 metros
                        fillColor = Color.Blue.copy(alpha = 0.3f),
                        strokeColor = Color.Blue,
                        strokeWidth = 2f,
                        clickable = true,
                        onClick = {
                            val intent = Intent(this@ParqueDetalleActivity, ParqueFotoActivity::class.java).apply {

                                putExtra("nombre", details["nombre"])
                                putExtra("foto", details["foto"])
                            }
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}
