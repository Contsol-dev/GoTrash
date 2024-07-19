package com.adinda.gotrash.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adinda.gotrash.R
import com.adinda.gotrash.databinding.FragmentProfileBinding
import com.adinda.gotrash.presentation.login.LoginActivity
import com.adinda.gotrash.utils.proceedWhen
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        bindAccount()
    }

    private fun bindAccount() {
        binding.tvName.text = viewModel.getUser()?.username.orEmpty()
        binding.tvEmail.text = viewModel.getUser()?.email.orEmpty()
    }

    private fun setClickListener() {
        binding.cvLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun doLogout() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                proceedLogout()
                navigateToLogin()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    private fun proceedLogout() {
        viewModel.doLogout().observe(viewLifecycleOwner) {
            it.proceedWhen (
               doOnSuccess = {
                   Log.d("login2", "checkLogin: ${viewModel.isLogin()}")
                   navigateToLogin()
               }, doOnError = {
                   Log.e("login2", "checkLogin: ${it.exception}")
                }
            )
        }
    }

    private fun navigateToLogin() {
        Log.d("login2", "checkLogin: ${viewModel.isLogin()}")
        startActivity(
            Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

}