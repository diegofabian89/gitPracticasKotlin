package com.uax.consecionario.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.uax.consecionario.databinding.ActivityLoginBinding
import com.uax.consecionario.model.Usuario

class LoginActivity : AppCompatActivity(),OnClickListener,OnCheckedChangeListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var usuarios: ArrayList<Usuario>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instancias()
        acciones()

    }

    private fun instancias() {
        usuarios=arrayListOf(Usuario("diego","diego@gmail.com", "123456"),
            Usuario("ramon","ramon@gmail.com", "123456"),
            Usuario("juan","juan@gmail.com", "123456"))
    }

    private fun acciones() {
        binding.btacceso.setOnClickListener(this)
        binding.grupoUser.setOnCheckedChangeListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btacceso.id -> {
                //cambiar de escena
                //si soy invitado accedo directamente
                //si soy registrado miro los edit y busco en la lista de usuarios
                val radioSelected=binding.grupoUser.checkedRadioButtonId
                val intent: Intent=Intent(this,CarActivity::class.java)
                when(radioSelected){

                    binding.userInvitado.id->{
                        //cambiar de escena
                        startActivity(intent)
                    }
                    binding.userRegistrado.id->{
                        //si soy registrado miro los edit y busco en la lista de usuarios
                        //tiene que ser tipo usuario y nulo ya qye find lo pide
                        //se usa un parametro para la busqueda por lo q se usa it pero solo puede usarse cuando en un parametro solo
                       var usuarioEncontrado: Usuario?= usuarios.find{
                           it.correo.equals(binding.correo.text.toString()) && it.password.equals(binding.password.text.toString())
                       }
                        if(usuarioEncontrado==null){
                            //mostrar mensaje de error

                            Snackbar.make(binding.root,"usuario no encontrado",Snackbar.LENGTH_LONG).show()
                        }else{
                            startActivity(intent)

                        }

                    }

                }

            }
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when(group?.id){
            binding.grupoUser.id-> {
                when (checkedId) {
                    binding.userInvitado.id -> {
                        binding.correo.isEnabled = false
                        binding.password.isEnabled = false
                    }

                    binding.userRegistrado.id -> {
                        binding.correo.isEnabled = true
                        binding.password.isEnabled = true
                    }

                    }
                }
            }
    }
}