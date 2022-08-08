package com.example.yemeksiparis.retrofit

class ApiUtils {
    companion object{

        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getYemeklerDao():YemeklerDao{

            return RetrofitClient.getClient(BASE_URL).create(YemeklerDao::class.java)
        }
    }
}