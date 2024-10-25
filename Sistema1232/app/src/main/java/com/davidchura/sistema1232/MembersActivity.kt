package com.davidchura.sistema1232

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import coil.compose.rememberAsyncImagePainter
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color4
import org.json.JSONArray

class MembersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        readService()
    }

    private fun readService() {
        val url = "http://10.0.2.2/servicio/serviciomaestro.php"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                val jsonArray = JSONArray(response)
                val arrayList = ArrayList<HashMap<String, String>>()
                for (i in 0 until jsonArray.length()) {
                    val member = jsonArray.getJSONObject(i)
                    val hashMap = HashMap<String, String>()
                    hashMap["tipo_id"] = member.getString("tipo_id")
                    hashMap["tipo"] = member.getString("tipo")
                    hashMap["descripcion"] = member.getString("descripcion")
                    hashMap["foto"] = "http://10.0.2.2/imagenes/${member.getString("foto")}"
                    arrayList.add(hashMap)
                }
                drawMembers(arrayList)
            },
            { error -> Log.e("VOLLEY", error.toString()) }
        )
        queue.add(stringRequest)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun drawMembers(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            com.davidchura.sistema1232.ui.theme.Sistema1232Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color1,
                                titleContentColor = Color4
                            ),
                            title = { Text(stringResource(R.string.title_members)) },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color4)
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        items(items = arrayList) { member ->
                            Box(modifier = Modifier.clickable { selectMember(member) }) {
                                DrawMemberItem(member)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun selectMember(member: HashMap<String, String>) {
        val intent = Intent(this, DetalleActivity::class.java).apply {
            putExtra("tipo_id", member["tipo_id"])
        }
        startActivity(intent)
    }
}
@Composable
fun DrawMemberItem(member: HashMap<String, String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically // Alinea verticalmente el contenido
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Tipo: ${member["tipo"]}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Descripción: ${member["descripcion"]}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Tipo ID: ${member["tipo_id"]}", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(modifier = Modifier.size(250.dp), contentAlignment = Alignment.Center) {
            Image(
                painter = rememberAsyncImagePainter(model = member["foto"]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize() // La imagen llenará el espacio dentro del Box
                    .padding(8.dp) // Ajusta este padding según sea necesario
            )
        }
    }
}
