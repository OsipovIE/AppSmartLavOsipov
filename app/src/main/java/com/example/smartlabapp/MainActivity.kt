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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.example.pr31.ui.components.OnboardDescription
import com.example.pr31.ui.components.OnboardHeader
import com.example.pr31.ui.components.TextButtonN

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
                        if (newQueue >= 3) {
                            currentQueue = 3
                        } else {
                            currentQueue = newQueue
                        }
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            TextButtonN(
                                modifier = Modifier,
                                text = "Пропустить",
                                onClick = { onQueueChange(3) } // Устанавливаем очередь на 3
                            )
                            Image(
                                ImageBitmap.imageResource(R.drawable.shape),
                                "imageshape",
                                modifier = Modifier.size(167.04.dp, 163.11.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(60.89.dp))
                        OnboardHeader(modifier = Modifier, text = "Анализы")
                        Spacer(modifier = Modifier.height(49.dp))
                        OnboardDescription(modifier = Modifier, text = "Экспресс сбор и получение проб")
                        Spacer(modifier = Modifier.height(60.dp))
                        Image(
                            ImageBitmap.imageResource(R.drawable.page1),
                            "imagepage",
                            modifier = Modifier.size(58.dp, 14.29.dp)
                        )
                        Spacer(modifier = Modifier.height(113.14.dp))
                        Image(
                            ImageBitmap.imageResource(R.drawable.colb),
                            "imagecolb",
                            modifier = Modifier.size(204.dp, 200.47.dp)
                        )
                    }
                }
                title = "Анализы",
                description = "Описание анализов",
                buttonLabel = "Пропустить",
                onButtonClick = { onQueueChange(3) } // Переход на последнюю очередь
            ),
            QueueContent(
                title = "Анализы",
                description = "Описание анализов",
                buttonLabel = "Пропустить",
                onButtonClick = { onQueueChange(3) } // Переход на последнюю очередь
            ),
            QueueContent(
                title = "Добро пожаловать",
                description = "",
                buttonLabel = "Завершить",
                onButtonClick = { onQueueChange(3) } // Переход на последнюю очередь
            ),
            QueueContent(
                title = "Спасибо за использование приложения!",
                description = "Вы завершили все очереди.",
                buttonLabel = "",
                onButtonClick = {}
            )
        )

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            if (currentQueue < queues.size) {
                val queue = queues[currentQueue]
                Header(queue.buttonLabel) { queue.onButtonClick() }
                Text(queue.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Text(queue.description, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Image(painter = painterResource(id = R.drawable.colb), contentDescription = "Colb Image")
            }
        }
    }

    @Composable
    fun Header(buttonLabel: String, onButtonClick: () -> Unit) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            if (buttonLabel.isNotEmpty()) { // Проверяем, есть ли текст для кнопки
                Button(onClick = onButtonClick) {
                    Text(buttonLabel, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
            Image(painter = painterResource(id = R.drawable.shape), contentDescription = "Shape Icon")
        }
    }

    data class QueueContent(
        val title: String,
        val description: String,
        val buttonLabel: String,
        val onButtonClick: () -> Unit
    )

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        SmartLabAppTheme {
            MainContent(currentQueue = 0) { /* No-op */ } // Начинаем с первой очереди
        }
    }
}
