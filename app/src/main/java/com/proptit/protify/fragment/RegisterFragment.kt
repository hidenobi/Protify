package com.proptit.protify.fragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.proptit.protify.R
import com.proptit.protify.databinding.FragmentRegisterBinding
import com.proptit.protify.extensions.hide
import com.proptit.protify.extensions.show
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RegisterFragment : Fragment() {
    private lateinit var binding:FragmentRegisterBinding
    private  var password:String = ""
    private  var firstName:String = ""
    private  var lastName:String = ""
    private  var email:String = ""
    private  var dob:String = ""
    private  var phone:String = ""
    private  var gender:String = ""
    private  var confirmPassword:String = ""
    private lateinit var myCalendar:Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)

        initComponent()

        binding.backToLoginBtn.setOnClickListener { findNavController().popBackStack() }

        binding.edtFirstName.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                onValidateFirstName(text)
            }
        }
        binding.edtFirstName.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                onValidateLastName(text)
            }
        }
        binding.edtEmail.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                onValidateEmail(text)
            }
        }
        binding.edtPhone.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                onValidatePhone(text)
            }
        }
        binding.edtPassword.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                onValidatePassword(text)
            }
        }
        binding.edtConfirmPassword.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                onValidateConfirmPassword(text)
            }
        }
        binding.registerBtn.setOnClickListener {
            onCheckForm()
        }
        binding.edtDob.setOnClickListener{setDatePicker(binding.edtDob)}
        return binding.root
    }

    private fun initComponent() {
        myCalendar = Calendar.getInstance()
    }

    private fun onValidateFirstName(text:CharSequence){
        if (text.isEmpty()) {
            binding.edtFirstName.error = getString(R.string.required)
        } else {
            binding.edtFirstName.error = null
        }
    }
    private fun onValidateLastName(text:CharSequence){
        if (text.isEmpty()) {
            binding.edtLastName.error = getString(R.string.required)
        } else {
            binding.edtLastName.error = null
        }
    }
    private fun onValidateEmail(text:CharSequence){
        email = binding.edtEmail.text.toString()
        if(text.isEmpty()){
            binding.edtEmail.error = getString(R.string.required)
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edtEmail.error = getString(R.string.in_valid_email)
        }
        else{
            binding.edtEmail.error = null
        }
    }
    private fun onValidatePhone(text:CharSequence){
        if (text.length < 10) {
            binding.edtPhone.error = getString(R.string.phone_error)
        }else if(!Patterns.PHONE.matcher(text).matches()){
            binding.edtPhone.error = getString(R.string.in_valid_phone)
        }
        else {
            binding.edtPhone.error = null
        }
    }
    private fun onValidatePassword(text:CharSequence){
        if (text.length < 8) {
            binding.edtPassword.error = getString(R.string.password_error)
        } else {
            binding.edtPassword.error = null
        }
    }
    private fun onValidateConfirmPassword(text:CharSequence){
        password = binding.edtPassword.text.toString()
        confirmPassword = text.toString()
        if (confirmPassword != password) {
            binding.edtConfirmPassword.error = getString(R.string.confirm_password_error)
        } else {
            binding.edtConfirmPassword.error = null
        }
    }

    private fun onCheckForm(){
        if(binding.edtFirstName.error == null && binding.edtPassword.error == null && binding.edtEmail.error == null &&
            binding.edtConfirmPassword.error == null){
            firstName = binding.edtFirstName.text.toString()
            password = binding.edtPassword.text.toString()
            email = binding.edtEmail.text.toString()
            confirmPassword = binding.edtConfirmPassword.text.toString()

            if(firstName.isEmpty()) binding.edtFirstName.error = getString(R.string.required)
            if(email.isEmpty()) binding.edtEmail.error = getString(R.string.required)
            if(password.isEmpty()) binding.edtPassword.error = getString(R.string.required)
            if(confirmPassword.isEmpty()) binding.edtConfirmPassword.error = getString(R.string.required)
            if(firstName.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && confirmPassword.isNotEmpty()){
                onRegister()
            }
        }else{
            Toast.makeText(context, getString(R.string.fill_all_the_form), Toast.LENGTH_SHORT).show()
        }
    }

    private fun onRegister(){

        binding.progressBar.show()
        binding.registerBtn.hide()
        Toast.makeText(context, firstName + password, Toast.LENGTH_SHORT).show()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            binding.progressBar.hide()
            binding.registerBtn.show()
            findNavController().popBackStack()
        }, 1000)

    }
    private fun setDatePicker(edtTime: EditText) {
        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel(edtTime)
            }
        val datePickerDialog = DatePickerDialog(
            requireContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(
                Calendar.MONTH
            ), myCalendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    private fun updateLabel(edtTime: EditText) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        edtTime.setText(dateFormat.format(myCalendar.getTime()))
    }
}