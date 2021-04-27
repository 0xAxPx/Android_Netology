package com.peshale.nmedia.activity

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.peshale.nmedia.R
import com.peshale.nmedia.adapter.OnItemClickListener
import com.peshale.nmedia.adapter.PostAdapter
import com.peshale.nmedia.databinding.FragmentMainBinding
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.utils.Arguments
import com.peshale.nmedia.vmodel.PostViewModel

class MainFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false
        )

        binding.swipeRefresh.setOnRefreshListener {
            if (!binding.progress.isAnimating) {
                viewModel.loadPosts()
            }
            binding.swipeRefresh.isRefreshing = false
        }

        val adapter = PostAdapter(object : OnItemClickListener {

            override fun onLike(post: Post) {
                if (!post.likedByMe) {
                    viewModel.likeById(post.id)

                } else {
                    viewModel.unlikeById(post.id)
                }
            }

            override fun onEdit(post: Post) {
                val bundle = Bundle().apply {
                    putString(Arguments.CONTENT, post.content)
                    putLong(Arguments.POST_ID, post.id)
                }
                findNavController().navigate(R.id.action_mainFragment_to_editPostFragment, bundle)
            }

            override fun onDelete(post: Post) {
                viewModel.deleteById(post.id)
            }

            override fun onPost(post: Post) {
                val bundle = Bundle().apply {
                    putLong("postId", post.id)
                }
                findNavController().navigate(R.id.action_mainFragment_to_postCardFragment, bundle)
            }
        })

        binding.rvPosts.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, { state ->
            adapter.submitList(state.posts)
            binding.progress.isVisible = state.loading
            binding.emptyText.isVisible = state.empty
        })

        binding.fabAddNewPost.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_newPostFragment)
        }
        return binding.root
    }
}