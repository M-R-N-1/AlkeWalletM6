package com.example.alkewalletm5.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class TransactionDTO(
    val id: Int? = null, val amount: Int, val type: String, val date: String? = null
)

interface WalletApiService {

    @GET("transactions")
    suspend fun getAllTransactions(): Response<List<TransactionDTO>>

    @POST("transactions")
    suspend fun createTransaction(@Body transaction: TransactionDTO): Response<TransactionDTO>
}