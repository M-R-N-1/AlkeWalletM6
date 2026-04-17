package com.example.alkewalletm5.data

import com.example.alkewalletm5.data.local.dao.WalletDao
import com.example.alkewalletm5.data.local.entities.UserEntity
import com.example.alkewalletm5.data.local.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

class WalletRepository(private val walletDao: WalletDao) {

    fun getUser(): Flow<UserEntity?> = walletDao.getUser()

    suspend fun insertUser(user: UserEntity) {
        walletDao.insertUser(user)
    }

    suspend fun insertTransaction(transaction: TransactionEntity) {
        walletDao.insertTransaction(transaction)
    }

    suspend fun actualizarSaldo(monto: Int) {
        walletDao.actualizarSaldo(monto)
    }

    fun getAllTransactions(): Flow<List<TransactionEntity>> = walletDao.getAllTransactions()
}