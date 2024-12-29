package com.davidchura.sistema1232

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.davidchura.sistema1232.draw.CanvasActivity
import com.davidchura.sistema1232.locations.MainActivity2
import com.davidchura.sistema1232.locations.MapasActivity
import com.davidchura.sistema1232.locations.PaisesActivity
import com.davidchura.sistema1232.locations.ParquesActivity
import com.davidchura.sistema1232.ui.theme.*
class TermsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Sistema1232Theme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color1)
                ) {
                    var searchText by remember { mutableStateOf("") }
                    var selectedPet by remember { mutableStateOf<String?>(null) }

                    val petNames = listOf(
                        "APAP", "ADOPTAME", "AMIGOS 24/7", "MASCOTA CO", "PATITAS", "PERU", "RESCATE", "VOZ ANIMAL",
                        "HUELLAS", "DERECHOS"
                    )
                    val buttonLabels = listOf(
                        R.string.lost,
                        R.string.found,
                        R.string.adoption
                    )
                    val imageIds = listOf(
                        R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5,
                        R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img10
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.terms),
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontFamily = Manrope,
                                    fontWeight = FontWeight.Medium
                                ),
                                color = Color4,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp)
                        ) {
                            TextField(
                                value = searchText,
                                onValueChange = { searchText = it },
                                placeholder = { Text(text = "Search...") },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = android.R.drawable.ic_search_category_default),
                                        contentDescription = "Lupa",
                                        tint = Color5,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                                    .background(Color3, RoundedCornerShape(10.dp))  // Fondo blanco con bordes redondeados
                                    .shadow(4.dp, RoundedCornerShape(10.dp))  // Sombra al contenedor
                            )
                        }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color2)
                                .padding(horizontal = 20.dp)
                        ) {
                            item {
                                Text(
                                    text = stringResource(id = R.string.terms_text),
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontFamily = Manrope
                                    ),
                                    color = Color4,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                            items(petNames.filter { it.lowercase().contains(searchText.lowercase()) }) { petName ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                        .background(Color3, RoundedCornerShape(16.dp))
                                        .border(2.dp, Color6, RoundedCornerShape(16.dp))
                                        .padding(16.dp)
                                        .clickable { selectedPet = petName }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val imageId = imageIds[petNames.indexOf(petName) % imageIds.size]
                                        Image(
                                            painter = painterResource(id = imageId),
                                            contentDescription = "Imagen",
                                            modifier = Modifier
                                                .size(150.dp)
                                                .clip(RoundedCornerShape(16.dp))
                                                .border(2.dp, Color6, RoundedCornerShape(16.dp))
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            val buttonText = stringResource(id = buttonLabels[petNames.indexOf(petName) % buttonLabels.size])
                                            Button(
                                                onClick = { /* Acción del botón */ },
                                                colors = ButtonDefaults.buttonColors(containerColor = Color1),
                                                modifier = Modifier
                                                    .align(Alignment.Start)
                                                    .padding(4.dp)
                                                    .height(32.dp)
                                            ) {
                                                Text(
                                                    text = buttonText,
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        fontFamily = Manrope,
                                                        fontWeight = FontWeight.Medium,
                                                        fontSize = 12.sp
                                                    ),
                                                    color = Color4
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = petName,
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    fontFamily = Manrope,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Medium
                                                ),
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            )
                                            Text(
                                                text = stringResource(id = R.string.terms_descirption),
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    fontFamily = Manrope,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Medium
                                                ),
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color3)
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            // Icono 1: Logo02 -> HomeActivity
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable {
                                        startActivity(
                                            Intent(
                                                this@TermsActivity,
                                                HomeActivity::class.java
                                            )
                                        )
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .border(2.dp, Color3, RoundedCornerShape(8.dp))
                                        .padding(4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.logo02),
                                        contentDescription = "Logo02",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Text(
                                    text = stringResource(id = R.string.found),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = Color4
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                            }
                            // Icono 2: Logo03 -> PaisesActivity
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable {
                                        startActivity(
                                            Intent(
                                                this@TermsActivity,
                                                CanvasActivity::class.java
                                            )
                                        )
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .border(2.dp, Color3, RoundedCornerShape(8.dp))
                                        .padding(4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.logo07),
                                        contentDescription = "Logo03",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Text(
                                    text = stringResource(id = R.string.statistics),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = Color4
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                            }
                            // Icono 2: Logo03 -> PaisesActivity
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable {
                                        startActivity(
                                            Intent(
                                                this@TermsActivity,
                                                MainActivity2::class.java
                                            )
                                        )
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .border(2.dp, Color3, RoundedCornerShape(8.dp))
                                        .padding(4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.logo01),
                                        contentDescription = "Logo03",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Text(
                                    text = stringResource(id = R.string.user),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = Color4
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                            }
                            // Icono 2: Logo03 -> Mapas
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable {
                                        startActivity(
                                            Intent(
                                                this@TermsActivity,
                                                MapasActivity::class.java
                                            )
                                        )
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .border(2.dp, Color3, RoundedCornerShape(8.dp))
                                        .padding(4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.logo05),
                                        contentDescription = "Logo05",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Text(
                                    text = stringResource(id = R.string.mapa),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = Color4
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                            }

                            // Icono 2: Logo03 -> PaisesActivity
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable {
                                        startActivity(
                                            Intent(
                                                this@TermsActivity,
                                                MembersActivity::class.java
                                            )
                                        )
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .border(2.dp, Color3, RoundedCornerShape(8.dp))
                                        .padding(4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.logo03),
                                        contentDescription = "Logo03",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Text(
                                    text = stringResource(id = R.string.adoption),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = Color4
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                            }

                            // Icono 2: Logo03 -> ParquesActivity
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable {
                                        startActivity(
                                            Intent(
                                                this@TermsActivity,
                                                ParquesActivity::class.java
                                            )
                                        )
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .border(2.dp, Color3, RoundedCornerShape(8.dp))
                                        .padding(4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.logo06),
                                        contentDescription = "Logo03",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Text(
                                    text = stringResource(id = R.string.parques),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = Color4
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                            }

                            // Icono 3: Logo04 -> MembersActivity
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable {
                                        startActivity(
                                            Intent(
                                                this@TermsActivity,
                                                PaisesActivity::class.java
                                            )
                                        )
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .border(2.dp, Color3, RoundedCornerShape(8.dp))
                                        .padding(4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.logo04),
                                        contentDescription = "Logo04",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Text(
                                    text = stringResource(id = R.string.main),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = Color4
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                    if (selectedPet != null) {
                        val imageId = imageIds[petNames.indexOf(selectedPet) % imageIds.size]
                        Dialog(onDismissRequest = { selectedPet = null }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable(onClick = { selectedPet = null })
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(550.dp)  // Cambiar tamaño para hacerlo más cuadrado
                                        .align(Alignment.Center)
                                        .background(Color2, RoundedCornerShape(16.dp))
                                        .padding(16.dp)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = imageId),
                                            contentDescription = "Imagen",
                                            modifier = Modifier
                                                .size(300.dp)  // Ajustar tamaño de la imagen para hacerlo más cuadrado
                                                .clip(RoundedCornerShape(16.dp))
                                                .border(2.dp, Color6, RoundedCornerShape(16.dp))
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = selectedPet ?: "",
                                            style = MaterialTheme.typography.headlineMedium.copy(
                                                fontFamily = Manrope,
                                                fontWeight = FontWeight.Medium,
                                                color = Color4
                                            ),
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = stringResource(id = R.string.terms_descirption),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontFamily = Manrope,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium
                                            ),
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(vertical = 8.dp)
                                        )
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Button(
                                                onClick = { selectedPet = null },
                                                colors = ButtonDefaults.buttonColors(containerColor = Color1),
                                                modifier = Modifier
                                                    .padding(4.dp)
                                                    .height(32.dp)
                                            ) {
                                                Text(
                                                    text = "Cerrar",
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        fontFamily = Manrope,
                                                        fontWeight = FontWeight.Medium,
                                                        fontSize = 14.sp
                                                    ),
                                                    color = Color4
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
    }
}
