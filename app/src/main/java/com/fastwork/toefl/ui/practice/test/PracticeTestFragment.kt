package com.fastwork.toefl.ui.practice.test

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.data.local.model.Question
import com.fastwork.toefl.data.local.model.ScoreType
import com.fastwork.toefl.data.local.model.TestType
import com.fastwork.toefl.databinding.FragmentTestPracticeBinding
import com.fastwork.toefl.utils.SCORE_TYPE_KEY
import com.fastwork.toefl.utils.TEST_TYPE_KEY
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PracticeTestFragment : Fragment() {

    private var testType: TestType? = null
    private lateinit var binding: FragmentTestPracticeBinding
    private val viewModel by viewModel<PracticeTestViewModel>()
    private val questionData = arrayListOf<Question>()
    private var currentQuestionPosition: Int = 0
    private var currentUserAnswer = 0

    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0

    private val period: Long = 1000
    private var job = CoroutineScope(Dispatchers.Main).launchPeriodicAsync(period) {}

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
        setupListener()
        return binding.root
    }

    private fun setupAudio(audiFileName: String?) {
        val audioFile = resources.getIdentifier(audiFileName, "raw", activity?.packageName)
        mp = MediaPlayer.create(context, audioFile)
        totalTime = mp.duration
        binding.seekBar.max = totalTime
        binding.seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mp.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )

        job = CoroutineScope(Dispatchers.Main).launchPeriodicAsync(period) {
            val currentPosition = mp.currentPosition
            binding.seekBar.progress = currentPosition
            val elapsedTime = createTimeLabel(currentPosition)
            binding.duration.text = elapsedTime
            val remainingTime = createTimeLabel(totalTime - currentPosition)
            binding.maxDuration.text =
                String.format(resources.getString(R.string.format_remaining), remainingTime)
            if (!mp.isPlaying) {
                binding.play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
            }
        }
    }

    private fun CoroutineScope.launchPeriodicAsync(repeatMillis: Long, action: () -> Unit) = async {
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(period)
            }
        } else {
            cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        if (this::mp.isInitialized) {
            mp.release()
        }
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
        if (this::mp.isInitialized) {
            mp.release()
        }
    }

    private fun createTimeLabel(time: Int): String {
        var timeLabel: String
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    private fun setupData() {
        if (testType?.category == READING) {
            testType?.subCategory?.let { viewModel.getReadingQuestion(it) }
            binding.parentPassage.visibility = View.VISIBLE
            return
        }
        if (testType?.category == STRUCTURE) {
            testType?.subCategory?.let {
                viewModel.getStructureQuestion(it)
            }
            binding.parentPassage.visibility = View.GONE
        }
        if (testType?.category == LISTENING) {
            testType?.subCategory?.let {
                binding.parentAudio.visibility = View.VISIBLE
                viewModel.getListeningQuestion(it)
            }
        }
    }

    private fun setupObserver() {
        viewModel.readLiveData.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                for (element in it) {
                    val question = Question(
                        element.paragraph,
                        element.question,
                        element.answer,
                        0,
                        element.optionAnswer
                    )
                    questionData.add(question)
                }
                setupViewQuestion()
            }
        })
        viewModel.structureLiveData.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                for (element in it) {
                    val question = Question(
                        question = element.question,
                        answer = element.answer,
                        userAnswer = 0,
                        answerOption = element.optionAnswer
                    )
                    questionData.add(question)
                }
                setupViewQuestion()
            }
        })
        viewModel.listeningLiveData.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                for (element in it) {
                    val question = Question(
                        question = element.question,
                        answer = element.answer,
                        userAnswer = 0,
                        answerOption = element.optionAnswer,
                        audioFileName = element.audioFileName
                    )
                    questionData.add(question)
                }
                setupViewQuestion()
            }
        })
    }

    private fun setupListener() {
        binding.btnExit.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnNext.setOnClickListener {
            handleNextQuestion()
        }
        binding.btnPrevious.setOnClickListener {
            handlePrevQuestion()
        }
        binding.play.setOnClickListener {
            if (mp.isPlaying) {
                mp.pause()
                binding.play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)

            } else {
                mp.start()
                binding.play.setBackgroundResource(R.drawable.ic_baseline_pause_24)
            }
        }
    }

    private fun handlePrevQuestion() {
        if (this::mp.isInitialized) {
            if (mp.isPlaying) {
                mp.stop()
            }
        }
        if (currentQuestionPosition != 0) {
            if (questionData[currentQuestionPosition].userAnswer == 0) {
                val questionUpdate = questionData[currentQuestionPosition]
                questionUpdate.userAnswer = currentUserAnswer
                questionData[currentQuestionPosition] = questionUpdate
            }
            --currentQuestionPosition
            setupViewQuestion()
        }

    }

    private fun handleNextQuestion() {
        if (this::mp.isInitialized) {
            if (mp.isPlaying) {
                mp.stop()
            }
        }
        if (currentQuestionPosition != questionData.size - 1) {
            if (questionData[currentQuestionPosition].userAnswer == 0) {
                val questionUpdate = questionData[currentQuestionPosition]
                questionUpdate.userAnswer = currentUserAnswer
                questionData[currentQuestionPosition] = questionUpdate
                binding.btnNext.text = resources.getString(R.string.next)
            }
            ++currentQuestionPosition
            setupViewQuestion()
            return
        }
        if (currentQuestionPosition == questionData.size - 1) {
            binding.btnNext.text = resources.getString(R.string.selesai)
            val bundle = Bundle().apply {
                val dataScore = ScoreType(category = testType!!.category, score = getScore())
                putSerializable(SCORE_TYPE_KEY, dataScore)
            }
            findNavController().navigateUp()
            findNavController().navigate(R.id.scoreResultFragment, bundle)
        }
    }

    private fun getScore(): Int {
        var numberOfCorrect = 0
        for (element in questionData) {
            if (element.userAnswer == element.answer) {
                numberOfCorrect++
            }
        }
        val result: Double = numberOfCorrect.toDouble() / questionData.size * 100
        return result.toInt()
    }

    @SuppressLint("ResourceType")
    private fun setupViewQuestion() {
        val data = questionData[currentQuestionPosition]
        data.audioFileName?.let {
            setupAudio(it.lowercase())
        }
        binding.tvQuestionCount.text = String.format(
            getString(R.string.format_question_count),
            currentQuestionPosition + 1,
            questionData.size
        )
        data.message.let {
            binding.tvPassages.text = it
            binding.tvPassages.movementMethod = ScrollingMovementMethod()
        }
        binding.tvQuestion.text = data.question
        val optionAnswerOne = RadioButton(requireContext())
        optionAnswerOne.id = 1
        val optionAnswerTwo = RadioButton(requireContext())
        optionAnswerTwo.id = 2
        val optionAnswerThree = RadioButton(requireContext())
        optionAnswerThree.id = 3
        val optionAnswerFour = RadioButton(requireContext())
        optionAnswerFour.id = 4
        optionAnswerOne.text = data.answerOption[0]
        optionAnswerTwo.text = data.answerOption[1]
        optionAnswerThree.text = data.answerOption[2]
        optionAnswerFour.text = data.answerOption[3]
        if (binding.radioGroup.childCount > 0) {
            clearRadioGroup()
        }
        binding.radioGroup.addView(optionAnswerOne)
        binding.radioGroup.addView(optionAnswerTwo)
        binding.radioGroup.addView(optionAnswerThree)
        binding.radioGroup.addView(optionAnswerFour)
        binding.radioGroup.clearCheck()
        if (data.userAnswer != 0) {
            binding.radioGroup.check(data.userAnswer)
        }
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            currentUserAnswer = checkedId
        }
        if (currentQuestionPosition == questionData.size - 1) {
            binding.btnNext.text = resources.getString(R.string.selesai)
        } else {
            binding.btnNext.text = resources.getString(R.string.next)
        }
    }

    private fun clearRadioGroup() {
        val count = binding.radioGroup.childCount
        for (i in count - 1 downTo 0) {
            val o: View = binding.radioGroup.getChildAt(i)
            if (o is RadioButton) {
                binding.radioGroup.removeViewAt(i)
            }
        }
    }

    companion object {
        const val LISTENING = "listening"
        const val READING = "reading"
        const val STRUCTURE = "structure"
        const val READING_EASY = "easy"
        const val READING_MEDIUM = "medium"
        const val READING_HARD = "hard"
        const val LISTENING_SORT_DIALOG = "sort dialogues"
        const val LISTENING_CASUAL_CONVERSATION = "casual conversation"
        const val STRUCTURE_ONE_CLAUSE = "one clause"
        const val STRUCTURE_MULTIPLE_CLAUSES = "multiple clauses"
        const val STRUCTURE_MORE_MULTIPLE_CLAUSE = "more multiple clauses"
        const val STRUCTURE_REDUCED_CLAUSES = "reduced clauses"
        const val STRUCTURE_INVERTED_SUBJECT_AND_VERB = "inverted subject and verb"
    }
}