package com.peshale.nmedia

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import com.peshale.nmedia.databinding.ActivityMainBinding
import com.peshale.nmedia.vmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        viewModel.data.observe(this, { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likeCount.text = viewModel.counterLikes()
                shareCount.text = viewModel.counterShares()
                viewsCount.text = viewModel.counterViews()
                like.setImageResource(
                    if (post.likedByMe) R.drawable.baseline_favorite_red_500_24dp else R.drawable.baseline_favorite_border_black_24dp
                )
            }
        })

        binding.like.setOnClickListener {
            viewModel.like()
        }

        binding.share.setOnClickListener {
            viewModel.share()
        }

        binding.views.setOnClickListener {
            viewModel.view()
        }
    }
}