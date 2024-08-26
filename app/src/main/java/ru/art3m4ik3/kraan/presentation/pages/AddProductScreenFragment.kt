package ru.art3m4ik3.kraan.presentation.pages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.art3m4ik3.kraan.databinding.FragmentAddProductScreenBinding

class AddProductScreenFragment : Fragment() {
    private var _binding: FragmentAddProductScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductScreenBinding.inflate(inflater, container, false)

        binding.buttonAddProduct.setOnClickListener {
            addProduct()
        }

        binding.editTextTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.editTextImageUrl.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.editTextDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.editTextCharacteristics.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addProduct() {
        if (!validateInput()) {
            return
        }

        // TODO: add product via api
    }

    private fun validateInput(): Boolean {
        binding.textViewError.visibility = View.GONE

        val title = binding.editTextTitle.text.toString().trim()
        val imageUrl = binding.editTextImageUrl.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val characteristics = binding.editTextCharacteristics.text.toString().trim()

        if (title.length < 3 || title.length > 50) {
            showError("Некорректное название.")
            return false
        }

        if (!imageUrl.contains("https://")) {
            showError("URL изображения некорректный, используйте HTTPS протокол.")
            return false
        }

        if (description.length < 10 || description.length > 1000) {
            showError("Некорректное описание.")
            return false
        }

        if (characteristics.length < 10 || characteristics.length > 1000) {
            showError("Некорректные характеристики.")
            return false
        }

        if (title.isEmpty() || imageUrl.isEmpty() || description.isEmpty() || characteristics.isEmpty()) {
            showError("Все поля должны быть заполнены.")
            return false
        }

        return true
    }

    private fun showError(message: String) {
        binding.textViewError.text = message
        binding.textViewError.visibility = View.VISIBLE
    }
}