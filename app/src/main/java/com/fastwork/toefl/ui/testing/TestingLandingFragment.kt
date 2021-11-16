package com.fastwork.toefl.ui.testing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.data.local.model.ModelSession
import com.fastwork.toefl.data.local.model.TestType
import com.fastwork.toefl.databinding.FragmentTestingLandingBinding
import com.fastwork.toefl.ui.postAndPreTest.DirectionFragment
import com.fastwork.toefl.ui.postAndPreTest.FullTestFragment
import com.fastwork.toefl.ui.practice.test.PracticeTestFragment
import com.fastwork.toefl.utils.SESSION_KEY
import com.fastwork.toefl.utils.TEST_CATEGORY_KEY
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
            val bundle = Bundle().apply {
                putString(TEST_CATEGORY_KEY, DirectionFragment.FULLTEST_ONE)
                val dataSession = ModelSession(
                    session = FullTestFragment.LISTENING_SESSION,
                    category = DirectionFragment.FULLTEST_ONE
                )
                putSerializable(SESSION_KEY, dataSession)
            }
            findNavController().navigateUp()
            findNavController().navigate(R.id.preAndPostTestFragment, bundle)
        }
        binding.btnFullTestTwo.setOnClickListener {
            val bundle = Bundle().apply {
                putString(TEST_CATEGORY_KEY, DirectionFragment.FULLTEST_TWO)
                val dataSession = ModelSession(
                    session = FullTestFragment.LISTENING_SESSION,
                    category = DirectionFragment.FULLTEST_TWO
                )
                putSerializable(SESSION_KEY, dataSession)
            }
            findNavController().navigateUp()
            findNavController().navigate(R.id.preAndPostTestFragment, bundle)
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