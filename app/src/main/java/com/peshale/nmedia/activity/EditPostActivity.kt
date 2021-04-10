package com.peshale.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.peshale.nmedia.R
import com.peshale.nmedia.databinding.ActivityEditPostBinding
import com.peshale.nmedia.utils.Utils

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = intent.extras
        val textForEdit = arguments!!["content"].toString()
        val videoLinkForEdit = arguments["videoLink"].toString()
        val binding = ActivityEditPostBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.etInputArea.setText(textForEdit)
        binding.etEditedPostVideoLink.setText(videoLinkForEdit)
        binding.etInputArea.requestFocus()
        binding.fabConfirmation.setOnClickListener {
            val intent = Intent()
            if (binding.etInputArea.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.etInputArea.text.toString()
                val videoLink = binding.etEditedPostVideoLink.text.toString()
                if (videoLink != "" && !Utils.urlValidChecker(videoLink)) {
                    Toast.makeText(
                        this@EditPostActivity,
                        getString(R.string.error_url_validation),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else {
                    intent.putExtra("content", content)
                    intent.putExtra("videoLink", videoLink)
                    setResult(Activity.RESULT_OK, intent)
                }
                finish()
            }
        }

        binding.mbCancelEditing.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
}
