package com.example.yemeksiparis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.example.yemeksiparis.databinding.ActivityGirisBinding
import com.example.yemeksiparis.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GirisActivity : AppCompatActivity() {

    private lateinit var tasarim: ActivityGirisBinding
    private lateinit var aut : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasarim =ActivityGirisBinding.inflate(layoutInflater)
        setContentView(tasarim.root)

        tasarim.toolbar.title = "Giriş Ekranı"

        aut = FirebaseAuth.getInstance()
        val aktifuser=aut.currentUser

        if (aktifuser !=null){

            val intent=Intent(this@GirisActivity,MainActivity::class.java)
            startActivity(intent)
            finish()

        }


    }


    fun btngiris(view : View){

        val email = tasarim.emailText.text.toString()
        val sifre = tasarim.sifreText.text.toString()

        if (email.equals("") || sifre.equals("") ){

            Log.e("Kayıt","Boş gecilemez")

        }else {

            aut.signInWithEmailAndPassword(email,sifre).addOnSuccessListener {

                Log.e("Kayıt","Giriş Yapıldı.")
                val intent= Intent(this@GirisActivity,MainActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {


                Log.e("Kayıt","Boş gecilemez")

            }
        }

    }


    fun btnkayitOl(view : View){

        val email = tasarim.emailText.text.toString()
        val sifre = tasarim.sifreText.text.toString()


        if (email.equals("") || sifre.equals("")){

            Log.e("Kayıt","Boş gecilemez")

            //Toast.makeText(this@GirisActivity,"sadada",Toast.LENGTH_LONG).show()

        }else {

            aut.createUserWithEmailAndPassword(email,sifre).addOnSuccessListener {
                Log.e("Kayıt","Başarılı")



            }.addOnFailureListener {

                Log.e("Kayıt","boş bırakmayınız")
               // Toast.makeText(this@GirisActivity,"sadada",Toast.LENGTH_LONG).show()
            }

        }


    }
}