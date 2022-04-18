package com.picpay.desafio.android.di

import androidx.room.Room
import com.picpay.desafio.android.data.local.database.AppDatabase
import com.picpay.desafio.android.data.remote.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.core.CoroutineContextProvider
import com.picpay.desafio.android.domain.interactor.GetUsersUseCase
import com.picpay.desafio.android.data.remote.core.BASE_URL
import com.picpay.desafio.android.data.remote.core.ServiceFactory
import com.picpay.desafio.android.data.remote.service.UserService
import com.picpay.desafio.android.domain.interactor.FetchUsersUseCase
import com.picpay.desafio.android.ui.user.UserViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module


object PicPayDI {
    val module = module {

        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                AppDatabase.FILE_NAME
            ).fallbackToDestructiveMigration().build()
        }

        single {
            OkHttpClient.Builder().build()
        }

        single {
            ServiceFactory.createWebService(url = BASE_URL, okHttpClient = get()) as UserService
        } bind UserService::class

        factory<UserRepository> { UserRepositoryImpl(get(), get()) }

        factory {
            CoroutineContextProvider()
        }

        factory {
            GetUsersUseCase(repository = get(), contextProvider = get() )
        }

        factory {
            FetchUsersUseCase(repository = get(), contextProvider = get() )
        }

        viewModel {
            UserViewModel(getUsersUseCase = get(), fetchUsersUseCase = get())
        }
    }
}