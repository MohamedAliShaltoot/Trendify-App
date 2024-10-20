package com.example.trendify.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.trendify.data.model.AddOrDeleteCartRequest
import com.example.trendify.data.model.CartItem
import com.example.trendify.data.model.DataXXXXXX
import com.example.trendify.data.model.productss
import com.example.trendify.presentation.viewmodel.CartViewModel

class CartScreen(val products: List<DataXXXXXX>) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: CartViewModel = hiltViewModel()
        val cartResponse = viewModel.getCartResponse.collectAsState()
        var showthanksMessage by remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Your Cart") },
                    navigationIcon = {
                        IconButton(onClick = {navigator.push(HomeScreen())}) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFFFA500)) // Orange color
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {

                // LazyColumn for Favorite Products
                val filteredcCart = products.filter { it.in_cart }

                if (filteredcCart.isNotEmpty()) {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredcCart) { CartItem ->
                            CartProductCard(product = CartItem)
                        }
                    }
                } else {
                    emptyCartMessage() // Show message if no items are in favorites
                }

                // Proceed to Checkout Button
                Button(
                    onClick = { showthanksMessage = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Proceed to Checkout")
                }
                // Show thanks message
                if (showthanksMessage) {
                    AlertDialog(onDismissRequest = {showthanksMessage = false},
                        title = { Text("Thanks") },
                        text = { Text("Your order has been placed successfully") },
                        confirmButton = { Button(onClick = { showthanksMessage=false })
                        { Text("OK") } })
                }
            }
        }
    }

    @Composable
    fun CartProductCard(product: DataXXXXXX) {
        val viewModel: CartViewModel = hiltViewModel()
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 16.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Price: ${product.price}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF4CAF50) // Green color for price
                    )
                    Text(
                        text = "Old Price: ${product.price}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                // Delete Icon Button
                IconButton(onClick = {
                    product.in_cart = false
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }

            }
        }
    }

    @Composable
    fun emptyCartMessage() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Your cart is empty", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Start adding products to your cart!")
            }
        }
    }
}
