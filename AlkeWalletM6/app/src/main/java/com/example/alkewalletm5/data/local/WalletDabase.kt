package com.example.alkewalletm5.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alkewalletm5.data.local.dao.WalletDao
import com.example.alkewalletm5.data.local.entities.TransactionEntity
import com.example.alkewalletm5.data.local.entities.UserEntity

@Database(
    entities = [UserEntity::class, TransactionEntity::class],
    version = 4,
    exportSchema = false
)
abstract class WalletDatabase : RoomDatabase() {

    abstract fun walletDao(): WalletDao

    companion object {
        @Volatile
        private var INSTANCE: WalletDatabase? = null

        fun getDatabase(context: Context): WalletDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WalletDatabase::class.java,
                    "wallet_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}