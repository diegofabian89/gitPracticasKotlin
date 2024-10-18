package com.uax.inicial

import android.content.Intent
import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.uax.inicial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //recuerda crear un metodo privado con lateinit para inicializar
    //mas tarde el binding
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // llamamos a la varibale binding igualamos al tipo que le hemos dado que Activity
        //inflate uno el layout
        binding =ActivityMainBinding.inflate(layoutInflater)
        //primer metodo del ciclo de vida
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       // ojo con esto si usamos binding no podemos usar el
    // el setContentView(R.layout.activity_main) ya que debemos llamar la variable binding en este caso
        //hacia el elemento root del xml del layout de esta manera.
        setContentView(binding.root)
        //apartir de aqui lo grafico y logico estan junto solo detras de esta linea
        //recordar hacer el binding siempre
        //para hacer el viewbinding se modifica el gradle de module app
        //anadiendo viewbindling enable true

        //con el binding tenemos todos los layouts de nuestra actividad
        var number=0
        binding.boton.setOnClickListener{
            number++
            //Snackbar.make(binding.root,"Enhorabuena has completado tu app",Snackbar.LENGTH_SHORT).show()

        }
        binding.bReset.setOnClickListener{
            number=0
        }
        binding.btsegundaVentana.setOnClickListener{
            val intent= Intent(baseContext,MostrarContandor::class.java)
            intent.putExtra("valor",number)
            startActivity(intent)
        }


    }
}