package com.picpay.desafio.android.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.picpay.desafio.android.data.UserPagingSource
import com.picpay.desafio.android.data.local.database.AppDatabase
import com.picpay.desafio.android.data.remote.core.PicPayError
import com.picpay.desafio.android.data.remote.core.requestWrapper
import com.picpay.desafio.android.data.remote.mapper.toEntityList
import com.picpay.desafio.android.data.remote.service.UserService
import com.picpay.desafio.android.domain.core.Either
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userService: UserService,
    private val database: AppDatabase
) : UserRepository {

    override suspend fun fetchUsers(): Either<Unit, PicPayError> = requestWrapper {
        userService.getUsers().also {
            database.userDao().deleteAll()
            database.userDao().insertAll(*it.toEntityList().toTypedArray())
        }
        Unit
    }

    override suspend fun getUserPaginated(
        scope: CoroutineScope,
        pagingConfig: PagingConfig
    ): Either<Flow<PagingData<User>>, PicPayError> = requestWrapper {
        Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                UserPagingSource(database.userDao())
            }
        ).flow.cachedIn(scope)
    }
}