package com.uax.calculadora

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.uax.calculadora.databinding.ActivityMainBinding
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), OnClickListener {
    // Lista para almacenar los números ingresados en la calculadora
    private var listaNumeros = mutableListOf<Double>()
    // Lista para almacenar los operadores ingresados en la calculadora
    private var listaOperadores = mutableListOf<String>()
    // Lista de botones para los operadores, inicializada de forma lazy
    private val listaOperadoresBot: List<Button> by lazy {
        listOf(
            binding.sumabutton,
            binding.restabutton,
            binding.multiplibutton,
            binding.divbutton
        )
    }
    // Lista de botones para los números, inicializada de forma lazy
    private val listaNumerosBot: List<Button> by lazy {
        listOf(
            binding.cerobutton,
            binding.unobutton,
            binding.dosbutton,
            binding.tresbutton,
            binding.cuatrobutton,
            binding.cincobutton,
            binding.seisbutton,
            binding.sietebutton,
            binding.ochobutton,
            binding.nuevebutton,
            binding.decimalbutton,


            )
    }
    // Variables auxiliares para el estado de la calculadora
    private var comprobarOperandoHorizontal: Boolean? = null
    private var comprobarOperando: Boolean? = null
    private var comprobarResultado: Boolean? = null
    // Valor actual ingresado, el resultado actual y el operador en uso
    private var valorActual: String = ""
    private var resultado: String = ""
    private val calculosPrev = StringBuilder()// Historial de cálculos
    private var operador: String = ""
    // Binding para acceder a los elementos del diseño
    private lateinit var binding: ActivityMainBinding
    // Método principal que se ejecuta al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializa el binding con el diseño asociado
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        // Configura el contenido de la actividad usando el root del binding
        setContentView(binding.root)


        // Asignar listeners a los botones de orientacion horizontal
        binding.raizbutton?.setOnClickListener(this)
        binding.porcentbutton?.setOnClickListener(this)
        binding.cuadradobutton?.setOnClickListener(this)
        // Asignar listeners a los botones de números
        listaNumerosBot.forEach { boton ->
            boton.setOnClickListener { agregarNumeros(boton.text.toString()) }
        }
        // Asignar listeners a los botones de operadores
        listaOperadoresBot.forEach { acciones ->
            acciones.setOnClickListener { asigOperando(acciones.text.toString()) }
        }// Asignar listeners a botones adicionales

        binding.cambiosignotbutton.setOnClickListener(this)
        binding.resultbutton.setOnClickListener(this)
        binding.cbutton.setOnClickListener(this)
        binding.acbutton.setOnClickListener(this)

    }

    /**
     * Método para agregar números al valor actual.
     * Verifica si es un número válido, maneja decimales y actualiza el estado.
     */
    private fun agregarNumeros(numero: String) {
        // Agrega "0" al principio si el número empieza con un punto (ej. ".5" -> "0.5")
        val nu = if (numero.startsWith(".") && valorActual.isEmpty()) "0$numero" else numero

        // Si ya hay un punto decimal, no permite agregar otro.
        if (numero == "." && valorActual.contains(".")) {
            Toast.makeText(this, "Ya existe un decimal", Toast.LENGTH_SHORT).show()
            return
        }

        // Actualiza el valor actual dependiendo del estado.
        valorActual =
            if (comprobarResultado == true) nu // Si hay un resultado previo, reinicia con el nuevo número
            else if (valorActual == "0.0") nu // Si el valor actual es "0.0", lo reemplaza
            else valorActual + nu // De lo contrario, concatena el nuevo número al valor actual

        // Si un operador horizontal está activo, añade el número directamente a la lista
        if (comprobarOperandoHorizontal == true) {
            listaNumeros.add(nu.toDouble())
            comprobarOperandoHorizontal = false
        }

        // Actualiza el texto en pantalla y los estados
        actualizarResultado(valorActual)
        comprobarOperando = false
        comprobarResultado = false
    }

    /**
     * Método para asignar un operador al cálculo actual.
     * Actualiza las listas y el historial según el operador ingresado.
     */
    private fun asigOperando(op: String) {
        when {
            // Caso: hay un número y no hay operador horizontal activo
            valorActual.isNotEmpty() && comprobarOperandoHorizontal != true -> {
                if (comprobarOperando != true) {
                    listaNumeros.add(valorActual.toDouble()) // Agrega el número actual a la lista
                    operador = op // Actualiza el operador actual
                    listaOperadores.add(operador) // Lo agrega a la lista de operadores
                    calculosPrev.append("$valorActual $operador ") // Lo añade al historial
                    valorActual = "" // Limpia el valor actual para el siguiente número
                    comprobarOperando = true // Marca que ya hay un operador
                    comprobarResultado = false // Marca que aún no se ha calculado el resultado
                }
            }
            // Caso: el operador ya existe, se actualiza
            comprobarOperando == true -> {
                listaOperadores.removeAt(listaOperadores.lastIndex) // Elimina el operador anterior
                operador = op // Actualiza el operador
                listaOperadores.add(operador)
                calculosPrev.delete(calculosPrev.length - 3, calculosPrev.length) // Actualiza el historial
                calculosPrev.append(" $operador ")
            }
            // Caso: el operador horizontal está activo
            comprobarOperandoHorizontal == true -> {
                operador = op
                listaOperadores.add(operador)
                calculosPrev.append(" $valorActual $operador ")
                comprobarResultado = true
            }
            else -> {
                // Si no hay números ingresados, muestra un mensaje de error.
                Toast.makeText(this, "Introduce un valor", Toast.LENGTH_SHORT).show()
                return
            }
        }
        // Actualiza el historial mostrado al usuario.
        actualizarHistorial(calculosPrev.toString())
    }
    /**
     * Método para calcular el resultado final del cálculo.
     * Maneja prioridad de operadores y realiza las operaciones en orden.
     */

    private fun calculoResult() {
        if ((comprobarOperando != true && listaNumeros.isEmpty())) {
            valorActual = ""
            Toast.makeText(this, "No se puede calcular introduce valores", Toast.LENGTH_SHORT)
                .show()

            return
        } else if (comprobarResultado == false && comprobarOperando == true) {
            Toast.makeText(this, "No se puede calcular introduce valores", Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            // Continúa con el cálculo si todo es válido
            listaNumeros.add(valorActual.toDouble())
            calculosPrev.append(valorActual)

            actualizarHistorial(calculosPrev.toString())
            // Realiza los cálculos respetando la prioridad de operadores multiplicacion division y luego suma resta
            // Verifica si hay un operador activo y si la lista de números tiene al menos dos elementos
            if (operador.isNotEmpty() && listaNumeros.size > 1) {
                var oper: Double // Variable para almacenar el resultado final

                // Listas temporales para manejar números y operadores durante las operaciones
                val listaNumerosFinales = mutableListOf<Double>()
                val listaOperadoresFinales = mutableListOf<String>()

                var i = 0 // Índice inicial para iterar sobre los números
                listaNumerosFinales.add(listaNumeros[i]) // Agrega el primer número a la lista final

                // Este bucle procesa los operadores, priorizando multiplicación/división antes que suma/resta
                for (operaciones in listaOperadores) {
                    i++ // Incrementa el índice para acceder al siguiente número
                    if (i < listaNumeros.size) { // Verifica que no se exceda el tamaño de la lista de números
                        //accede uno a uno a los operadores
                        when (operaciones) {
                            "x" -> {
                                // Multiplica el último número en la lista final temporal por el número guardado en la lista de números
                                //y el resultado lo agrega a la lista final
                                //con removelast usa el ultimo numero de la lista y lo elimina
                                val result = listaNumerosFinales.removeLast() * listaNumeros[i]
                                listaNumerosFinales.add(result) // Agrega el resultado a la lista final
                            }

                            "÷" -> {
                                // Manejo de división, con validación para evitar dividir por cero
                                if (listaNumeros[i] == 0.0 || listaNumerosFinales[listaNumerosFinales.lastIndex] == 0.0) {
                                    Toast.makeText(
                                        this,
                                        "No se puede dividir por cero", // Mensaje de error
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // Actualiza el historial con un mensaje de error y desactiva botones
                                    calculosPrev.append("ERROR (Division por cero) Presione C para limpiar")
                                    actualizarHistorial(calculosPrev.toString())
                                    deshabilitarBotones()
                                    return // Finaliza el cálculo
                                } else {
                                    // Divide el último número en la lista final por el número actual
                                    val result = listaNumerosFinales.removeLast() / listaNumeros[i]
                                    listaNumerosFinales.add(result) // Agrega el resultado a la lista final
                                }
                            }

                            else -> {
                                // Si el operador no es multiplicación/división, agrega el número actual
                                listaNumerosFinales.add(listaNumeros[i])
                                listaOperadoresFinales.add(operaciones) // Guarda el operador para procesar después
                            }
                        }
                    }
                }

                // Si hay operadores restantes (suma/resta), procesa los números finales
                if (listaOperadoresFinales.size > 0) {
                    i = 0 // Reinicia el índice
                    oper = listaNumerosFinales[i] // Toma el primer número como base del cálculo
                    for (operaciones in listaOperadoresFinales) {
                        i++ // Avanza al siguiente número
                        when (operaciones) {
                            "+" -> {
                                oper += listaNumerosFinales[i] // Realiza la suma
                            }

                            "-" -> {
                                oper -= listaNumerosFinales[i] // Realiza la resta
                            }
                        }
                    }
                } else {
                    // Si no hay operadores restantes, el resultado es el único número en la listafinal
                    oper = listaNumerosFinales[0]
                }


            // Se actualiza el historial y se muestra el resultado
                resultado = oper.toString()// Aquí se coloca el resultado calculado
                valorActual = resultado
                calculosPrev.append(" = $resultado \n ")
                listaNumerosFinales.clear()
                actualizarHistorial(calculosPrev.toString())
                // Limpia listas y estados después de calcular
                listaOperadores.clear()
                listaOperadoresFinales.clear()
                listaNumeros.clear()
                comprobarResultado = true
            }
        }

    }
    // Método para actualizar resultado mostrado en pantalla
    private fun actualizarResultado(valor: String) {
        binding.textresultado.text = valor
    }

    // Método para cambiar el signo del número actual
    private fun cambioSigno() {
        val num: Double
        if (valorActual.isNotEmpty()) {
            // Convierte el valor actual a negativo o positivo multiplicando por -1
            num = valorActual.toDouble() * -1
            valorActual = num.toString()// Actualiza el valor actual con el nuevo signo
            actualizarResultado(valorActual) // Muestra el resultado en la pantalla
        } else {
            // Muestra un mensaje si no hay valor actual
            Toast.makeText(
                this,
                "No se puede cambiar el signo introduce numero",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

    }
    // Método para actualizar el historial mostrado en pantalla
    private fun actualizarHistorial(valor: String) {

        binding.textHistorial.text = valor
    }
    // Método para la función del botón "C" (limpiar todo)
    private fun cButtom() {
        actualizarResultado("0.0") // Restablece el resultado mostrado a "0.0"
        actualizarHistorial("") // Limpia el historial mostrado
        if (deshabilitarBotones()) {
            habilitarBotones() // Habilita los botones en caso de que estén deshabilitados
        }
        comprobarOperando = false // Reinicia el estado de la operación
        valorActual = "" // Limpia el valor actual
        operador = "" // Limpia el operador actual
        listaNumeros.clear() // Limpia la lista de números
        calculosPrev.clear() // Limpia el historial de cálculos
    }

    // Método para la función del botón "CE" (borrar último dígito)
    private fun ceButton() {
        if (valorActual.isNotEmpty()) {
            // Elimina el último carácter del valor actual
            valorActual = valorActual.dropLast(1)
            actualizarResultado(valorActual) // Actualiza el resultado en pantalla
        }
        if (calculosPrev.isNotEmpty()) {
            // Elimina el último carácter del historial
            calculosPrev.deleteCharAt(calculosPrev.length - 1)
            calculosPrev.append("\n") // Añade un salto de línea al final del historial
        }
    }
    // Método para deshabilitar los botones de la calculadora
    private fun deshabilitarBotones(): Boolean {
        //list of not null porque lo botones la estar solo en horizontal pueden no existir en vertical
        // Lista de botones que están disponibles solo en modo horizontal
        val listaBotonesHorizontales = listOfNotNull(
            binding.raizbutton,
            binding.cuadradobutton,
            binding.porcentbutton
        )
        // Lista de botones adicionales
        val listaBotonesExtra =
            listOf(binding.resultbutton, binding.cambiosignotbutton, binding.acbutton)
        // Combina todos los botones en una sola lista
        val listaTotalBotones =
            listaBotonesExtra + listaOperadoresBot + listaNumerosBot + listaBotonesHorizontales
        listaTotalBotones.forEach {
            it.isEnabled = false // Deshabilita el botón
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.fondodesactivo)) // Cambia el color del botón
        }
        return true // Indica que los botones están deshabilitados
    }

    private fun habilitarBotones() {
        //list of not null porque lo botones la estar solo en horizontal pueden no existir en vertical
        val listaBotonesHorizontales = listOfNotNull(
            binding.raizbutton,
            binding.cuadradobutton,
            binding.porcentbutton
        )
        val listaBotonesExtra = listOf(
            binding.resultbutton,
            binding.cambiosignotbutton,
            binding.acbutton,
            binding.cbutton
        )
        val listaTotalBotones =
            listaBotonesExtra + listaOperadoresBot + listaNumerosBot + listaBotonesHorizontales
        listaTotalBotones.forEach {
            it.isEnabled = true// Habilita el botón
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.verdebuton))
        }
    }

    // Método que gestiona los clics en los botones de la calculadora
    override fun onClick(v: View?) {
        when (v?.id) {
            binding.cambiosignotbutton.id -> cambioSigno() // Cambiar el signo del número actual
            binding.resultbutton.id -> {
                calculoResult() // Calcula el resultado
                actualizarResultado(resultado) // Muestra el resultado en pantalla
            }
            binding.cbutton.id -> cButtom() // Limpia todo
            binding.acbutton.id -> ceButton() // Elimina el último dígito
            binding.raizbutton?.id -> {
                // Calcula la raíz cuadrada del valor actual si está disponible
                if (valorActual.isNotEmpty()) {
                    actualizarResultado(valorActual)
                    val resultadoRaiz = sqrt(valorActual.toDouble()) // Calcula la raíz cuadrada
                    listaNumeros.add(resultadoRaiz) // Agrega el resultado a la lista de números
                    calculosPrev.append(" √ $valorActual = ") // Agrega el cálculo al historial
                    actualizarHistorial(calculosPrev.toString())
                    valorActual = resultadoRaiz.toString()
                    actualizarResultado(valorActual)
                    comprobarOperandoHorizontal = true // Marca que un operador horizontal está activo
                } else {
                    Toast.makeText(
                        this,
                        "Introduce un valor para calcular la raíz",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.cuadradobutton?.id -> {
                // Calcula el cuadrado del valor actual si está disponible
                if (valorActual.isNotEmpty()) {
                    actualizarResultado(valorActual)
                    val resultadoPotencia = valorActual.toDouble() * valorActual.toDouble() // Calcula el cuadrado
                    listaNumeros.add(resultadoPotencia)
                    calculosPrev.append(" ( $valorActual )² = ") // Agrega el cálculo al historial
                    actualizarHistorial(calculosPrev.toString())
                    valorActual = resultadoPotencia.toString()
                    actualizarResultado(valorActual)
                    comprobarOperandoHorizontal = true
                } else {
                    Toast.makeText(
                        this,
                        "Introduce un valor para calcular la potencia",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.porcentbutton?.id -> {
                // Calcula el porcentaje del valor actual si está disponible
                if (valorActual.isNotEmpty()) {
                    actualizarResultado(valorActual)
                    val resultadoPotencia = valorActual.toDouble() / 100 // Calcula el porcentaje
                    listaNumeros.add(resultadoPotencia)
                    calculosPrev.append("  % $valorActual = ")
                    actualizarHistorial(calculosPrev.toString())
                    valorActual = resultadoPotencia.toString()
                    actualizarResultado(valorActual)
                    comprobarOperandoHorizontal = true
                } else {
                    Toast.makeText(
                        this,
                        "Introduce un valor para calcular el porcentaje",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    // Método para guardar el estado de la actividad
    // Guardado y restauración del estado en caso de rotación de pantalla
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("valorActual", valorActual)
        outState.putString("resultado", resultado)
        outState.putString("historial", calculosPrev.toString())

    }
    // Método para restaurar el estado de la actividad
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        valorActual = savedInstanceState.getString("valorActual") ?: ""
        resultado = savedInstanceState.getString("resultado") ?: ""
        val historial = savedInstanceState.getString("historial") ?: ""
        calculosPrev.clear()
        //compruebo si  historial tiene valor si no con let hago la funcion de append it que seria lo que tiene historial
        //y lo meto en calculosPrev
        historial.let { calculosPrev.append(it) } // Agrega el historial restaurado
        actualizarResultado(valorActual) // Actualiza el resultado mostrado
        actualizarHistorial(calculosPrev.toString()) // Actualiza el historial mostrado
    }
}

