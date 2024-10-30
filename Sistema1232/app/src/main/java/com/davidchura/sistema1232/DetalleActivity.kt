package com.davidchura.sistema1232

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color4
import org.json.JSONArray

class DetalleActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val tipoId = intent.getStringExtra("tipo_id")

        setContent {
            val detallesState = remember { mutableStateOf<List<HashMap<String, String>>?>(null) }

            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color1,
                            titleContentColor = Color4
                        ),
                        title = { Text(stringResource(R.string.title_detalle)) },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color4)
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    DetailScreen(detallesState.value)
                }
            }

            LaunchedEffect(tipoId) {
                tipoId?.let {
                    readService(it, { detallesState.value = it }, { Log.e("VOLLEY", it) })
                }
            }
        }
    }

    private fun readService(
        tipoId: String,
        onResult: (List<HashMap<String, String>>) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "http://10.0.2.2/servicio/serviciodetalle.php?tipo_id=$tipoId"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val detalles = JSONArray(response).run {
                    (0 until length()).map { i ->
                        val item = getJSONObject(i)
                        hashMapOf(
                            "id" to item.getString("id"),
                            "nombre" to item.getString("nombre"),
                            "descripcion" to item.getString("descripcion"),
                            "edad" to item.getString("edad"),
                            "tipo_id" to item.getString("tipo_id"),
                            "foto" to "http://10.0.2.2/imagenes/${item.getString("foto")}"
                        )
                    }
                }
                onResult(detalles)
            },
            { error -> onError(error.toString()) }
        )
        queue.add(stringRequest)
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(detalles: List<HashMap<String, String>>?) {
    if (detalles == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val pagerState = rememberPagerState(pageCount = { detalles.size })

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val detalle = detalles[page]
            Box(modifier = Modifier.fillMaxSize()) {
                // Imagen de fondo con blur aumentado
                Image(
                    painter = rememberAsyncImagePainter(model = detalle["foto"]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(radius = 20.dp),  // Aumentado de 10dp a 20dp
                    contentScale = ContentScale.FillBounds
                )

                // Gradiente más oscuro y suave
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.6f),  // Más oscuro
                                    Color.Black.copy(alpha = 0.8f)   // Más oscuro
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // ID Card con sombra más pronunciada
                    Card(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(4.dp),  // Añadido padding para la sombra
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color1.copy(alpha = 0.95f)  // Más opaco
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 24.dp,    // Aumentado de 12dp
                            pressedElevation = 12.dp,    // Aumentado de 6dp
                            focusedElevation = 24.dp     // Aumentado de 12dp
                        )
                    ) {
                        Text(
                            text = "ID: ${detalle["id"]}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color4,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)  // Padding aumentado
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Imagen principal con sombra dramática
                        Card(
                            modifier = Modifier
                                .size(340.dp)
                                .padding(12.dp),  // Aumentado de 8dp
                            shape = RoundedCornerShape(20.dp),  // Aumentado de 16dp
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 32.dp,    // Aumentado de 20dp
                                pressedElevation = 16.dp,    // Aumentado de 10dp
                                focusedElevation = 32.dp     // Aumentado de 20dp
                            )
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = detalle["foto"]),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))  // Aumentado de 8dp

                        // Tarjeta de información con efecto glassmorphism mejorado
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),  // Aumentado de 8dp
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.9f)  // Más opaco
                            ),
                            shape = RoundedCornerShape(20.dp),  // Aumentado de 16dp
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 24.dp,    // Aumentado de 16dp
                                pressedElevation = 12.dp,    // Aumentado de 8dp
                                focusedElevation = 24.dp     // Aumentado de 16dp
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),  // Aumentado de 16dp
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = detalle["nombre"] ?: "",
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(12.dp))  // Aumentado de 8dp
                                Text(
                                    text = detalle["descripcion"] ?: "",
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(12.dp))  // Aumentado de 8dp
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    // Mini-cards con sombras más pronunciadas
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color1.copy(alpha = 0.15f)  // Ligeramente más visible
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 8.dp  // Aumentado de 4dp
                                        )
                                    ) {
                                        Text(
                                            text = "Edad: ${detalle["edad"]}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Black,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)  // Padding aumentado
                                        )
                                    }

                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color1.copy(alpha = 0.15f)
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 8.dp
                                        )
                                    ) {
                                        Text(
                                            text = "Tipo ID: ${detalle["tipo_id"]}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Black,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Indicador de página con sombras más pronunciadas
                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp)  // Aumentado de 16dp
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(detalles.size) { iteration ->
                            val color = if (pagerState.currentPage == iteration) {
                                Color.White
                            } else {
                                Color.White.copy(alpha = 0.5f)
                            }
                            Card(
                                modifier = Modifier
                                    .padding(3.dp)  // Aumentado de 2dp
                                    .size(8.dp),
                                shape = RoundedCornerShape(4.dp),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 8.dp  // Aumentado de 4dp
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = color
                                )
                            ) {
                                Box(modifier = Modifier.fillMaxSize())
                            }
                        }
                    }
                }
            }
        }
    }
}