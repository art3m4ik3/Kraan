package ru.art3m4ik3.kraan.presentation.pages

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.art3m4ik3.kraan.databinding.FragmentLogoutScreenBinding

class LogoutScreenFragment : Fragment() {
    private var _binding: FragmentLogoutScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogoutScreenBinding.inflate(inflater, container, false)

        binding.logoutButton.setOnClickListener {
            val sharedPref =
                activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
            with(sharedPref.edit()) {
                clear()
                apply()
            }

            Toast.makeText(context, "Успешный выход.", Toast.LENGTH_SHORT).show()

            val activity = activity as? MainActivity
            activity?.openMainFragment()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}