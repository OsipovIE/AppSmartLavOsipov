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
import com.example.smartlabapp.ui.theme.SmartLabAppTheme
import com.google.accompanist.pager.*
import android.content.SharedPreferences

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var currentQueue by mutableStateOf(0) // Начинаем с первой очереди

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        currentQueue = sharedPreferences.getInt("current_queue", 0) // Загружаем сохраненное состояние

        setContent {
            SmartLabAppTheme {
                // Устанавливаем состояние для пейджера
                val pagerState = rememberPagerState(initialPage = currentQueue)

                HorizontalPager(
                    count = 4, // Количество экранов
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    userScrollEnabled = currentQueue < 3 // Запретить свайп, если на последней очереди
                ) { page ->
                    MainContent(page) { newQueue ->
                        currentQueue = if (newQueue >= 3) 3 else newQueue
                    }
                }

                // Следим за изменениями страницы
                LaunchedEffect(pagerState.currentPage) {
                    currentQueue = pagerState.currentPage
                }

                // Следим за изменением currentQueue и прокручиваем к последней странице
                LaunchedEffect(currentQueue) {
                    if (currentQueue == 3) {
                        pagerState.scrollToPage(3) // Переход на последний экран
                    }
                    // Сохраняем состояние в SharedPreferences
                    sharedPreferences.edit().putInt("current_queue", currentQueue).apply()
                }
            }
        }
    }

    @Composable
    fun MainContent(currentQueue: Int, onQueueChange: (Int) -> Unit) {
        val queues = listOf(
            QueueContent(
                title = "Анализы",
                description = "Экспресс сбор и получение проб",
                buttonLabel = "Пропустить",
                onButtonClick = { onQueueChange(3) }, // Переход на последнюю очередь
                shapeImage = R.drawable.shape,
                pageImage = R.drawable.page1,
                colbImage = R.drawable.colb
            ),
            QueueContent(
                title = "Анализы",
                description = "Описание анализов",
                buttonLabel = "Пропустить",
                onButtonClick = { onQueueChange(3) }, // Переход на последнюю очередь
                shapeImage = R.drawable.shape,
                pageImage = R.drawable.page2,
                colbImage = R.drawable.doctor2
            ),
            QueueContent(
                title = "Добро пожаловать",
                description = "",
                buttonLabel = "Завершить",
                onButtonClick = { onQueueChange(3) }, // Переход на последнюю очередь
                shapeImage = R.drawable.shape,
                pageImage = R.drawable.page3,
                colbImage = R.drawable.dooctor3
            ),
            QueueContent(
                title = "Регистрация",
                description = "Введите Свое имя",
                buttonLabel = "",
                onButtonClick = {},
                shapeImage = R.drawable.shape,
                pageImage = R.drawable.page1,
                colbImage = R.drawable.colb
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
                colbImage = queue.colbImage
            )
        }
    }

    @Composable
    fun QueueContentScreen(
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
                    TextButton(
                        onClick = onButtonClick,
                        modifier = Modifier
                    ) {
                        Text(buttonLabel)
                    }
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
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(49.dp))
                Text(
                    text = description,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(60.dp))
                Image(
                    painter = painterResource(id = pageImage),
                    contentDescription = "Page Image",
                    modifier = Modifier.size(58.dp, 14.29.dp)
                )
                Spacer(modifier = Modifier.height(113.14.dp))
                Image(
                    painter = painterResource(id = colbImage),
                    contentDescription = "Colb Image",
                    modifier = Modifier.size(204.dp, 200.47.dp)
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
        val colbImage: Int
    )

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        SmartLabAppTheme {
            MainContent(currentQueue = 0) { /* No-op */ } // Начинаем с первой очереди
        }
    }
}
