package ru.art3m4ik3.kraan.presentation.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.art3m4ik3.kraan.R
import ru.art3m4ik3.kraan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: get auth data from api
        val authorized = true
        if (authorized) {
            binding.bottomNavigationView.menu.removeItem(R.id.navigation_login)
        } else {
            binding.bottomNavigationView.menu.removeItem(R.id.navigation_logout)
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, MainScreenFragment())
            .commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == binding.bottomNavigationView.selectedItemId) {
                return@setOnItemSelectedListener true
            }

            when (it.itemId) {
                R.id.navigation_main -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainer.id, MainScreenFragment())
                        .commit()
                }

                R.id.navigation_products -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainer.id, ProductsScreenFragment())
                        .commit()
                }

                R.id.navigation_contacts -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainer.id, ContactScreenFragment())
                        .commit()
                }
            }

            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}