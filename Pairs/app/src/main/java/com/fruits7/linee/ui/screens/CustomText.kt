package com.fruits7.linee.ui.screens

import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fruits7.linee.ui.theme.CustomFont
import com.fruits7.linee.ui.theme.TextColor

@Composable
fun CustomText(
    text: String,
    modifier: Modifier = Modifier,
    size: TextUnit = 16.sp,
    weight: FontWeight = FontWeight.Medium,
    shadowColor: Color = Color.DarkGray,
    textColor: Color = TextColor
) {
    Text(
        modifier = modifier
            .offset(x = 2.dp, y = 2.dp)
            .alpha(0.75f),
        text = text,
        fontSize = size,
        color = shadowColor,
        textAlign = TextAlign.Center,
        fontFamily = CustomFont,
        fontWeight = weight,
    )
    Text(
        modifier = modifier,
        text = text,
        fontSize = size,
        color = textColor,
        textAlign = TextAlign.Center,
        fontFamily = CustomFont,
        fontWeight = weight,
    )
}