package com.peshale.nmedia.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.peshale.nmedia.R
import com.peshale.nmedia.utils.Arguments.DRAFT_TEXT
import com.peshale.nmedia.utils.Arguments.DRAFT_VIDEO_LINK
import com.peshale.nmedia.databinding.FragmentNewPostBinding
import com.peshale.nmedia.utils.AndroidUtils
import com.peshale.nmedia.vmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_new_post.*

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
            false
        )

        val prefs: SharedPreferences? = this.context?.getSharedPreferences(
            "draft",
            Context.MODE_PRIVATE
        )

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (prefs != null) {
                saveDraft(prefs)
                findNavController().navigateUp()
            }
        }

        restoreDraft(prefs, binding)

        arguments?.textArg
            ?.let(binding.etInputArea::setText)

        callback.isEnabled
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
                        binding.etPostVideoLink.text.toString(),
                        dateOfEditing = ""
                    )
                    viewModel.addPost()
                    if (prefs != null) {
                        clearDraft(prefs)
                    }
                    AndroidUtils.hideKeyboard(requireView())
                    findNavController().navigateUp()
                }
            }
        }
        return binding.root
    }

    private fun restoreDraft(
        prefs: SharedPreferences?,
        binding: FragmentNewPostBinding
    ) {
        val draftText = prefs?.getString(DRAFT_TEXT, "")
        val draftVideoLink = prefs?.getString(DRAFT_VIDEO_LINK, "")

        if (draftText != "") {
            binding.etInputArea.setText(draftText)
        }

        if (draftVideoLink != "") {
            binding.etPostVideoLink.setText(draftVideoLink)
        }
    }

    private fun saveDraft(prefs: SharedPreferences) {
        val editor = prefs.edit()
        editor.putString(DRAFT_TEXT, etInputArea.text.toString())
        editor.putString(DRAFT_VIDEO_LINK, etPostVideoLink.text.toString())
        editor.apply()
    }

    private fun clearDraft(prefs: SharedPreferences) {
        val editor = prefs.edit()
        editor.remove(DRAFT_TEXT)
        editor.remove(DRAFT_VIDEO_LINK)
        editor.apply()
    }
}