package com.example.smartlabapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartlabapp.ui.components.CustomTextButton
import com.example.smartlabapp.ui.theme.SmartLabAppTheme
import android.content.SharedPreferences
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.smartlabapp.ui.theme.GreenTitle
import com.example.smartlabapp.ui.theme.GreyDiscr
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var currentQueue by mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        setContent{
            Navigation()
        }
        // Проверка, было ли приложение открыто ранее
        currentQueue = sharedPreferences.getInt("current_queue", 0)

        // Если пользователь уже прошел свайпы, сразу переходим на экран авторизации
        if (sharedPreferences.getBoolean("is_switch", false)) {
            setContent {
                SmartLabAppTheme {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "authorization") {
                        composable("authorization") { Autorization(navController) }
                        composable("WelcomeIn") { WelcomeIn(navController) }
                        composable("Autorization") { Autorization(navController) }
                        composable("AutorizationReg") { AutorizationReg(navController) }
                    }
                }
            }
        } else {
            setContent {
                SmartLabAppTheme {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "main") {
                        composable("main") { MainScreen(navController) }
                        composable("authorization") { Autorization(navController) }
                        composable("WelcomeIn") { WelcomeIn(navController) }
                    }
                }
            }
        }
    }
    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "main") {
            composable("main") { MainScreen(navController) }
            composable("Autorization") { Autorization(navController) }
            composable("WelcomeIn") { WelcomeIn(navController) }
        }
    }
    @Composable
    fun MainScreen(navController: NavController) {
        val pagerState = rememberPagerState(initialPage = currentQueue)

        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = true // включаем возможность пролистывания
        ) { page ->
            MainContent(page, navController) { newQueue ->
                if (newQueue > currentQueue) {
                    currentQueue = newQueue
                    sharedPreferences.edit().putInt("current_queue", currentQueue).apply()
                    if (newQueue == 3) {
                        println("Переход на экран авторизации")
                        sharedPreferences.edit().putBoolean("is_switch", true).apply()
                        navController.navigate("authorization") // Переход на авторизацию
                    }

                }
            }
        }

        LaunchedEffect(pagerState.currentPage) {
            currentQueue = pagerState.currentPage
        }
    }

    @Composable
    fun MainContent(currentQueue: Int, navController: NavController, onQueueChange: (Int) -> Unit) {
        val queues = listOf(
            QueueContent(
                title = "Анализы",
                description = "Экспресс сбор и получение проб",
                buttonLabel = "Пропустить",
                onButtonClick = { onQueueChange(3) },
                shapeImage = R.drawable.shape,
                pageImage = R.drawable.page1,
                colbImage = R.drawable.colb,
                modifierSize = Modifier.size(204.dp, 200.47.dp)
            ),
            QueueContent(
                title = "Уведомления",
                description = "Вы быстро узнаете о результатах",
                buttonLabel = "Пропустить",
                onButtonClick = { onQueueChange(3) },
                shapeImage = R.drawable.shape,
                pageImage = R.drawable.page2,
                colbImage = R.drawable.doctor2,
                modifierSize = Modifier.size(366.dp, 217.dp)
            ),
            QueueContent(
                title = "Мониторинг",
                description = "Наши врачи всегда наблюдают \n" +
                        "за вашими показателями здоровья",
                buttonLabel = "Завершить",
                onButtonClick = { onQueueChange(3) },
                shapeImage = R.drawable.shape,
                pageImage = R.drawable.page3,
                colbImage = R.drawable.dooctor3,
                modifierSize = Modifier.size(359.dp, 269.dp)
            )

        )

        if (currentQueue < queues.size) {
            val queue = queues[currentQueue]
            QueueContentScreen(
                title = queue.title,
                description = queue.description,
                buttonLabel = queue.buttonLabel,
                onButtonClick = queue.onButtonClick,
                shapeImage = queue.shapeImage,
                pageImage = queue.pageImage,
                colbImage = queue.colbImage,
                modifierSize = queue.modifierSize
            )
        }
    }

    @Composable
    fun QueueContentScreen(
        modifierSize: Modifier,
        title: String,
        description: String,
        buttonLabel: String,
        onButtonClick: () -> Unit,
        shapeImage: Int,
        pageImage: Int,
        colbImage: Int
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomTextButton(
                        label = buttonLabel,
                        onClick = onButtonClick,
                        modifier = Modifier
                    )
                    Image(
                        painter = painterResource(id = shapeImage),
                        contentDescription = "Shape Icon",
                        modifier = Modifier.size(167.04.dp, 163.11.dp)
                    )
                }
                Spacer(modifier = Modifier.height(60.89.dp))
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                    color = GreenTitle
                )
                Spacer(modifier = Modifier.height(26.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                    color = GreyDiscr
                )
                Spacer(modifier = Modifier.height(60.dp))
                Image(
                    painter = painterResource(id = pageImage),
                    contentDescription = "Page Image",
                    modifier = Modifier.size(58.dp, 14.29.dp)
                )
                Spacer(modifier = Modifier.height(106.71.dp))
                Image(
                    painter = painterResource(id = colbImage),
                    contentDescription = "Colb Image",
                    modifier = modifierSize
                )
            }
        }
    }

    data class QueueContent(
        val title: String,
        val description: String,
        val buttonLabel: String,
        val onButtonClick: () -> Unit,
        val shapeImage: Int,
        val pageImage: Int,
        val colbImage: Int,
        val modifierSize: Modifier
    )

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        SmartLabAppTheme {
            MainContent(currentQueue = 0, navController = rememberNavController()) { /* No-op */ }
        }
    }
}
