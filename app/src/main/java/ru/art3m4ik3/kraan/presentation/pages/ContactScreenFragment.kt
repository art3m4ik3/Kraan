package ru.art3m4ik3.kraan.presentation.pages

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.art3m4ik3.kraan.data.models.Message
import ru.art3m4ik3.kraan.data.storage.Auth
import ru.art3m4ik3.kraan.data.storage.RetrofitHelper
import ru.art3m4ik3.kraan.databinding.FragmentContactScreenBinding
import java.util.regex.Pattern


class ContactScreenFragment : Fragment() {
    private var _binding: FragmentContactScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactScreenBinding.inflate(inflater, container, false)

        binding.messageEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.sendButton.setOnClickListener {
            send()
        }

        binding.loginButton.setOnClickListener {
            val activity = activity as? MainActivity
            activity?.openLoginFragment()
        }

        val auth = Auth()
        if (auth.authorized(requireContext())) {
            binding.loginButton.visibility = View.GONE
        } else {
            binding.loginButton.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateInput(): Boolean {
        binding.errorTextView.visibility = View.GONE

        val name = binding.nameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val message = binding.messageEditText.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
            showError("Все поля должны быть заполнены.")
            return false
        }

        val emailRegex = "^[A-Za-z0-9]+\\.[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$"
        val pattern = Pattern.compile(emailRegex)

        if (!pattern.matcher(email).matches()) {
            showError("Неверный формат email.")
            return false
        }

        return true
    }

    private fun send() {
        if (!validateInput()) {
            return
        }

        val name = binding.nameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val message = binding.messageEditText.text.toString().trim()

        binding.errorTextView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        RetrofitHelper.kraanApi.sendMessage(Message(name, email, message)).enqueue(object :
            Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Сообщение успешно отправлено", Toast.LENGTH_SHORT)
                        .show()
                    Log.i("ContactScreenFragment", "${response.code()} ${response.raw()}")

                    binding.nameEditText.text.clear()
                    binding.emailEditText.text.clear()
                    binding.messageEditText.text.clear()
                    binding.progressBar.visibility = View.GONE
                    binding.errorTextView.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                    showError("Ошибка при отправке: ${response.code()} ${response.message()}")
                    Log.e("ContactScreenFragment", "${response.code()} ${response.raw()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                showError("Ошибка при отправке: ${t.message}")
                t.message?.let { Log.e("ContactScreenFragment", it) }
            }
        })
    }

    private fun showError(message: String) {
        binding.errorTextView.text = message
        binding.errorTextView.visibility = View.VISIBLE
    }
}