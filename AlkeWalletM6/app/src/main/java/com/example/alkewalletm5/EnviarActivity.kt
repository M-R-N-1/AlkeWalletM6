package com.example.alkewalletm5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.alkewalletm5.databinding.EnviarActivityBinding
import com.example.alkewalletm5.viewModel.WalletViewModel

class EnviarActivity : AppCompatActivity() {

    private lateinit var binding: EnviarActivityBinding

    private val viewModel: WalletViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EnviarActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnviarDinero.setOnClickListener {
            ejecutarRetiro()
        }
    }

    private fun ejecutarRetiro() {
        val textoMonto = binding.textEnviarDinero.text.toString().trim()

        if (textoMonto.isEmpty()) {
            displayMessage("Ingresa un monto")
            return
        }

        val amount = textoMonto.toIntOrNull() ?: 0

        if (amount > 0) {
            viewModel.realizarTransaccion(amount, esDeposito = false)

            displayMessage("Envío de $$amount realizado con éxito")
            finish()
        } else {
            displayMessage("El monto debe ser mayor a 0")
        }
    }

    private fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}