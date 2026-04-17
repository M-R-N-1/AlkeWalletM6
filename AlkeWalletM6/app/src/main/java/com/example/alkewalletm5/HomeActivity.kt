package com.example.alkewalletm5

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.alkewalletm5.databinding.HomeActivityBinding
import com.example.alkewalletm5.utils.NetworkResult
import com.example.alkewalletm5.viewModel.WalletViewModel
import androidx.core.content.edit

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding

    private val viewModel: WalletViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.userProfile.observe(this) { usuario ->
            if (usuario != null) {
                binding.txtNombre.text = usuario.nombre
                binding.txtSaldo.text = "$${usuario.saldo}"
            } else {
                binding.txtNombre.text = "Cargando..."
                binding.txtSaldo.text = "$0"
            }
        }

        viewModel.apiResponse.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                }

                is NetworkResult.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnIngresarDinero.setOnClickListener {
            val intent = Intent(this, IngresarActivity::class.java)
            startActivity(intent)
        }

        binding.btnEnviarDinero.setOnClickListener {
            val intent = Intent(this, EnviarActivity::class.java)
            startActivity(intent)
        }

        binding.btnCerrar.setOnClickListener {
            Toast.makeText(this, "Sesión cerrada con éxito", Toast.LENGTH_LONG).show()

            val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
            prefs.edit { clear() }

            val intent = Intent(this, LogeoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }

    }
}