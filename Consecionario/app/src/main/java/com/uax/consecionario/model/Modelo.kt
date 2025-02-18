package com.uax.consecionario.model

import java.io.Serializable

class Modelo(var modelo:String,var marca:String,var precio:Int,var cv:Int,var descripcion:String,var imagen:Int):
    Serializable {
    override fun toString(): String {
        return "Modelo: $modelo\nMarca: $marca\nPrecio: $precio\nCV: $cv\nDescripcion: $descripcion"
    }
}