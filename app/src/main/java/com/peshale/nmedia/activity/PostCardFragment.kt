package com.peshale.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.peshale.nmedia.R
import com.peshale.nmedia.databinding.FragmentPostCardBinding
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.utils.AndroidUtils
import com.peshale.nmedia.utils.Arguments
import com.peshale.nmedia.vmodel.PostViewModel
import kotlinx.android.synthetic.main.card_post.*

class PostCardFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var post = viewModel.searchPost(arguments?.get("postId") as Long)

        val binding = FragmentPostCardBinding.inflate(
            inflater,
            container,
            false
        )

        binding.apply {
            tvAuthorPost.text = post.author
            published.text = post.published
            content.text = post.content
            likeButton.text = AndroidUtils.counter(post.likes)
            likeButton.isChecked = post.likedByMe
        }

        binding.likeButton.setOnClickListener {
            if (!post.likedByMe) {
                viewModel.likeById(post.id)
            } else {
                viewModel.unlikeById(post.id)
            }
            viewModel.data.observe(viewLifecycleOwner, {
                post = viewModel.searchPost(post.id)
                binding.likeButton.text = AndroidUtils.counter(post.likes)
            })
        }

        binding.ibMenu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.menu_main)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menuItemDelete -> {
                            viewModel.deleteById(post.id)
                            findNavController().navigateUp()
                            true
                        }
                        R.id.menuItemEdit -> {
                            val bundle = Bundle().apply {
                                putString(Arguments.CONTENT, post.content)
                                putLong(Arguments.POST_ID, post.id)
                            }
                            findNavController().navigate(
                                R.id.action_postCardFragment_to_editPostFragment,
                                bundle
                            )
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
        return binding.root
    }
}