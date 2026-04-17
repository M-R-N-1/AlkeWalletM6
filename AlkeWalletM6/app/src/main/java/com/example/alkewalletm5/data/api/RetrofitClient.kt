package com.example.alkewalletm5.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL =
        "http://wallet2-env.eba-aj2m9f3p.us-east-1.elasticbeanstalk.com/api-docs/"

    val instance: WalletApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(WalletApiService::class.java)
    }
}