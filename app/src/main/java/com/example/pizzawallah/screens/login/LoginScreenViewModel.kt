package com.example.pizzawallah.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzawallah.model.MPizza
import com.example.pizzawallah.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await as await



class LoginScreenViewModel:ViewModel() {
    private val auth:FirebaseAuth=Firebase.auth
   // val db:FirebaseFirestore=FirebaseFirestore.getInstance().collection("admins")
    private val _loading=MutableLiveData(false)
    val loading:LiveData<Boolean> = _loading


     fun signInWithNameEmailPassword(email:String,password:String,home:()->Unit)=viewModelScope.launch{
    try{
    auth.signInWithEmailAndPassword(email,password)
        .addOnCompleteListener{task->
            if(task.isSuccessful)
            {

                home()
            }
            else{
                Log.d("FB_ERROR" ,"signInWithNameEmailPassword:${task.result.toString()} ")
            }
        }
    }
    catch(ex:Exception){
        Log.d("ERROR_WHILE_LOGIN", "signInWithNameEmailPassword: ${ex.message}")
    }
    }

    fun createUserWithNameEmailPassword(
        email:String,
        password: String,
        username:String,
        home:()->Unit
    ){
            if(_loading.value==false){
                _loading.value=true

                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener {task->

                        if(task.isSuccessful)
                        {
                            val displayName=username
                            createUser(displayName)
                          //  val admins=db.collection("admin").get()

                            home()
                        }
                        else
                        {
                            Log.d("SIGNUP_ERROR", "createUserWithNameEmailPassword: ${task.result.toString()}")
                        }
                        _loading.value=false
                    }
            }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid

        val user = MUser(userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Life is great",
            profession = "Android Developer",
            id = null,
            isAdmin=false).toMap()

        val add = HashMap<String,String>()
        add["id"]=userId.toString()


        FirebaseFirestore.getInstance().collection("users")
            .add(user).addOnSuccessListener {
                it.update("id",it.id)

            }
        FirebaseFirestore.getInstance().collection("admins")
            .add(add)

    }
}
