package com.example.pizzawallah.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.pizzawallah.data.DataOrException
import com.example.pizzawallah.model.MPizza
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.tasks.asDeferred
import javax.inject.Inject
import kotlinx.coroutines.tasks.await as await

class FireRepository @Inject constructor(private val queryPizza: Query) {

    suspend fun getAllPizzaFromDatabase():DataOrException<List<MPizza>,Boolean,Exception>{
        val dataOrException=DataOrException<List<MPizza>,Boolean,Exception>()
        try{
            dataOrException.loading=true

            dataOrException.data=queryPizza.get().await().documents.map { documentSnapshot->
              Log.d("SNAP", "getAllPizzaFromDatabase: ${documentSnapshot.data}")
                documentSnapshot.toObject(MPizza::class.java)!!
            }
            }
        catch(exception: FirebaseFirestoreException)
        {
            dataOrException.e=exception
        }
    return dataOrException

    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun getPizzaInfo(pizzaId:String): MutableState<DataOrException<MutableMap<String, Any>, Boolean, Exception>> {
        val dataOrException: MutableState<DataOrException<MutableMap<String,Any>,Boolean,Exception>> =mutableStateOf(DataOrException(null,true,Exception("")))
        try{
            dataOrException.value.loading=true

        val db:FirebaseFirestore=FirebaseFirestore.getInstance()
       // var p=MPizza()
         //  Log.d("PIZZAID", "getPizzaInfo:$pizzaId ")
           // db.collection("pizzas").await()


          val p=db.collection("pizzas").document("so7OWd0Y2paEjNanHvad").get().await().data
            dataOrException.value.data=p
            dataOrException.value.loading=false
        }
        catch (exception: FirebaseFirestoreException)
        {
            //dataOrException.loading=false
            dataOrException.value.e=exception
            dataOrException.value.loading=false
        }
        Log.d("DATAOR", "getPizzaInfo:${dataOrException.value.data} ")
        return dataOrException

    }
}
