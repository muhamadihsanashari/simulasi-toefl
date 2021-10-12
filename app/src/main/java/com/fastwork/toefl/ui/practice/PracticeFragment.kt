package com.fastwork.toefl.ui.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fastwork.toefl.R
import com.fastwork.toefl.databinding.FragmentPracticeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PracticeFragment : Fragment() {

    private lateinit var binding: FragmentPracticeBinding
    private var category: Int = READING

    private val viewModel by viewModel<PracticeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_practice, container, false)
        binding.apply {
            lifecycleOwner = this@PracticeFragment
            viewModel = this@PracticeFragment.viewModel
        }
        setCategory(category)
        setupListener()
        return binding.root
    }

    private fun setupListener() {
        binding.btnReading.setOnClickListener {
            category = READING
            setCategory(category)
        }
        binding.btnStructure.setOnClickListener {
            category = STURCTURE
            setCategory(category)
        }
        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setCategory(category: Int) {
        when (category) {
            LISTENING -> {
            }
            READING -> {
                binding.parentReading.visibility = View.VISIBLE
                binding.parentStructure.visibility = View.GONE
            }
            STURCTURE -> {
                binding.parentReading.visibility = View.GONE
                binding.parentStructure.visibility = View.VISIBLE
            }
        }
    }


    companion object {
        const val LISTENING = 0
        const val READING = 1
        const val STURCTURE = 2

    }
}