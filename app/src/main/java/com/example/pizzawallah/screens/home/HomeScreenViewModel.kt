package com.example.pizzawallah.screens.home

import android.util.Log
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
class HomeScreenViewModel @Inject constructor(
    private val repository: FireRepository) :ViewModel(){

    val data:MutableState<DataOrException<List<MPizza>,Boolean,Exception>>
        = mutableStateOf(DataOrException(listOf(),true,Exception("")))

    init {
        getAllPizzaFromDatabase()
    }

    private  fun getAllPizzaFromDatabase() {
        viewModelScope.launch {
            data.value.loading=true
            data.value=repository.getAllPizzaFromDatabase()
            if(!data.value.data.isNullOrEmpty())
                data.value.loading=false
        }

        Log.d("VIEWMODEL", "getAllPizzaFromDatabase: ${data.value.data?.toList().toString()}")
    }

}
