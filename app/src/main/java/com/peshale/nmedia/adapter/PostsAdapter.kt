package com.peshale.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peshale.nmedia.R
import com.peshale.nmedia.databinding.CardPostBinding
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.utils.Utils

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit
typealias OnViewListener = (post: Post) -> Unit

class PostAdapter(
    private val onLikeListener: OnLikeListener,
    private var onShareListener: OnShareListener,
    private var onViewListener: OnViewListener
) : RecyclerView.Adapter<PostViewHolder>() {

    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener, onViewListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size

}

class PostViewHolder (
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onViewListener: OnViewListener

) : RecyclerView.ViewHolder(binding.root) {

    fun bind (post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            numberOfLikes.text = Utils.counter(post.likes)
            numberOfShare.text = Utils.counter(post.shares)
            numberOfViews.text = Utils.counter(post.views)
            likeButton.setImageResource(
                if (post.likedByMe) {
                    R.drawable.baseline_favorite_red_500_24dp
                } else {
                    R.drawable.baseline_favorite_border_black_24dp
                }
            )
            likeButton.setOnClickListener {
                onLikeListener(post)
            }
            toShareButton.setOnClickListener {
                onShareListener(post)
            }
            viewsButton.setOnClickListener {
                onViewListener(post)
            }
        }
    }
}