package com.example.yemeksiparis.retrofit

import com.example.yemeksiparis.data.entitiy.CRUDCevap
import com.example.yemeksiparis.data.entitiy.SepetCevap
import com.example.yemeksiparis.data.entitiy.YemeklerCevap
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface YemeklerDao {
    //http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php


    @GET("yemekler/tumYemekleriGetir.php")
    fun tumYemekler():Call<YemeklerCevap>



    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    fun yemekEkle(@Field("yemek_adi") yemek_adi:String,
                  @Field("yemek_resim_adi") yemek_resim_adi:String,
                  @Field("yemek_fiyat") yemek_fiyat:Int,
                  @Field("yemek_siparis_adet") yemek_siparis_adet:Int,
                  @Field("kullanici_adi") kullanici_adi:String) : Call<CRUDCevap>


    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun tumsepet(@Field("kullanici_adi") kullanici_adi:String) : Call<SepetCevap>


    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    fun sepettenYemekSil(@Field("sepet_yemek_id") sepet_yemek_id:Int,
                         @Field("kullanici_adi") kullanici_adi:String) : Call<CRUDCevap>


}