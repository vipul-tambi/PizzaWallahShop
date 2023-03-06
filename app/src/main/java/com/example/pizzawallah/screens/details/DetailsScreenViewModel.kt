package com.example.pizzawallah.screens.details

import android.util.Log
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzawallah.data.DataOrException
import com.example.pizzawallah.model.MPizza
import com.example.pizzawallah.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsScreenViewModel @Inject constructor(private val repository:FireRepository) :ViewModel(){
// suspend fun getPizzaInfo(pizzaId:String):DataOrException<MPizza,Boolean,Exception>{
//    return repository.getPizzaInfo(pizzaId)
//}

    val data: MutableState<DataOrException<MutableMap<String,Any>, Boolean, Exception>>
            = mutableStateOf(DataOrException(null,true,Exception("")))

//    init {
//        getPizzaInfo(pizzaId)
//    }

    fun getPizzaInfo(pizzaId:String) {
        viewModelScope.launch {
            data.value.loading=true

            data.value.data=repository.getPizzaInfo(pizzaId).value.data
            data.value.loading=false
        }
        Log.d("AAAA", "getPizzaInfo: ${data.value}")

    }
}
