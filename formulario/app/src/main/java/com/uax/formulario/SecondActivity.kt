package com.uax.formulario

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.uax.formulario.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    //creamos este metodo para recoger una string
    private lateinit var correoRecover :String
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivitySecondBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        //aqui llamamos al intente recuperando los datos q contiene el bundle lo igualamos a una variable de tipo bundle
       //ademas decimos q el extre puede ser de tipo null ya q puede q no trecuperemos datos
        var bundleRecover : Bundle ?= intent.extras?.getBundle("datos")
        //usando el metodo antes creado asignamos el valor del bundel con un get de su clave q es correo y en el caso de no tner
        //el correo usamos una operacion elvis para tener un texto secundario
        correoRecover=bundleRecover?.getString("correo")?:"sin correo"
        // aqui igualamos el contenido del contenedor correo de la view con el valor de correoRecover
        binding.correo.text=correoRecover

    }
}