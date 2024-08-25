package ru.art3m4ik3.kraan.presentation.pages

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.art3m4ik3.kraan.databinding.FragmentLoginScreenBinding


class LoginScreenFragment : Fragment() {
    private var _binding: FragmentLoginScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {
            login()
        }

        binding.usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.showPasswordButton.setOnClickListener {
            if (binding.passwordEditText.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                binding.passwordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.confirmPasswordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                binding.passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.confirmPasswordEditText.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            binding.passwordEditText.setSelection(binding.passwordEditText.text.length)
            binding.confirmPasswordEditText.setSelection(binding.confirmPasswordEditText.text.length)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showError(message: String) {
        binding.errorTextView.text = message
        binding.errorTextView.visibility = View.VISIBLE
    }

    private fun login() {
        if (!validateInput()) {
            return
        }

        if (validateInput() == false) {
            return
        }

        // TODO: get auth data from api
        val token = "token"
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("AUTH_TOKEN", token)
            apply()
        }

        Toast.makeText(context, "Вход выполнен. Перезапустите приложение", Toast.LENGTH_LONG).show()
        System.exit(0)
    }

    private fun validateInput(): Boolean {
        binding.errorTextView.visibility = View.GONE

        val username = binding.usernameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

        val lengthValid = password.length in 6..32
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { it in "!@#$%^&*()_+-=[]{}|;:'\",.<>?/" }

        val isValidPassword =
            lengthValid && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Все поля должны быть заполнены")
            return false
        }

        if (password != confirmPassword) {
            showError("Пароли не совпадают")
            return false
        }

        val passwordRegex =
            "^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{6,32}\$".toRegex()
        if (!passwordRegex.matches(password) || !isValidPassword) {
            showError("Пароль не соответствует требованиям")
            return false
        }

        return true
    }
}