package com.uax.consecionario.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.uax.consecionario.R
import com.uax.consecionario.adapters.AdaptadorMarcas
import com.uax.consecionario.adapters.AdaptadorModelo
import com.uax.consecionario.databinding.ActivityCarBinding
import com.uax.consecionario.model.Marca
import com.uax.consecionario.model.Modelo

class CarActivity : AppCompatActivity(),OnItemSelectedListener {
    private lateinit var binding: ActivityCarBinding
    //TODO falta tipar el arraylist
    private lateinit var listaMarcas: ArrayList<Marca>
    //tenemos q crear el adapter del spinner que en este caso sera personalizado
    //al querer tener la marca con su imagen en el spinner por lo q tenemos q crear un xml con su aspecto
    private lateinit var adaptador: AdaptadorMarcas
    //creamos un adaptador para los modelos
    private lateinit var adaptadorModelos: AdaptadorModelo

    private lateinit var listaModelos:ArrayList<Modelo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        acciones()
        instancias()


    }

    private fun acciones() {
        binding.spinnermarcas.onItemSelectedListener=this
    }

    private fun instancias() {
        listaMarcas=arrayListOf(Marca("Audi",R.drawable.audi),
            Marca("BMW",R.drawable.bmw),
            Marca("Ford",R.drawable.ford),
            Marca("Opel",R.drawable.opel),
            Marca("Peugeot",R.drawable.peugeot),
            Marca("BYD",R.drawable.byd),
            Marca("Mercedes",R.drawable.mercedes))
        adaptador=AdaptadorMarcas(listaMarcas,applicationContext)
        binding.spinnermarcas.adapter=adaptador

        listaModelos=arrayListOf(Modelo("C 63","Mercedes",1300,240,"sport rapido",R.drawable.c63),
            Modelo("e 63","Mercedes",33000,240,"sport rapido y comodo",R.drawable.e63),
            Modelo("explorer","Ford",33000,240,"deportivo",R.drawable.explorerst),
            Modelo("mustang gt","Ford",67000,280,"campo",R.drawable.mustangt),
            Modelo("x5","BMW",67000,280,"deportivo",R.drawable.x5),
            Modelo("s 63","byd",13000,220,"sport rapido",R.drawable.s63),
            Modelo("rs7","audi",67000,280,"deportivo",R.drawable.rs7),)
        adaptadorModelos=AdaptadorModelo(listaModelos,this)
        binding.reciclemodelos.adapter=adaptadorModelos
        if(resources.configuration.orientation==1){
        binding.reciclemodelos.layoutManager=LinearLayoutManager(this,
            //si decimos horizontal haria el scroll horinzontal
            LinearLayoutManager.VERTICAL,false)}
        else if (resources.configuration.orientation==2){
            binding.reciclemodelos.layoutManager=GridLayoutManager(this,2)
        }


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val marcaSeleccionada: Marca = adaptador.getItem(position) as Marca
        Snackbar.make(binding.root,marcaSeleccionada.nombre,Snackbar.LENGTH_LONG).show()
        //aqui filtramos las marcas que se obtinenen de la marca seleccionada en el spinner
        //luego la filtramos en el adaptador del recycler view el resultado es un arraylist de modelos
        //puede ser 0 no vacio por lo q si es vacio no retorna nada luego comparamos esto con la marca en los modelos
        //y creando un metodo filtrar en la clase adaptadormodelos cambiamos la lista de modelos por la filtrada
        var listaFiltrada:ArrayList<Modelo> =listaModelos.filter{
            it.marca.equals(marcaSeleccionada.nombre,true)
            //importante castear a tipo arraylist para evitar errores
            //TODO ver clase desde el min 2h 20min clase experincial del 9 de 11
        }as ArrayList<Modelo>
        adaptadorModelos.filtrar(listaFiltrada)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}