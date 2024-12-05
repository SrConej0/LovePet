package com.davidchura.sistema1232

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.davidchura.sistema1232.dao.DatabaseProvider
import com.davidchura.sistema1232.dao.User
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color3
import com.davidchura.sistema1232.ui.theme.Color4
import com.davidchura.sistema1232.ui.theme.Sistema1232Theme
import kotlinx.coroutines.launch

class UserUpdateActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val userId = bundle!!.getInt("userid")
        val userName = bundle.getString("userName").toString()
        val userEmail = bundle.getString("userEmail").toString()
        val userAge = bundle.getInt("userAge").toString()
        enableEdgeToEdge()
        setContent {
            var id by remember { mutableStateOf(userId) }
            var name by remember { mutableStateOf(userName) }
            var email by remember { mutableStateOf(userEmail) }
            var age by remember { mutableStateOf(userAge) }

            Sistema1232Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color1,
                                titleContentColor = Color4
                            ),
                            title = { Text(stringResource(R.string.update_user), color = Color4) },
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
                                    text = "Actualizar Usuario",
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
                                    value = name,
                                    onValueChange = { name = it },
                                    label = { Text("Nombre", color = Color4) },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text("Email", color = Color4) },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = age,
                                    onValueChange = { age = it },
                                    label = { Text("Edad", color = Color4) },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            val database = DatabaseProvider.getDatabase(this@UserUpdateActivity)
                                            val userDao = database.userDao()
                                            lifecycleScope.launch {
                                                val user = User(
                                                    id = id,
                                                    name = name,
                                                    email = email,
                                                    age = age.toInt()
                                                )
                                                userDao.updateUser(user)
                                                finish()
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
                                            val database = DatabaseProvider.getDatabase(this@UserUpdateActivity)
                                            val userDao = database.userDao()
                                            lifecycleScope.launch {
                                                userDao.deleteUserById(id)
                                                finish()
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