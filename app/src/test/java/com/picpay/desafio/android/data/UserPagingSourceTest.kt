package com.picpay.desafio.android.data

import androidx.paging.PagingSource.*
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.local.entities.UserEntity
import com.picpay.desafio.android.data.remote.mapper.toUserList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import io.kotlintest.shouldBe

internal class UserPagingSourceTest {

    private val userDao: UserDao = mockk(relaxed = true)
    private lateinit var pagingSource: UserPagingSource
    private val userEntityList : List<UserEntity> = listOf(
        UserEntity(0, "Jhon", "jhon_02", "https://image.jpeg"),
        UserEntity(1, "Petter", "petter_02", "https://image.jpeg"),
        UserEntity(2, "Harry", "harry_02", "https://image.jpeg"),
        UserEntity(3, "Frida", "frida_02", "https://image.jpeg"),
        UserEntity(4, "Elton", "elton_02", "https://image.jpeg"),
        UserEntity(5, "Julius", "julius_02", "https://image.jpeg")
    )

    @Before
    fun setup() {
        pagingSource = UserPagingSource(userDao = userDao)
    }

    @Test
    fun `GIVEN userPagingSource WHEN userDao getUsersPaginated is called with size 6 THEN must be return a User domain list`() =
        runBlocking {
            // GIVEN
            val userEntityListFiltered = userEntityList.filter { it.id == 0 || it.id == 1 }
            val userEntityListFilteredTwo = userEntityList.filter { it.id == 2 || it.id == 3 }

            coEvery {
                userDao.getUsersPaginated(2, 0)
            } returns userEntityListFiltered

            // WHEN
            val result = pagingSource.load(
                LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )

            coEvery {
                userDao.getUsersPaginated(2, 2)
            } returns userEntityListFilteredTwo

            val resultTwo = pagingSource.load(
                LoadParams.Refresh(
                    key = 1,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )

            // THEN
            result shouldBe LoadResult.Page(
                data = userEntityListFiltered.toUserList(),
                prevKey = null,
                nextKey = 1
            )
            resultTwo shouldBe LoadResult.Page(
                data = userEntityListFilteredTwo.toUserList(),
                prevKey = 0,
                nextKey = 2
            )
        }

    @Test
    fun `GIVEN userPagingSource WHEN load is called consecutive two times with loadSize 2 THEN must be return a User domain list`() =
        runBlocking {
            // GIVEN
            coEvery {
                userDao.getUsersPaginated(6, 0)
            } returns userEntityList

            // WHEN
            val result = pagingSource.load(
                LoadParams.Refresh(
                    key = null,
                    loadSize = 6,
                    placeholdersEnabled = false
                )
            )

            // THEN
            result shouldBe LoadResult.Page(
                data = userEntityList.toUserList(),
                prevKey = null,
                nextKey = 1
            )
        }

    @Test
    fun `GIVEN userPagingSource WHEN userDao getUserPaginated returns empty list THEN result must be empty and nextKey should be null`() =
        runBlocking {
            // GIVEN
            coEvery {
                userDao.getUsersPaginated(any(), any())
            } returns emptyList()

            // WHEN
            val result = pagingSource.load(
                LoadParams.Refresh(
                    key = null,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )

            // THEN
            result shouldBe LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )
        }

    @Test
    fun `GIVEN userPagingSource WHEN userDao getUserPaginated returns error THEN result must be LoadResult Error`() =
        runBlocking {
            // GIVEN
            val exception = Exception("Any Exception")
            coEvery {
                userDao.getUsersPaginated(any(), any())
            } throws exception

            // WHEN
            val result = pagingSource.load(
                LoadParams.Refresh(
                    key = null,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )

            // THEN
            result shouldBe LoadResult.Error(exception)
        }

    @Test
    fun `WHEN userPagingSource getRefreshKey is called THEN results must be null`() {
        // WHEN
        val result = pagingSource.getRefreshKey(mockk(relaxed = true))

        // THEN
        result shouldBe null
    }
}