package com.davidchura.sistema1232.locations

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.davidchura.sistema1232.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Random

class MapasViewActivity : ComponentActivity() {

    private fun generateRandomColor(): Int {
        val random = Random()
        return android.graphics.Color.rgb(
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val mapaLatitud = intent.getDoubleExtra("mapaLatitud", 0.0)
        val mapaLongitud = intent.getDoubleExtra("mapaLongitud", 0.0)
        val mapaNombre = intent.getStringExtra("mapaNombre") ?: "Ubicación"
        val mapaDescripcion = intent.getStringExtra("mapaDescripcion") ?: ""

        val topBarColor = generateRandomColor()

        setContent {
            val ubicacion = LatLng(mapaLatitud, mapaLongitud)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(ubicacion, 16f)
            }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(topBarColor),
                            titleContentColor = Color.White
                        ),
                        title = { Text(stringResource(R.string.albergues)) },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null,
                                    tint = Color.White
                                )
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
                    // Main Location Marker with custom color
                    Marker(
                        state = MarkerState(position = ubicacion),
                        title = mapaNombre,
                        snippet = mapaDescripcion,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
                    )

                    // Single Circle with random color
                    val circleColor = generateRandomColor()
                    Circle(
                        center = ubicacion,
                        radius = 200.0,
                        fillColor = Color(circleColor).copy(alpha = 0.3f),
                        strokeColor = Color(circleColor),
                        strokeWidth = 2f,
                        clickable = true,
                        onClick = {
                            Toast.makeText(this@MapasViewActivity, "Área de cobertura", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}