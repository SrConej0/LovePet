package com.davidchura.sistema1232.locations

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1232.R
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color4
import com.davidchura.sistema1232.ui.theme.Sistema1232Theme
import org.json.JSONArray

class PaisesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        readService()
    }

    private fun readService() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/paises.php"
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                val jsonArray = JSONArray(response)
                val arrayList = ArrayList<HashMap<String, String>>()
                for (i in 0 until jsonArray.length()) {
                    val pais = jsonArray.getJSONObject(i)
                    val hashMap = HashMap<String, String>()
                    hashMap["codpais"] = pais.getString("codpais")
                    hashMap["pais"] = pais.getString("pais")
                    hashMap["capital"] = pais.getString("capital")
                    hashMap["area"] = pais.getString("area")
                    hashMap["poblacion"] = pais.getString("poblacion")
                    hashMap["continente"] = pais.getString("continente")
                    arrayList.add(hashMap)
                }
                drawPaises(arrayList)
            },
            { error -> Log.e("VOLLEY", error.toString()) }
        )
        queue.add(stringRequest)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun drawPaises(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            Sistema1232Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color1,
                                titleContentColor = Color4
                            ),
                            title = { Text(stringResource(R.string.title_paises)) },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color4)
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            startActivity(Intent(this, PaisesInsertActivity::class.java))
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    }
                ) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        LazyColumn {
                            items(items = arrayList) { pais ->
                                Box(Modifier.clickable { selectPais(pais) }) {
                                    DrawPaisItem(pais)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun selectPais(pais: HashMap<String, String>) {
        val intent = Intent(this, PaisesInsertActivity::class.java).apply {
            putExtra("codpais", pais["codpais"])
            putExtra("pais", pais["pais"])
            putExtra("capital", pais["capital"])
            putExtra("area", pais["area"])
            putExtra("poblacion", pais["poblacion"])
            putExtra("continente", pais["continente"])
        }
        startActivity(intent)
    }
}

@Composable
fun DrawPaisItem(pais: HashMap<String, String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            .padding(16.dp)
    ) {
        Text(text = "Código: ${pais["codpais"]}", style = MaterialTheme.typography.titleLarge)
        Text(text = "País: ${pais["pais"]}", style = MaterialTheme.typography.titleMedium)
        Text(text = "Capital: ${pais["capital"]}")
        Text(text = "Área: ${pais["area"]}")
        Text(text = "Población: ${pais["poblacion"]}")
        Text(text = "Continente: ${pais["continente"]}")
    }
}
