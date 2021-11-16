package com.fastwork.toefl.ui.postAndPreTest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.data.local.model.ModelSession
import com.fastwork.toefl.data.local.model.ScoreType
import com.fastwork.toefl.databinding.FragmentSessionBinding
import com.fastwork.toefl.utils.SCORE_TYPE_KEY
import com.fastwork.toefl.utils.SESSION_KEY
import com.fastwork.toefl.utils.TEST_CATEGORY_KEY
import kotlin.math.roundToInt

class SessionFragment : Fragment() {
    private var dataTest: ModelSession? = null
    private lateinit var binding: FragmentSessionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_session, container, false)
        dataTest = arguments?.getSerializable(SESSION_KEY) as ModelSession
        setupData()
        setupListener()
        return binding.root
    }

    private fun setupData() {
        if (dataTest?.session == FullTestFragment.LISTENING_SESSION) {
            binding.tvSessionText.text = getString(R.string.listening_session_complete)
            return
        }
        if (dataTest?.session == FullTestFragment.READING_SESSION) {
            binding.tvSessionText.text = getString(R.string.reading_session_complete)
            return
        }
        if (dataTest?.session == FullTestFragment.STRUCTURE_SESSION) {
            binding.tvSessionText.text = getString(R.string.all_session_complete)
            binding.btnNext.text = getString(R.string.finish)
            return
        }
    }

    private fun setupListener() {
        binding.btnPrevious.setOnClickListener {
            findNavController().navigate(R.id.dialogExitSession)
        }
        binding.btnNext.setOnClickListener {
            if (dataTest?.session == FullTestFragment.LISTENING_SESSION) {
                val bundle = Bundle().apply {
                    val dataSession = ModelSession(
                        session = FullTestFragment.READING_SESSION,
                        category = dataTest?.category,
                        dataTest = dataTest?.dataTest
                    )
                    putSerializable(SESSION_KEY, dataSession)
                    putSerializable(TEST_CATEGORY_KEY, dataTest?.category)
                }
                findNavController().navigateUp()
                findNavController().navigate(R.id.preAndPostTestFragment, bundle)
            }
            if (dataTest?.session == FullTestFragment.READING_SESSION) {
                val bundle = Bundle().apply {
                    val dataSession = ModelSession(
                        session = FullTestFragment.STRUCTURE_SESSION,
                        category = dataTest?.category,
                        dataTest = dataTest?.dataTest
                    )
                    putSerializable(SESSION_KEY, dataSession)
                    putSerializable(TEST_CATEGORY_KEY, dataTest?.category)
                }
                findNavController().navigateUp()
                findNavController().navigate(R.id.preAndPostTestFragment, bundle)
            }
            if (dataTest?.session == FullTestFragment.STRUCTURE_SESSION) {
                val bundle = Bundle().apply {
                    val scoreType = ScoreType(dataTest?.category, calculateScore())
                    putSerializable(SCORE_TYPE_KEY, scoreType)
                }
                findNavController().navigateUp()
                findNavController().navigate(R.id.scoreResultFragment, bundle)
            }
        }
    }

    private fun calculateScore(): Int? {
        return dataTest?.getConversionScore()?.toDouble()?.div(3)?.times(10)?.roundToInt()
    }

}