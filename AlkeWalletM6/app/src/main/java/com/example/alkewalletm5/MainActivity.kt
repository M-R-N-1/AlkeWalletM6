package com.example.alkewalletm5

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnCerrarSesion)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.hide()
        }

        val TIEMPO_ESPERA: Int = 3000 // 3 segundos

        android.os.Handler().postDelayed(
            object : Runnable {
                override fun run() {
                    val intent: Intent = Intent(this@MainActivity, LogeoActivity::class.java)
                    startActivity(intent)

                    finish()
                }
            }, TIEMPO_ESPERA.toLong()
        )
    }
}