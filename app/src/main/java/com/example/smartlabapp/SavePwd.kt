package com.example.smartlabapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import androidx.navigation.compose.rememberNavController
import com.example.smartlabapp.ui.theme.Blueall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavePwd(navController: NavController) {
    var imagesState by remember { mutableStateOf(List(4) { R.drawable.nullpwd }) } // Состояние для изображений
    var password by remember { mutableStateOf("") } // Состояние для пароля

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextButton(
            onClick = { navController.navigate("WelcomeIn") },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Пропустить",
                color = Blueall,
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                fontWeight = FontWeight.W400
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Заголовок "Создайте пароль"
        Text(
            text = "Создайте пароль",
            color = Color.Black,
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.nunito_bold)),
            fontWeight = FontWeight.W700,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Подзаголовок "Для защиты ваших персональных данных"
        Text(
            text = "Для защиты ваших персональных данных",
            color = Color.Gray,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.nunito_bold)),
            fontWeight = FontWeight.W400,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Добавляем изображения
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            imagesState.forEach { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))



        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White, RoundedCornerShape(10.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Первая строка кнопок
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    NumberButton("1", navController, { imagesState = updateImages(imagesState, navController, password) })
                    NumberButton("2", navController, { imagesState = updateImages(imagesState, navController, password) })
                    NumberButton("3", navController, { imagesState = updateImages(imagesState, navController, password) })
                }

                // Вторая строка кнопок
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    NumberButton("4", navController, { imagesState = updateImages(imagesState, navController, password) })
                    NumberButton("5", navController, { imagesState = updateImages(imagesState, navController, password) })
                    NumberButton("6", navController, { imagesState = updateImages(imagesState, navController, password) })
                }

                // Третья строка кнопок
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    NumberButton("7", navController, { imagesState = updateImages(imagesState, navController, password) })
                    NumberButton("8", navController, { imagesState = updateImages(imagesState, navController, password) })
                    NumberButton("9", navController, { imagesState = updateImages(imagesState, navController, password) })
                }

                // Четвертая строка с кнопкой "0" и изображением "clearbut.png"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NumberButton("0", navController, { imagesState = updateImages(imagesState, navController, password) })
                    Spacer(modifier = Modifier.width(46.dp))
                    Image(
                        painter = painterResource(id = R.drawable.clearbut), // Укажите ваш ресурс
                        contentDescription = "Clear Button",
                        modifier = Modifier.size(24.dp)
                            .clickable { /* Действие для очистки */ }
                    )
                }
            }
        }
    }
}

@Composable
fun NumberButton(number: String, navController: NavController, onPress: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(80.dp)
            .background(if (isPressed) Color.Blue else Color.LightGray, RoundedCornerShape(40.dp))
            .clickable {
                isPressed = true
                onPress() // Вызываем функцию при нажатии
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = number, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        if (isPressed) {
            LaunchedEffect(Unit) {
                delay(500)
                isPressed = false
            }
        }
    }
}

// Функция для обновления состояния изображений
fun updateImages(currentImages: List<Int>, navController: NavController, password: String): List<Int> {
    val updatedImages = currentImages.toMutableList()
    for (i in updatedImages.indices) {
        if (updatedImages[i] == R.drawable.nullpwd) {
            updatedImages[i] = R.drawable.pwd // Меняем на pwd.png
            // Проверяем, заменены ли все изображения
            if (updatedImages.all { it == R.drawable.pwd }) {
                // Если все изображения заменены, сохраняем пароль и переходим на экран WelcomeIn
                // Здесь можно сохранить пароль во временной памяти, например, в ViewModel или другой структуре
                // Для примера, просто переходим на экран
                navController.navigate("WelcomeIn")
            }
            break // Меняем только первое изображение
        }
    }
    return updatedImages
}

@Preview
@Composable
fun PreviewSavePwd() {
    SavePwd(navController = rememberNavController())
}
