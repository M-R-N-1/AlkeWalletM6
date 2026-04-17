package com.example.alkewalletm5

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.alkewalletm5.databinding.IngresarActivityBinding
import com.example.alkewalletm5.viewModel.WalletViewModel

class IngresarActivity : AppCompatActivity() {

    private lateinit var binding: IngresarActivityBinding

    private val viewModel: WalletViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IngresarActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIngresarDinero.setOnClickListener {
            ejecutarDeposito()
        }
    }

    private fun ejecutarDeposito() {
        val textoMonto = binding.textIngresarDinero.text.toString().trim()

        if (textoMonto.isEmpty()) {
            displayMessage("El campo no puede estar vacío")
            return
        }

        val amount = textoMonto.toIntOrNull() ?: 0

        if (amount > 0) {
            viewModel.realizarTransaccion(amount, esDeposito = true)

            displayMessage("Depósito de $$amount realizado correctamente")

            setResult(RESULT_OK)
            finish()
        } else {
            displayMessage("El monto debe ser mayor a 0")
        }
    }

    private fun displayMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}