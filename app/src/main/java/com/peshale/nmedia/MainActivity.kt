package com.peshale.nmedia

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import com.peshale.nmedia.adapter.PostAdapter
import com.peshale.nmedia.databinding.ActivityMainBinding
import com.peshale.nmedia.vmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter({viewModel.likeById(it.id)}, {viewModel.toShareById(it.id)}) {
            viewModel.toShareById(it.id)
            viewModel.toViewById(it.id)
        }
        binding.posts.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.list = posts
        }
    }
}