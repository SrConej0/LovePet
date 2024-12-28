package com.davidchura.sistema1232

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
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
import com.davidchura.sistema1232.dao.Canvas
import com.davidchura.sistema1232.dao.CanvasDatabaseProvider
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color4
import com.davidchura.sistema1232.ui.theme.Sistema1232Theme
import kotlinx.coroutines.launch
import java.util.Date

class CanvasUpdateActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        val canvasId = bundle!!.getInt("canvasId")
        val canvasNombre = bundle.getString("canvasNombre").toString()
        val canvasNumero1 = bundle.getInt("canvasNumero1")
        val canvasNumero2 = bundle.getInt("canvasNumero2")
        val canvasNumero3 = bundle.getInt("canvasNumero3")
        val canvasNumero4 = bundle.getInt("canvasNumero4")
        val canvasNumero5 = bundle.getInt("canvasNumero5")
        val canvasNumero6 = bundle.getInt("canvasNumero6")

        enableEdgeToEdge()
        setContent {
            var id by remember { mutableStateOf(canvasId) }
            var nombre by remember { mutableStateOf(canvasNombre) }
            var numero1 by remember { mutableStateOf(canvasNumero1.toString()) }
            var numero2 by remember { mutableStateOf(canvasNumero2.toString()) }
            var numero3 by remember { mutableStateOf(canvasNumero3.toString()) }
            var numero4 by remember { mutableStateOf(canvasNumero4.toString()) }
            var numero5 by remember { mutableStateOf(canvasNumero5.toString()) }
            var numero6 by remember { mutableStateOf(canvasNumero6.toString()) }

            Sistema1232Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color1,
                                titleContentColor = Color4
                            ),
                            title = { Text("Actualizar Canvas", color = Color4) },
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
                                    text = "Actualizar Canvas",
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
                                    value = numero1,
                                    onValueChange = { numero1 = it },
                                    label = { Text("Primer Mes", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                OutlinedTextField(
                                    value = numero2,
                                    onValueChange = { numero2 = it },
                                    label = { Text("Segundo Mes", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                OutlinedTextField(
                                    value = numero3,
                                    onValueChange = { numero3 = it },
                                    label = { Text("Tercer Mes", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                OutlinedTextField(
                                    value = numero4,
                                    onValueChange = { numero4 = it },
                                    label = { Text("Cuarto Mes", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                OutlinedTextField(
                                    value = numero5,
                                    onValueChange = { numero5 = it },
                                    label = { Text("Quinto Mes", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                OutlinedTextField(
                                    value = numero6,
                                    onValueChange = { numero6 = it },
                                    label = { Text("Sexto Mes", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            val database = CanvasDatabaseProvider.getDatabase(this@CanvasUpdateActivity)
                                            val canvasDao = database.canvasDao()
                                            lifecycleScope.launch {
                                                try {
                                                    val canvas = Canvas(
                                                        id = id,
                                                        nombre = nombre,
                                                        numero1 = numero1.toIntOrNull() ?: 0,
                                                        numero2 = numero2.toIntOrNull() ?: 0,
                                                        numero3 = numero3.toIntOrNull() ?: 0,
                                                        numero4 = numero4.toIntOrNull() ?: 0,
                                                        numero5 = numero5.toIntOrNull() ?: 0,
                                                        numero6 = numero6.toIntOrNull() ?: 0,
                                                        createdAt = Date()
                                                    )
                                                    canvasDao.updateCanvas(canvas)
                                                    Toast.makeText(this@CanvasUpdateActivity, "Canvas actualizado", Toast.LENGTH_SHORT).show()
                                                    finish()
                                                } catch (e: Exception) {
                                                    Toast.makeText(this@CanvasUpdateActivity, "Error al actualizar el canvas", Toast.LENGTH_SHORT).show()
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
                                            val database = CanvasDatabaseProvider.getDatabase(this@CanvasUpdateActivity)
                                            val canvasDao = database.canvasDao()
                                            lifecycleScope.launch {
                                                try {
                                                    val canvas = Canvas(
                                                        id = id,
                                                        nombre = nombre,
                                                        numero1 = numero1.toIntOrNull() ?: 0,
                                                        numero2 = numero2.toIntOrNull() ?: 0,
                                                        numero3 = numero3.toIntOrNull() ?: 0,
                                                        numero4 = numero4.toIntOrNull() ?: 0,
                                                        numero5 = numero5.toIntOrNull() ?: 0,
                                                        numero6 = numero6.toIntOrNull() ?: 0,
                                                        createdAt = Date()
                                                    )
                                                    canvasDao.deleteCanvas(canvas)
                                                    Toast.makeText(this@CanvasUpdateActivity, "Canvas eliminado", Toast.LENGTH_SHORT).show()
                                                    finish()
                                                } catch (e: Exception) {
                                                    Toast.makeText(this@CanvasUpdateActivity, "Error al eliminar el canvas", Toast.LENGTH_SHORT).show()
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
                                            modifier = Modifier.padding(vertical = 4.dp) )
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