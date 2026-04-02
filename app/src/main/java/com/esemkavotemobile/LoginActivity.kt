package com.esemkavotemobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.esemkavotemobile.api.AuthAPI
import com.esemkavotemobile.databinding.ActivityLoginBinding
import com.esemkavotemobile.model.LoginRequest
import com.esemkavotemobile.parser.AuthParser
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                setAlertDialog("Warning", "Please fill all input field")
                return@setOnClickListener
            }

            val request = LoginRequest(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )

            thread {
                val response = AuthAPI.login(request)
                runOnUiThread {
                    if (response.data.isEmpty()) {
                        setAlertDialog("Warning", response.message)
                    }
                    else {
                        val loginResponse = AuthParser.loginResponse(response)
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("token", loginResponse.token)
                        startActivity(intent)
                        finish()
                    }

                }
            }
        }
    }

    private fun setAlertDialog(title: String, message : String) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }
}