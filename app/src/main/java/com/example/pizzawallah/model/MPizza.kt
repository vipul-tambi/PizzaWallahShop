package com.example.pizzawallah.model

data class MPizza(var id:String?=null,
                  var name:String?=null,
                  var varients:List<String> = listOf<String>(),
                  var category:String?=null,
                  var image:String?=null,
                  var description:String?=null,
                  var large:String?=null,
                  var small:String?=null,
                  var medium:String?=null
)
