package com.example.kmtestsuitmedia.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.kmtestsuitmedia.R
import com.example.kmtestsuitmedia.data.repo.ThirdRepository
import com.example.kmtestsuitmedia.databinding.ActivityThirdBinding
import com.example.kmtestsuitmedia.ui.viewmodel.ThirdViewModel
import com.example.kmtestsuitmedia.ui.viewmodel.ViewModelFactory

class ThirdActivity : AppCompatActivity(), UserAdapter.OnItemClickCallback {
    private lateinit var binding: ActivityThirdBinding
    private lateinit var thirdRepo: ThirdRepository
    private lateinit var thirdVM: ThirdViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var name: String? = null
    private var currentPage = 1
    private var totalPages = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        thirdRepo = ThirdRepository()
        thirdVM = ViewModelProvider(this, ViewModelFactory())[ThirdViewModel::class.java]

        name = intent.getStringExtra("name")
        userAdapter = UserAdapter(emptyList())
        userAdapter.setOnItemClickCallback(this)
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = userAdapter

        thirdVM.getUsers(1, 15)

        thirdVM.userData.observe(this) { data ->
            data?.let {
                if (data.isNotEmpty()) {
                    userAdapter.addData(data)
                    binding.tvEmptyState.visibility = View.GONE
                } else {
                    userAdapter.userList = emptyList()
                    binding.tvEmptyState.visibility = View.VISIBLE
                }
                swipeRefreshLayout.isRefreshing = false
            }
        }

        binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    if (currentPage < totalPages) {
                        currentPage++
                        thirdVM.getUsers(currentPage, 15)
                    }
                }
            }
        })

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }

        binding.backButton.setOnClickListener{
            finish()
        }
    }

    override fun onItemClicked(username: String) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("username", username)
        intent.putExtra("name", name)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }

    private fun refreshData() {
        userAdapter.userList = emptyList()
        userAdapter.notifyDataSetChanged()
        thirdVM.refreshData(15)
    }
}