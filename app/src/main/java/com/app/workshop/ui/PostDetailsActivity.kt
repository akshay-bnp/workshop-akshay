package com.app.workshop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.databinding.DataBindingUtil
import com.app.workshop.R
import com.app.workshop.data.Post
import com.app.workshop.databinding.ActivityPostDetailBinding


class PostDetailsActivity: AppCompatActivity() {

    private lateinit var postDetailsActivityBinding: ActivityPostDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postDetailsActivityBinding = DataBindingUtil.setContentView(this@PostDetailsActivity, R.layout.activity_post_detail)

        if(intent.hasExtra(MainActivity.NEXT_SCREEN)){
            // get the Serializable data model class with the details in it
            postDetailsActivityBinding.post =
                IntentCompat.getParcelableExtra(intent, MainActivity.NEXT_SCREEN, Post::class.java)
        }
    }

}