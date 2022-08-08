package com.example.yemeksiparis.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.yemeksiparis.GirisActivity
import com.example.yemeksiparis.MainActivity
import com.example.yemeksiparis.R
import com.example.yemeksiparis.data.entitiy.Yemekler
import com.example.yemeksiparis.databinding.FragmentAnasayfaBinding
import com.example.yemeksiparis.ui.adapter.YemeklerAdapter
import com.example.yemeksiparis.ui.viewmodel.AnasayfaFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AnasayfaFragment : Fragment(){
    private lateinit var tasarim:FragmentAnasayfaBinding
    private lateinit var viewModel:AnasayfaFragmentViewModel
    private lateinit var aut:FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        tasarim = DataBindingUtil.inflate(inflater, R.layout.fragment_anasayfa,container, false)
        tasarim.anasayfaFragment = this

        aut = FirebaseAuth.getInstance()


        tasarim.anasayfaToolbarBaslik = "Yemekler"
        (activity as AppCompatActivity).setSupportActionBar(tasarim.toolbarAnasayfa)


            viewModel.yemeklerListesi.observe(viewLifecycleOwner) {


                val adapter = YemeklerAdapter(requireContext(), it, viewModel)
                tasarim.yemeklerAdapter = adapter

            }


        return (tasarim.root)

    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val tempViewModel:AnasayfaFragmentViewModel by viewModels()
        viewModel = tempViewModel

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.ust_menu,menu)
        val item =menu.findItem(R.id.action_ara)
        var searchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {


                Log.e("ara",newText.toString())
                return true
            }

            })


       /* if (item !=null){



            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText!!.isNotEmpty()){


                        var search=newText.toLowerCase(Locale.getDefault())

                        for (yemekler in yemeklerListesi){
                            if (yemekler.toLowerCase(Locale.getDefault()).contains(search)){
                                yemeklerListesi.add(yemekler)
                            }

                            tasarim.yemeklerAdapter!!.notifyDataSetChanged()


                        }

                    }else
                    {

                        yemeklerListesi.addAll(yemeklerListesi)
                        tasarim.yemeklerAdapter!!.notifyDataSetChanged()

                    }

                    return false
                }

            })
        }*/






        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_cıkıs){

            aut.signOut()
            (activity as AppCompatActivity).finish()
            val intent = Intent(this@AnasayfaFragment.activity,GirisActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }




/*
    override fun onQueryTextSubmit(query: String): Boolean {


        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {





        return false
    }

fun ara (aramakelimesi:String){
    Log.e("Kişi",aramakelimesi)

   }*/
}