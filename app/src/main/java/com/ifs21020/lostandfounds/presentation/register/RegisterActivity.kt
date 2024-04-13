package com.ifs21020.lostandfounds.presentation.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ifs21020.lostandfounds.data.remote.MyResult
import com.ifs21020.lostandfounds.databinding.ActivityRegisterBinding
import com.ifs21020.lostandfounds.presentation.ViewModelFactory
import com.ifs21020.lostandfounds.presentation.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
    }

    private fun setupView() {
        showLoading(false)
    }

    private fun setupAction() {
        binding.apply {
            // Redirect to login screen
            loginRedirectText.setOnClickListener {
                openLoginActivity()
            }

            // Perform registration when register button is clicked
            signupButton.setOnClickListener {
                val name = signupName.text.toString()
                val email = signupEmail.text.toString()
                val phone = signupPhone.text.toString()
                val password = signupPassword.text.toString()
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder(this@RegisterActivity).apply {
                        setTitle("Oh No!")
                        setMessage("Please fill in all fields!")
                        setPositiveButton("Okay") { _, _ -> }
                        create()
                        show()
                    }
                    return@setOnClickListener
                }
                observeRegister(name, email, phone, password)
            }
        }
    }

    private fun observeRegister(name: String, email: String, phone: String, password: String) {
        viewModel.register(name, email, password).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is MyResult.Loading -> {
                        showLoading(true)
                    }
                    is MyResult.Success -> {
                        showLoading(false)
                        AlertDialog.Builder(this).apply {
                            setTitle("Success!")
                            setMessage(result.data.message)
                            setPositiveButton("Okay") { _, _ ->
                                openLoginActivity()
                            }
                            setCancelable(false)
                            create()
                            show()
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
        binding.signupButton.isActivated = !isLoading
        binding.signupButton.text = if (isLoading) "" else "Sign Up"
    }

    private fun openLoginActivity() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
