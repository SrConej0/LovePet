package com.davidchura.sistema1232

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidchura.sistema1232.ui.theme.Color4
import com.davidchura.sistema1232.ui.theme.Color5
import com.davidchura.sistema1232.ui.theme.Color6
import com.davidchura.sistema1232.ui.theme.Sistema1232Theme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Sistema1232Theme {
                var currentPage by remember { mutableIntStateOf(0) }
                var startX by remember { mutableStateOf(0f) }
                var isDragging by remember { mutableStateOf(false) }

                val images = listOf(R.drawable.fondo01, R.drawable.fondo02, R.drawable.fondo03)
                val texts = listOf(
                    Pair(R.string.home1, R.string.home10),
                    Pair(R.string.home2, R.string.home20),
                    Pair(R.string.home3, R.string.home30)
                )

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color6),
                    content = { paddingValues ->
                        Column(
                            modifier = Modifier
                                .padding(paddingValues)
                                .background(Color6)
                                .pointerInput(Unit) {
                                    detectHorizontalDragGestures(
                                        onDragStart = { offset ->
                                            startX = offset.x
                                            isDragging = true
                                        },
                                        onDragEnd = {
                                            isDragging = false
                                        },
                                        onHorizontalDrag = { change, dragAmount ->
                                            if (isDragging) {
                                                val endX = change.position.x
                                                val deltaX = endX - startX

                                                if (deltaX > 100 && currentPage > 0) {
                                                    // Deslizamiento hacia la derecha (retroceder)
                                                    currentPage--
                                                    isDragging = false
                                                } else if (deltaX < -100 && currentPage < images.size - 1) {
                                                    // Deslizamiento hacia la izquierda (avanzar)
                                                    currentPage++
                                                    isDragging = false
                                                }
                                            }
                                        }
                                    )
                                }
                        ) {
                            // Resto del código de tu UI permanece igual...
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(50.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = stringResource(R.string.skip),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color5
                                    ),
                                    modifier = Modifier.clickable {
                                        startActivity(Intent(this@HomeActivity, TermsActivity::class.java))
                                    }
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(Color6),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    AnimatedContent(
                                        targetState = currentPage,
                                        transitionSpec = {
                                            (slideInHorizontally { width -> width / 2 } + fadeIn()).togetherWith(
                                                slideOutHorizontally { width -> -width / 2 } + fadeOut())
                                        }, label = ""
                                    ) { targetPage ->
                                        Image(
                                            painter = painterResource(id = images[targetPage]),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(400.dp)
                                                .clip(CircleShape)
                                                .border(2.dp, Color6, CircleShape)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(50.dp))
                                    Text(
                                        text = stringResource(texts[currentPage].first),
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 28.sp,
                                        color = Color4,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                    Text(
                                        text = stringResource(texts[currentPage].second),
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontSize = 14.sp,
                                            color = Color4,
                                            textAlign = TextAlign.Center
                                        ),
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(horizontal = 62.dp, vertical = 22.dp)
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(bottom = 32.dp)
                                    ) {
                                        repeat(images.size) { index ->
                                            Box(
                                                modifier = Modifier
                                                    .size(10.dp)
                                                    .clip(CircleShape)
                                                    .background(if (index == currentPage) Color.Black else Color.Gray)
                                                    .padding(7.dp)
                                                    .offset(y = (-20).dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}