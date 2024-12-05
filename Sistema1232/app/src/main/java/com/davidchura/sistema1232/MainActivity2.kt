package com.davidchura.sistema1232

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davidchura.sistema1232.dao.DatabaseProvider
import com.davidchura.sistema1232.dao.User
import com.davidchura.sistema1232.dao.UserDao
import com.davidchura.sistema1232.ui.theme.Color1
import com.davidchura.sistema1232.ui.theme.Color4

class MainActivity2 : ComponentActivity() {
    private lateinit var userDao: UserDao
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = DatabaseProvider.getDatabase(this)
        userDao = database.userDao()

        enableEdgeToEdge()
        setContent {
            val userList = remember { mutableStateOf(listOf<User>()) }

            LaunchedEffect(Unit) {
                userDao.getAllUsers().collect { users ->
                    userList.value = users
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
                        title = { Text(stringResource(R.string.user)) },
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
                                Intent(this@MainActivity2, UserInsertActivity::class.java)
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
                    items(userList.value) { user ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val intent = Intent(this@MainActivity2, UserUpdateActivity::class.java)
                                    intent.putExtra("userid", user.id)
                                    intent.putExtra("userName", user.name)
                                    intent.putExtra("userEmail", user.email)
                                    intent.putExtra("userAge", user.age)
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
                                Text(
                                    text = "ID: ${user.id}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = user.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = user.email,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Edad: ${user.age}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Creado: ${user.createdAt}",
                                    style = MaterialTheme.typography.bodySmall
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