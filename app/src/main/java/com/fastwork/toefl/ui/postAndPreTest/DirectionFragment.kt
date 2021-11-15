package com.fastwork.toefl.ui.postAndPreTest

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fastwork.toefl.R
import com.fastwork.toefl.data.local.model.ModelDirection
import com.fastwork.toefl.databinding.FragmentDirectionBinding
import com.fastwork.toefl.utils.DIRECTION_KEY


class DirectionFragment : Fragment() {

    private var dataDirection: ModelDirection? = null
    private lateinit var binding: FragmentDirectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_direction, container, false)
        dataDirection = arguments?.get(DIRECTION_KEY) as ModelDirection
        setupData()
        setupListener()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setupData() {
        if (dataDirection != null) {
            if (dataDirection?.category == PRETEST_TYPE) {
                binding.tvTitle.text = getString(R.string.label_pre_test)
                binding.textDirection.text = getString(R.string.pre_test_direction)
            } else if (dataDirection?.category == POSTTEST_TYPE) {
                binding.tvTitle.text = getString(R.string.label_post_test)
                binding.textDirection.text = getString(R.string.post_test_direction)
            }
            binding.chanceCount.text = dataDirection?.chanceCount.toString() + " Times"
        }
    }

    private fun setupListener() {
        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        const val PRETEST_TYPE = "pretest"
        const val POSTTEST_TYPE = "posttest"
    }
}