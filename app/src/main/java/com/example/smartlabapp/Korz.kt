package com.example.smartlabapp

import Product
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.reflect.TypeToken
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartlabapp.ui.theme.Blueall
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Korz(navController: NavController, cartStateString: String, totalPrice: Int, welcomeInProducts: List<Product>) {
    // Декодируем строку состояния корзины в Map
    val cartStateType = object : TypeToken<Map<String, Int>>() {}.type
    val cartState: Map<String, Int> = Gson().fromJson(cartStateString, cartStateType)

    // Получаем статические продукты
    val staticProducts = getStaticProductsKorz()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Корзина",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (cartState.isEmpty()) {
            Text(text = "Корзина пуста", color = Color.Gray)
        } else {
            // Выводим список продуктов в корзине
            for ((id, count) in cartState) {
                // Находим продукт по ID из статических продуктов
                val product = staticProducts.firstOrNull { it.id == id }
                if (product != null) {
                    Text(text = "${product.name} x $count", fontSize = 16.sp)
                } else {
                    Text(text = "Продукт с ID $id не найден", color = Color.Red)
                }
            }
        }



        Text(text = "Итого: $totalPrice ₽", fontSize = 20.sp, color = Blueall)

        Button(
            onClick = { /* Действие при нажатии на кнопку для оформления заказа */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Blueall)
        ) {
            Text(text = "Оформить заказ", color = Color.White)
        }
    }
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
