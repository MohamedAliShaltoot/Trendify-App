package com.example.trendify.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberImagePainter
import com.example.trendify.data.model.DataXXXX

@Composable
fun CategoryItem (category: DataXXXX,) {
    val navigator =  LocalNavigator .currentOrThrow
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(100.dp)
            .clickable {
                navigator.push(CategoryDetailsScreen(category.id,category.name))
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(category.image),
            contentDescription = category.name,
            modifier = Modifier
                .size(80.dp)
        )
        Text(
            text = category.name,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}