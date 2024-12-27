package com.example.smartlabapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartlabapp.ui.theme.Blueall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutorizationReg(navController: NavController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }

    // Проверка на корректность email
    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        val validDomains = listOf("@gmail.com", "@yahoo.com", "@mail.ru", "@yandex.ru", "@example.com")
        return emailRegex.matches(email) && validDomains.any { email.endsWith(it, ignoreCase = true) }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            // Верхняя часть с заголовком
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(top = 70.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hello),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "Добро пожаловать",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W900,
                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(26.dp))
                Text(
                    text = "Зарегистрируйтесь, чтобы пользоваться   \nфункциями приложения",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily(Font(R.font.nunito_medium)),
                    color = Color.Black
                )
            }

            // Поля ввода email и пароля
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(top = 262.dp)
            ) {
                Text(
                    text = "E-mail",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily(Font(R.font.nunito_medium)),
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !isEmailValid(it.text)
                    },
                    placeholder = { Text(text = "example@mail.ru", fontSize = 14.sp) },
                    modifier = Modifier
                        .height(50.dp)
                        .width(335.dp)
                        .background(if (emailError) Color.Red.copy(alpha = 0.2f) else Color.Transparent),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                // Сообщение об ошибке
                if (emailError) {
                    Text(
                        text = "Некорректный email",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily(Font(R.font.nunito_medium)),
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("*****") },
                    modifier = Modifier.height(50.dp)
                        .width(335.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            val icon =
                                if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                            Image(painter = painterResource(id = icon), contentDescription = null)
                        }
                    },
                    shape = RoundedCornerShape(10.dp)
                )
            }

            // Кнопка "Далее" и текст "Уже есть аккаунт?"
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 452.dp)

            ) {
                val isButtonEnabled =
                    email.text.isNotEmpty() && password.text.isNotEmpty() && !emailError

                Button(
                    onClick = { navController.navigate("SavePwd") },
                    enabled = isButtonEnabled,
                    modifier = Modifier
                        .width(335.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isButtonEnabled) Blueall else Color.Gray // Устанавливаем цвет кнопки
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Далее", color = Color.White)
                }


                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Уже есть аккаунт? ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily(Font(R.font.nunito_medium)),
                        color = Color.Gray
                    )
                    Text(
                        text = "Войти",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_medium)),
                        fontWeight = FontWeight.W600,
                        color = Blueall,
                        modifier = Modifier
                            .clickable { navController.navigate("Autorization") }
                    )
                }
            }
        }
    }
}
@Preview
@Composable
fun PreviewAuthorizationReg() {
    AutorizationReg(navController = rememberNavController())
}
