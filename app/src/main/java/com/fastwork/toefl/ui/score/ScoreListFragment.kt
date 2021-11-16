package com.fastwork.toefl.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fastwork.toefl.R
import com.fastwork.toefl.databinding.FragmentListScoreBinding
import com.fastwork.toefl.ui.score.adapter.AdapterScore
import com.fastwork.toefl.utils.SCORE_CATEGORY_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreListFragment : Fragment() {

    private lateinit var adapter: AdapterScore
    private lateinit var binding: FragmentListScoreBinding
    private val viewModel: ScoreViewModel by viewModel()

    private var category: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_score, container, false)
        binding.apply {
            viewModel = this@ScoreListFragment.viewModel
            lifecycleOwner = this@ScoreListFragment
        }
        setupData()
        setupListener()
        setupObserver()
        return binding.root
    }

    private fun setupListener() {
        binding.btnClear.setOnClickListener {
            viewModel.resetScores(category)
        }
    }

    private fun setupObserver() {
        viewModel.scoreListLiveData.observe(viewLifecycleOwner, {
            adapter.addItems(it)
        })
        viewModel.successDelete.observe(viewLifecycleOwner, {
            adapter.clearItems()
        })
    }

    private fun setupData() {
        adapter = AdapterScore(arrayListOf())
        binding.adapter = adapter
        category = arguments?.getString(SCORE_CATEGORY_KEY)
        binding.tvTitle.text = category?.capitalize()
        viewModel.getScores(category)
    }
}