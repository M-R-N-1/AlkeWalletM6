package com.example.alkewalletm5.model

class DepositoModel {

    fun saveDeposito(monto: Int): Boolean {
        return Deposito.realizarDeposito(monto)
    }

    fun saveRetiro(monto: Int): Boolean {
        return Deposito.realizarRetiro(monto)
    }

    val saldoActual: Int
        get() = Deposito.totalSaldo
}