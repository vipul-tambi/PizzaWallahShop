package com.example.pizzawallah.screens


import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.runtime.remember
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzawallah.R
import com.example.pizzawallah.navigation.PizzaScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    val scale=remember{
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }))
        delay(2000L)
        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
        {
            Log.d("LOGINSCREEN", "SplashScreen:111111 ")
            navController.navigate(PizzaScreens.LoginScreen.name)
        }
        else
        {
            Log.d("HOMESCREEN", "SplashScreen:222222 ")
            navController.navigate(PizzaScreens.PizzaHomeScreen.name)
        }


    }
    Surface(modifier = Modifier
        .padding(15.dp)
        .size(330.dp)
        .scale(scale.value),
        shape= CircleShape,
        color= Color.Green.copy(0.2f),
        border= BorderStroke(width=2.dp,color=Color.Green)
    ) {

        Column(modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {

            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Pizza Wallah", style = MaterialTheme.typography.h5,color=Color.Green)
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painterResource(R.drawable.logo),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )


        }

    }



}
