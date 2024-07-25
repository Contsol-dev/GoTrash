package com.adinda.gotrash.data.source.api.service

import com.adinda.gotrash.BuildConfig
import com.adinda.gotrash.data.source.api.model.NotificationResponseItem
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("get-notif")
    suspend fun getNotification(): List<NotificationResponseItem>

    @GET("mark-as-read")
    suspend fun markAsRead(
        @Path("id") id: Int,
    )

    companion object {
        @JvmStatic
        operator fun invoke(): ApiService {
            val okHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}