package com.example.yemeksiparis.data.entitiy

import com.google.gson.annotations.SerializedName

class SepetCevap (@SerializedName("sepet_yemekler") var sepet:List<Sepet>,
                  @SerializedName("success") var success:Int){
}