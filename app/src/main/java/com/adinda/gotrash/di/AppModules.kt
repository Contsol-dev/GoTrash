package com.adinda.gotrash.di

import com.adinda.gotrash.data.datasource.auth.AuthDataSource
import com.adinda.gotrash.data.datasource.auth.AuthDataSourceImpl
import com.adinda.gotrash.data.repository.auth.AuthRepository
import com.adinda.gotrash.data.repository.auth.AuthRepositoryImpl
import com.adinda.gotrash.data.source.firebase.FirebaseService
import com.adinda.gotrash.data.source.firebase.FirebaseServiceImpl
import com.adinda.gotrash.presentation.home.HomeViewModel
import com.adinda.gotrash.presentation.login.LoginViewModel
import com.adinda.gotrash.presentation.main.MainViewModel
import com.adinda.gotrash.presentation.profile.ProfileViewModel
import com.adinda.gotrash.presentation.signup.SignupViewModel
import com.adinda.gotrash.presentation.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val firebaseModule =
        module {
            single<FirebaseAuth> {
                FirebaseAuth.getInstance()
            }
            single<FirebaseService> {
                FirebaseServiceImpl(get())
            }
        }

    private val datasource =
        module {
            single<AuthDataSource> { AuthDataSourceImpl(get()) }

        }

    private val repository =
        module {
            single<AuthRepository> { AuthRepositoryImpl(get()) }

        }

    private val viewModelModule =
        module {
            viewModelOf(::LoginViewModel)
            viewModelOf(::SignupViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::SplashViewModel)
            viewModelOf(::ProfileViewModel)

        }

    val modules =
        listOf<Module>(
            firebaseModule,
            datasource,
            repository,
            viewModelModule,
        )
}