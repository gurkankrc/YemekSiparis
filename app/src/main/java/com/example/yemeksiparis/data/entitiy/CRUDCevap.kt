package com.example.yemeksiparis.data.entitiy

import com.google.gson.annotations.SerializedName

class CRUDCevap(@SerializedName("success")var success:Int,
                @SerializedName("message") var message:String){
}