package com.example.alkewalletm5.data.local.dao

import androidx.room.*
import com.example.alkewalletm5.data.local.entities.TransactionEntity
import com.example.alkewalletm5.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUser(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Query("SELECT * FROM transactions ORDER BY fecha DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("UPDATE user_profile SET saldo = saldo + :monto WHERE id = 1")
    suspend fun actualizarSaldo(monto: Int)
}