package com.picpay.desafio.android.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.domain.model.User
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ListItemUserBinding.bind(itemView)

    fun bind(userRemote: User) {
        with(binding) {
            name.text = userRemote.name
            username.text = userRemote.userName
            progressBar.visibility = View.VISIBLE
            Picasso.get()
                .load(userRemote.imageUrl)
                .error(R.drawable.ic_round_account_circle)
                .into(picture, object : Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        progressBar.visibility = View.GONE
                    }
                })
        }
    }
}