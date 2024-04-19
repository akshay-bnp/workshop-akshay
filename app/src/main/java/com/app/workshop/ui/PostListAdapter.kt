package com.app.workshop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.workshop.data.Post
import com.app.workshop.databinding.PostListItemBinding


class PostListAdapter() : RecyclerView.Adapter<PostListAdapter.DataViewHolder>() {
    private var onClickListener: OnClickListener? = null

    private lateinit var binding: PostListItemBinding
    var postList = arrayListOf<Post>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        binding = PostListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    // A function to bind the onclickListener.
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Post)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataModel = postList[position]
        holder.bind(dataModel)
        // Finally add an onclickListener to the item.
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, dataModel )
            }
        }
    }

    override fun getItemCount(): Int = postList.size

    inner class DataViewHolder(private val binding: PostListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            try {
                binding.post = post
                binding.executePendingBindings()
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    fun setPosts(data: List<Post>) {
        try {
//            postList.clear()
            if (data.isNotEmpty()) {
                postList.addAll(data)
            }
            notifyDataSetChanged()
        } catch (e: Exception) {
            e.stackTrace
        }
    }


    fun clearAllItems(){
        postList.clear()
        notifyDataSetChanged()
    }


}