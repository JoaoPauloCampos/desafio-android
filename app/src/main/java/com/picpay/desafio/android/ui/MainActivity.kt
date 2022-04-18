package com.picpay.desafio.android.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.base.hide
import com.picpay.desafio.android.base.show
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.ui.user.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding
    private val recyclerView by lazy { binding.recyclerView }
    private val progressBar by lazy { binding.userListProgressBar }
    private val motionLayout by lazy { binding.motionLayout }
    private val adapter: UserListAdapter by lazy { UserListAdapter() }

    private val userViewModel by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        handleObserver()
    }

    private fun setupView() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        addLoadStateListenerOnAdapter()
    }

    private fun addLoadStateListenerOnAdapter() {
        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Error -> {
                    showError()
                }
                is LoadState.Loading -> {
                    motionLayout.hide()
                    progressBar.show()
                }
                is LoadState.NotLoading -> {
                    showEmptyMessage(loadState)
                }
            }
        }
    }

    private fun showEmptyMessage(loadState: CombinedLoadStates) {
        if (adapter.itemCount > 0) {
            progressBar.hide()
            motionLayout.show()
        } else if (loadState.append.endOfPaginationReached && adapter.itemCount == 0) {
            progressBar.hide()
            Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showError() {
        val message = getString(R.string.error)
        progressBar.hide()
        motionLayout.hide()
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleObserver() {
        userViewModel.usersLiveData.observe(this) { userPagingData ->
            adapter.submitData(lifecycle, userPagingData)
        }
    }
}
