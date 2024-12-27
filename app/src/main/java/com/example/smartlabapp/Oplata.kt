package com.example.smartlabapp

import Product
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Oplata(navController: NavController, currentTotalPrice: Int) {
    // Состояния для хранения введенных данных
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("+7") } // Изначально устанавливаем +7
    var comment by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Заголовок
            Box(
                modifier = Modifier
                    .width(329.dp)
                    .height(100.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { navController.popBackStack() }
                )
                Text(
                    text = "Оформление заказа",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
            Text(text = "Адрес(Обязательно для ввода)", color = Color.Red, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            // Поле для ввода адреса (ввод на любом языке)
            Box(
                modifier = Modifier
                    .width(329.dp)
                    .height(72.dp)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.TopStart
            ) {
                TextField(
                    value = address,
                    onValueChange = { newValue ->
                        // Убираем фильтрацию, разрешаем ввод любых символов
                        address = newValue
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Введите ваш адрес") }
                )
            }
            Text(text = "Телефон(Обязательно для ввода)", color = Color.Red, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            // Поле для ввода телефона (изначально +7, потом только цифры)
            Box(
                modifier = Modifier
                    .width(329.dp)
                    .height(72.dp)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.TopStart
            ) {
                TextField(
                    value = phone,
                    onValueChange = { newValue ->
                        // Не позволяем удалить +7 и ограничиваем ввод до 12 цифр после +7
                        if (newValue.length <= 12 && (newValue.isEmpty() || newValue.startsWith("+7"))) {
                            if (newValue.length == 1 && newValue == "+") {
                                phone = "+7"
                            } else if (newValue.length > 2 && newValue.startsWith("+7")) {
                                phone = "+7" + newValue.substring(2).filter { it.isDigit() }
                            } else if (newValue == "+7") {
                                phone = "+7"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("+7___-___-__-__") }
                )
            }

            // Поле для комментария
            Box(
                modifier = Modifier
                    .width(329.dp)
                    .height(152.dp)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Text(text = "Комментарий (необязательно)", fontWeight = FontWeight.Bold)
                TextField(
                    value = comment,
                    onValueChange = { comment = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(128.dp),
                    placeholder = { Text("Введите комментарий") }
                )
            }

            // Отображение сообщения об ошибке
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Сумма и кнопка заказа
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Сумма анализов:", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.W600)
                Text(text = "$currentTotalPrice ₽", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.W600)
            }

            Button(
                onClick = {
                    // Валидация обязательных полей
                    if (address.isBlank() || phone.isBlank() || phone.length < 12) {
                        errorMessage = "Пожалуйста, заполните обязательные поля корректно."
                    } else {
                        // Если все поля заполнены, переходим на экран OplataTrue
                        navController.navigate("OplataTrue")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(text = "Заказать", color = Color.White)
            }
        }
    }
}
