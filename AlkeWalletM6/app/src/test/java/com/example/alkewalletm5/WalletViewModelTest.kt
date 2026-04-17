package com.example.alkewalletm5

import org.junit.Assert
import org.junit.Test

class WalletViewModelTest {

    @Test
    fun validarMontoNegativo() {
        val monto = -500
        val esValido = monto > 0

        Assert.assertFalse("El monto no debería ser válido", esValido)
    }

    @Test
    fun validarMontoPositivo() {
        val monto = 1000
        val esValido = monto > 0

        Assert.assertTrue("El monto debería ser válido", esValido)
    }
}