package com.example.pr31.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.pr31.R
import com.example.pr31.ui.theme.OneColor
import com.example.pr31.ui.theme.TwoColor

@Composable
fun OnboardHeader(modifier: Modifier, text: String){
    Text(
        text = text,
        color = OneColor,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        fontFamily = FontFamily(Font(R.font.lato_bold)),
        textAlign = TextAlign.Center
    )
}