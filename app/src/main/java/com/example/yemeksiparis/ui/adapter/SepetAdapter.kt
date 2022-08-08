package com.example.yemeksiparis.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.yemeksiparis.R
import com.example.yemeksiparis.data.entitiy.Sepet
import com.example.yemeksiparis.data.entitiy.Yemekler
import com.example.yemeksiparis.databinding.CardTasarimiBinding
import com.example.yemeksiparis.ui.viewmodel.SepetFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class SepetAdapter(var mContext: Context, var sepetListesi:List<Sepet>,var viewModel:SepetFragmentViewModel)
    : RecyclerView.Adapter<SepetAdapter.CardTasarimTutucu>(){

    inner class CardTasarimTutucu(tasarim: CardTasarimiBinding): RecyclerView.ViewHolder(tasarim.root){

        var tasarim: CardTasarimiBinding
        init {
            this.tasarim=tasarim
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SepetAdapter.CardTasarimTutucu {
        val layoutInflater = LayoutInflater.from(mContext)
        val tasarim:CardTasarimiBinding = DataBindingUtil.inflate(layoutInflater,R.layout.card_tasarimi,parent,false )
        return CardTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: SepetAdapter.CardTasarimTutucu, position: Int) {

        val yemek = sepetListesi.get(position)

        val t = holder.tasarim


        t.textViewSepetUrunAd.text ="Yemek Adı: ${yemek.sepet_yemek_ad}"
        t.textViewSepetFiyat.text = "Yemek Fiyatı: ${yemek.sepet_yemek_fiyat} ₺"
        t.textViewSayi.text ="Yemek Adet: ${yemek.sepet_yemek_siparis_adet}"

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.sepet_yemek_resim_adi}"
        Picasso.get().load(url).into(t.imageView2)

        t.imageView.setOnClickListener {
            Snackbar.make(it,"Yemek Silinsin mi?",Snackbar.LENGTH_LONG)
                .setAction("EVET"){
                    viewModel.sil(yemek.sepet_yemek_id,yemek.kullanici_adi)


                }.show()
        }

    }






    override fun getItemCount(): Int {

        return sepetListesi.size

    }


}