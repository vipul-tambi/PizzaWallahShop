package com.example.pizzawallah.navigation

enum class PizzaScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    PizzaHomeScreen,
    DetailsScreen,
    PaymentScreen,
    CartScreen,
    NewPizzaScreen;


    companion object{
    fun fromRoute(route:String):PizzaScreens
    =when(route.substringBefore("/")){
        SplashScreen.name -> SplashScreen
        LoginScreen.name->LoginScreen
        CreateAccountScreen.name->CreateAccountScreen
        PizzaHomeScreen.name ->PizzaHomeScreen
        DetailsScreen.name->DetailsScreen
        CartScreen.name->CartScreen
        PaymentScreen.name->PaymentScreen
        NewPizzaScreen.name->NewPizzaScreen
        else -> throw java.lang.IllegalArgumentException("Route $route is not recognized")
    }
    }
}
