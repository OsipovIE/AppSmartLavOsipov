package com.example.smartlabapp

import com.google.gson.Gson
import Action
import Category
import Product
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartlabapp.ui.theme.Blueall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeIn(navController: NavController) {
    var search by remember { mutableStateOf(TextFieldValue("")) }
    var actions by remember { mutableStateOf(getStaticActions()) }
    var products by remember { mutableStateOf(getStaticProducts()) }
    var errorMessage by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    var isHidden by remember { mutableStateOf(false) }

    // Хранение состояния для добавления продуктов
    val cartState = remember { mutableStateMapOf<String, Int>() }
    var totalPrice by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, dragAmount ->
                    isHidden = dragAmount < 0
                }
            }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = search,
                onValueChange = { search = it },
                placeholder = {
                    Text(
                        text = "Искать анализы",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_medium)),
                        fontWeight = FontWeight.W400,
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .height(68.dp)
                    .width(350.dp)
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp),
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.search_ic),
                        contentDescription = "Search Icon",
                        modifier = Modifier.size(20.dp)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (!isHidden) {
                item {
                    Text(
                        text = "Акции и новости",
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                        fontWeight = FontWeight.W600,
                        fontSize = 17.sp,
                        lineHeight = 24.sp,
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp)
                    )
                }

                item {
                    LazyRow(
                        modifier = Modifier
                            .height(152.dp)
                            .padding(start = 20.dp, top = 16.dp)
                    ) {
                        items(actions) { action ->
                            Box(
                                modifier = Modifier
                                    .width(260.dp)
                                    .height(152.dp)
                                    .padding(end = 16.dp)
                                    .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                            ) {
                                Image(
                                    painter = painterResource(id = action.imageResId),
                                    contentDescription = action.title,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Каталог анализов",
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                        fontWeight = FontWeight.W600,
                        fontSize = 17.sp,
                        lineHeight = 24.sp,
                        modifier = Modifier.padding(start = 20.dp, top = 32.dp)
                    )
                }
            }

            item {
                LazyRow(
                    modifier = Modifier.padding(start = 20.dp, top = 16.dp)
                ) {
                    items(getStaticCategories()) { category ->
                        Button(
                            onClick = {  },
                            modifier = Modifier
                                .wrapContentWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (category.name == "Популярные") Blueall else Color.Gray
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = category.name,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }

            items(products) { product ->
                val isAdded = cartState[product.id] ?: 0
                val buttonText = if (isAdded > 0) "Убрать" else "Добавить"
                val buttonColor = if (isAdded > 0) Blueall else Color.White
                val buttonBackgroundColor = if (isAdded > 0) Color.White else Blueall

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 24.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(text = product.name, fontWeight = FontWeight.Bold)
                        Text(text = "Цена: ${product.price}")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    if (isAdded > 0) {
                                        // Убираем продукт из корзины
                                        cartState[product.id] = isAdded - 1
                                    } else {
                                        // Добавляем продукт в корзину
                                        cartState[product.id] = isAdded + 1
                                    }
                                    // Обновляем общую сумму
                                    totalPrice = cartState.entries.sumOf { (id, count) ->
                                        if (count > 0) {
                                            products.first { it.id == id }.price.toInt() * count
                                        } else 0
                                    }
                                },
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonBackgroundColor
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = buttonText,
                                    color = buttonColor
                                )
                            }
                        }
                    }
                }
            }

            if (errorMessage.isNotEmpty()) {
                item {
                    Text(
                        text = "Ошибка: $errorMessage",
                        color = Color.Red,
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp)
                    )
                }
            }
        }

        // Бокс для корзины
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .padding(16.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            val gson = Gson()
            val cartStateJson = gson.toJson(cartState)

            Button(
                onClick = {
                    navController.navigate("Korz/$cartStateJson/$totalPrice")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Blueall)
            )  {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_cart), // Укажите иконку для корзины
                            contentDescription = "Корзина",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "В корзину", color = Color.White)
                    }
                    Text(text = "$totalPrice ₽", color = Color.White)
                }
            }
        }
    }
}

fun getStaticActions(): List<Action> {
    return listOf(
        Action("1", R.drawable.banner2, "Первый баннер"),
        Action("2", R.drawable.banner2, "Второй баннер")
    )
}

fun getStaticProducts(): List<Product> {
    return listOf(
        Product(id = "1", name = "ПЦР-тест на определение РНК коронавируса стандартный", "1800"),
        Product(id = "2", name ="Клинический анализ крови с лейкоцитарной формулировкой", "690"),
        Product(id = "3", name ="Биохимический анализ крови, базовый", "2440"),
        Product(id = "4",name = "СОЭ (венозная кровь)", "240"),
        Product(id ="5", name ="Общий анализ мочи", "350"),
        Product(id ="6", name ="Тироксин свободный (Т4 свободный)", "680"),
        Product(id ="7",name = "Группа крови + Резус-фактор", "750"),
        Product(id ="8",name = "ПЦР-тест на определение РНК коронавируса стандартный", "1800"),
        Product(id ="9",name = "Клинический анализ крови с лейкоцитарной формулировкой", "690"),
        Product(id ="10",name = "Биохимический анализ крови, базовый", "2440"),
        Product(id ="11", name ="СОЭ (венозная кровь)", "240"),
        Product(id ="12",name = "Общий анализ мочи", "350"),
        Product(id ="13",name = "Тироксин свободный (Т4 свободный)", "680"),
        Product(id ="14",name = "Группа крови + Резус-фактор", "750"),
        Product(id ="15",name = "СОЭ (капиллярная кровь)", "400"),
        Product(id ="16",name = "Исследования кала на скрытую кровь", "400"),
        Product(id ="17", name ="Инфекции, передающиеся половым путем (кровь)", "800")
    )
}

fun getStaticCategories(): List<Category> {
    return listOf(
        Category("1", "Популярные"),
        Category("2", "Covid"),
        Category("3", "Комплексные"),
        Category("4", "Чекапы"),
        Category("5", "Биохимия"),
        Category("6", "Гормоны"),
        Category("7", "Иммунитет"),
        Category("8", "Витамины"),
        Category("9", "Аллергены"),
        Category("10", "Анализ крови"),
        Category("11", "Анализ мочи"),
        Category("12", "Анализ кала"),
        Category("13", "Только в клинике")
    )
}

@Preview
@Composable
fun PreviewWelcome() {
    WelcomeIn(navController = rememberNavController())
}
