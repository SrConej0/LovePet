package com.davidchura.sistema1232

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color3
import com.davidchura.sistema1232.ui.theme.Color4
import org.json.JSONArray

class ParquesActivity : ComponentActivity() {
    private var parksList = ArrayList<HashMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        readService()
    }

    private fun readService() {
        val url = "http://10.0.2.2/servicio/parquesmaestro.php"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                val jsonArray = JSONArray(response)
                parksList.clear()
                for (i in 0 until jsonArray.length()) {
                    val park = jsonArray.getJSONObject(i)
                    val hashMap = HashMap<String, String>()
                    hashMap["id"] = park.getString("id")
                    hashMap["nombre"] = park.getString("nombre")
                    hashMap["descripcion"] = park.getString("descripcion")
                    hashMap["foto"] = "http://10.0.2.2/imagenes/${park.getString("foto")}"
                    hashMap["visitantes"] = park.getString("visitantes")
                    hashMap["areas_verdes"] = park.getString("areas_verdes")
                    hashMap["instalaciones"] = park.getString("instalaciones")
                    parksList.add(hashMap)
                }
                drawParks()
            },
            { error -> Log.e("VOLLEY", error.toString()) }
        )
        queue.add(stringRequest)
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    private fun drawParks() {
        setContent {
            val pagerState = rememberPagerState(pageCount = { parksList.size })
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
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
            ) { innerPadding ->
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = rememberAsyncImagePainter(model = parksList[pagerState.currentPage]["foto"]),
                        contentDescription = "Imagen del Parque",
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(radius = 10.dp),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(1.0f)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.9f),
                                        Color.Transparent
                                    ),
                                    startY = Float.POSITIVE_INFINITY,
                                    endY = 0f
                                )
                            )
                            .align(Alignment.BottomCenter)
                    )
                    // Contenido Principal
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.weight(1f)
                        ) { page ->
                            val park = parksList[page]
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 16.dp)
                            ) {
                                Text(
                                    text = park["nombre"] ?: "",
                                    color = Color4,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                Image(
                                    painter = rememberAsyncImagePainter(model = park["foto"]),
                                    contentDescription = park["nombre"],
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Transparent,
                                                    Color.Black.copy(alpha = 0.7f)
                                                ),
                                                startY = 0f,
                                                endY = Float.POSITIVE_INFINITY
                                            )
                                        )
                                        .padding(horizontal = 16.dp, vertical = 16.dp)
                                ) {
                                    Text(text = "Descripción: ${park["descripcion"] ?: ""}", color = Color3, fontSize = 14.sp)
                                    Text(text = "Visitantes: ${park["visitantes"] ?: ""}", color = Color3, fontSize = 14.sp)
                                    Text(text = "Áreas verdes: ${park["areas_verdes"] ?: ""}", color = Color3, fontSize = 14.sp)
                                    Text(text = "Instalaciones: ${park["instalaciones"] ?: ""}", color = Color3, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                    SmallFloatingActionButton(
                        onClick = { selectPark(parksList[pagerState.currentPage]) },
                        containerColor = Color1,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Ver Detalles",
                            tint = Color4
                        )
                    }
                }
            }
        }
    }

    private fun selectPark(park: HashMap<String, String>) {
        val intent = Intent(this, ParqueDetalleActivity::class.java).apply {
            putExtra("id", park["id"])
        }
        startActivity(intent)
    }
}
