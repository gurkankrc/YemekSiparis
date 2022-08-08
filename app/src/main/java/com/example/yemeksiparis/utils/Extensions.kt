package com.example.yemeksiparis.utils

import android.content.Intent
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation



fun Navigation.gecisYap(v: View, id: NavDirections){
    findNavController(v).navigate(id)
}

