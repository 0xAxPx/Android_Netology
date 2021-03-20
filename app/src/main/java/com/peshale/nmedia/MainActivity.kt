package com.peshale.nmedia

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import com.peshale.nmedia.adapter.OnItemClickListener
import com.peshale.nmedia.adapter.PostAdapter
import com.peshale.nmedia.databinding.ActivityMainBinding
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.vmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(object: OnItemClickListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.toShareById(post.id)
            }

            override fun onView(post: Post) {
                viewModel.toViewById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
        })

        binding.posts.adapter = adapter
        viewModel.data.observe(this, {
            posts -> adapter.submitList(posts)
        })
    }
}