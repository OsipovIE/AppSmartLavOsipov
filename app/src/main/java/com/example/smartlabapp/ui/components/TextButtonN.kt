package com.example.pr31.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.pr31.R
import com.example.pr31.ui.theme.ThreeColor

@Composable
fun TextButtonN(modifier: Modifier, text: String,onClick: ()-> Unit){
        TextButton(onClick = onClick) {
            Text(
                text = text,
                modifier = Modifier,
                textAlign = TextAlign.Left,
                color = ThreeColor,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                fontFamily = FontFamily(Font(R.font.lato_bold))
            )
        }
    }
