package com.adinda.gotrash.di

import com.adinda.gotrash.data.datasource.auth.AuthDataSource
import com.adinda.gotrash.data.datasource.auth.AuthDataSourceImpl
import com.adinda.gotrash.data.datasource.trash.TrashDataSource
import com.adinda.gotrash.data.datasource.trash.TrashDataSourceImpl
import com.adinda.gotrash.data.repository.auth.AuthRepository
import com.adinda.gotrash.data.repository.auth.AuthRepositoryImpl
import com.adinda.gotrash.data.repository.trash.TrashRepository
import com.adinda.gotrash.data.repository.trash.TrashRepositoryImpl
import com.adinda.gotrash.data.source.firebase.AuthService
import com.adinda.gotrash.data.source.firebase.AuthServiceImpl
import com.adinda.gotrash.data.source.firebase.RealTimeDatabaseService
import com.adinda.gotrash.data.source.firebase.RealTimeDatabaseServiceImpl
import com.adinda.gotrash.presentation.home.HomeViewModel
import com.adinda.gotrash.presentation.login.LoginViewModel
import com.adinda.gotrash.presentation.main.MainViewModel
import com.adinda.gotrash.presentation.profile.ProfileViewModel
import com.adinda.gotrash.presentation.profile.edit.EditNameViewModel
import com.adinda.gotrash.presentation.signup.SignupViewModel
import com.adinda.gotrash.presentation.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val firebaseModule =
        module {
            single<FirebaseAuth> {
                FirebaseAuth.getInstance()
            }
            single<AuthService> {
                AuthServiceImpl(get())
            }
            single<AuthRepository> {
                AuthRepositoryImpl(get())
            }
            single<DatabaseReference>{
                FirebaseDatabase.getInstance().getReference("tps-data")
            }
            single<RealTimeDatabaseService> {
                RealTimeDatabaseServiceImpl(get())
            }
        }

    private val datasource =
        module {
            single<AuthDataSource> { AuthDataSourceImpl(get()) }
            single<TrashDataSource> { TrashDataSourceImpl(get()) }
        }

    private val repository =
        module {
            single<AuthRepository> { AuthRepositoryImpl(get()) }
            single<TrashRepository> { TrashRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::LoginViewModel)
            viewModelOf(::SignupViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::SplashViewModel)
            viewModelOf(::ProfileViewModel)
            viewModelOf(::EditNameViewModel)

        }

    val modules =
        listOf<Module>(
            firebaseModule,
            datasource,
            repository,
            viewModelModule,
        )
}