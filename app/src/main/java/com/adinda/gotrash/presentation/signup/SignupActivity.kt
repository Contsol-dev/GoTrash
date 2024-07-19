package com.adinda.gotrash.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.adinda.gotrash.R
import com.adinda.gotrash.databinding.ActivitySignupBinding
import com.adinda.gotrash.presentation.login.LoginActivity
import com.adinda.gotrash.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupActivity : AppCompatActivity() {
    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    private val viewModel: SignupViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setupForm()
        setCLickListener()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun setCLickListener() {
        binding.signUpButton.setOnClickListener {
            doRegister()
        }
    }

    private fun doRegister() {
        if (isFormValid()) {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val username = binding.username.text.toString().trim()
            proceedRegister(username = username, email = email, password = password)
        }
    }

    private fun proceedRegister(username: String, email: String, password: String) {
        viewModel.doRegister(username, email, password).observe(this){ it ->
            it.proceedWhen(
                doOnSuccess = {
                    //binding.pbLoadingRegister.isVisible = false
                    binding.signUpButton.isVisible = true
                    navigateToLogin()
                },
                doOnError = {
                    //binding.pbLoadingRegister.isVisible = false
                    binding.signUpButton.isVisible = true
                    Toast.makeText(
                        this,
                        "Register Failed : ${it.exception?.message.orEmpty()}",Toast.LENGTH_SHORT).show()
                },
                doOnEmpty = {
                    //binding.pbLoadingRegister.isVisible = false
                    binding.signUpButton.isVisible = false
                }
            )
        }
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    private fun isFormValid(): Boolean {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val username = binding.username.text.toString().trim()
        return checkEmailValidation(email) &&
                checkPasswordValidation(password, binding.passwordLayout) &&
                checkUsernameValidation(username)
    }

    private fun checkUsernameValidation(username: String): Boolean {
        return if (username.isEmpty()) {
            binding.usernameLayout.isErrorEnabled = true
            binding.usernameLayout.error = getString(R.string.text_error_email_empty)
            false
        } else {
            binding.usernameLayout.isErrorEnabled = false
            true
        }
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.usernameLayout.isErrorEnabled = true
            binding.usernameLayout.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.usernameLayout.isErrorEnabled = true
            Log.d("email", email)
            binding.usernameLayout.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.usernameLayout.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(
        confirmPassword: String,
        textInputLayout: TextInputLayout,
    ): Boolean {
        return if (confirmPassword.isEmpty()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_password_empty)
            false
        } else if (confirmPassword.length < 8) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_password_less_than_8_char)
            false
        } else {
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun setupForm() {
        binding.usernameLayout.isVisible = true
        binding.email.isVisible = true
        binding.password.isVisible = true
    }
}