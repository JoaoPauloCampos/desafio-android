package com.picpay.desafio.android.ui

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.domain.model.User

class UserListDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.userName == newItem.userName
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
       return oldItem == newItem
    }
}