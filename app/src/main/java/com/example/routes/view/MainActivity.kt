package com.example.routes.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routes.R
import com.example.routes.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    private val routesAdapter = RouteListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        routesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = routesAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.routesVm.observe(this, Observer { routes ->
            routes?.let {
                routesListRecyclerView.visibility = View.VISIBLE
                routesAdapter.updateRoutes(it)
            }
        })

        viewModel.routesLoadErrorVm.observe(this, Observer { isError ->
            isError?.let { listErrorTextView.visibility = if (it) View.VISIBLE else View.GONE }
        })

        viewModel.loadingVm.observe(this, Observer { isLoading: Boolean? ->
            isLoading?.let {
                loading_view_progressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listErrorTextView.visibility = View.GONE
                    routesListRecyclerView.visibility = View.GONE
                }
            }
        })
    }
}
