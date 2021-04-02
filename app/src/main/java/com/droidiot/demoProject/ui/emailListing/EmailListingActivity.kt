package com.droidiot.demoProject.ui.emailListing

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidiot.demoProject.DemoApplication
import com.droidiot.demoProject.R
import com.droidiot.demoProject.databinding.ActivityEmailListingBinding
import com.droidiot.demoProject.di.components.DaggerActivityComponents
import com.droidiot.demoProject.di.module.ActivityModule
import com.droidiot.demoProject.ui.emailDetails.EmailDetailsActivity
import com.droidiot.demoProject.utils.Status
import javax.inject.Inject


class EmailListingActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: EmailListingViewModel
    lateinit var view: ActivityEmailListingBinding
    lateinit var emailListingAdapter: EmailListingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependency()
        setupAdapter()
        setupDataObserver()
        setupSearch()
        viewModel.getEmailList()
    }

    private fun setupDependency() {
        DaggerActivityComponents.builder()
            .activityModule(ActivityModule(this))
            .appComponents((application as DemoApplication).getApplicationComponent()).build()
            .inject(this)
        view = DataBindingUtil.setContentView(this, R.layout.activity_email_listing)
        view.lifecycleOwner = this

    }

    private fun setupAdapter() {
        emailListingAdapter =
            EmailListingAdapter({ emailItem ->
                startActivity(EmailDetailsActivity.getStartIntent(this, emailItem))
            })
        view.rvEmailList.apply {
            adapter = emailListingAdapter
            layoutManager =
                LinearLayoutManager(this@EmailListingActivity, RecyclerView.VERTICAL, false)
        }
    }

    private fun setupSearch() {
        view.svEmailList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                emailListingAdapter.filter.filter(query)
                return false

            }

            override fun onQueryTextChange(query: String?): Boolean {
                emailListingAdapter.filter.filter(query)
                return false
            }

        })
    }

    private fun setupDataObserver() {
        viewModel.responseListData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    updateUiState(false, "Loading")
                }
                Status.SUCCESS -> {
                    //hide loading
                    emailListingAdapter.updateList(it.data)
                    if (emailListingAdapter.itemCount <= 0) {
                        updateUiState(false, "No Data to Show")
                    } else {
                        updateUiState(true, null)
                    }
                }
                Status.ERROR -> {
                    updateUiState(false, it.errorMessage)
                }
                else -> {
                    updateUiState(false, "Processing....")
                }
            }

        })
    }

    private fun updateUiState(haveData: Boolean, emptyStateText: String?) {
        view.rvEmailList.visibility = if (!haveData) View.GONE else View.VISIBLE
        view.llEmptyData.visibility = if (haveData) View.GONE else View.VISIBLE
        view.tvStatus.text = emptyStateText
    }


}

