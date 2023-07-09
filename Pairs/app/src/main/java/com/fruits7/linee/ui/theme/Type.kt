package com.fruits7.linee.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fruits7.linee.R

val CustomFont = FontFamily(
    Font(R.font.nunito_semibold, FontWeight.W300),
    Font(R.font.nunito_semibold, FontWeight.W400),
    Font(R.font.nunito_semibold, FontWeight.W500),
)

val CustomFontTypography = Typography(
    h1 = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.W500,
        fontSize = 30.sp,
        color = TextColor
    ),
    h2 = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp,
        color = TextColor
    ),
    h3 = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp, color = TextColor
    ),
    h4 = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp, color = TextColor
    ),
    h5 = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp, color = TextColor
    ),
    h6 = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp, color = TextColor
    ),
    subtitle1 = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp, color = TextColor
    ),
    subtitle2 = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp, color = TextColor
    ),
    body1 = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp, color = TextColor
    ),
    body2 = TextStyle(
        fontFamily = CustomFont,
        fontSize = 14.sp, color = TextColor
    ),
    button = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        color = TextColor
    ),
    caption = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp, color = TextColor
    ),
    overline = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp, color = TextColor
    )
)