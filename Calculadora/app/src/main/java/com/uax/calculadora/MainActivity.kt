package com.uax.calculadora
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.uax.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {
    private var listaNumeros = mutableListOf<Double>()
    private var listaOperadores = mutableListOf<String>()
    private val listaOperadoresBot: List<Button> by lazy {
        listOf(
            binding.sumabutton,
            binding.restabutton,
            binding.multiplibutton,
            binding.divbutton
        )
    }
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
            binding.decimalbutton
        )
    }

    private var comprobarOperando: Boolean? = null
    private var comprobarResultado: Boolean? = null
    private var valorActual: String = ""
    private var resultado: String = ""
    private val calculosPrev = StringBuilder()
    private var operador: String = ""
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        listaNumerosBot.forEach { boton ->
            boton.setOnClickListener { agregarNumeros(boton.text.toString()) }
        }
        listaOperadoresBot.forEach { acciones ->
            acciones.setOnClickListener { asigOperando(acciones.text.toString()) }
        }
        binding.cambiosignotbutton.setOnClickListener(this)
        binding.resultbutton.setOnClickListener(this)
        binding.cbutton.setOnClickListener(this)
        binding.acbutton.setOnClickListener(this)

    }

    private fun agregarNumeros(numero: String) {

        val nu = if (numero.startsWith(".") && valorActual.isEmpty()) "0$numero" else numero
        if (numero == "." && valorActual.contains(".")) {
            Toast.makeText(this, "Ya existe un decimal", Toast.LENGTH_SHORT).show()
            return
        }
        valorActual = if (comprobarResultado == true) nu else (valorActual + nu)

        actualizarResultado(valorActual)
        comprobarOperando = false
        comprobarResultado = false

    }


    private fun asigOperando(op: String) {
        if (valorActual.isNotEmpty()) {
            if (comprobarOperando != true) {
                listaNumeros.add(valorActual.toDouble())
                operador = op
                listaOperadores.add(operador)
                calculosPrev.append("$valorActual $operador ")
                valorActual = ""
                comprobarOperando = true
            } else {
                listaOperadores.removeAt(listaOperadores.lastIndex)
                operador = op
                listaOperadores.add(operador)
                calculosPrev.delete(calculosPrev.length - 3, calculosPrev.length)
                calculosPrev.append(" $operador ")
            }
            actualizarHistorial(calculosPrev.toString())
        } else {
            Toast.makeText(this, "Introduce un valor", Toast.LENGTH_SHORT).show()
            return
        }
    }

    private fun calculoResult() {
        if (comprobarOperando != true && listaNumeros.isEmpty()) {
            valorActual = "0.0"
            Toast.makeText(this, "No se puede calcular introduce valores", Toast.LENGTH_SHORT).show()

            return
        } else {

            listaNumeros.add(valorActual.toDouble())
            calculosPrev.append(valorActual)

            actualizarHistorial(calculosPrev.toString())
            if (operador.isNotEmpty() && listaNumeros.size > 1) {
                var oper: Double

                var listaNumerosFinales = mutableListOf<Double>()
                var listaOperadoresFinales = mutableListOf<String>()
                var i = 0
                listaNumerosFinales.add(listaNumeros[i])
                for (operaciones in listaOperadores) {
                    i++
                    if (i < listaNumeros.size) {
                        when (operaciones) {

                            "x" -> {
                                println(listaOperadores)
                                val result = listaNumerosFinales.removeLast() * listaNumeros[i]
                                listaNumerosFinales.add(result)

                            }

                            "÷" -> {

                                if (listaNumeros[i] == 0.0 || listaNumerosFinales[listaNumerosFinales.lastIndex] == 0.0) {
                                    Toast.makeText(
                                        this,
                                        "No se puede dividir por cero",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    calculosPrev.append("ERROR (Division por cero) Presione C para limpiar")
                                    actualizarHistorial(calculosPrev.toString())
                                    deshabilitarBotones()
                                    return


                                } else {
                                    val result = listaNumerosFinales.removeLast() / listaNumeros[i]
                                    listaNumerosFinales.add(result)

                                }

                            }

                            else -> {
                                listaNumerosFinales.add(listaNumeros[i])
                                listaOperadoresFinales.add(operaciones)
                            }
                        }
                    }
                }
                if (listaOperadoresFinales.size > 0) {
                    i = 0
                    oper = listaNumerosFinales[i]
                    for (operaciones in listaOperadoresFinales) {
                        i++
                        when (operaciones) {
                            "+" -> {
                                oper += listaNumerosFinales[i]
                            }

                            "-" -> {
                                oper -= listaNumerosFinales[i]

                            }
                        }
                    }
                } else {
                    oper = listaNumerosFinales[0]
                }
                resultado = oper.toString()
                valorActual = resultado
                calculosPrev.append(" = $resultado \n ")
                listaNumerosFinales.clear()
                actualizarHistorial(calculosPrev.toString())
                listaOperadores.clear()
                listaOperadoresFinales.clear()
                listaNumeros.clear()
                comprobarResultado = true
            }
        }

    }

    private fun actualizarResultado(valor: String) {
        binding.textresultado.text = valor
    }

    private fun cambioSigno() {
        val num: Double
        if (valorActual.isNotEmpty()) {
            num = valorActual.toDouble() * -1
            valorActual = num.toString()
            actualizarResultado(valorActual)
        } else {
            Toast.makeText(
                this,
                "No se puede cambiar el signo introduce numero",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

    }

    private fun actualizarHistorial(valor: String) {

        binding.textHistorial.text = valor
    }

    private fun cButtom() {

        actualizarResultado("0.0")
        actualizarHistorial("")
        if (deshabilitarBotones()) {
            habilitarBotones()
        }
        valorActual = ""
        operador = ""
        listaNumeros.clear()
        calculosPrev.clear()
    }

    private fun ceButton() {

        if (valorActual.isNotEmpty()) {
            valorActual = valorActual.dropLast(1)

            actualizarResultado(valorActual)
        }
        if (calculosPrev.isNotEmpty()) {
            calculosPrev.deleteCharAt(calculosPrev.length - 1)
            calculosPrev.append("\n")

        }

    }

    private fun deshabilitarBotones(): Boolean {
        val listaBotonesExtra =
            listOf(binding.resultbutton, binding.cambiosignotbutton, binding.acbutton)
        val listaTotalBotones = listaBotonesExtra + listaOperadoresBot + listaNumerosBot
        listaTotalBotones.forEach {
            it.isEnabled = false
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.fondodesactivo))
        }
        return true
    }

    private fun habilitarBotones() {
        val listaBotonesExtra = listOf(
            binding.resultbutton,
            binding.cambiosignotbutton,
            binding.acbutton,
            binding.cbutton
        )
        val listaTotalBotones = listaBotonesExtra + listaOperadoresBot + listaNumerosBot
        listaTotalBotones.forEach {
            it.isEnabled = true
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.verdebuton))
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.cambiosignotbutton.id -> {
                cambioSigno()
            }

            binding.resultbutton.id -> {
                calculoResult()
                actualizarResultado(resultado)
            }

            binding.cbutton.id -> {
                cButtom()
            }

            binding.acbutton.id -> {
                ceButton()

            }

        }


    }
}

