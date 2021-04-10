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
import com.peshale.nmedia.databinding.FragmentNewPostBinding
import com.peshale.nmedia.utils.AndroidUtils
import com.peshale.nmedia.vmodel.PostViewModel

class NewPostFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by AndroidUtils.StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false)

        arguments?.textArg
            ?.let(binding.etInputArea::setText)

        binding.etInputArea.requestFocus()
        binding.fabConfirmation.setOnClickListener {
            if (binding.etInputArea.text.isNullOrBlank() && binding.etPostVideoLink.text.isNullOrBlank()) {
                AndroidUtils.hideKeyboard(requireView())
                findNavController().navigateUp()
            } else {
                val videoLink = binding.etPostVideoLink.text.toString()
                if (videoLink != "" && !AndroidUtils.urlValidChecker(videoLink)) {
                    Toast.makeText(
                        activity,
                        getString(R.string.error_url_validation),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else {
                    viewModel.changeContent(
                        binding.etInputArea.text.toString(),
                        binding.etPostVideoLink.text.toString()
                    )
                    viewModel.addPost()
                    AndroidUtils.hideKeyboard(requireView())
                    findNavController().navigateUp()
                }
            }
        }
        return binding.root
    }
}