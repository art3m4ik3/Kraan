package ru.art3m4ik3.kraan.presentation.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.art3m4ik3.kraan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}