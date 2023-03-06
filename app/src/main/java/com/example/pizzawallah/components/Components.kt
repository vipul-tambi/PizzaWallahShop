package com.example.pizzawallah.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TopAppBar(
    modifier: Modifier
    , backgroundColor: Color = Color.Black,
    Icon: ImageVector =Icons.Rounded.AccountBox) {
    Row {
        Text(
            text = "Pizza Wallah",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color.White
        )
        androidx.compose.material.Icon(imageVector = Icon, contentDescription ="" )
    }
}
