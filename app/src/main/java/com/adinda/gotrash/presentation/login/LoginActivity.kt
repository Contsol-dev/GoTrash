package com.adinda.gotrash.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.adinda.gotrash.presentation.main.MainActivity
import com.adinda.gotrash.R
import com.adinda.gotrash.databinding.ActivityLoginBinding
import com.adinda.gotrash.presentation.signup.SignupActivity
import com.adinda.gotrash.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val viewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        setupForm()
        setClickListener()
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    private fun setClickListener() {
        binding.signInButton.setOnClickListener {
            doLogin()
        }
        binding.signUpPrompt.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun navigateToSignUp() {
        startActivity(
            Intent(this, SignupActivity::class.java)
        )
    }

    private fun setupForm() {
        binding.usernameLayout.isVisible = true
        binding.passwordLayout.isVisible = true
        binding.signInButton.isVisible = true
    }

    private fun doLogin() {
        if (isFormValid()) {
            val email = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()
            proceedLogin(email = email, password = password)
        }
    }

    private fun proceedLogin(
        email: String,
        password: String,
    ) {
        viewModel.doLogin(email = email, password = password).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoadingLogin.isVisible = false
                    binding.signInButton.isVisible = true
                    navigateToMain()
                },
                doOnError = {
                    binding.pbLoadingLogin.isVisible = false
                    binding.signInButton.isVisible = true
                    Toast.makeText(
                        this,
                        "Login Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoadingLogin.isVisible = true
                    binding.signInButton.isVisible = false
                },
            )
        }
    }

    private fun navigateToMain() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    private fun isFormValid(): Boolean {
        val email = binding.username.text.toString().trim()
        val password = binding.password.text.toString().trim()

        return checkEmailValidation(email) &&
                checkPasswordValidation(password, binding.passwordLayout)
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.usernameLayout.isErrorEnabled = true
            binding.usernameLayout.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.usernameLayout.isErrorEnabled = true
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

}