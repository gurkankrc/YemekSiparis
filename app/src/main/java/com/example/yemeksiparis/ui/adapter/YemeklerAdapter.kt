package com.example.yemeksiparis.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.yemeksiparis.R
import com.example.yemeksiparis.data.entitiy.Sepet
import com.example.yemeksiparis.data.entitiy.Yemekler
import com.example.yemeksiparis.databinding.AnasayfaCardTasarimiBinding
import com.example.yemeksiparis.ui.fragment.AnasayfaFragmentDirections
import com.example.yemeksiparis.ui.viewmodel.AnasayfaFragmentViewModel
import com.example.yemeksiparis.ui.viewmodel.SepetFragmentViewModel
import com.example.yemeksiparis.utils.gecisYap
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class YemeklerAdapter(var mContext: Context, var yemeklerListesi:List<Yemekler>, var viewModel : AnasayfaFragmentViewModel)
    : RecyclerView.Adapter<YemeklerAdapter.CardTasarimTutucu>(){

private lateinit var aut:FirebaseAuth

    inner class CardTasarimTutucu(tasarim: AnasayfaCardTasarimiBinding): RecyclerView.ViewHolder(tasarim.root){

        var tasarim:AnasayfaCardTasarimiBinding
        init {
            this.tasarim = tasarim

        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {

        aut = FirebaseAuth.getInstance()
        val layoutInflater = LayoutInflater.from(mContext)
        val tasarim:AnasayfaCardTasarimiBinding =DataBindingUtil.inflate(layoutInflater,R.layout.anasayfa_card_tasarimi,parent,false )
        return CardTasarimTutucu(tasarim)

    }
    val yemek_adet = 1
    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {



        val yemek = yemeklerListesi.get(position)
        val t = holder.tasarim



        t.textViewUrunAd.text = yemek.yemek_ad
        t.textViewUrunFiyat.text = "${yemek.yemek_fiyat} ₺"
        t.textViewUrunAdet.text = yemek_adet.toString()

       val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Picasso.get().load(url).into(t.imageViewResimAnasayfa)

        t.anasayfaCard.setOnClickListener {


            val gecis = AnasayfaFragmentDirections.detayFragmentGecis(yemek = yemek)
            Navigation.gecisYap(it,gecis)
        }





        t.buttonAzalt2.setOnClickListener {

            if (t.textViewUrunAdet.text.toString().toInt() > 1){

                t.textViewUrunAdet.text = (t.textViewUrunAdet.text .toString().toInt() - 1).toString()

            }else{

                Snackbar.make(it,"Siparis Adeti Sıfır Olamaz",Snackbar.LENGTH_SHORT).show()

            }


        }
        t.buttonArttir2.setOnClickListener {

            t.textViewUrunAdet.text = (t.textViewUrunAdet.text.toString().toInt() + 1).toString()
        }



        t.buttonSepeteEkle.setOnClickListener {

            try{


                var sayac = 0
                var geciciAdet = 0

                Log.e("gelenid",viewModel.krepo.sepetListesi.value?.get(0)?.sepet_yemek_id.toString())


                val gelensayi = viewModel.krepo.sepetListesi.value?.size!!

                for (x in 0..gelensayi - 1) {
                    if ( yemek.yemek_ad == viewModel.krepo.sepetListesi.value?.get(x)?.sepet_yemek_ad){

                        geciciAdet = viewModel.krepo.sepetListesi.value?.get(x)?.sepet_yemek_siparis_adet.toString().toInt()

                        viewModel.silAnasayfa(viewModel.krepo.sepetListesi.value?.get(x)?.sepet_yemek_id.toString().toInt(),"${aut.currentUser}")
                        sayac++

                    }
                }

                buttonSepeteEkleTikla(yemek.yemek_ad,yemek.yemek_resim_adi,yemek.yemek_fiyat * (t.textViewUrunAdet.text.toString().toInt() + geciciAdet),t.textViewUrunAdet.text.toString().toInt()+ geciciAdet,"${aut.currentUser}")




                if  (sayac == 0){

                    Snackbar.make(it," Sepete ${yemek.yemek_ad} Eklendi",Snackbar.LENGTH_SHORT).show()
                }else {
                    Snackbar.make(it," Sepeteki ${yemek.yemek_ad} Güncellendi.",Snackbar.LENGTH_SHORT).show()
                }

                val gecisSepet = AnasayfaFragmentDirections.sepetFragmentGecis(siparisler = yemek)

            }catch(e: Exception){


                buttonSepeteEkleTikla(yemek.yemek_ad,yemek.yemek_resim_adi,yemek.yemek_fiyat * t.textViewUrunAdet.text.toString().toInt(),t.textViewUrunAdet.text.toString().toInt(),"${aut.currentUser}")
                Snackbar.make(it,"Sepete ${yemek.yemek_ad} Eklendi.",Snackbar.LENGTH_SHORT).show()


            }

        }

    }

    fun buttonSepeteEkleTikla(yemek_adi:String, yemek_resim_adi:String, yemek_fiyat:Int, yemek_siparis_adet:Int, kullanici_adi:String){
        viewModel.krepo.tumSepetiAl()
        viewModel.krepo.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        viewModel.krepo.tumSepetiAl()

        Log.e("Sepet3",viewModel.krepo.sepetiGetir().value.toString())




    }


    override fun getItemCount(): Int {
        return yemeklerListesi.size
    }
}