package com.example.yemeksiparis.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yemeksiparis.data.entitiy.Sepet
import com.example.yemeksiparis.data.entitiy.Yemekler
import com.example.yemeksiparis.data.repo.YemeklerDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnasayfaFragmentViewModel @Inject constructor(var krepo:YemeklerDaoRepository):ViewModel() {


    var yemeklerListesi = MutableLiveData<List<Yemekler>>()

    init {

        yemekleriYukle()
        yemeklerListesi = krepo.yemekleriGetir()

    }



    fun yemekleriYukle(){
        krepo.tumYemekleriAl()
    }

    fun silAnasayfa(sepet_yemek_id:Int,kullanici_adi:String){
        krepo.sepetYemekSil(sepet_yemek_id,kullanici_adi)
    }

  /*  fun ara(sonuc : String) {
        krepo.yemekAra(sonuc)
    }*/

}