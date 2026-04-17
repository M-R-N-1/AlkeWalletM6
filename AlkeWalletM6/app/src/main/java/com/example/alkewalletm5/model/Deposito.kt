package com.example.alkewalletm5.model

object Deposito {
    var totalSaldo: Int = 0
        private set

    fun realizarDeposito(monto: Int): Boolean {
        if (monto > 0) {
            totalSaldo += monto
            return true
        }
        return false
    }

    fun realizarRetiro(monto: Int): Boolean {
        if (monto > 0 && monto <= totalSaldo) {
            totalSaldo -= monto
            return true
        }
        return false
    }
}