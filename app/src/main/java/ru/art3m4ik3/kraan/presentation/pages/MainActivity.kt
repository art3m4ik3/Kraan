package ru.art3m4ik3.kraan.presentation.pages

import android.content.Context
import android.os.Bundle
import android.view.Menu
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
        val authorized = false
        // isTokenValid()
        if (authorized) {
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
            }

            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun isTokenValid(): Boolean {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val token = sharedPref.getString("AUTH_TOKEN", null)

        // TODO: get auth data from api

        return token != null
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
}