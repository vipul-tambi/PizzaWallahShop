package com.example.pizzawallah.screens.details


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pizzawallah.R
import com.example.pizzawallah.navigation.PizzaScreens
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(navController: NavController, pizzaId: String,viewModel: DetailsScreenViewModel= hiltViewModel()) {

  viewModel.getPizzaInfo(pizzaId)

    val currentUserName = FirebaseAuth.getInstance().currentUser?.email?.split("@")
        ?.get(0)
    var showDown by remember {
        mutableStateOf(false)
    }
    val pizza: Map<String, Any>? = viewModel.data.value.data


    var showVarient by remember {
        mutableStateOf(false)
    }

    var showQuantity by remember {
        mutableStateOf(false)
    }

    var varients by rememberSaveable {
        mutableStateOf("small")
    }
   
    var quantities by rememberSaveable {
        mutableStateOf(1)
    }
    var image: String? = null
    var name: String? = null
    var category: String? = null
    var description: String? = null
    var small: String? = null
    var medium: String? = null
    var large: String? = null

    var price by rememberSaveable{
        mutableStateOf(0.0f)
    }
    var total by rememberSaveable{

        mutableStateOf(0.0f)
    }
    if (!pizza.isNullOrEmpty()) {
        image = pizza["image"].toString()
        name = pizza["name"].toString()
        category = pizza["category"].toString()
        description = pizza["description"].toString()
        small = pizza["small"].toString()
        medium = pizza["medium"].toString()
        large = pizza["large"].toString()
        price= small.toFloat()
        total =small.toFloat()
    }

    Log.d("TOTAL", "DetailsScreen: $small")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .fillMaxHeight(),
        shape = RoundedCornerShape(24.dp)

    ) {

        Scaffold(

            topBar = {
                TopAppBar(backgroundColor = Color.Black,
                    title = {
                        Row {
                            Icon(
                                modifier=Modifier.clickable {
                                    navController.popBackStack()
                                },
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "",
                                tint = Color.White,

                                )
                            Text(
                                text = name.toString(),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                    },
                    actions = {
                        Box {
                            IconButton(onClick = { showDown = !showDown }) {
                                Icon(Icons.Default.ArrowDropDown, "",tint=Color.White)
                            }
                            Column {

                                DropdownMenu(
                                    expanded = showDown,
                                    onDismissRequest = { showDown= false },
                                    modifier = Modifier
                                ) {
                                    DropdownMenuItem(onClick = {
                                        FirebaseAuth.getInstance().signOut().run {
                                            navController.navigate(PizzaScreens.LoginScreen.name)
                                        }
                                    }){
                                        Row {
                                            Text(text = currentUserName.toString())
                                            Icon(
                                                imageVector = Icons.Default.AccountCircle,
                                                contentDescription = "LogOut"

                                            )
                                        }
                                    }

                                    DropdownMenuItem(onClick = {
                                        FirebaseAuth.getInstance().signOut().run {
                                            navController.navigate(PizzaScreens.LoginScreen.name)
                                        }
                                    }){
                                        Row{
                                            Text(text = "Logout")
                                            Icon(
                                                imageVector = Icons.Default.ExitToApp, contentDescription = "LogOut"

                                            )
                                        }


                                    }





                                }
                            }
                        }


                    }
                )
            }) {

            Column(
                modifier = Modifier
                    .background(color = Color.Green.copy(0.1f))
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {

                if (pizza != null) {
                    AsyncImage(
                        model = image, contentDescription = "Pizza Image",
                        modifier = Modifier
                            .height(250.dp)
                            .width(250.dp)
                    )
                }

                if (name != null) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green,
                        fontSize = 25.sp
                    )
                }

                Row() {
                    Text(text = "category : ", fontWeight = FontWeight.Bold)
                    if (category != null) {
                        Text(text = category)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                val localDims = LocalContext.current.resources.displayMetrics
                Surface(
                    modifier = Modifier
                        .height(localDims.heightPixels.dp.times(0.09f))
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = RectangleShape,
                    border = BorderStroke(1.dp, Color.DarkGray)
                ) {

                    LazyColumn(modifier = Modifier.padding(3.dp)) {
                        item {

                            if (description != null) {
                                Text(text = description)
                            }
                        }

                    }
                }

                Row(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround) {

                    Column(modifier = Modifier.fillMaxWidth(0.5f)) {


                        Row {
                            Text(text = "Varient : ",fontWeight = FontWeight.Bold)
                            varients.let { Text(text = it) }
                            Box(modifier = Modifier
                                .weight(1f)
                                .offset(x = (-10).dp, y = (-14).dp)) {
                                IconButton(onClick = { showVarient = !showVarient }) {
                                    Icon(Icons.Default.ArrowDropDown, "")
                                }
                                Row {


                                    DropdownMenu(
                                        expanded = showVarient,
                                        onDismissRequest = { showVarient = false }) {
                                        DropdownMenuItem(onClick = {
                                            varients="small"
                                            showVarient=!showVarient
                                            if (small != null) {
                                                price=small.toFloat()
                                            }
                                            total= Calculate(price, quantities )

                                        }) {
                                            Text(text = "small")
                                        }
                                        DropdownMenuItem(onClick = {
                                            varients="medium"
                                            showVarient=!showVarient
                                            if (medium != null) {
                                                price=medium.toFloat()
                                            }
                                            total= Calculate(price, quantities)
                                        }) {
                                            Text(text = "medium")
                                        }
                                        DropdownMenuItem(onClick = {
                                            varients="large"
                                            showVarient=!showVarient
                                            if (large != null) {
                                                price=large.toFloat()
                                            }
                                            total= Calculate(price, quantities)
                                        }) {
                                            Text(text = "large")
                                        }

                                    }
                                }
                            }




                        }








                        Row() {
                            Text(text = "Quantities : ", fontWeight = FontWeight.Bold)
                            quantities.let { Text(text = it.toString()) }
                            Box(modifier = Modifier
                                .weight(1f)
                                .offset(x = 0.dp, y = (-14).dp)) {

                                IconButton(onClick = { showQuantity = !showQuantity }) {
                                    Icon(Icons.Default.ArrowDropDown, "")
                                }



                                DropdownMenu(
                                    expanded = showQuantity,
                                    onDismissRequest = { showQuantity= false }
                                ) {
                                    for (i in 1..10){
                                        DropdownMenuItem(onClick = {
                                            quantities=i
                                            showQuantity=!showQuantity
                                            total= Calculate(price, quantities )
                                        }) {
                                            Text(text = i.toString())
                                        }
                                    }



                                }
                            }




                        }

                    }

                    Column {
                        Text(text = "\u20B9" + total.toString(), color = Color.Blue)

                        Button(modifier=Modifier.padding(top=10.dp),
                            colors =ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                            onClick = {navController.navigate(PizzaScreens.PaymentScreen.name)}) {
                            Text(text = "Order Now",color=Color.White)
                        }
                    }





                }


            }

        }



    }
}


fun Calculate(price: Number? = 1, quantities: Number = 1): Float {
    return (price?.toFloat())?.times((quantities.toFloat()))!!
}
