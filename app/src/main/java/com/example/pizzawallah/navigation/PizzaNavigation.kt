package com.example.pizzawallah.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pizzawallah.screens.SplashScreen
import com.example.pizzawallah.screens.details.DetailsScreen
import com.example.pizzawallah.screens.home.HomeScreen
import com.example.pizzawallah.screens.login.LoginScreen
import com.example.pizzawallah.screens.PaymentScreenActivity as PaymentScreenActivity


@Composable
fun PizzaNavigation(){
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = PizzaScreens.SplashScreen.name){
        composable(PizzaScreens.SplashScreen.name){
                SplashScreen(navController = navController)
        }


        composable(PizzaScreens.LoginScreen.name){
            LoginScreen(navController = navController)
        }

        composable(PizzaScreens.PizzaHomeScreen.name){
            HomeScreen(navController = navController)
        }

        composable(PizzaScreens.LoginScreen.name){
           LoginScreen(navController=navController)
        }

        val detailsName=PizzaScreens.DetailsScreen.name
        composable("$detailsName/{pizzaId}",arguments= listOf(navArgument("pizzaId"){
            type= NavType.StringType
        })){ backStackEntry->

            backStackEntry.arguments?.getString("pizzaId").let{
                DetailsScreen(navController=navController,pizzaId=it.toString())
            }

        }

        composable(PizzaScreens.PaymentScreen.name){
         PaymentScreenActivity(navController=navController)
        }

    }
    
}
