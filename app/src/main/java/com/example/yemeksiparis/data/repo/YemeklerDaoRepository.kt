package com.example.yemeksiparis.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.yemeksiparis.data.entitiy.*
import com.example.yemeksiparis.retrofit.YemeklerDao
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class YemeklerDaoRepository(var kdao:YemeklerDao) {

    private lateinit var aut : FirebaseAuth


    var yemeklerListesi:MutableLiveData<List<Yemekler>>
    var sepetListesi:MutableLiveData<List<Sepet>>

    init {
        aut = FirebaseAuth.getInstance()
        yemeklerListesi = MutableLiveData()
        sepetListesi=MutableLiveData()


    }
    fun yemekleriGetir():MutableLiveData<List<Yemekler>>{
        return yemeklerListesi
    }



    fun sepetiGetir():MutableLiveData<List<Sepet>>{
        return sepetListesi
    }





    fun sepetYemekSil(sepet_yemek_id:Int,kullanici_adi:String){

            kdao.sepettenYemekSil(sepet_yemek_id,"${aut.currentUser}").enqueue(object : Callback<CRUDCevap>{
                override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>) {
                    val basari = response.body()!!.success
                    val mesaj = response.body()!!.message
                    Log.e("Yemek silindi", "$basari - $mesaj")
                    tumSepetiAl()

                }

                override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {}
            })


    }

    fun sepeteEkle(yemek_adi:String, yemek_resim_adi:String, yemek_fiyat:Int, yemek_siparis_adet:Int, kullanici_adi:String){


                kdao.yemekEkle(
                    yemek_adi,
                    yemek_resim_adi,
                    yemek_fiyat,
                    yemek_siparis_adet,
                    kullanici_adi
                ).enqueue(object : Callback<CRUDCevap> {
                    override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>) {
                        val basari = response.body()!!.success
                        val mesaj = response.body()!!.message
                        Log.e("Yemek sepete ekle", "$basari - $mesaj")
                    }

                    override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {}
                })


    }

    fun tumSepetiAl() {
            kdao.tumsepet("${aut.currentUser}").enqueue(object : Callback<SepetCevap> {
                override fun onResponse(call: Call<SepetCevap>?, response: Response<SepetCevap>) {
                    val liste = response.body().sepet

                    sepetListesi.value = liste
                }

                override fun onFailure(call: Call<SepetCevap>?, t: Throwable) {
                    sepetListesi.value = emptyList()


                }
            })



    }

/*
    fun yemekAra(sonuc : String) {
        kdao.tumYemekler().enqueue(object : Callback<YemeklerCevap> {
            override fun onResponse(call: Call<YemeklerCevap>?, response: Response<YemeklerCevap>) {
                val liste = response.body().yemekler

                val sonuc = liste.filter { it.yemek_ad.contains(sonuc, true) }
                yemeklerListesi.value = sonuc
            }

            override fun onFailure(call: Call<YemeklerCevap>?, t: Throwable?) {}
        })

    }*/




    fun tumYemekleriAl(){
      kdao.tumYemekler().enqueue(object:Callback<YemeklerCevap>{
          override fun onResponse(call: Call<YemeklerCevap>?, response: Response<YemeklerCevap>) {
             val liste = response.body().yemekler
              yemeklerListesi.value = liste
          }

          override fun onFailure(call: Call<YemeklerCevap>?, t: Throwable?) {

          }

      })
    }
}