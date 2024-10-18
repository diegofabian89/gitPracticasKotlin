package com.uax.formulario

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.uax.formulario.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener{
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        //agregamos el objeto tipo onlistener el cual esta en la propia clase ya q es de tipo
        //appactivity  y tambien de onclick por ello usamos this
        binding.btLogin.setOnClickListener(this)
        binding.btLimpiar.setOnClickListener(this)

    }
    //aqui en el metodo de la interfaz de onclick la implementamos
    //con lo accion a ejecutar dependiendo del boton pulsado de la lista de botones anteriores
    //usando su id usando un swithc en este caso un when



    override fun onClick(v: View?) {
        // v es el nombre del pareametro ,
        //del evento q incia la accion decimos que puede ser login por si no se pulsa ningun boton


        when(v?.id){
            binding.btLogin.id->{
                if(binding.editPass.text.isNotEmpty()&&binding.editCorreo.text.isNotEmpty()){
                    //para pasar de una pantalla a otra creamos una variable de tipo intent
                    //es un accion de muchas  para cmabiar de pantalla se necesita el origen - destino
                    //applicationContext podria ser con this tambien es el origen dond nos encontramos y el destino es el nombre de
                    //la segunda actividad su clase para ejecutar codigo es ::classs.java ya que necesita una
                    //clase en codigo java
                    val intent:Intent=Intent(applicationContext,SecondActivity::class.java)
                    //con start activa la accion del intent si no la inicia

                    //para tener los datos por ejemplo del correo q introducimos en esta pantalla y mostrarlos en la segunda pantalla
                    //creamso un bundle para luego agregar un extra al intent
                    //la variable es de tipo bundle e igual a la clase bundle
                    val bundle:Bundle=Bundle()
                    //agregamos el el valor de correo y casteamos a string
                    // agregamos este bundle al intent dado un numbre al extra y su valor
                    bundle.putString("correo",binding.editCorreo.text.toString())
                    intent.putExtra("datos",bundle)
                    startActivity(intent)


                }
                else{
                    //nos lanza una notificacion  dentro de la pantalla el length
                    //es para mantener la notificacion unos segundo y el metodo show es para mostrarla
                    Snackbar.make(binding.root,"Faltas datos",Snackbar.LENGTH_SHORT).show()

                }
            }
            binding.btLimpiar.id->{
            limpiar()
            }


        }

    }
    //override del ciclo de vida de la primera actividad al volver de la segunda se limpia elformulario

    override fun onRestart() {
        super.onRestart()
        limpiar()
    }
    //funcion para limpiar el contenedor de cada texto usando el binding su id y text con el metodo clear
    fun limpiar(){
        binding.editCorreo.text.clear()
        binding.editPass.text.clear()
    }
}