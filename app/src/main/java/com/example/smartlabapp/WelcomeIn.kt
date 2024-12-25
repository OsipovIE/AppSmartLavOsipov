package com.example.smartlabapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import com.example.smartlabapp.ui.theme.GreyDiscr

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeIn(navController: NavController) {
    var search by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(top = 48.dp)
            ) {
                TextField(
                    value = search,
                    onValueChange = {
                        search = it
                    },
                    placeholder = {
                        Text(
                            text = "Искать анализы",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_medium)),
                            fontWeight = FontWeight.W400,
                            color = GreyDiscr
                        )
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .width(350.dp),
                    leadingIcon = {
                        // Иконка поиска
                        Image(
                            painter = painterResource(id = R.drawable.search_ic), // Замените на ваш ресурс
                            contentDescription = "Search Icon",
                            modifier = Modifier.size(20.dp) // Размер иконки
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(top = 148.dp)
                .padding(start = 20.dp)
        ) {
            Text(
                text = "Акции и новости",
                color = GreyDiscr,
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                fontWeight = FontWeight.W600,
                fontSize = 17.sp,
                lineHeight = 24.sp
            )
            Box(
                modifier = Modifier
                    .width(355.dp)
                    .height(152.dp)
                    .padding(start = 20.dp)
                    .padding(top = 188.dp),
                contentAlignment = Alignment.BottomStart,
            ) {}
        }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(top = 372.dp)
                .padding(start = 20.dp)
        ) {
            Text(
                text = "Каталог анализов",
                color = GreyDiscr,
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                fontWeight = FontWeight.W600,
                fontSize = 17.sp,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("WelcomeIn") },
                modifier = Modifier
                    .width(133.dp)
                    .height(50.dp)) {
                Text(text = "Популярные")
            }
            Box(
                modifier = Modifier
                    .width(355.dp)
                    .height(88.dp)
                    .padding(start = 20.dp)
                    .padding(top = 88.dp),
                contentAlignment = Alignment.BottomStart,
            ) {

            }

        }
    }
}

@Preview
@Composable
fun PreviewAuthorization2() {
    WelcomeIn(navController = rememberNavController())
}
