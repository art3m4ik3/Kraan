package ru.art3m4ik3.kraan.presentation.pages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.art3m4ik3.kraan.R
import ru.art3m4ik3.kraan.data.models.Message
import ru.art3m4ik3.kraan.data.storage.RetrofitHelper
import ru.art3m4ik3.kraan.databinding.FragmentContactScreenBinding
import java.util.regex.Pattern


class ContactScreenFragment : Fragment() {
    lateinit var binding: FragmentContactScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactScreenBinding.inflate(inflater, container, false)

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isValidEmail(s.toString().trim())) {
                    binding.errorTextView.visibility = View.GONE
                } else {
                    showError("Неверный email.")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.sendButton.setOnClickListener {
            send()
        }

        binding.loginButton.setOnClickListener {
            login()
        }

        // TODO: get auth data from api
        val authorized = true
        if (authorized) {
            binding.loginButton.visibility = View.GONE
        } else {
            binding.loginButton.visibility = View.VISIBLE
        }

        return binding.root
    }

    private fun send() {
        val name = binding.nameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val message = binding.messageEditText.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
            showError("Все поля должны быть заполнены.")
            return
        }

        if (!isValidEmail(email)) {
            showError("Неверный email.")
            return
        }

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

    private fun login() {
        // TODO: open login screen
    }

    private fun showError(message: String) {
        binding.errorTextView.text = message
        binding.errorTextView.visibility = View.VISIBLE
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9]+\\.[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$"
        val pattern = Pattern.compile(emailRegex)

        return pattern.matcher(email).matches()
    }
}