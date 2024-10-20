package com.example.trendify.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Design by Mohamed Ali
class MyOrders : Screen {
    @Composable
    override fun Content() {
        MyOrdersScreen()
    }


}


@Composable
fun MyOrdersScreen() {
    val selectedTab = remember { mutableStateOf("Delivered") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {

            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "My Orders",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TabItem("Delivered", selectedTab.value) { selectedTab.value = "Delivered" }
            TabItem("Processing", selectedTab.value) { selectedTab.value = "Processing" }
            TabItem("Cancelled", selectedTab.value) { selectedTab.value = "Cancelled" }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Order Cards
        OrderCard(
            orderNumber = "№1947034",
            trackingNumber = "IW3475453455",
            date = "05-12-2019",
            quantity = 3,
            totalAmount = 112,
            status = "Delivered"
        )
        Spacer(modifier = Modifier.height(16.dp))
        OrderCard(
            orderNumber = "№1947034",
            trackingNumber = "IW3475453455",
            date = "05-12-2019",
            quantity = 3,
            totalAmount = 112,
            status = "Delivered"
        )
        Spacer(modifier = Modifier.height(16.dp))
        OrderCard(
            orderNumber = "№1947034",
            trackingNumber = "IW3475453455",
            date = "05-12-2019",
            quantity = 3,
            totalAmount = 112,
            status = "Delivered"
        )
    }
}

@Composable
fun TabItem(tabName: String, selectedTab: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
             if (tabName == selectedTab) Color(0xFF6055d8) else Color.LightGray
        ),
        shape = CircleShape,
        modifier = Modifier.height(40.dp)
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = tabName,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
    }
}

@Composable
fun OrderCard(
    orderNumber: String,
    trackingNumber: String,
    date: String,
    quantity: Int,
    totalAmount: Int,
    status: String
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        modifier = Modifier
            .fillMaxWidth().background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Order $orderNumber", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = date, fontSize = 14.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Tracking number: $trackingNumber", fontSize = 14.sp, fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Quantity: $quantity", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Total Amount: $$totalAmount", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }


            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(40.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Details", fontSize = 14.sp, fontWeight = FontWeight.Bold,color = Color.Black)
                }

                Text(text = status, color = Color(0xFF02AB05), fontWeight = FontWeight.Bold)
            }

        }
    }
}

