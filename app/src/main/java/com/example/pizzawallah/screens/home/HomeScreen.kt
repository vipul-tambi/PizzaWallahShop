package com.example.pizzawallah.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pizzawallah.model.MPizza
import com.example.pizzawallah.navigation.PizzaScreens
import com.example.pizzawallah.screens.details.Calculate
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("RememberReturnType", "UnrememberedMutableState",
   "UnusedMaterialScaffoldPaddingParameter"
)
@Composable
fun HomeScreen(navController: NavController,viewModel: HomeScreenViewModel= hiltViewModel()) {
   val currentUser = FirebaseAuth.getInstance().currentUser
   val currentUserName = FirebaseAuth.getInstance().currentUser?.email?.split("@")
      ?.get(0)

   var showDown by remember {
      mutableStateOf(false)
   }
   var listOfPizzas = emptyList<MPizza>()
   if (!viewModel.data.value.data.isNullOrEmpty()) {
      listOfPizzas = viewModel.data.value.data!!.toList()
      Log.d("Books", "HomeContent:${listOfPizzas} ")
   }
//   val listOfPizza= listOf(
//      MPizza(id="1231",name="your Pizza",category="veg",image="https://www.dominos.co.in/files/items/Paneer_Makhni.jpg",description="Very nice Pizza"),
//      MPizza(id="1232",name="your Pizza",category="veg",image="https://www.dominos.co.in/files/items/Paneer_Makhni.jpg",description="Very nice Pizza"),
//      MPizza(id="1233",name="your Pizza",category="veg",image="https://www.dominos.co.in/files/items/Paneer_Makhni.jpg",description="Very nice Pizza"),
//      MPizza(id="1234",name="your Pizza",category="veg",image="https://www.dominos.co.in/files/items/Paneer_Makhni.jpg",description="Very nice Pizza"),
//      MPizza(id="1235",name="your Pizza",category="veg",image="https://www.dominos.co.in/files/items/Paneer_Makhni.jpg",description="Very nice Pizza")
//   )


   Column(modifier = Modifier.padding(12.dp)) {
      Scaffold(
         topBar = {
            TopAppBar(backgroundColor = Color.Black, title = {
               Row {
                  Icon(
                     imageVector = Icons.Default.Home,
                     contentDescription = "",
                     tint = Color.White,

                     )
                  Text(
                     text = "Pizza Wallah Pizzass",
                     modifier = Modifier.fillMaxWidth(),
                     textAlign = TextAlign.Center,
                     color = Color.White
                  )
               }
            }, actions = {
               Box {
                  IconButton(onClick = { showDown = !showDown }) {
                     Icon(Icons.Default.ArrowDropDown, "", tint = Color.White)
                  }
                  Column {

                     DropdownMenu(
                        expanded = showDown,
                        onDismissRequest = { showDown = false },
                        modifier = Modifier
                     ) {
                        DropdownMenuItem(onClick = {
                           FirebaseAuth.getInstance().signOut().run {
                              navController.navigate(PizzaScreens.LoginScreen.name)
                           }
                        }) {
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
                        }) {
                           Row {
                              Text(text = "Logout")
                              Icon(
                                 imageVector = Icons.Default.ExitToApp,
                                 contentDescription = "LogOut"

                              )
                           }


                        }


                     }
                  }
               }


            })
         }

      ) {
         LazyColumn {
            items(items = listOfPizzas) {

               Card(
                  modifier = Modifier
                     .height(150.dp)
                     .padding(4.dp)
                     .fillMaxWidth()
                     .border(width = 3.dp, color = Color.Green)
                     .clickable {
                        navController.navigate(PizzaScreens.DetailsScreen.name + "/${it.id}")
                     },


                  backgroundColor = Color.Green.copy(0.4f)
               ) {

                  Row() {
                     AsyncImage(
                        model = it.image, contentDescription = "Pizza Image",
                        modifier = Modifier
                           .height(150.dp)
                           .width(150.dp)
                     )

                     Spacer(modifier = Modifier.width(15.dp))

                     Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                           text = it.name.toString(),
                           style = MaterialTheme.typography.h5,
                           textAlign = TextAlign.Center,
                           overflow = TextOverflow.Clip,
                           color = Color.Red
                        )

                        Row() {
                           Text(text = "category : ", fontWeight = FontWeight.Bold)
                           Text(text = it.category.toString())
                        }

                        Row() {
                           Text(text = "description : ", fontWeight = FontWeight.Bold)

                        }
                        Text(text = it.description.toString(), overflow = TextOverflow.Ellipsis)

                     }
                  }
               }

            }

         }
      }


   }

}



