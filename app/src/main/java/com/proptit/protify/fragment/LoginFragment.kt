package com.proptit.protify.fragment

import android.os.Bundle

import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.proptit.protify.R
import com.proptit.protify.databinding.FragmentLoginBinding
import com.proptit.protify.extensions.hide
import com.proptit.protify.extensions.show

import com.proptit.protify.models.LoginResponse
import com.proptit.protify.services.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private  var password:String = ""
    private  var email:String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerButton.setOnClickListener { findNavController().navigate(R.id.registerFragment) }
        binding.edtEmail.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                onValidateEmail(text)
            }
        }
        binding.edtPassword.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                onValidatePassword(text)
            }
        }
        binding.loginButton.setOnClickListener {
            onCheckForm()
        }
    }
    override fun onResume() {
        super.onResume()
        binding.edtEmail.error = null
        binding.edtPassword.error = null
    }

    private fun onValidateEmail(text:CharSequence){
        if(text.isEmpty()){
            binding.edtEmail.error = getString(R.string.required)
        }
//        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            binding.edtEmail.error = getString(R.string.in_valid_email)
//        }
        else{
            binding.edtEmail.error = null
        }
    }
    private fun onValidatePassword(text:CharSequence){
        if (text.length < 6) {
            binding.edtPassword.error = getString(R.string.password_error)
        } else {
            binding.edtPassword.error = null
        }
    }

    private fun onCheckForm(){
        if (binding.edtEmail.error == null && binding.edtPassword.error == null) {
            email = binding.edtEmail.text.toString()
            password = binding.edtPassword.text.toString()

            if(email.isEmpty()) binding.edtEmail.error = getString(R.string.required)
            if(password.isEmpty()) binding.edtPassword.error = getString(R.string.required)
            if(email.isNotEmpty() && password.isNotEmpty()){
                onAuth()
            }
        } else {
            Toast.makeText(context, getString(R.string.fill_all_the_form), Toast.LENGTH_SHORT).show()
        }
    }
    private fun onAuth(){
        binding.progressBar.show()
        binding.loginButton.hide()
        Toast.makeText(context, email + password, Toast.LENGTH_SHORT).show()

        val call = ApiClient.apiService.login(email,password)
        call.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.accessToken
                    findNavController().navigate(R.id.homeFragment)
                    Log.d( "onResponse: ", token!!)
                } else {
                    Log.d( "onResponse: ", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d( "onResponse: ",t.toString())
            }
        })
//        binding.progressBar.hide()
//        binding.loginButton.show()
    }
}