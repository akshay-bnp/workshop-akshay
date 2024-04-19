package com.app.workshop.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.workshop.R
import com.app.workshop.data.ApiResultHandler
import com.app.workshop.data.Post
import com.app.workshop.databinding.ActivityMainBinding
import com.app.workshop.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var postListAdapter: PostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        init()
        getPosts()
        observePostRequestData()
    }

    private fun init() {
        try {
            postListAdapter = PostListAdapter()
            activityMainBinding.list.apply { adapter= postListAdapter }
            activityMainBinding.swipeRefreshLayout.setOnRefreshListener { getPosts() }
            activityMainBinding.list.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        mainViewModel.sendPostsInBatches()
                    }
                }
            })

            postListAdapter.setOnClickListener (object  : PostListAdapter.OnClickListener {
                override fun onClick(position: Int, model: Post) {
                    showDetails(model)
                }
            })
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    fun showDetails(model : Post){
        val intent = Intent(this@MainActivity, PostDetailsActivity::class.java)
        intent.putExtra(NEXT_SCREEN, model)
        startActivity(intent)
    }

    private fun observePostRequestData() {
        try {
            mainViewModel.responseposts.observe(this) { response ->
                val apiResultHandler = ApiResultHandler<List<Post>>(this@MainActivity,
                    onLoading = {
                        activityMainBinding.progress.visibility = View.VISIBLE
                    },
                    onSuccess = { data ->

                        activityMainBinding.progress.visibility = View.GONE
                        data?.let {
                            postListAdapter
                            mainViewModel.setPostsList(it)
                            observePostBatchData()
                        }
                        activityMainBinding.swipeRefreshLayout.isRefreshing = false
                    },
                    onFailure = {
                        activityMainBinding.progress.visibility = View.GONE
                    })
                apiResultHandler.handleApiResult(response)
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun observePostBatchData() {
        try {
            mainViewModel.currentBatchposts.observe(this) { response ->
                response?.let {
                    postListAdapter.setPosts(it)
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun getPosts() {
        postListAdapter.clearAllItems()
        mainViewModel.getPostsList()
    }

    companion object{
        val NEXT_SCREEN="details_screen"
    }

}