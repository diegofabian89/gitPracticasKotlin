package com.uax.practica2

import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.uax.practica2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.text.
        binding.checkbox.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView){

        }
    }
}