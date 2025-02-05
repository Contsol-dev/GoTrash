package com.adinda.gotrash.data.datasource.auth

import com.adinda.gotrash.data.mapper.toUser
import com.adinda.gotrash.data.model.User
import com.adinda.gotrash.data.source.firebase.AuthService
import java.lang.Exception


interface AuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(
        email: String,
        fullName: String,
        password: String,
    ): Boolean

    suspend fun updateProfile(fullName: String? = null): Boolean
    suspend fun doLogout(): Boolean

    fun isLoggedIn(): Boolean
    fun getCurrentUser(): User?
}

class AuthDataSourceImpl(private val service: AuthService) : AuthDataSource {
    override suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean {
        return service.doLogin(email, password)
    }

    override suspend fun doRegister(
        email: String,
        fullName: String,
        password: String,
    ): Boolean {
        return service.doRegister(email, fullName, password)
    }

    override suspend fun updateProfile(fullName: String?): Boolean {
        return service.updateProfile(fullName)
    }

    override suspend fun doLogout(): Boolean {
        return service.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return service.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return service.getCurrentUser().toUser()
    }
}
