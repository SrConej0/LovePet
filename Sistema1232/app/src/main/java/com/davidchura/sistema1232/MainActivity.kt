package com.davidchura.sistema1232

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidchura.sistema1232.ui.theme.Color2
import com.davidchura.sistema1232.ui.theme.Color3
import com.davidchura.sistema1232.ui.theme.Color4
import com.davidchura.sistema1232.ui.theme.Manrope
import com.davidchura.sistema1232.ui.theme.Sistema1232Theme
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity() : ComponentActivity(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema1232Theme {
                var isPopupVisible by remember { mutableStateOf(false) }
                var phoneNumber by remember { mutableStateOf("") }
                var errorMessage by remember { mutableStateOf("") }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color2)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.greeting),
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontFamily = Manrope,
                                fontWeight = FontWeight.Medium,
                                fontSize = dimensionResource(id = R.dimen.size_2).value.sp,
                                lineHeight = 30.sp,
                                color = Color4,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 28.dp)
                                .align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            modifier = Modifier
                                .size(350.dp, 55.dp)
                                .background(Color3, shape = RoundedCornerShape(8.dp)),
                            shape = RoundedCornerShape(12.dp),
                            placeholder = { Text(text = "+(51)") }
                        )

                        if (errorMessage.isNotEmpty()) {
                            Text(
                                text = errorMessage,
                                color = Color.Red,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        ElevatedButton(
                            onClick = {
                                // Enviar datos al PHP para verificación
                                val url = "http://10.0.2.2/servicio/ingreso.php"
                                val requestBody = FormBody.Builder()
                                    .add("telefono", phoneNumber)
                                    .build()
                                val request = Request.Builder()
                                    .url(url)
                                    .post(requestBody)
                                    .build()
                                val client = OkHttpClient()
                                client.newCall(request).enqueue(object : Callback {
                                    override fun onFailure(call: Call, e: IOException) {
                                        // Manejar error de conexión
                                        errorMessage = "Error de conexión. Inténtalo de nuevo."
                                    }

                                    override fun onResponse(call: Call, response: Response) {
                                        if (response.isSuccessful) {
                                            val responseBody = response.body?.string()
                                            val jsonResponse = JSONObject(responseBody)

                                            if (jsonResponse.getBoolean("success")) {
                                                val intent = Intent(
                                                    this@MainActivity,
                                                    BeginActivity::class.java
                                                ).apply {
                                                    putExtra("telefono", phoneNumber)
                                                }
                                                startActivity(intent)
                                            } else {
                                                errorMessage = jsonResponse.getString("message")
                                            }
                                        } else {
                                            errorMessage =
                                                "Error en la verificación. Inténtalo de nuevo."
                                        }
                                    }
                                })
                            },
                            modifier = Modifier.size(350.dp, 55.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color4,
                                contentColor = Color3
                            )
                        ) {
                            Text(text = stringResource(id = R.string.begin))
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = stringResource(id = R.string.link),
                            modifier = Modifier.clickable { isPopupVisible = true },
                            color = Color4,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                textDecoration = TextDecoration.Underline
                            )
                        )
                    }


                        if (isPopupVisible) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0x16000000)),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color4,
                                        shape = RoundedCornerShape(
                                            topStart = 25.dp,
                                            topEnd = 25.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 0.dp
                                        )
                                    )
                                    .padding(16.dp)
                                    .padding(bottom = 54.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.logo6),
                                    contentDescription = "Close Icon",
                                    modifier = Modifier
                                        .size(52.dp)
                                        .clickable { isPopupVisible = false },
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = stringResource(id = R.string.login),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color3,
                                        fontSize = 24.sp
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                // Botones de inicio de sesión
                                listOf(
                                    Triple(R.drawable.logo1, R.string.login_apple, Color3),
                                    Triple(R.drawable.logo2, R.string.login_google, Color3),
                                    Triple(R.drawable.logo3, R.string.login_facebook, Color3),
                                    Triple(R.drawable.logo4, R.string.login_email, Color3)
                                ).forEach { (icon, text, color) ->
                                    Button(
                                        onClick = { /* Acción del botón */ },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                            .height(50.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = color,
                                            contentColor = Color.Black
                                        )
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = icon),
                                                contentDescription = "Logo",
                                                modifier = Modifier.size(dimensionResource(id = R.dimen.size_2))
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = stringResource(id = text),
                                                modifier = Modifier.weight(1f),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 36.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Text(
                            text = stringResource(id = R.string.rights),
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontSize = dimensionResource(id = R.dimen.size_0).value.sp,
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 85.dp, vertical = 8.dp)
                                .offset(y = (-10).dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}