package com.uax.consecionario.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.uax.consecionario.R
import com.uax.consecionario.model.Marca

class AdaptadorMarcas(var listaMarcas:ArrayList<Marca>,var contexto:Context): BaseAdapter(){
    override fun getCount(): Int {

        return listaMarcas.size

    }

    override fun getItem(position: Int): Any {
        return listaMarcas[position]
    }

    override fun getItemId(position: Int): Long {

        //id de cada fila
       return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //retorna el aspecto de cada fila
        //necesitamos inflar la vista
        //creamos un view usando layoutinflater y con from le decimos el contexto por lo q creamos el contexto en el
        //parametro de la clase para asi poder inflar la vista, luego indicamos dond esta el xml y con parent es el parametro del metodo
        //en el que nos encontramos  para decir en el grupo q estamos y false es para q no se mezclen estos datos
        val view:View= LayoutInflater.from(contexto).inflate(R.layout.item_marca,parent,false)
        //ahora necesitamos los elementos del view en este caso del xml item_marca
        //con findview buscamos por su id la imagen
        val logo: ImageView =view.findViewById(R.id.imgsp)
        //ahora el texto
        val texto: TextView =view.findViewById(R.id.textsp)
        //necesitamos la marca en la posicion actual
        val marca:Marca=listaMarcas[position]
        logo.setImageResource(marca.logo)
        texto.text=marca.nombre

        return view
    }

}
