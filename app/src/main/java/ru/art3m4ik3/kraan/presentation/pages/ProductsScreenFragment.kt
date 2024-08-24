package ru.art3m4ik3.kraan.presentation.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.art3m4ik3.kraan.data.models.Product
import ru.art3m4ik3.kraan.databinding.FragmentProductsScreenBinding
import ru.art3m4ik3.kraan.presentation.adapters.ProductsAdapter

class ProductsScreenFragment : Fragment() {
    private var _binding: FragmentProductsScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductsAdapter
    private lateinit var addButton: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsScreenBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerViewProducts
        addButton = binding.buttonAddProduct
        adapter = ProductsAdapter(::onDeleteProduct)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        // TODO: get auth data from api
        val authorized = true
        if (authorized) {
            addButton.visibility = View.VISIBLE
            addButton.setOnClickListener {
                // TODO: open AddProductScreen fragment
            }
        } else {
            addButton.visibility = View.GONE
        }

        loadProducts()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadProducts() {
        // TODO: load products from api

        val products = listOf(
            Product(1, "Продукт 1", "Описание 1", "Характеристики 1", "img.png", 100.0),
            Product(2, "Продукт 2", "Описание 2", "Характеристики 2", "img.png", 200.0),
            Product(3, "Продукт 3", "Описание 3", "Характеристики 3", "img.png", 300.0)
        )

        adapter.submitList(products)
    }

    private fun onDeleteProduct(productId: Int) {
        // TODO: delete product from api

        loadProducts()
    }
}