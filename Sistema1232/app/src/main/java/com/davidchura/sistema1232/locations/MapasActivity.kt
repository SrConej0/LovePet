package com.davidchura.sistema1232.locations

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davidchura.sistema1232.R
import com.davidchura.sistema1232.dao.Mapa
import com.davidchura.sistema1232.dao.MapaDao
import com.davidchura.sistema1232.dao.MapaDatabaseProvider
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color4
import kotlinx.coroutines.launch

class MapasActivity : ComponentActivity() {
    private lateinit var mapaDao: MapaDao

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = MapaDatabaseProvider.getDatabase(this)
        mapaDao = database.mapaDao()

        enableEdgeToEdge()
        setContent {
            val mapaList = remember { mutableStateOf(listOf<Mapa>()) }
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                mapaDao.getAllMapas().collect { mapas ->
                    mapaList.value = mapas
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
                        title = { Text(stringResource(R.string.mapa)) },
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
                                Intent(this@MapasActivity, MapasInsertActivity::class.java)
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
                    items(mapaList.value) { mapa ->
                        var showDeleteDialog by remember { mutableStateOf(false) }

                        if (showDeleteDialog) {
                            ConfirmDeleteDialog(
                                onConfirm = {
                                    coroutineScope.launch {
                                        mapaDao.deleteMapa(mapa)
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
                                    val intent = Intent(this@MapasActivity, MapasViewActivity::class.java).apply {
                                        putExtra("mapaId", mapa.id)
                                        putExtra("mapaNombre", mapa.nombre)
                                        putExtra("mapaLatitud", mapa.latitud)
                                        putExtra("mapaLongitud", mapa.longitud)
                                        putExtra("mapaDescripcion", mapa.descripcion)
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
                                        .padding(bottom = 8.dp)  // Bajar los íconos
                                ) {
                                    Text(
                                        text = "ID: ${mapa.id}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(onClick = {
                                            val intent = Intent(this@MapasActivity, MapasUpdateActivity::class.java).apply {
                                                putExtra("mapaId", mapa.id)
                                                putExtra("mapaNombre", mapa.nombre)
                                                putExtra("mapaLatitud", mapa.latitud)
                                                putExtra("mapaLongitud", mapa.longitud)
                                                putExtra("mapaDescripcion", mapa.descripcion)
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
                                    text = mapa.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Latitud: ${mapa.latitud}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Longitud: ${mapa.longitud}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = mapa.descripcion,
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

@Composable
fun ConfirmDeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color4
                )
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color4
                )
            ) {
                Text("Cancelar")
            }
        },
        containerColor = Color1,
        title = { Text("Confirmar eliminación") },
        text = { Text("¿Estás seguro de que deseas eliminar este elemento?") }
    )
}
