package com.davidchura.sistema1232

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidchura.sistema1232.ui.theme.Color2
import com.davidchura.sistema1232.ui.theme.Color3
import com.davidchura.sistema1232.ui.theme.Color4
import com.davidchura.sistema1232.ui.theme.Color5
import com.davidchura.sistema1232.ui.theme.Manrope
import com.davidchura.sistema1232.ui.theme.Sistema1232Theme
import kotlinx.coroutines.delay
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class BeginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Contraseno es 999999999 y 1111
            Sistema1232Theme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color2)
                ) {
                    var timer by remember { mutableIntStateOf(60) }
                    var code1 by remember { mutableStateOf("") }
                    var code2 by remember { mutableStateOf("") }
                    var code3 by remember { mutableStateOf("") }
                    var code4 by remember { mutableStateOf("") }
                    var errorMessage by remember { mutableStateOf("") }
                    LocalFocusManager.current
                    val telefono = intent.getStringExtra("telefono")

                    LaunchedEffect(timer) {
                        if (timer > 0) {
                            delay(1000L)
                            timer--
                        } else {
                            startActivity(Intent(this@BeginActivity, MainActivity::class.java))
                            finish()
                        }
                    }

                    LaunchedEffect(code1, code2, code3, code4) {
                        if (code1.length == 1 && code2.length == 1 && code3.length == 1 && code4.length == 1) {
                            val codigo = code1 + code2 + code3 + code4

                            if (codigo == "1111") {
                                startActivity(Intent(this@BeginActivity, HomeActivity::class.java))
                                finish()
                            } else {

                                val url = "http://10.0.2.2/servicio/ingresocodigo.php"
                                val requestBody = FormBody.Builder()
                                    .add("telefono", telefono ?: "")
                                    .add("codigo", codigo)
                                    .build()

                                val request = Request.Builder()
                                    .url(url)
                                    .post(requestBody)
                                    .build()

                                val client = OkHttpClient()
                                client.newCall(request).enqueue(object : Callback {
                                    override fun onFailure(call: Call, e: IOException) {
                                        errorMessage = "Error de conexión. Inténtalo de nuevo."
                                    }

                                    override fun onResponse(call: Call, response: Response) {
                                        if (response.isSuccessful) {
                                            val responseBody = response.body?.string()
                                            val jsonResponse = JSONObject(responseBody.toString())

                                            if (jsonResponse.getBoolean("success")) {
                                                startActivity(
                                                    Intent(
                                                        this@BeginActivity,
                                                        HomeActivity::class.java
                                                    )
                                                )
                                                finish()
                                            } else {
                                                errorMessage = jsonResponse.getString("message")
                                            }
                                        } else {
                                            errorMessage =
                                                "Error en la verificación. Inténtalo de nuevo."
                                        }
                                    }
                                })
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color2)
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = stringResource(id = R.string.begin2),
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontFamily = Manrope,
                                fontWeight = FontWeight.Normal,
                                fontSize = dimensionResource(id = R.dimen.size_2).value.sp,
                                lineHeight = 30.sp,
                                color = Color4
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp)
                                .align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BasicTextField(
                                value = code1,
                                onValueChange = { if (it.length <= 1) code1 = it },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .size(65.dp)
                                    .background(Color3, shape = RoundedCornerShape(8.dp))
                                    .padding(16.dp)
                            )
                            BasicTextField(
                                value = code2,
                                onValueChange = { if (it.length <= 1) code2 = it },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .size(65.dp)
                                    .background(Color3, shape = RoundedCornerShape(8.dp))
                                    .padding(16.dp)
                            )
                            BasicTextField(
                                value = code3,
                                onValueChange = { if (it.length <= 1) code3 = it },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .size(65.dp)
                                    .background(Color3, shape = RoundedCornerShape(8.dp))
                                    .padding(16.dp)
                            )
                            BasicTextField(
                                value = code4,
                                onValueChange = { if (it.length <= 1) code4 = it },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .size(65.dp)
                                    .background(Color3, shape = RoundedCornerShape(8.dp))
                                    .padding(16.dp)
                            )
                        }

                        if (errorMessage.isNotEmpty()) {
                            Text(
                                text = errorMessage,
                                color = Color.Red,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.hour),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color5,
                                    textDecoration = TextDecoration.Underline
                                ),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Text(
                                text = "(${timer})",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color5,
                                    textDecoration = TextDecoration.Underline
                                ),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = stringResource(id = R.string.hour_description),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color4
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = stringResource(id = R.string.rights),
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontSize = dimensionResource(id = R.dimen.size_0).value.sp,
                                textAlign = TextAlign.Center,
                                lineHeight = 25.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 72.dp, vertical = 4.dp)
                                .offset(y = (-10).dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
