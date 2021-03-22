package com.peshale.nmedia

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import com.peshale.nmedia.databinding.ActivityMainBinding
import com.peshale.nmedia.util.Util.counter
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
                like.setImageResource(
                    if (post.likedByMe) R.drawable.baseline_favorite_red_500_24dp else R.drawable.baseline_favorite_border_black_24dp
                )

                //listener for likes
                like.setOnClickListener {
                    post.likedByMe = !post.likedByMe
                    like.setImageResource(
                        if (post.likedByMe) R.drawable.baseline_favorite_red_500_24dp else R.drawable.baseline_favorite_border_black_24dp
                    )
                    if (post.likedByMe) ++post.likes else --post.likes
                    likeCount.text = counter(post.likes)
                }

                //listener to shares
                share.setOnClickListener {
                    ++post.shares
                    shareCount.text = counter(post.shares)
                }

                //listener for views
                views.setOnClickListener {
                    ++post.views
                    viewsCount.text = counter(post.views)
                }
            }
        })
    }
}