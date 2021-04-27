package com.peshale.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.peshale.nmedia.R
import com.peshale.nmedia.databinding.FragmentEditPostBinding
import com.peshale.nmedia.utils.AndroidUtils
import com.peshale.nmedia.utils.Arguments
import com.peshale.nmedia.vmodel.PostViewModel

class EditPostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentEditPostBinding.inflate(
            inflater,
            container,
            false
        )

        val textForEdit = arguments?.getString(Arguments.CONTENT).toString()
        val postId = arguments?.getLong(Arguments.POST_ID)

        binding.etInputArea.setText(textForEdit)

        binding.etInputArea.requestFocus()

        binding.fabConfirmation.setOnClickListener {
            if (binding.etInputArea.text.isNullOrBlank()) {
                AndroidUtils.hideKeyboard(requireView())
                findNavController().navigateUp()
                return@setOnClickListener
            }

            val content = binding.etInputArea.text.toString()
            if (postId != null) {
                viewModel.changeContent(postId, content)
            }
            viewModel.updatePost()

            AndroidUtils.hideKeyboard(requireView())

            findNavController().navigate(R.id.mainFragment)
        }

        binding.mbCancelEditing.setOnClickListener {
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root
    }
}