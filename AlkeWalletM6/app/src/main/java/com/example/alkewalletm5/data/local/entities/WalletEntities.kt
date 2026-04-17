package com.example.alkewalletm5.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val nombre: String,
    val password: String,
    val saldo: Int
)

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val monto: Int,
    val tipo: String,
    val fecha: Long = System.currentTimeMillis()
)