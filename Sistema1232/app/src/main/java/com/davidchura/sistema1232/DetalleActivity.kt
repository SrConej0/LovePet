package com.davidchura.sistema1232

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
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
        val stringRequest = StringRequest(Request.Method.GET, url,
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

@Composable
fun DetailScreen(detalles: List<HashMap<String, String>>?) {
    if (detalles == null) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(detalles) { detalle ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Text(
                            text = "ID: ${detalle["id"]}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color4,
                            modifier = Modifier
                                .background(Color1)
                                .padding(4.dp)
                        )
                    }

                    Text(
                        text = "Nombre: ${detalle["nombre"]}",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = detalle["foto"]),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Descripción
                    Text(text = "Descripción: ${detalle["descripcion"]}")
                    Spacer(modifier = Modifier.height(4.dp))
                    // Edad
                    Text(text = "Edad: ${detalle["edad"]}")
                    Spacer(modifier = Modifier.height(4.dp))
                    // Tipo ID al lado de la edad
                    Text(text = "Tipo ID: ${detalle["tipo_id"]}")
                }
            }
        }
    }
}
