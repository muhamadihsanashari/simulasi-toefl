package com.fastwork.toefl.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.data.local.model.ModelDirection
import com.fastwork.toefl.databinding.FragmentMainBinding
import com.fastwork.toefl.ui.postAndPreTest.DirectionFragment
import com.fastwork.toefl.ui.score.ScoreLandingFragment
import com.fastwork.toefl.utils.DIRECTION_KEY
import com.fastwork.toefl.utils.SCORE_CATEGORY_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainPageFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val mainMenuViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.apply {
            viewModel = mainMenuViewModel
            lifecycleOwner = this@MainPageFragment
        }
        setupData()
        setupObserver()
        return binding.root
    }

    private fun setupData() {
        if (mainMenuViewModel.userRoles != null && mainMenuViewModel.userRoles == "admin") {
            binding.parentAdmin.visibility = View.VISIBLE
            binding.parentUser.visibility = View.GONE
            binding.ivPhoto.setImageResource(R.drawable.ic_photo_admin)
            binding.tvName.text = "Admin"
            binding.tvNim.visibility = View.GONE
        } else {
            binding.parentAdmin.visibility = View.GONE
            binding.parentUser.visibility = View.VISIBLE
        }
    }

    private fun setupObserver() {
        mainMenuViewModel.onPracticeClicked.observe(this, {
            findNavController().navigate(R.id.toPracticeFragment)
        })
        mainMenuViewModel.onTestingClicked.observe(this, {
            findNavController().navigate(R.id.fragmentTestinLanding)
        })
        mainMenuViewModel.onPreTestClicked.observe(this, {
            val bundle = Bundle().apply {
                val data = ModelDirection(
                    DirectionFragment.PRETEST_TYPE,
                    mainMenuViewModel.preTestChance
                )
                putSerializable(DIRECTION_KEY, data)
            }
            findNavController().navigate(R.id.directionFragment, bundle)
        })
        mainMenuViewModel.onPostTestClicked.observe(this, {
            val bundle = Bundle().apply {
                val data = ModelDirection(
                    DirectionFragment.POSTTEST_TYPE,
                    mainMenuViewModel.postTestChance
                )
                putSerializable(DIRECTION_KEY, data)
            }
            findNavController().navigate(R.id.directionFragment, bundle)
        })
        mainMenuViewModel.onScoreClicked.observe(this, {
            findNavController().navigate(R.id.scoreLanding)
        })
        mainMenuViewModel.onProfileClicked.observe(this, {
            findNavController().navigate(R.id.profileFragment)
        })
        mainMenuViewModel.onClickAbout.observe(this, {
            findNavController().navigate(R.id.aboutFragment)
        })
        mainMenuViewModel.onDownloadClicked.observe(this, {
            findNavController().navigate(R.id.fragmentDownload)
        })
        binding.btnScorePreTest.setOnClickListener {
            navigateToScoreList(ScoreLandingFragment.PRE_TEST)
        }
        binding.btnScorePostTest.setOnClickListener {
            navigateToScoreList(ScoreLandingFragment.POST_TEST)
        }
    }

    private fun navigateToScoreList(category: String) {
        val bundle = Bundle().apply { putString(SCORE_CATEGORY_KEY, category) }
        findNavController().navigate(R.id.fragmentScoreListAdmin, bundle)
    }
}