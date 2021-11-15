package com.fastwork.toefl.ui.postAndPreTest

import android.media.MediaPlayer
import androidx.fragment.app.Fragment
import com.fastwork.toefl.data.local.model.Question
import com.fastwork.toefl.data.local.model.TestType
import com.fastwork.toefl.databinding.FragmentTestPracticeBinding
import com.fastwork.toefl.utils.launchPeriodicAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.android.viewModel

class PreAndPostTestFragment : Fragment() {

    private var testType: TestType? = null
    private lateinit var binding: FragmentTestPracticeBinding
    private val viewModel by viewModel<PreAndPostTestViewModel>()
    private val questionData = arrayListOf<Question>()
    private var currentQuestionPosition: Int = 0
    private var currentUserAnswer = 0

    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0

    private val period: Long = 1000
    private var job = CoroutineScope(Dispatchers.Main).launchPeriodicAsync(period) {}


}