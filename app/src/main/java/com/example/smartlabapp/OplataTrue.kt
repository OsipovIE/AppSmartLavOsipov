package com.example.smartlabapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun OplataTrue(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }
    val rotationState = remember { Animatable(0f) }

    // Запускаем корутину для имитации процесса оплаты
    LaunchedEffect(Unit) {
        // Запускаем анимацию вращения
        rotationState.animateTo(
            targetValue = 1440f,
            animationSpec = tween(durationMillis = 8000)
        )

        isLoading = false // После задержки показываем сообщение об успешной оплате
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            // Экран загрузки
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Оплата",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Анимация загрузки
                Image(
                    painter = painterResource(id = R.drawable.loading), // Замените на ваш ресурс
                    contentDescription = "Loading",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 26.dp)
                        .graphicsLayer(rotationZ = rotationState.value) // Применяем вращение
                )

                Text(
                    text = "Производим оплату..",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        } else {
            // Экран успешной оплаты
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Оплата произошла успешно",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = { navController.navigate("WelcomeIn") },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "На главную")
                }
            }
        }
    }
}
