package com.example.pizzawallah

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pizzawallah.navigation.PizzaNavigation
import com.example.pizzawallah.screens.SplashScreen
import com.example.pizzawallah.ui.theme.PizzaWallahTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            PizzaWallahTheme {
                // A surface container using the 'background' color from the theme


                    Log.d("ENTER", "onCreate:1233 ")
                    PizzaApp()
            }
        }
    }
}

@Composable
fun PizzaApp(){
    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            PizzaNavigation()
        }

    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PizzaWallahTheme {

    }
}
