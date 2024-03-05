package com.proptit.protify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.proptit.protify.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    private  var password:String = ""
    private  var name:String = ""
    private  var email:String = ""
    private  var confirmPassword:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backToLoginBtn.setOnClickListener { finish() }

        binding.edtName.doOnTextChanged { text, start, before, count ->
            if(text!!.isEmpty()){
                binding.edtName.error = getString(R.string.required)
            }else{
                binding.edtName.error = null
            }
        }
        binding.edtEmail.doOnTextChanged { text, start, before, count ->
            email = binding.edtEmail.text.toString()
            if(text!!.isEmpty()){
                binding.edtEmail.error = getString(R.string.required)
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmail.error = getString(R.string.in_valid_email)
            }
            else{
                binding.edtEmail.error = null
            }
        }
        binding.edtPassword.doOnTextChanged { text, start, before, count ->
            if(text!!.length < 8){
                binding.edtPassword.error = getString(R.string.password_error)
            }else{
                binding.edtPassword.error = null
            }
        }
        binding.edtConfirmPassword.doOnTextChanged { text, start, before, count ->
             password = binding.edtPassword.text.toString()
             confirmPassword = text.toString()
            if (confirmPassword != password) {
                binding.edtConfirmPassword.error = getString(R.string.confirm_password_error)
            } else {
                binding.edtConfirmPassword.error = null
            }

        }
        binding.registerBtn.setOnClickListener {
            if(binding.edtName.error == null && binding.edtPassword.error == null && binding.edtName.error == null &&
                binding.edtConfirmPassword.error == null){
                name = binding.edtName.text.toString()
                password = binding.edtPassword.text.toString()
                email = binding.edtEmail.text.toString()
                confirmPassword = binding.edtConfirmPassword.text.toString()
                onRegister()
            }else{
                Toast.makeText(this, getString(R.string.fill_all_the_form), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onRegister(){
        if(name.isEmpty()) binding.edtName.error = getString(R.string.required)
        if(email.isEmpty()) binding.edtEmail.error = getString(R.string.required)
        if(password.isEmpty()) binding.edtPassword.error = getString(R.string.required)
        if(confirmPassword.isEmpty()) binding.edtConfirmPassword.error = getString(R.string.required)

        if(name.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && confirmPassword.isNotEmpty()){
            binding.progressBar.visibility = View.VISIBLE
            binding.registerBtn.visibility = View.GONE
            Toast.makeText(this, name + password, Toast.LENGTH_SHORT).show()
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                binding.progressBar.visibility = View.INVISIBLE
                binding.registerBtn.visibility = View.VISIBLE
            }, 1000)
        }
    }
}