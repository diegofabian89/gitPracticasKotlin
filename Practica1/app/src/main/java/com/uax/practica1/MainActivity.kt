package com.uax.practica1



import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.uax.practica1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Configura el botón de mensaje
        binding.mensajeboton.setOnClickListener {
            //usamos contect compat aqui con el this estamos diciendo que estamos en el activity main
            binding.main.setBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_primary_dark))
            binding.mensaje.text = "Has presionado el botón para enseñar un mensaje \n ten un día estupendo"
        }

        // Configura el botón de color
        binding.colorboton.setOnClickListener {
            binding.main.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))
        }

        // Configura el listener para el Spinner
        //aqui creamos un objeto anonimo con object de tipo adapterview para poder instanciar on item selected
        binding.snipper.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val snipper: String = binding.snipper.selectedItem.toString() // Obtener el elemento seleccionado
                when (snipper) {
                    "Amanecer" -> {
                        //al estar dentro de la instancia de Adapter view necesitamos usar this @main activity para poder acceder a los
                        //recursos de este
                        binding.main.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.dia)
                        binding.textView.setText("Buenos Dias")
                    }
                    "Atardecer" -> {
                        binding.main.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.tardes)
                        binding.textView.setText("Buenas Tardes")
                    }
                    "Anochecer" -> {
                        binding.main.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.night)
                        binding.textView.setText("Buenas Noches")
                    }
                    else -> {
                        binding.main.setBackgroundColor(ContextCompat.getColor(this@MainActivity, com.google.android.material.R.color.m3_chip_background_color))
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.main.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.pink))
            }
        }

        }
    }





