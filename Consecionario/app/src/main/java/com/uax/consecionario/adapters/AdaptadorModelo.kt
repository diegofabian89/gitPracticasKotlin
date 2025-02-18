package com.uax.consecionario.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.uax.consecionario.R
import com.uax.consecionario.model.Modelo
import com.uax.consecionario.ui.DetailActivity

class AdaptadorModelo(var listaModelos:ArrayList<Modelo>,var contexto: Context): RecyclerView.Adapter<AdaptadorModelo.MyHolder>() {
    class MyHolder(itemView: View) : ViewHolder(itemView) {
        //saco los elementos del xml adaptado personalizado
        var imagen: ImageView = itemView.findViewById(R.id.imgmodelo)
        var texto: TextView = itemView.findViewById(R.id.textmodelo)
        var boton: Button = itemView.findViewById(R.id.btmodelo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        //crea el patron de la fila
        //creamos una vista usando layoutinflater le decimos el recurso  q usaremos y el contexto
        //esto se hace porque este metodo pide retornar un tipo myholder de la clase anidada creada anteriormente
        //por ello al crear una variable holder de este tipo necesitamos crear la vista como su paramentro
        var vista: View = LayoutInflater.from(contexto).inflate(R.layout.item_modelo, parent, false)
        var holder: MyHolder = MyHolder(vista)
        return holder
    }

    override fun getItemCount(): Int {
        //cuantas filas queremos
        return listaModelos.size


    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        //como se comporta cada fila
        //parametro holder es de tipo holder por lo q necesitamos decirle su elementos para asignarles su valor
        //para ello creamos una var de tipo modelo y le asignamos la posicion actual de la lista y en ella buscaremos los
        //valores a asignar al view
        var modelo: Modelo = listaModelos[position]
        holder.imagen.setImageResource(modelo.imagen)
        holder.texto.text = modelo.modelo
        //decimos los comportamientos de los elementos del view personalizado por ejemplo aqui si
        //pulsamos el boton de manera larga se elimina el modelo de la lista en la posicion actual
        holder.imagen.setOnLongClickListener {
            listaModelos.remove(modelo)
            //notificamos que se ha eliminado un elemento en la posicion actual
            notifyItemRemoved(position)
            true
        }
        holder.boton.setOnClickListener{
            val intent= Intent(contexto,DetailActivity::class.java)
            //necesitamos usar sel setflag para decirle que se abra en una nueva tarea
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            var bundle= Bundle()
            //aqui anadimos un put serializble ya q estamos pasando un modelo con todos su atributos le damos una etiqueta
            //modelo
            bundle.putSerializable("modelo",modelo)
            //ojo que al intent hay q pasarle el extra en singular no plural
            intent.putExtra("datos",bundle)
            //antes de pasar el modelo a la otra activity lo pasamos el bundle con los datos del modelo a la otra activity
            //pasamos el modelo a la otra activity
            contexto.startActivity(intent)

        }


    }
    fun filtrar(lista:ArrayList<Modelo>){
        listaModelos=lista
        notifyDataSetChanged()

    }
}