package com.example.trendify.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.trendify.data.model.DataXXXXXX
import com.example.trendify.data.model.DataXXXXXXXX
import com.example.trendify.presentation.viewmodel.ProductsViewModel

class ProductsScreen(private val id: Int) : Screen {
    @Composable
    override fun Content() {
        ProductsScreen(id.toString())
    }

    @SuppressLint("NotConstructor")
    @Composable
    fun ProductsScreen(id: String) {
        val viewModel: ProductsViewModel = hiltViewModel()
        val productResponse = viewModel.detailsproductResponse.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getDetailsProducts(id)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val product = productResponse.value?.data

            if (product != null) {
                ProductCard(product)
            } else {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ProductCard(product: DataXXXXXX) {
        val scrollstate = rememberScrollState()
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Product Detail") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back to Home"
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .verticalScroll(scrollstate)
                .padding(16.dp)
        ) {

            AsyncImage(
                model = product.image,
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFD700) // Gold color for the star
                )
                Spacer(modifier = Modifier.width(4.dp))
                // Text(
                //   text = "${product.rating} / 5 (${product.reviewCount})",
                // style = MaterialTheme.typography.bodyMedium
                //)
            }

            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Green,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Old Price: $${product.old_price} - Discount: ${product.discount}%",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { product.in_cart = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add to Cart", color = Color.White)
            }
        }
    }
}
}