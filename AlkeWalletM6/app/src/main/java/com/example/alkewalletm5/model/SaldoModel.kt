package com.example.alkewalletm5.model

object SaldoModel {
    var saldo: Int = 0

    fun saveSaldo(saldo: Int): Boolean {
        this.saldo = saldo
        return true
    }

    fun showSaldo(): String {
        return "$ $saldo"
    }
}