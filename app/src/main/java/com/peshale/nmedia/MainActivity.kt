package com.peshale.nmedia

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.peshale.nmedia.adapter.OnItemClickListener
import com.peshale.nmedia.adapter.PostAdapter
import com.peshale.nmedia.databinding.ActivityMainBinding
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.utils.Utils
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

            override fun onDelete(post: Post) {
                viewModel.deleteById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onCancelEdit(post: Post) {
                viewModel.cancelEdit()
            }
        })

        binding.posts.adapter = adapter
        viewModel.data.observe(this, {
            posts -> adapter.submitList(posts)
        })
        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                return@observe
            }
            binding.postUndo.text = it.content
            binding.group.visibility = View.VISIBLE
            with(binding.inputTextArea) {
                requestFocus()
                setText(it.content)
            }
        }

        binding.confirmationButton.setOnClickListener {
            with(binding.inputTextArea) {
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.editContent(text.toString())
                viewModel.addPost()

                setText("")
                binding.group.visibility = View.GONE
                clearFocus()
                Utils.hideKeyboard(this)
            }
        }

        binding.cancelEditingButton.setOnClickListener {
            with(binding.inputTextArea) {
                viewModel.cancelEdit()
                setText("")
                binding.group.visibility = View.GONE
                clearFocus()
                Utils.hideKeyboard(this)
            }
        }
    }
}