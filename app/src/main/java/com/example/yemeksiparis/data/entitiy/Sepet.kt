package com.example.yemeksiparis.data.entitiy

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Sepet (@SerializedName("sepet_yemek_id")var sepet_yemek_id:Int,
                  @SerializedName("yemek_adi")var sepet_yemek_ad:String,
                  @SerializedName("yemek_resim_adi")var sepet_yemek_resim_adi:String,
                  @SerializedName("yemek_fiyat")var sepet_yemek_fiyat:Int,
                  @SerializedName("yemek_siparis_adet")var sepet_yemek_siparis_adet:Int,
                  @SerializedName("kullanici_adi")var kullanici_adi:String) : Serializable {
}