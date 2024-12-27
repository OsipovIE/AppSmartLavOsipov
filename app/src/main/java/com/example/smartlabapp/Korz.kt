package com.example.smartlabapp

import Product
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import com.google.gson.reflect.TypeToken
import androidx.navigation.NavController
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Korz(navController: NavController, cartStateString: String, totalPrice: Int, welcomeInProducts: List<Product>) {
    // Декодируем строку состояния корзины в Map
    val cartStateType = object : TypeToken<MutableMap<String, Int>>() {}.type
    var cartState: MutableMap<String, Int> = Gson().fromJson(cartStateString, cartStateType) ?: mutableMapOf()

    // Получаем статические продукты
    val staticProducts = getStaticProductsKorz()

    // Состояние для хранения общей суммы
    var currentTotalPrice by remember { mutableStateOf(totalPrice) }

    // Функция для обновления суммы
    fun updateTotalPrice() {
        currentTotalPrice = calculateTotalPrice(cartState, staticProducts)
    }

    // Инициализация общей суммы при первом запуске
    LaunchedEffect(cartState) {
        updateTotalPrice()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Верхний бокс с кнопками и заголовком
            Box(
                modifier = Modifier
                    .width(329.dp)
                    .height(100.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back), // Убедитесь, что у вас есть изображение back.png
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { navController.popBackStack() }
                )
                Text(
                    text = "Корзина",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
                Image(
                    painter = painterResource(id = R.drawable.korz), // Убедитесь, что у вас есть изображение korz.png
                    contentDescription = "Clear Cart",
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.BottomEnd)
                        .clickable {
                            cartState.clear()
                            updateTotalPrice()
                        }
                )
            }

            // Скроллируемая область для продуктов
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .weight(1f), // Занимает оставшееся пространство
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for ((id, count) in cartState) {
                    val product = staticProducts.firstOrNull { it.id == id }
                    if (product != null) {
                        ProductBox(product = product, count = count)
                    }
                }
            }

            // Отображение общей суммы
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Сумма", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.W600)
                Text(text = "$currentTotalPrice ₽", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.W600)
            }

            // Кнопка для перехода к оформлению заказа
            Button(
                onClick = { /* Действие при нажатии на кнопку для оформления заказа */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(text = "Перейти к оформлению заказа", color = Color.White)
            }
        }
    }
}

@Composable
fun ProductBox(product: Product, count: Int) {
    Box(
        modifier = Modifier
            .width(332.dp)
            .height(138.dp)
            .padding(8.dp)
            .border(1.dp, Color.Gray)
            .padding(16.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = product.name, fontSize = 16.sp, color = Color.Black)
            Text(text = "Цена: ${product.price} ₽", fontSize = 16.sp, color = Color.Black)
            Text(text = "Количество: $count", fontSize = 16.sp, color = Color.Black)
        }
    }
}

fun calculateTotalPrice(cartState: Map<String, Int>, staticProducts: List<Product>): Int {
    var total = 0
    for ((id, count) in cartState) {
        val product = staticProducts.firstOrNull { it.id == id }
        if (product != null) {
            total += product.price.toInt() * count
        }
    }
    return total
}

fun getStaticProductsKorz(): List<Product> {
    return listOf(
        Product(id = "1", name = "ПЦР-тест на определение РНК коронавируса стандартный", price = "1800"),
        Product(id = "2", name ="Клинический анализ крови с лейкоцитарной формулировкой", price = "690"),
        Product(id = "3", name ="Биохимический анализ крови, базовый", price = "2440"),
        Product(id = "4", name = "СОЭ (венозная кровь)", price = "240"),
        Product(id = "5", name = "Общий анализ мочи", price = "350"),
        Product(id = "6", name = "Тироксин свободный (Т4 свободный)", price = "680"),
        Product(id = "7", name = "Группа крови + Резус-фактор", price = "750"),
        Product(id = "8", name = "ПЦР-тест на определение РНК коронавируса стандартный", price = "1800"),
        Product(id = "9", name = "Клинический анализ крови с лейкоцитарной формулировкой", price = "690"),
        Product(id = "10", name = "Биохимический анализ крови, базовый", price = "2440"),
        Product(id = "11", name = "СОЭ (венозная кровь)", price = "240"),
        Product(id = "12", name = "Общий анализ мочи", price = "350"),
        Product(id = "13", name = "Тироксин свободный (Т4 свободный)", price = "680"),
        Product(id = "14", name = "Группа крови + Резус-фактор", price = "750"),
        Product(id = "15", name = "СОЭ (капиллярная кровь)", price = "400"),
        Product(id = "16", name = "Исследования кала на скрытую кровь", price = "400"),
        Product(id = "17", name = "Инфекции, передающиеся половым путем (кровь)", price = "800")
    )
}
