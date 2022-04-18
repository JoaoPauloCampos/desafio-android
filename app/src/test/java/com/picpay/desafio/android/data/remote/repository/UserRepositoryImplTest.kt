package com.picpay.desafio.android.data.remote.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.picpay.desafio.android.data.local.database.AppDatabase
import com.picpay.desafio.android.data.remote.mapper.toEntityList
import com.picpay.desafio.android.data.remote.model.UserRemote
import com.picpay.desafio.android.data.remote.service.UserService
import com.picpay.desafio.android.domain.repository.UserRepository
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private val service: UserService = mockk(relaxed = true, relaxUnitFun = true)
    private val appDatabase: AppDatabase = mockk(relaxed = true, relaxUnitFun = true)
    private lateinit var userRepository: UserRepository

    private val userRemoteList : List<UserRemote> = listOf(
        UserRemote(id = 0, name =  "Jhon", username =  "jhon_02", img = "https://image.jpeg"),
        UserRemote(id = 1, name =  "Petter", username =  "petter_02", img = "https://image.jpeg"),
        UserRemote(id = 2, name =  "Harry", username =  "harry_02", img = "https://image.jpeg"),
        UserRemote(id = 3, name =  "Frida", username =  "frida_02", img = "https://image.jpeg"),
        UserRemote(id = 4, name =  "Elton", username =  "elton_02", img = "https://image.jpeg"),
        UserRemote(id = 5, name =  "Julius", username =  "julius_02", img = "https://image.jpeg")
    )
    
    @Before
    fun setup() {
        userRepository = UserRepositoryImpl(service, appDatabase)
    }

    @Test
    fun `GIVEN userRepository WHEN fetchUsers() is requested with success THEN the user list must be persisted on database`() =
        runBlocking {
            // GIVEN
            coEvery { service.getUsers() } returns userRemoteList

            // WHEN
            val result = userRepository.fetchUsers()

            // THEN
            val userDao = appDatabase.userDao()
            coVerify(exactly = 1) { userDao.deleteAll() }
            coVerify(exactly = 1) {
                userDao.insertAll(*userRemoteList.toEntityList().toTypedArray())
            }
            result.isSuccess shouldBe true
            result.isFailure shouldBe false
        }

    @Test
    fun `GIVEN userRepository WHEN service getUsers() throws exception THEN result must be Failure`() =
        runBlocking {
            // GIVEN
            coEvery { service.getUsers() } throws Exception("Any Exception")

            // WHEN
            val result = userRepository.fetchUsers()

            // THEN
            val userDao = appDatabase.userDao()
            coVerify(exactly = 0) { userDao.deleteAll() }
            coVerify(exactly = 0) { userDao.insertAll(any()) }

            result.isSuccess shouldBe false
            result.isFailure shouldBe true
        }

    @Test
    fun `GIVEN userRepository WHEN service getUsers() returns invalid response THEN result must be Failure`() = runBlocking {
        val userJsonResult = "[{\"img\":null,\"name\":null,\"id\":0,\"username\":null}]"
        val itemType = object : TypeToken<List<UserRemote>>() {}.type
        val userRemoteList = Gson().fromJson<List<UserRemote>>(userJsonResult, itemType)

        coEvery { service.getUsers() } returns userRemoteList

        // WHEN
        val result = userRepository.fetchUsers()

        // THEN
        result.isSuccess shouldBe false
        result.isFailure shouldBe true
    }

    @Test
    fun `GIVEN userRepository WHEN service getUserPaginated() is called THEN result must be Success`() = runBlocking {
        // WHEN
        val result = userRepository.getUserPaginated(scope = this)

        // THEN
        result.isSuccess shouldBe true
        result.isFailure shouldBe false
    }
}