package ru.art3m4ik3.kraan.presentation.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.art3m4ik3.kraan.R
import ru.art3m4ik3.kraan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
            }

            true
        }
    }
}