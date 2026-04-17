package com.example.alkewalletm5.model

object UsuarioModel {
    var nombre: String = ""

    fun saveUser(nombre: String): Boolean {
        this.nombre = nombre
        return true
    }

    fun showUser(): String {
        return nombre
    }
}