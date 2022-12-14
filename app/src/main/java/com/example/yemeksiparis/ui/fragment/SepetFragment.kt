package com.example.yemeksiparis.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yemeksiparis.R
import com.example.yemeksiparis.data.entitiy.Sepet
import com.example.yemeksiparis.data.entitiy.Yemekler
import com.example.yemeksiparis.databinding.FragmentAnasayfaBinding
import com.example.yemeksiparis.databinding.FragmentSepetBinding
import com.example.yemeksiparis.ui.adapter.SepetAdapter
import com.example.yemeksiparis.ui.adapter.YemeklerAdapter
import com.example.yemeksiparis.ui.viewmodel.SepetFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class SepetFragment : Fragment() {

    private lateinit var tasarim:FragmentSepetBinding
    private lateinit var viewModel:SepetFragmentViewModel
    private lateinit var aut:FirebaseAuth



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        aut =FirebaseAuth.getInstance()






        //tasarim.avSplashAnimation.setAnimation("99298-food-delivery.json")
       // tasarim.avSplashAnimation.playAnimation()

        tasarim= DataBindingUtil.inflate(inflater, R.layout.fragment_sepet,container, false)

        tasarim.toolbarSepet.title = "Sepet"
        tasarim.sepetFragment = this

        tasarim.avSplashAnimation.setAnimation("71216-delivery-guy.json")
        tasarim.avSplashAnimation.playAnimation()


        viewModel.sepetListesi.observe(viewLifecycleOwner) {


            val adapter = SepetAdapter(requireContext(), it, viewModel)
            tasarim.sepetAdapter = adapter
        }

        toplamFiyat()





        tasarim.buttonSepetOnayla.setOnClickListener {

            Snackbar.make(it, "${tasarim.textViewSepetToplam.text} Sepeti Onayl??yormusunuz?", Snackbar.LENGTH_LONG)
                .setAction("EVET") {

                    viewModel.sepetListesi.observe(viewLifecycleOwner) {

                        for (x in 0..it.size - 1) {
                            //viewModel.sil(it.get(x).sepet_yemek_id, it.get(x).kullanici_adi)

                            viewModel.sil(viewModel.krepo.sepetListesi.value?.get(x)?.sepet_yemek_id.toString().toInt(),"${aut.currentUser}")

                        }

                    }

                    tasarim.avSplashAnimation.isVisible = true
                   Snackbar.make(it,"Sipari??iniz Al??nm????t??r.",Snackbar.LENGTH_LONG).show()

                }.show()


            }

        return tasarim.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:SepetFragmentViewModel by viewModels()
        viewModel = tempViewModel



    }


    override fun onResume() {
        super.onResume()
        viewModel.sepetiYukle()
        viewModel.krepo.sepetiGetir()
        toplamFiyat()
    }


    fun toplamFiyat(){

        var toplam = 0
        viewModel.sepetListesi.observe(viewLifecycleOwner){

            for (x in 0..it.size-1){

                toplam += it.get(x).sepet_yemek_fiyat


            }
            Log.e("Sepet Fiyat",toplam.toString())
            tasarim.textViewSepetToplam.text = "Toplam Sepet Tutar?? : ${toplam} ??? "
            toplam = 0

        }
    }






}