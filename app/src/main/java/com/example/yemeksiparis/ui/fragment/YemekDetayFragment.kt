package com.example.yemeksiparis.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.yemeksiparis.R
import com.example.yemeksiparis.databinding.FragmentYemekDetayBinding
import com.example.yemeksiparis.ui.viewmodel.YemekDetayFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YemekDetayFragment : Fragment() {

    private lateinit var tasarim: FragmentYemekDetayBinding
    private lateinit var viewModel: YemekDetayFragmentViewModel
    private lateinit var aut :FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        tasarim= DataBindingUtil.inflate(inflater, R.layout.fragment_yemek_detay,container, false)


        aut =FirebaseAuth.getInstance()
        viewModel.krepo.tumSepetiAl()


        tasarim.toolbarDetay.title = "Yemek Detay Sayfası"

        val bundle:YemekDetayFragmentArgs by navArgs()
        val gelenYemek = bundle.yemek

       val url = "http://kasimadalan.pe.hu/yemekler/resimler/${gelenYemek.yemek_resim_adi}"
       Picasso.get().load(url).into(tasarim.imageViewYemekDetay)

        tasarim.textViewDetayAd.text = gelenYemek.yemek_ad
        tasarim.textViewDetayFiyat.text = "${gelenYemek.yemek_fiyat.toString()} ₺"
        tasarim.textViewAdetDetay.text = "1"






        tasarim.buttonSepeteEkleDetay.setOnClickListener {

            var sayac = 0
            var geciciAdet = 0

            try {

                val gelensayi = viewModel.krepo.sepetListesi.value?.size!!

                for (x in 0..gelensayi - 1) {
                    if ( gelenYemek.yemek_ad == viewModel.krepo.sepetListesi.value?.get(x)?.sepet_yemek_ad){

                        geciciAdet = viewModel.krepo.sepetListesi.value?.get(x)?.sepet_yemek_siparis_adet.toString().toInt()

                        viewModel.silDetay(viewModel.krepo.sepetListesi.value?.get(x)?.sepet_yemek_id.toString().toInt(),"${aut.currentUser}")
                        sayac++

                    }
                }
                buttonSepeteEkleDetay(gelenYemek.yemek_ad,gelenYemek.yemek_resim_adi,gelenYemek.yemek_fiyat * (tasarim.textViewAdetDetay.text.toString().toInt() + geciciAdet),tasarim.textViewAdetDetay.text.toString().toInt() + geciciAdet,"${aut.currentUser}")

                if (sayac == 0){
                    Snackbar.make(it,"Sepete ${gelenYemek.yemek_ad}  Eklendi",Snackbar.LENGTH_SHORT).show()
                }else{
                    Snackbar.make(it," Sepeteki ${gelenYemek.yemek_ad} Güncellendi.",Snackbar.LENGTH_SHORT).show()
                }
               val gecisSepet = AnasayfaFragmentDirections.sepetFragmentGecis(siparisler = gelenYemek)

            }catch (e:Exception){

                buttonSepeteEkleDetay(gelenYemek.yemek_ad,gelenYemek.yemek_resim_adi,gelenYemek.yemek_fiyat * (tasarim.textViewAdetDetay.text.toString().toInt() + geciciAdet),tasarim.textViewAdetDetay.text.toString().toInt() + geciciAdet,"${aut.currentUser}")

                Snackbar.make(it,"Sepete ${gelenYemek.yemek_ad} Eklendi.",Snackbar.LENGTH_SHORT).show()



            }
        }


        tasarim.buttonArttir3.setOnClickListener {

            tasarim.textViewAdetDetay.text = (tasarim.textViewAdetDetay.text.toString().toInt() + 1).toString()
        }

        tasarim.buttonAzalt3.setOnClickListener {

            if (tasarim.textViewAdetDetay.text.toString().toInt() > 1){

                tasarim.textViewAdetDetay.text = (tasarim.textViewAdetDetay.text .toString().toInt() - 1).toString()

            }else{

                Snackbar.make(it,"Siparis Adeti Sıfırdan Olamaz",Snackbar.LENGTH_SHORT).show()

            }
        }




        return tasarim.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val tempViewModel:YemekDetayFragmentViewModel by viewModels()
        viewModel = tempViewModel


    }

    fun buttonSepeteEkleDetay(yemek_adi:String, yemek_resim_adi:String, yemek_fiyat:Int, yemek_siparis_adet:Int, kullanici_adi:String){
        viewModel.krepo.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        viewModel.krepo.tumSepetiAl()
    }

}