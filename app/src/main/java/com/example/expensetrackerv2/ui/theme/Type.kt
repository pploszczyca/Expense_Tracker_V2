package com.example.expensetrackerv2.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.expensetrackerv2.R

// Set of Material typography styles to start with
val robotoFamilyFont = FontFamily(
    Font(R.font.roboto_black),
    Font(R.font.roboto_bold, weight = FontWeight.Bold),
    Font(R.font.roboto_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.roboto_light, weight = FontWeight.Light),
    Font(R.font.roboto_thin, weight = FontWeight.Thin)
)

val Typography = Typography(
    defaultFontFamily = robotoFamilyFont,

    body1 = TextStyle(
        fontFamily = robotoFamilyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)