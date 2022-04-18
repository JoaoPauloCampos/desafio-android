package com.picpay.desafio.android.ui.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.picpay.desafio.android.base.asLiveData
import com.picpay.desafio.android.data.remote.core.PicPayError
import com.picpay.desafio.android.domain.interactor.FetchUsersUseCase
import com.picpay.desafio.android.domain.interactor.GetUsersUseCase
import com.picpay.desafio.android.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val fetchUsersUseCase: FetchUsersUseCase
) : ViewModel() {

    private val _usersLiveData: MutableLiveData<PagingData<User>> by lazy {
        MutableLiveData<PagingData<User>>()
    }
    val usersLiveData = _usersLiveData.asLiveData()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        fetchUsersUseCase(
            scope = viewModelScope,
            onSuccess = {
                getUsers()
            },
            onError = {
                Log.d("UserViewModel", "fetchUsers()", (it as PicPayError.GenericError).exception)
            }
        )
    }

    private fun getUsers() {
        getUsersUseCase(
            scope = viewModelScope,
            params = viewModelScope,
            onSuccess = {
                collectLatestData(it)
            },
            onError = {
                Log.d("UserViewModel", "getUsers()", (it as PicPayError.GenericError).exception)
            }
        )
    }

    private fun collectLatestData(userFlow: Flow<PagingData<User>>) {
        viewModelScope.launch {
            userFlow.collectLatest {
                _usersLiveData.postValue(it)
            }
        }
    }
}