package com.uax.inicial

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.uax.inicial.databinding.ActivityMainBinding
import com.uax.inicial.databinding.ActivityMostrarContandorBinding

class MostrarContandor : AppCompatActivity() {
    //recuerda en el binding de una segunda actividad tienes que usar el nombre dado en el layout de esa
    //actividad
    private lateinit var binding: ActivityMostrarContandorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMostrarContandorBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        //hacemos un get del valor recogido en el intent de la main activity y damos un valor por defecto en caso de no recoger valores
        var contandor:Int?=intent.getIntExtra("valor",0)
        binding.textContador.setText(contandor.toString())
        binding.btclose.setOnClickListener{
            finish()
        }

    }
}