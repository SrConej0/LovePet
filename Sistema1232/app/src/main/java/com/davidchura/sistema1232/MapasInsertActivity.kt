package com.davidchura.sistema1232

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.davidchura.sistema1232.dao.Mapa
import com.davidchura.sistema1232.dao.MapaDatabaseProvider
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color4
import com.davidchura.sistema1232.ui.theme.Sistema1232Theme
import kotlinx.coroutines.launch

class MapasInsertActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var nombre by remember { mutableStateOf("") }
            var latitud by remember { mutableStateOf("") }
            var longitud by remember { mutableStateOf("") }
            var descripcion by remember { mutableStateOf("") }

            Sistema1232Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color1,
                                titleContentColor = Color4
                            ),
                            title = { Text("Nuevo Mapa", color = Color4) },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null,
                                        tint = Color4
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Nuevo Mapa",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Color4,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                                OutlinedTextField(
                                    value = nombre,
                                    onValueChange = { nombre = it },
                                    label = { Text("Nombre", color = Color4) },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = latitud,
                                    onValueChange = { latitud = it },
                                    label = { Text("Latitud", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = longitud,
                                    onValueChange = { longitud = it },
                                    label = { Text("Longitud", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = descripcion,
                                    onValueChange = { descripcion = it },
                                    label = { Text("Descripción", color = Color4) },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                OutlinedButton(
                                    onClick = {
                                        val database = MapaDatabaseProvider.getDatabase(this@MapasInsertActivity)
                                        val mapaDao = database.mapaDao()
                                        lifecycleScope.launch {
                                            try {
                                                val newMapa = Mapa(
                                                    nombre = nombre,
                                                    latitud = latitud.toDoubleOrNull() ?: 0.0,
                                                    longitud = longitud.toDoubleOrNull() ?: 0.0,
                                                    descripcion = descripcion
                                                )
                                                mapaDao.insertMapa(newMapa)
                                                Toast.makeText(this@MapasInsertActivity, "Mapa insertado", Toast.LENGTH_SHORT).show()
                                                finish()
                                            } catch (e: Exception) {
                                                Log.e("MapasInsertActivity", "Error al insertar el mapa", e)
                                                Toast.makeText(this@MapasInsertActivity, "Error al insertar el mapa", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = Color1,
                                        contentColor = Color1
                                    )
                                ) {
                                    Text(
                                        text = "Guardar",
                                        color = Color4,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
