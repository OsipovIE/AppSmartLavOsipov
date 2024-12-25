package com.example.smartlabapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartlabapp.R
import com.example.smartlabapp.ui.theme.BlueButton

@Composable
fun CustomTextButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = BlueButton,
    border: BorderStroke? = null
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
            .border(border ?: BorderStroke(0.dp, Color.Transparent)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = contentColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            fontFamily = FontFamily(Font(R.font.nunito_bold))
        )
    }
}
