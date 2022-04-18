package com.picpay.desafio.android.data.remote.mapper

import com.picpay.desafio.android.data.local.entities.UserEntity
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.data.remote.model.UserRemote

fun UserRemote.toDomain(): User {
    return User(
        imageUrl = img,
        name = name,
        userName = username
    )
}

fun List<UserRemote>.toDomainList(): List<User> {
    return this.map {
        it.toDomain()
    }
}

fun UserRemote.toEntity(): UserEntity {
    return UserEntity(
        image = img,
        id = id,
        name = name,
        userName = username
    )
}

fun List<UserRemote>.toEntityList(): List<UserEntity> {
    return this.map {
        it.toEntity()
    }
}

fun UserEntity.toDomain(): User {
    return User(
        imageUrl = image,
        name = name,
        userName = userName
    )
}

fun List<UserEntity>.toUserList(): List<User> {
    return this.map {
        it.toDomain()
    }
}

