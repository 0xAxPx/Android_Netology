package com.peshale.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.peshale.nmedia.R
import com.peshale.nmedia.databinding.ActivityIntentHandlerBinding

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.getStringExtra(Intent.EXTRA_TEXT)?.let {
            binding.root.text = it
        } ?: run {
            Snackbar.make(binding.root, getString(R.string.error_empty_content), Snackbar.LENGTH_SHORT).show()
        }
    }
}