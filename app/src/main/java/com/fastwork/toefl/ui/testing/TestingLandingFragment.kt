package com.fastwork.toefl.ui.testing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.data.local.model.TestType
import com.fastwork.toefl.databinding.FragmentTestingLandingBinding
import com.fastwork.toefl.ui.practice.test.PracticeTestFragment
import com.fastwork.toefl.utils.TEST_TYPE_KEY

class TestingLandingFragment : Fragment() {

    private lateinit var binding: FragmentTestingLandingBinding
    private var category: Int = INDIVIDUAL_TEST

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_testing_landing, container, false)
        setCategory(category)
        setupListener()
        return binding.root
    }

    private fun setupListener() {
        binding.btnListening.setOnClickListener {
            val bundle = Bundle().apply {
                val dataTest = TestType(
                    PracticeTestFragment.INDIVIDUAL_TEST_LISTENING,
                    PracticeTestFragment.INDIVIDUAL_SUB_CATEGORY
                )
                putSerializable(TEST_TYPE_KEY, dataTest)
            }
            findNavController().navigate(R.id.practiceTestFragment, bundle)
        }
        binding.btnReading.setOnClickListener {
            val bundle = Bundle().apply {
                val dataTest = TestType(
                    PracticeTestFragment.INDIVIDUAL_TEST_READING,
                    PracticeTestFragment.INDIVIDUAL_SUB_CATEGORY
                )
                putSerializable(TEST_TYPE_KEY, dataTest)
            }
            findNavController().navigate(R.id.practiceTestFragment, bundle)
        }
        binding.btnStructure.setOnClickListener {
            val bundle = Bundle().apply {
                val dataTest = TestType(
                    PracticeTestFragment.INDIVIDUAL_TEST_STRUCTURE,
                    PracticeTestFragment.INDIVIDUAL_SUB_CATEGORY
                )
                putSerializable(TEST_TYPE_KEY, dataTest)
            }
            findNavController().navigate(R.id.practiceTestFragment, bundle)
        }
        binding.btnFullTestOne.setOnClickListener {

        }
        binding.btnFullTestTwo.setOnClickListener {

        }
        binding.btnIndividualTest.setOnClickListener {
            setCategory(INDIVIDUAL_TEST)
        }
        binding.btnCompleteTest.setOnClickListener {
            setCategory(COMPLETE_TEST)
        }
    }

    private fun setCategory(category: Int) {
        when (category) {
            INDIVIDUAL_TEST -> {
                binding.parentIndividualTest.visibility = View.VISIBLE
                binding.parentFullTest.visibility = View.GONE
            }
            COMPLETE_TEST -> {
                binding.parentIndividualTest.visibility = View.GONE
                binding.parentFullTest.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val INDIVIDUAL_TEST = 0
        const val COMPLETE_TEST = 1
    }
}