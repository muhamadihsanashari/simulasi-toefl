package com.fastwork.toefl.ui.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.data.local.model.TestType
import com.fastwork.toefl.databinding.FragmentPracticeBinding
import com.fastwork.toefl.ui.practice.test.PracticeTestFragment
import com.fastwork.toefl.utils.TEST_TYPE_KEY
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
        binding.btnListening.setOnClickListener {
            category = LISTENING
            setCategory(category)
        }
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
        binding.btnEasyPassage.setOnClickListener {
            val bundle = Bundle()
            val testType = TestType(PracticeTestFragment.READING, PracticeTestFragment.READING_EASY)
            bundle.putSerializable(TEST_TYPE_KEY, testType)
            findNavController().navigate(R.id.action_to_practice_test, bundle)
        }
        binding.btnMediumPassage.setOnClickListener {
            val bundle = Bundle()
            val testType = TestType(PracticeTestFragment.READING, PracticeTestFragment.READING_MEDIUM)
            bundle.putSerializable(TEST_TYPE_KEY, testType)
            findNavController().navigate(R.id.action_to_practice_test, bundle)
        }
        binding.btnHardPassage.setOnClickListener {
            val bundle = Bundle()
            val testType = TestType(PracticeTestFragment.READING, PracticeTestFragment.READING_HARD)
            bundle.putSerializable(TEST_TYPE_KEY, testType)
            findNavController().navigate(R.id.action_to_practice_test, bundle)
        }
    }

    private fun setCategory(category: Int) {
        when (category) {
            LISTENING -> {
                binding.parentListening.visibility = View.VISIBLE
                binding.parentStructure.visibility = View.GONE
                binding.parentReading.visibility = View.GONE
            }
            READING -> {
                binding.parentReading.visibility = View.VISIBLE
                binding.parentStructure.visibility = View.GONE
                binding.parentListening.visibility = View.GONE
            }
            STURCTURE -> {
                binding.parentReading.visibility = View.GONE
                binding.parentStructure.visibility = View.VISIBLE
                binding.parentListening.visibility = View.GONE
            }
        }
    }


    companion object {
        const val LISTENING = 0
        const val READING = 1
        const val STURCTURE = 2

    }
}