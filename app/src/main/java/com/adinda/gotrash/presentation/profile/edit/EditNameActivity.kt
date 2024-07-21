package com.adinda.gotrash.presentation.profile.edit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.adinda.gotrash.R
import com.adinda.gotrash.databinding.ActivityEditNameBinding
import com.adinda.gotrash.presentation.login.LoginActivity
import com.adinda.gotrash.presentation.profile.ProfileFragment
import com.adinda.gotrash.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditNameActivity : AppCompatActivity() {
    private val binding : ActivityEditNameBinding by lazy{
        ActivityEditNameBinding.inflate(layoutInflater)
    }
    private val viewModel : EditNameViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForm()
        bindUsername()
        setClick()
    }

    private fun setupForm() {
        binding.nameInputLayout.isVisible = true
    }

    private fun setClick() {
        binding.btnSaveEditProfile.setOnClickListener{
            doUpdateName()
        }
        binding.ivBackHeader.setOnClickListener{
            finish()
        }
    }

    private fun doUpdateName() {
        if (isFormValid()) {
            val name = binding.nameEditText.text.toString().trim()
            updateProfile(name)
        }
    }

    private fun updateProfile(name: String) {
        viewModel.updateName(name).observe(this){
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
                    navigateToProfile()
                },
                doOnLoading = {
                    binding.btnSaveEditProfile.isVisible = false
                    binding.pbLoadingEdit.isVisible = true
                },
                doOnError = {
                    Toast.makeText(this, "Edit Profile Failed}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun navigateToProfile() {
        startActivity(
            Intent(this, ProfileFragment::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    private fun isFormValid(): Boolean {
        val name = binding.nameEditText.text.toString()
        return validateField(name)
    }

    private fun validateField(name: String): Boolean {
            return if (name.isEmpty()) {
                binding.nameInputLayout.isErrorEnabled = true
                binding.nameInputLayout.error = getString(R.string.text_error_name_empty)
                false
            } else {
                binding.nameInputLayout.isErrorEnabled = false
                true
            }
        }

    private fun bindUsername() {
        binding.nameEditText.setText(viewModel.getUser()?.username.orEmpty())
    }
}