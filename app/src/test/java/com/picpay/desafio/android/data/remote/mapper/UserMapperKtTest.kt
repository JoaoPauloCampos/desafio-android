package com.picpay.desafio.android.data.remote.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.picpay.desafio.android.data.local.entities.UserEntity
import com.picpay.desafio.android.data.remote.model.UserRemote
import io.kotlintest.shouldBe
import org.junit.Test

class UserMapperKtTest {

    @Test(expected = NullPointerException::class)
    fun `GIVEN UserRemote WHEN toDomainList is called with invalid json THEN throws NullPointerException`() {
        // GIVEN
        val userJsonResult = "[{\"img\":null,\"name\":null,\"id\":0,\"username\":null}]"
        val itemType = object : TypeToken<List<UserRemote>>() {}.type
        val userRemoteList = Gson().fromJson<List<UserRemote>>(userJsonResult, itemType)

        // WHEN
        userRemoteList.toDomainList()

        // THEN throws NullPointerException
    }

    @Test
    fun `GIVEN UserRemote WHEN toDomainList is called with valid json THEN result must be UserList`() {
        // GIVEN
        val userJsonResult = "[{\"img\":\"https://img.jpeg\",\"name\":\"Jhon\",\"id\":0,\"username\":\"jhon_02\"}]"
        val itemType = object : TypeToken<List<UserRemote>>() {}.type
        val userRemoteList = Gson().fromJson<List<UserRemote>>(userJsonResult, itemType)

        // WHEN
        val result = userRemoteList.toDomainList()

        // THEN
        result.size shouldBe 1
        with(result) {
            get(0).imageUrl shouldBe "https://img.jpeg"
            get(0).name shouldBe "Jhon"
            get(0).userName shouldBe "jhon_02"
        }
    }

    @Test(expected = NullPointerException::class)
    fun `GIVEN UserRemote WHEN toDomain is called with invalid json THEN throws NullPointerException`() {
        // GIVEN
        val userJsonResult = "{\"img\":null,\"name\":null,\"id\":0,\"username\":null}"
        val userRemote = Gson().fromJson(userJsonResult, UserRemote::class.java)

        // WHEN
        userRemote.toDomain()

        // THEN throws NullPointerException
    }

    @Test
    fun `GIVEN UserRemote WHEN toDomain is called with valid json THEN result must be UserList`() {
        // GIVEN
        val userJsonResult = "{\"img\":\"https://img.jpeg\",\"name\":\"Jhon\",\"id\":0,\"username\":\"jhon_02\"}"
        val userRemote = Gson().fromJson(userJsonResult, UserRemote::class.java)

        // WHEN
        val result = userRemote.toDomain()

        // THEN
        with(result) {
            imageUrl shouldBe "https://img.jpeg"
            name shouldBe "Jhon"
            userName shouldBe "jhon_02"
        }
    }

    @Test(expected = NullPointerException::class)
    fun `GIVEN UserRemote WHEN toEntityList is called with invalid json THEN throws NullPointerException`() {
        // GIVEN
        val userJsonResult = "[{\"img\":null,\"name\":null,\"id\":0,\"username\":null}]"
        val itemType = object : TypeToken<List<UserRemote>>() {}.type
        val userRemoteList = Gson().fromJson<List<UserRemote>>(userJsonResult, itemType)

        // WHEN
        userRemoteList.toEntityList()

        // THEN throws NullPointerException
    }

    @Test
    fun `GIVEN UserRemote WHEN toEntityList is called with valid json THEN result must be UserEntityList`() {
        // GIVEN
        val userJsonResult = "[{\"img\":\"https://img.jpeg\",\"name\":\"Jhon\",\"id\":0,\"username\":\"jhon_02\"}]"
        val itemType = object : TypeToken<List<UserRemote>>() {}.type
        val userRemoteList = Gson().fromJson<List<UserRemote>>(userJsonResult, itemType)

        // WHEN
        val result = userRemoteList.toEntityList()

        // THEN
        result.size shouldBe 1
        with(result) {
            get(0).image shouldBe "https://img.jpeg"
            get(0).name shouldBe "Jhon"
            get(0).userName shouldBe "jhon_02"
            get(0).id shouldBe 0
        }
    }

    @Test(expected = NullPointerException::class)
    fun `GIVEN UserRemote WHEN toEntity is called with invalid json THEN throws NullPointerException`() {
        // GIVEN
        val userJsonResult = "{\"img\":null,\"name\":null,\"id\":0,\"username\":null}"
        val userRemote = Gson().fromJson(userJsonResult, UserRemote::class.java)

        // WHEN
        userRemote.toEntity()

        // THEN throws NullPointerException
    }

    @Test
    fun `GIVEN UserRemote WHEN toEntity is called with valid json THEN result must be User`() {
        // GIVEN
        val userJsonResult = "{\"img\":\"https://img.jpeg\",\"name\":\"Jhon\",\"id\":0,\"username\":\"jhon_02\"}"
        val userRemote = Gson().fromJson(userJsonResult, UserRemote::class.java)

        // WHEN
        val result = userRemote.toEntity()

        // THEN
        with(result) {
            image shouldBe "https://img.jpeg"
            name shouldBe "Jhon"
            userName shouldBe "jhon_02"
            id shouldBe 0
        }
    }

    @Test(expected = NullPointerException::class)
    fun `GIVEN UserEntity WHEN toUserList() is called with invalid json THEN throws NullPointerException`() {
        // GIVEN
        val userJsonResult = "[{\"img\":null,\"name\":null,\"id\":0,\"username\":null}]"
        val itemType = object : TypeToken<List<UserEntity>>() {}.type
        val userRemoteList = Gson().fromJson<List<UserEntity>>(userJsonResult, itemType)

        // WHEN
        userRemoteList.toUserList()

        // THEN throws NullPointerException
    }

    @Test
    fun `GIVEN UserEntity WHEN toUserList is called with valid json THEN result must be UserList`() {
        // GIVEN
        val userJsonResult = "[{\"image\":\"https://img.jpeg\",\"name\":\"Jhon\",\"id\":0,\"userName\":\"jhon_02\"}]"
        val itemType = object : TypeToken<List<UserEntity>>() {}.type
        val userEntityList = Gson().fromJson<List<UserEntity>>(userJsonResult, itemType)

        // WHEN
        val result = userEntityList.toUserList()

        // THEN
        result.size shouldBe 1
        with(result) {
            get(0).imageUrl shouldBe "https://img.jpeg"
            get(0).name shouldBe "Jhon"
            get(0).userName shouldBe "jhon_02"
        }
    }

    @Test(expected = NullPointerException::class)
    fun `GIVEN UserEntity WHEN toDomain() is called with invalid json THEN throws NullPointerException`() {
        // GIVEN
        val userJsonResult = "{\"image\":null,\"name\":null,\"id\":0,\"userName\":null}"
        val userEntity = Gson().fromJson(userJsonResult, UserEntity::class.java)

        // WHEN
        userEntity.toDomain()

        // THEN throws NullPointerException
    }

    @Test
    fun `GIVEN UserEntity WHEN toDomain() is called with valid json THEN result must be User`() {
        // GIVEN
        val userJsonResult = "{\"image\":\"https://img.jpeg\",\"name\":\"Jhon\",\"id\":0,\"userName\":\"jhon_02\"}"
        val userEntity = Gson().fromJson(userJsonResult, UserEntity::class.java)

        // WHEN
        val result = userEntity.toDomain()

        // THEN
        with(result) {
            imageUrl shouldBe "https://img.jpeg"
            name shouldBe "Jhon"
            userName shouldBe "jhon_02"
        }
    }
}