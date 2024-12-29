package com.davidchura.sistema1232.locations

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1232.R
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color4
import com.davidchura.sistema1232.ui.theme.Sistema1232Theme
import kotlinx.coroutines.launch
import org.json.JSONObject

class PaisesInsertActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema1232Theme {
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color1,
                                titleContentColor = Color4
                            ),
                            title = { Text(stringResource(R.string.title_insert_pais)) },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color4)
                                }
                            }
                        )
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    InsertPaisScreen(
                        modifier = Modifier.padding(innerPadding),
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    }

    @Composable
    fun InsertPaisScreen(
        modifier: Modifier = Modifier,
        snackbarHostState: SnackbarHostState
    ) {
        var codpais by remember { mutableStateOf("") }
        var pais by remember { mutableStateOf("") }
        var capital by remember { mutableStateOf("") }
        var area by remember { mutableStateOf("") }
        var poblacion by remember { mutableStateOf("") }
        var continente by remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = codpais,
                onValueChange = { codpais = it },
                label = { Text("Código País") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = pais,
                onValueChange = { pais = it },
                label = { Text("País") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = capital,
                onValueChange = { capital = it },
                label = { Text("Capital") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = area,
                onValueChange = { area = it },
                label = { Text("Área") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = poblacion,
                onValueChange = { poblacion = it },
                label = { Text("Población") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = continente,
                onValueChange = { continente = it },
                label = { Text("Continente") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    insertPais(
                        codpais, pais, capital, area, poblacion, continente,
                        onSuccess = {
                            scope.launch {
                                snackbarHostState.showSnackbar("País insertado exitosamente")
                                finish()
                            }
                        },
                        onError = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Error al insertar país")
                            }
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Insertar País")
            }
        }
    }

    private fun insertPais(
        codpais: String,
        pais: String,
        capital: String,
        area: String,
        poblacion: String,
        continente: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/paisesinsert.php"
        val stringRequest = object : StringRequest(Method.POST, url,
            { response ->
                val jsonObject = JSONObject(response)
                if (jsonObject.getBoolean("success")) {
                    onSuccess()
                } else {
                    onError()
                }
            },
            { error ->
                Log.e("VOLLEY", error.toString())
                onError()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["codpais"] = codpais
                params["pais"] = pais
                params["capital"] = capital
                params["area"] = area
                params["poblacion"] = poblacion
                params["continente"] = continente
                return params
            }
        }
        queue.add(stringRequest)
    }
}