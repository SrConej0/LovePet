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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

class CanvasInsertActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var nombre by remember { mutableStateOf("") }
            var numero1 by remember { mutableStateOf("") }
            var numero2 by remember { mutableStateOf("") }
            var numero3 by remember { mutableStateOf("") }
            var numero4 by remember { mutableStateOf("") }
            var numero5 by remember { mutableStateOf("") }
            var numero6 by remember { mutableStateOf("") }

            Sistema1232Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color1,
                                titleContentColor = Color4
                            ),
                            title = { Text("Nuevo Canvas", color = Color4) },
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
                                    text = "Nuevo Canvas",
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

                                // 6 Number Input Fields
                                OutlinedTextField(
                                    value = numero1,
                                    onValueChange = { numero1 = it },
                                    label = { Text("Número 1", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = numero2,
                                    onValueChange = { numero2 = it },
                                    label = { Text("Número 2", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = numero3,
                                    onValueChange = { numero3 = it },
                                    label = { Text("Número 3", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = numero4,
                                    onValueChange = { numero4 = it },
                                    label = { Text("Número 4", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = numero5,
                                    onValueChange = { numero5 = it },
                                    label = { Text("Número 5", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = numero6,
                                    onValueChange = { numero6 = it },
                                    label = { Text("Número 6", color = Color4) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                OutlinedButton(
                                    onClick = {
                                        val database = CanvasDatabaseProvider.getDatabase(this@CanvasInsertActivity)
                                        val canvasDao = database.canvasDao()
                                        lifecycleScope.launch {
                                            try {
                                                val newCanvas = Canvas(
                                                    nombre = nombre,
                                                    numero1 = numero1.toIntOrNull() ?: 0,
                                                    numero2 = numero2.toIntOrNull() ?: 0,
                                                    numero3 = numero3.toIntOrNull() ?: 0,
                                                    numero4 = numero4.toIntOrNull() ?: 0,
                                                    numero5 = numero5.toIntOrNull() ?: 0,
                                                    numero6 = numero6.toIntOrNull() ?: 0
                                                )
                                                canvasDao.insertCanvas(newCanvas)
                                                Toast.makeText(
                                                    this@CanvasInsertActivity,
                                                    "Canvas insertado",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                finish()
                                            } catch (e: Exception) {
                                                Toast.makeText(
                                                    this@CanvasInsertActivity,
                                                    "Error al insertar el canvas",
                                                    Toast.LENGTH_SHORT
                                                ).show()
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