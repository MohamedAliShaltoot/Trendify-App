package com.example.trendify.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.trendify.data.model.DataXXXXXX
import com.example.trendify.data.model.products
import com.example.trendify.presentation.viewmodel.CategoryViewModel
import com.example.trendify.presentation.viewmodel.HomeViewModel

class HomeScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = hiltViewModel()
        val homeResponse = viewModel.HomeResponse.collectAsState()
        val categoryViewModel: CategoryViewModel = hiltViewModel()
        val categories by categoryViewModel.categories.collectAsState(initial = emptyList())
        var searchQuery by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            categoryViewModel.fetchCategories()
        }


        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Home Screen") },
                    navigationIcon = {
                        IconButton(onClick = { /* Handle menu click */ }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Handle settings click */ }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFFFA500)) // Orange color
                )
            },
            bottomBar = {
                homeResponse.value?.data?.products?.let { products ->
                    BottomNavigationBar(products = products)
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {

                // Banner Section
                homeResponse.value?.data?.banners?.let { banners ->
                    BannerSection(banners = banners.map { it.image })  // Assuming each banner has an image field
                }

                // Categories Label
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 5.dp, bottom = 8.dp)
                )


                // LazyRow for Categories
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    items(categories?: emptyList()){categories->
                        CategoryItem(categories)
                    }
                }



                // LazyColumn for Products
                Text(
                    text = "All Products",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp))
                homeResponse.value?.data?.products?.let { products ->
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(products.filter { product ->
                            product.name.contains(searchQuery, ignoreCase = true)
                        }) { product ->
                            HomeProductCard(products = product)
                        }
                    }
                } ?: run {
                    // Handle empty or loading state if necessary
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "No products available")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Loading...")
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(products: List<DataXXXXXX>) {
    val navigator = LocalNavigator.currentOrThrow
    NavigationBar(
        modifier = Modifier.height(50.dp),
        containerColor = Color.White // You can set the background color here
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = true,
            onClick = { /* Handle home click */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
            selected = false,
            onClick = { navigator.push(FavoriteScreen(products)) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Payment") },
            selected = false,
            onClick = { navigator.push(CartScreen(products)) }
        )
    }
}


@Composable
fun ProductGrid(products: List<DataXXXXXX>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Divide products into pairs
        items(products.chunked(2)) { productPair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Display the first product
                HomeProductCard(productPair[0])

                // Check if there's a second product in the pair
                if (productPair.size > 1) {
                    HomeProductCard(productPair[1])
                } else {
                    // Add an empty space to align the layout properly if odd number of products
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun HomeProductCard(products: DataXXXXXX) {
    val isFavorite = remember { mutableStateOf(products.in_favorites) }
    val navigator =  LocalNavigator .currentOrThrow

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {
//        Box(modifier = Modifier.fillMaxWidth()){
//           AsyncImage( model = products.image, contentDescription ="Image", modifier = Modifier.fillMaxWidth() , contentScale = ContentScale.FillWidth)
//
//            Text("Welcome to Trenday",
//                fontSize = 20.sp,
//                color = Color.Black,
//                fontFamily = FontFamily.SansSerif,
//                modifier = Modifier
//                    .align(Alignment.BottomStart)
//                    .padding(start = 16.dp, bottom = 26.dp))
//        }

        Spacer(modifier = Modifier.height(30.dp))
        Column(Modifier.padding(start = 18.dp, end = 14.dp)) {

//            Row( modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween ,verticalAlignment = Alignment.CenterVertically,  ) {
//                Text(text = "All products " ,
//                    fontSize = 11.sp,
//                    color = Color.Black,
//                    fontFamily = FontFamily.SansSerif,)
//
//            }
            Spacer(modifier = Modifier.height(22.dp))
            //item
            Column {
                Box (modifier = Modifier.height(210.dp)
                    .clickable{navigator.push(ProductsScreen(products.id))}){
                    AsyncImage( modifier = Modifier
                        .height(200.dp)
                        .width(150.dp), contentScale = ContentScale.Crop, model = products.image, contentDescription ="Image")

                    Button(onClick = {} , enabled = false, colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Red,containerColor = Color.Red) ,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .defaultMinSize(
                                minHeight = 10.dp, minWidth = 5.dp
                            ), contentPadding = PaddingValues(horizontal = 5.dp, vertical = 5.dp) ) {
                        Text(text = "-20 %" ,
                            color = Color.White ,
                            fontSize = 11.sp ,
                            textAlign = TextAlign.Center
                        )

                    }
                    Surface(color = Color.White  ,
                        shadowElevation = 10.dp,
                        shape = CircleShape  ,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(top = 5.dp)) {

                        //add to favorite
                        IconButton(onClick = {
                            isFavorite.value = !isFavorite.value
                            products.in_favorites=isFavorite.value} ) {
                            Icon(Icons.Outlined.Favorite, contentDescription = "Favorite" , tint = if (isFavorite.value) Color.Red else  Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    var rating by remember { mutableStateOf(0f) }
                    Row(modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(top = 7.dp) , verticalAlignment = Alignment.CenterVertically) {

                        StarRatingBar(
                            maxStars = 5,
                            rating = rating,
                            onRatingChanged = {
                                rating = it
                            },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = products.name ,
                    fontSize = 20.sp ,
                    fontFamily = FontFamily.Cursive ,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Price: ${products.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    color = Color(0xFF4CAF50) // Green color for price
                )
                Text(
                    text = "Old Price: ${products.old_price}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    color = Color(0xFF4CAF50) // Green color for price
                )

                //button to add card to cart
                Button(onClick = {products.in_cart=true}) {
                    Text(text = "Add To Cart")
                    Icons.Filled.ShoppingCart

                }

            }
        }


    }

}


@Composable
fun BannerSection(banners: List<String>) { // Assume banners is a list of image URLs
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
    ) {
        items(banners) { banner ->
            BannerCard(bannerImage = banner)
        }
    }
}

@Composable
fun BannerCard(bannerImage: String) {
    AsyncImage(
        model = bannerImage,
        contentDescription = "Banner Image",
        modifier = Modifier
            .height(150.dp)  // Adjust height as needed
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current.density
    val starSize = (8f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC107) else Color(0x20161515)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i.toFloat())
                        }
                    )
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}