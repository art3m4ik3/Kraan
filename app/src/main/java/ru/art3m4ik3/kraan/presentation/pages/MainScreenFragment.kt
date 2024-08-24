package ru.art3m4ik3.kraan.presentation.pages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import ru.art3m4ik3.kraan.R
import ru.art3m4ik3.kraan.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)

        setCompanyInfo()
        setFeatures(
            listOf(
                mapOf("name" to "this is a name1", "description" to "this is a description1"),
                mapOf("name" to "this is a name2", "description" to "this is a description2"),
                mapOf("name" to "this is a name3", "description" to "this is a description3")
            )
        )

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCompanyInfo() {
        view.apply {
            // TODO: get info from api

            binding.companyNameTextView.text = "Kraan"
            binding.companyInfoTextView.text =
                "Kraan - ведущая компания по продаже мобильных кранов. Мы предлагаем широкий выбор колесных кранов грузоподъёемностью от 16 до 300 тонн для различных строительных и промышленных задач."
        }
    }

    private fun setFeatures(features: List<Map<String, String>>) {
        view.apply {
            // TODO: get info from api

            features.forEach { map ->
                val cardView = LayoutInflater.from(context)
                    .inflate(R.layout.feature_card, binding.featuresContainer, false) as CardView

                map.forEach { (key, value) ->
                    Log.i(key, value)

                    when (key) {
                        "name" -> {
                            cardView.findViewById<TextView>(R.id.featureTitleTextView).text = value
                        }

                        "description" -> {
                            cardView.findViewById<TextView>(R.id.featureDescriptionTextView).text =
                                value
                        }
                    }
                }

                val layoutParams = LinearLayout.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.feature_card_width),
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.marginEnd =
                    resources.getDimensionPixelSize(R.dimen.feature_card_margin)
                cardView.layoutParams = layoutParams

                binding.featuresContainer.addView(cardView)
            }
        }
    }
}