package ru.art3m4ik3.kraan.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.art3m4ik3.kraan.R
import ru.art3m4ik3.kraan.data.models.Product

class ProductsAdapter(private val onDeleteClick: (Int) -> Unit) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private var products: List<Product> = emptyList()

    fun submitList(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewProductName)
        private val priceTextView: TextView = itemView.findViewById(R.id.textViewProductPrice)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDeleteProduct)

        fun bind(product: Product) {
            nameTextView.text = product.title
            priceTextView.text = "Цена: ${product.price}"

            // TODO: get auth data from api
            val authorized = false
            if (authorized) {
                deleteButton.visibility = View.VISIBLE
                deleteButton.setOnClickListener { onDeleteClick(product.id) }
            } else {
                deleteButton.visibility = View.GONE
            }
        }
    }
}