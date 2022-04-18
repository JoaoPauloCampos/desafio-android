package com.picpay.desafio.android.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.picpay.desafio.android.data.PAGE_SIZE_DEFAULT
import com.picpay.desafio.android.data.remote.core.PicPayError
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.core.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchUsers(): Either<Unit, PicPayError>

    suspend fun getUserPaginated(
        scope: CoroutineScope,
        pagingConfig: PagingConfig = PagingConfig(
            pageSize = PAGE_SIZE_DEFAULT,
            initialLoadSize = PAGE_SIZE_DEFAULT,
            enablePlaceholders = false
        )
    ): Either<Flow<PagingData<User>>, PicPayError>
}