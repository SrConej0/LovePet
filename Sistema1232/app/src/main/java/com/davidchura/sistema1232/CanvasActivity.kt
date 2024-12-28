package com.davidchura.sistema1232

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davidchura.sistema1232.dao.Canvas
import com.davidchura.sistema1232.dao.CanvasDao
import com.davidchura.sistema1232.dao.CanvasDatabaseProvider
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color4
import kotlinx.coroutines.launch

class CanvasActivity : ComponentActivity() {
    private lateinit var canvasDao: CanvasDao

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = CanvasDatabaseProvider.getDatabase(this)
        canvasDao = database.canvasDao()

        enableEdgeToEdge()
        setContent {
            val canvasList = remember { mutableStateOf(listOf<Canvas>()) }
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                canvasDao.getAllCanvases().collect { canvases ->
                    canvasList.value = canvases
                }
            }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color1,
                            titleContentColor = Color4
                        ),
                        title = { Text("Canvas") },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color4)
                            }
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            startActivity(
                                Intent(this@CanvasActivity, CanvasInsertActivity::class.java)
                            )
                        },
                        containerColor = Color1,
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp)
                ) {
                    items(canvasList.value) { canvas ->
                        var showDeleteDialog by remember { mutableStateOf(false) }

                        if (showDeleteDialog) {
                            ConfirmDeleteDialog(
                                onConfirm = {
                                    coroutineScope.launch {
                                        canvasDao.deleteCanvas(canvas)
                                        showDeleteDialog = false
                                    }
                                },
                                onDismiss = { showDeleteDialog = false }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val intent = Intent(this@CanvasActivity, CanvasViewActivity::class.java).apply {
                                        putExtra("canvasId", canvas.id)
                                        putExtra("canvasNombre", canvas.nombre)
                                        putExtra("canvasNumero1", canvas.numero1)
                                        putExtra("canvasNumero2", canvas.numero2)
                                        putExtra("canvasNumero3", canvas.numero3)
                                        putExtra("canvasNumero4", canvas.numero4)
                                        putExtra("canvasNumero5", canvas.numero5)
                                        putExtra("canvasNumero6", canvas.numero6)
                                    }
                                    startActivity(intent)
                                },
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp)
                                ) {
                                    Text(
                                        text = "ID: ${canvas.id}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(onClick = {
                                            // Navigate to Update Activity
                                            val intent = Intent(this@CanvasActivity, CanvasUpdateActivity::class.java).apply {
                                                putExtra("canvasId", canvas.id)
                                                putExtra("canvasNombre", canvas.nombre)
                                                putExtra("canvasNumero1", canvas.numero1)
                                                putExtra("canvasNumero2", canvas.numero2)
                                                putExtra("canvasNumero3", canvas.numero3)
                                                putExtra("canvasNumero4", canvas.numero4)
                                                putExtra("canvasNumero5", canvas.numero5)
                                                putExtra("canvasNumero6", canvas.numero6)
                                            }
                                            startActivity(intent)
                                        }) {
                                            Icon(
                                                Icons.Default.Edit,
                                                contentDescription = "Editar",
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                        IconButton(onClick = { showDeleteDialog = true }) {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "Eliminar",
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = canvas.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Números: ${canvas.numero1}, ${canvas.numero2}, ${canvas.numero3}, ${canvas.numero4}, ${canvas.numero5}, ${canvas.numero6}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}