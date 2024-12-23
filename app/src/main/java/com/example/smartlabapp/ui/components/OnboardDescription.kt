package com.example.pr31.ui.components

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.ResourceFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pr31.R
import com.example.pr31.ui.theme.OneColor
import com.example.pr31.ui.theme.TwoColor
import org.w3c.dom.Text

@Composable
fun OnboardDescription(modifier: Modifier, text: String){
    Text(
        text = text,
        textAlign = TextAlign.Center,
        color = TwoColor,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 20.sp,
        fontFamily = FontFamily(Font(R.font.lato_bold))
    )
}
