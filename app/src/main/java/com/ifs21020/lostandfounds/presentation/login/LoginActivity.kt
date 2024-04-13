package com.ifs21020.lostandfounds.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ifs21020.lostandfounds.data.pref.UserModel
import com.ifs21020.lostandfounds.data.remote.MyResult
import com.ifs21020.lostandfounds.databinding.ActivityLoginBinding
import com.ifs21020.lostandfounds.presentation.register.RegisterActivity
import com.ifs21020.lostandfounds.presentation.ViewModelFactory
import com.ifs21020.lostandfounds.presentation.main.MainActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
    }

    private fun setupView() {
        showLoading(false)
    }

    private fun setupAction() {
        binding.apply {
            // Redirect to registration screen
            signupRedirectText.setOnClickListener {
                openRegisterActivity()
            }

            // Perform login when login button is clicked
            loginButton.setOnClickListener {
                val email = loginEmail.text.toString()
                val password = loginPassword.text.toString()
                if (email.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder(this@LoginActivity).apply {
                        setTitle("Oh No!")
                        setMessage("Please fill in all fields!")
                        setPositiveButton("Okay") { _, _ -> }
                        create()
                        show()
                    }
                    return@setOnClickListener
                }
                observeLogin(email, password)
            }
        }
    }

    private fun observeLogin(email: String, password: String) {
        viewModel.login(email, password).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is MyResult.Loading -> {
                        showLoading(true)
                    }
                    is MyResult.Success -> {
                        showLoading(false)
                        lifecycleScope.launch {
                            viewModel.saveSession(UserModel(result.data.token))
                                .observe(this@LoginActivity) {
                                    openMainActivity()
                                }
                        }
                    }
                    is MyResult.Error -> {
                        showLoading(false)
                        AlertDialog.Builder(this).apply {
                            setTitle("Oops!")
                            setMessage(result.error)
                            setPositiveButton("Okay") { _, _ -> }
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loginButton.isActivated = !isLoading
        binding.loginButton.text = if (isLoading) "" else "Login"
    }

    private fun openRegisterActivity() {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun openMainActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
