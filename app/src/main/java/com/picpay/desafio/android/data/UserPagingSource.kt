package com.picpay.desafio.android.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.remote.mapper.toUserList
import com.picpay.desafio.android.domain.model.User
import java.lang.Exception

const val PAGE_SIZE_DEFAULT = 10

class UserPagingSource(private val userDao: UserDao) : PagingSource<Int, User>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 0
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: INITIAL_PAGE_INDEX // 0 1 2
        val pageSize = params.loadSize  // 10 10 10
        val offset = page * pageSize  // 0 10 20

        return try {
            val userList = userDao.getUsersPaginated(
                size = params.loadSize,
                offset = offset
            ).toUserList()

            val nextPage: Int? = if (userList.size == pageSize) page + 1 else null  // 1

            LoadResult.Page(
                data = userList,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = nextPage
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}