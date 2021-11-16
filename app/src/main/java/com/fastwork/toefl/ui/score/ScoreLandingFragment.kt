package com.fastwork.toefl.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.databinding.FragmentScoreLandingBinding
import com.fastwork.toefl.utils.SCORE_CATEGORY_KEY

class ScoreLandingFragment : Fragment() {

    private lateinit var binding: FragmentScoreLandingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_score_landing, container, false)
        setupListener()
        return binding.root
    }

    private fun setupListener() {
        var category: String = ""
        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnListening.setOnClickListener {
            category = LISTENING
            navigateToScoreList(category)
        }
        binding.btnReading.setOnClickListener {
            category = READING
            navigateToScoreList(category)
        }
        binding.btnStructure.setOnClickListener {
            category = STRUCTURE
            navigateToScoreList(category)
        }
        binding.btnIndividualTestListening.setOnClickListener {
            category = INDIVIDUAL_TEST_LISTENING
            navigateToScoreList(category)
        }
        binding.btnIndividualTestReading.setOnClickListener {
            category = INDIVIDUAL_TEST_READING
            navigateToScoreList(category)
        }
        binding.btnIndividualTestStructure.setOnClickListener {
            category = INDIVIDUAL_TEST_STRUCTURE
            navigateToScoreList(category)
        }
        binding.btnPreTest.setOnClickListener {
            category = PRE_TEST
            navigateToScoreList(category)
        }
        binding.btnPostTest.setOnClickListener {
            category = POST_TEST
            navigateToScoreList(category)
        }
        binding.btnCompleteTest1.setOnClickListener {
            category = FULL_TEST1
            navigateToScoreList(category)
        }
        binding.btnCompleteTest2.setOnClickListener {
            category = FULL_TEST2
            navigateToScoreList(category)
        }
    }

    private fun navigateToScoreList(category: String) {
        val bundle = Bundle().apply { putString(SCORE_CATEGORY_KEY, category) }
        findNavController().navigate(R.id.scoreListFragment, bundle)
    }

    companion object {
        const val LISTENING = "listening"
        const val READING = "reading"
        const val STRUCTURE = "structure"
        const val INDIVIDUAL_TEST_LISTENING = "individual test listening"
        const val INDIVIDUAL_TEST_READING = "individual test reading"
        const val INDIVIDUAL_TEST_STRUCTURE = "individual test structure"
        const val PRE_TEST = "pretest"
        const val POST_TEST = "posttest"
        const val FULL_TEST1 = "fulltest1"
        const val FULL_TEST2 = "fulltest2"
    }
}