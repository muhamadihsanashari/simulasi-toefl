package com.fastwork.toefl.ui.practice.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fastwork.toefl.R
import com.fastwork.toefl.data.local.model.Question
import com.fastwork.toefl.data.local.model.TestType
import com.fastwork.toefl.databinding.FragmentTestPracticeBinding
import com.fastwork.toefl.utils.TEST_TYPE_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class PracticeTestFragment : Fragment() {

    private var testType: TestType? = null
    private lateinit var binding: FragmentTestPracticeBinding
    private val viewModel by viewModel<PracticeTestViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_test_practice, container, false
        )
        binding.apply {
            viewModel = this@PracticeTestFragment.viewModel
            lifecycleOwner = this@PracticeTestFragment
        }
        testType = arguments?.get(TEST_TYPE_KEY) as TestType?
        setupData()
        setupObserver()
        return binding.root
    }

    private fun setupData() {
        if (testType?.category == READING) {
            testType?.subCategory?.let { viewModel.getReadingQuestion(it) }
        }
    }

    private fun setupObserver() {
        viewModel.readLiveData.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {

            }
        })
    }

    companion object {
        const val LISTENING = "listening"
        const val READING = "reading"
        const val STRUCTURE = "structure"
        const val READING_EASY = "easy"
        const val READING_MEDIUM = "medium"
        const val READING_HARD = "hard"
        const val LISTENING_SORT_DIALOG = "sort dialog"
        const val LISTENING_CASUAL_CONVERSATION = "casual conversation"
        const val LISTENING_ACADEMIC_DISCUSSION = "academic discussion"
        const val LISTENING_ACADEMIC_LECTURES = "academic lectures"
        const val STRUCTURE_ONE_CLAUSE = "one clause"
        const val STRUCTURE_MULTIPLE_CLAUSES = "multiple clauses"
        const val STRUCTURE_MORE_MULTIPLE_CLAUSE = "more multiple clauses"
        const val STRUCTURE_REDUCED_CLAUSES = "reduced clauses"
        const val STRUCTURE_INVERTED_SUBJECT_AND_VERB = "inverted subject and verb"
    }
}