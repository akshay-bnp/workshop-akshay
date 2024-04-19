package com.app.workshop.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.workshop.data.Post
import com.app.workshop.data.Repository
import com.app.workshop.utils.NetWorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository, application: Application): BaseViewModel(application) {

    private val _responseposts: MutableLiveData<NetWorkResult<List<Post>>> = MutableLiveData()
    val responseposts: LiveData<NetWorkResult<List<Post>>> = _responseposts

    private val _currentBatchposts: MutableLiveData<List<Post>> = MutableLiveData()
    val currentBatchposts: LiveData<List<Post>> = _currentBatchposts

    private val batchSize = 20
    private var nextBatchStartIndex = 0 // Track the index of the next batch start

    private lateinit var allPosts: List<Post>

    fun getPostsList() = viewModelScope.launch {
        repository.getPostList(context).collect { values ->
            _responseposts.value = values
        }
    }

    fun setPostsList(posts : List<Post>){
        nextBatchStartIndex = 0
        allPosts = posts
        sendPostsInBatches()
    }

    // Function to send data in batches to the activity
    fun sendPostsInBatches() {
        if(nextBatchStartIndex == allPosts.lastIndex)
            return
        viewModelScope.launch {
            val endIndex = (nextBatchStartIndex + batchSize).coerceAtMost(allPosts.size)
            val postsBatch = allPosts.subList(nextBatchStartIndex, endIndex)
            _currentBatchposts.value = postsBatch
            nextBatchStartIndex = endIndex
        }
    }

}