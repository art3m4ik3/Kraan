package ru.art3m4ik3.kraan.presentation.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.art3m4ik3.kraan.R
import ru.art3m4ik3.kraan.data.storage.Auth
import ru.art3m4ik3.kraan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth = Auth()
        if (auth.authorized(this)) {
            binding.bottomNavigationView.menu.removeItem(R.id.navigation_login)
        } else {
            binding.bottomNavigationView.menu.removeItem(R.id.navigation_logout)
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, MainScreenFragment())
            .commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
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

                R.id.navigation_login -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainer.id, LoginScreenFragment())
                        .commit()
                }

                R.id.navigation_logout -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainer.id, LogoutScreenFragment())
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

    fun openLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginScreenFragment())
            .commit()

        binding.bottomNavigationView.setSelectedItemId(R.id.navigation_login)
    }

    fun openAddProductFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AddProductScreenFragment())
            .commit()
    }

    fun openMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainScreenFragment())
            .commit()

        binding.bottomNavigationView.setSelectedItemId(R.id.navigation_main)
    }
}