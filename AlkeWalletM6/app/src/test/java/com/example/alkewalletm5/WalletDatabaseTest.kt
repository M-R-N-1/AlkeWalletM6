package com.example.alkewalletm5

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.alkewalletm5.data.local.WalletDatabase
import com.example.alkewalletm5.data.local.dao.WalletDao
import com.example.alkewalletm5.data.local.entities.UserEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WalletDatabaseTest {

    private lateinit var dao: WalletDao
    private lateinit var db: WalletDatabase

    @Before
    fun crearDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WalletDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.walletDao()
    }

    @After
    fun cerrarDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertarYLeerUsuario() = runBlocking {
        val usuario = UserEntity(id = 1, nombre = "Prueba", password = "123", saldo = 1000)
        dao.insertUser(usuario)

        val userResult = dao.getUser().first()

        Assert.assertNotNull(userResult)
        Assert.assertEquals("Prueba", userResult?.nombre)
    }
}