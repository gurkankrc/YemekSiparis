package com.example.yemeksiparis.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yemeksiparis.data.entitiy.Sepet
import com.example.yemeksiparis.data.repo.YemeklerDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YemekDetayFragmentViewModel @Inject constructor(var krepo:YemeklerDaoRepository) : ViewModel()  {

    var sepetListesi = MutableLiveData<List<Sepet>>()


    init {

        sepetiYukle()
        sepetListesi=krepo.sepetiGetir()
    }

    fun sepetiYukle(){
        krepo.tumSepetiAl()
    }

    fun silDetay(sepet_yemek_id:Int,kullanici_adi:String){
        krepo.sepetYemekSil(sepet_yemek_id,kullanici_adi)
    }

}