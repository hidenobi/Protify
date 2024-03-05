package com.proptit.protify

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.proptit.protify.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private  var password:String = ""
    private  var name:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registerButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.edtName.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                binding.edtName.error = getString(R.string.required)
            } else {
                binding.edtName.error = null
            }
        }
        binding.edtPassword.doOnTextChanged { text, start, before, count ->
            if (text!!.length < 8) {
                binding.edtPassword.error = getString(R.string.password_error)
            } else {
                binding.edtPassword.error = null
            }
        }
        binding.loginButton.setOnClickListener {
            if (binding.edtName.error == null && binding.edtPassword.error == null) {
                name = binding.edtName.text.toString()
                password = binding.edtPassword.text.toString()
                onLogin()
            } else {
                Toast.makeText(this, getString(R.string.fill_all_the_form), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun onLogin(){
        if(name.isEmpty()) binding.edtName.error = getString(R.string.required)
        if(password.isEmpty()) binding.edtPassword.error = getString(R.string.required)

        if(name.isNotEmpty() && password.isNotEmpty()){
            binding.progressBar.visibility = View.VISIBLE
            binding.loginButton.visibility = View.GONE
            Toast.makeText(this, name + password, Toast.LENGTH_SHORT).show()
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                binding.progressBar.visibility = View.INVISIBLE
                binding.loginButton.visibility = View.VISIBLE
            }, 1000)
        }
    }

}