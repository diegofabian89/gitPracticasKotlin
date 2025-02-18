package com.uax.consecionario.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.uax.consecionario.R
import com.uax.consecionario.databinding.ActivityDetailBinding
import com.uax.consecionario.model.Modelo

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var modelo: Modelo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        var bundle = intent.getBundleExtra("datos")
        //aqui recuperamos el modelo que nos paso la otra activity y lo casteamos a modelo
        modelo=bundle!!.getSerializable("modelo")!! as Modelo
        binding.textdetalles.text=modelo.toString()
        binding.imgmodelo.setImageResource(modelo.imagen)




    }
}