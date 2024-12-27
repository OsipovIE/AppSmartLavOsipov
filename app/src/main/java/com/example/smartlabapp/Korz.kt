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
import com.example.smartlabapp.ui.theme.Blueall
import com.google.gson.Gson

@Composable
fun Korz(navController: NavController, cartStateString: String, totalPrice: Int, welcomeInProducts: List<Product>) {
    val cartStateType = object : TypeToken<MutableMap<String, Int>>() {}.type
    var cartState: MutableMap<String, Int> = Gson().fromJson(cartStateString, cartStateType) ?: mutableMapOf()

    val staticProducts = getStaticProductsKorz()

    var currentTotalPrice by remember { mutableStateOf(totalPrice) }

    val visibleProducts = remember { mutableStateMapOf<String, Boolean>() }

    fun updateTotalPrice() {
        currentTotalPrice = calculateTotalPrice(cartState, staticProducts)
    }

    LaunchedEffect(cartState) {
        cartState.keys.forEach { id ->
            visibleProducts[id] = true
        }
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
                    text = "Корзина",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
                Image(
                    painter = painterResource(id = R.drawable.korz),
                    contentDescription = "Clear Cart",
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.BottomEnd)
                        .clickable {
                            cartState.clear()
                            currentTotalPrice = 0 // Сбрасываем сумму при очистке корзины
                        }
                )
            }

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for ((id, count) in cartState) {
                    if (visibleProducts[id] == true) {
                        val product = staticProducts.first { it.id == id }
                        ProductBox(
                            product = product,
                            count = count,
                            onDelete = {
                                cartState.remove(id)
                                visibleProducts[id] = false
                                currentTotalPrice -= (product.price.toInt() * count) // Вычитаем стоимость удаляемого товара
                                if (cartState.isEmpty()) {
                                    currentTotalPrice = 0 // Сбрасываем сумму, если корзина пуста
                                }
                            },
                            cartStateString = cartStateString,
                            welcomeInProducts = welcomeInProducts
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Сумма", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.W600)
                Text(text = "$currentTotalPrice ₽", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.W600)
            }

            Button(
                onClick = { navController.navigate("Oplata/$currentTotalPrice") },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blueall)
            ) {
                Text(text = "Перейти к оформлению заказа", color = Color.White)
            }

        }
    }
}

@Composable
fun ProductBox(
    product: Product,
    count: Int,
    onDelete: () -> Unit,
    cartStateString: String,
    welcomeInProducts: List<Product>
) {
    val cartStateType = object : TypeToken<MutableMap<String, Int>>() {}.type
    var cartState: MutableMap<String, Int> = Gson().fromJson(cartStateString, cartStateType) ?: mutableMapOf()
    var showDeleteConfirmation by remember { mutableStateOf(false) }

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
        Image(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "Delete",
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    showDeleteConfirmation = true
                }
        )
    }

    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Подтверждение удаления") },
            text = { Text("Вы уверены, что хотите удалить этот продукт из корзины?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete() // Вызываем функцию удаления
                    showDeleteConfirmation = false // Закрываем диалог
                }) {
                    Text("Да")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text("Нет")
                }
            }
        )
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
