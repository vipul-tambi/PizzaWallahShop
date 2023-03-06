package com.example.pizzawallah.di

import com.example.pizzawallah.repository.FireRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFirePizzaRepository()
    =FireRepository(queryPizza = FirebaseFirestore.getInstance().collection("pizzas"))

//    @Singleton
//    @Provides
//    fun provideUserAdminDetails()

}
