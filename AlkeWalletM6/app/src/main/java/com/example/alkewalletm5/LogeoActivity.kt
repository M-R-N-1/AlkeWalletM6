package com.example.alkewalletm5

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alkewalletm5.databinding.LogeoActivityBinding
import com.example.alkewalletm5.model.UsuarioModel

class LogeoActivity : AppCompatActivity() {
    private lateinit var binding: LogeoActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LogeoActivityBinding.inflate(getLayoutInflater())
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener {
            val nombre = binding.textUsuarioNombre.getText().toString().uppercase().trim()
            val password = binding.textPassword.getText().toString().uppercase().trim()

            if (nombre.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Los campos no pueden estar vacíos", Toast.LENGTH_LONG).show()
            } else {
                UsuarioModel.saveUser(nombre)
                Toast.makeText(this, "Datos ingresados correctamente", Toast.LENGTH_LONG).show();
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                this.finish()
            }
        }
    }
}