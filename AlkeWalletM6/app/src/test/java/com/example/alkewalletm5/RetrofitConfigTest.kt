package com.example.alkewalletm5

import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfigTest {

    @Test
    fun verificarUrlBase() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://wallet2-env.eba-aj2m9f3p.us-east-1.elasticbeanstalk.com/api-docs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Assert.assertTrue(retrofit.baseUrl().toString().endsWith("/"))
    }
}