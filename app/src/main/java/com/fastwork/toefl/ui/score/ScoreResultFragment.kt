package com.fastwork.toefl.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.data.local.model.ScoreType
import com.fastwork.toefl.databinding.FragmentScoreResultBinding
import com.fastwork.toefl.utils.SCORE_TYPE_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreResultFragment : Fragment() {

    private lateinit var binding: FragmentScoreResultBinding
    private val viewModel: ScoreViewModel by viewModel()
    private var scoreType: ScoreType? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_score_result, container, false)
        binding.apply {
            viewModel = this@ScoreResultFragment.viewModel
            lifecycleOwner = this@ScoreResultFragment
        }
        scoreType = arguments?.get(SCORE_TYPE_KEY) as ScoreType
        setupData()
        setupListener()
        return binding.root
    }

    private fun setupListener() {
        binding.btnToMain.setOnClickListener {
            viewModel.insertData(scoreType)
        }
        viewModel.successInsert.observe(this, {
            findNavController().navigateUp()
            findNavController().navigate(R.id.mainFragment)
        })
    }

    private fun setupData() {
        binding.score.text = scoreType?.score.toString()
    }

}