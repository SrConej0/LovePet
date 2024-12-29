package com.davidchura.sistema1232.locations

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.davidchura.sistema1232.R
import com.davidchura.sistema1232.dao.Mapa
import com.davidchura.sistema1232.dao.MapaDatabaseProvider
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color4
import com.davidchura.sistema1232.ui.theme.Sistema1232Theme
import kotlinx.coroutines.launch

class MapasUpdateActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        val mapaId = bundle!!.getInt("mapaId")
        val mapaNombre = bundle.getString("mapaNombre").toString()
        val mapaLatitud = bundle.getDouble("mapaLatitud").toString()
        val mapaLongitud = bundle.getDouble("mapaLongitud").toString()
        val mapaDescripcion = bundle.getString("mapaDescripcion").toString()

        enableEdgeToEdge()
        setContent {
            var id by remember { mutableStateOf(mapaId) }
            var nombre by remember { mutableStateOf(mapaNombre) }
            var latitud by remember { mutableStateOf(mapaLatitud) }
            var longitud by remember { mutableStateOf(mapaLongitud) }
            var descripcion by remember { mutableStateOf(mapaDescripcion) }

            Sistema1232Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color1,
                                titleContentColor = Color4
                            ),
                            title = { Text(stringResource(R.string.update_mapa), color = Color4) },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
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
                                    text = "Actualizar Mapa",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Color4,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                                OutlinedTextField(
                                    value = id.toString(),
                                    onValueChange = { },
                                    label = { Text("Código", color = Color4) },
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = false
                                )

                                Spacer(modifier = Modifier.height(16.dp))

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

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            val database = MapaDatabaseProvider.getDatabase(this@MapasUpdateActivity)
                                            val mapaDao = database.mapaDao()
                                            lifecycleScope.launch {
                                                try {
                                                    val mapa = Mapa(
                                                        id = id,
                                                        nombre = nombre,
                                                        latitud = latitud.toDoubleOrNull() ?: 0.0,
                                                        longitud = longitud.toDoubleOrNull() ?: 0.0,
                                                        descripcion = descripcion
                                                    )
                                                    mapaDao.updateMapa(mapa)
                                                    Toast.makeText(this@MapasUpdateActivity, "Mapa actualizado", Toast.LENGTH_SHORT).show()
                                                    finish()
                                                } catch (e: Exception) {
                                                    Toast.makeText(this@MapasUpdateActivity, "Error al actualizar el mapa", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            containerColor = Color1,
                                            contentColor = Color1
                                        )
                                    ) {
                                        Text(
                                            text = "Actualizar",
                                            color = Color4,
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        )
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            val database = MapaDatabaseProvider.getDatabase(this@MapasUpdateActivity)
                                            val mapaDao = database.mapaDao()
                                            lifecycleScope.launch {
                                                try {
                                                    mapaDao.deleteMapa(Mapa(id, nombre, latitud.toDouble(), longitud.toDouble(), descripcion))
                                                    Toast.makeText(this@MapasUpdateActivity, "Mapa eliminado", Toast.LENGTH_SHORT).show()
                                                    finish()
                                                } catch (e: Exception) {
                                                    Toast.makeText(this@MapasUpdateActivity, "Error al eliminar el mapa", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            containerColor = Color1,
                                            contentColor = Color1
                                        )
                                    ) {
                                        Text(
                                            text = "Eliminar",
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
}
