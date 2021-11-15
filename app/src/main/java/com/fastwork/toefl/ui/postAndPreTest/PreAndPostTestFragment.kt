package com.fastwork.toefl.ui.postAndPreTest

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
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
import com.fastwork.toefl.data.local.model.ModelFullTest
import com.fastwork.toefl.data.local.model.ModelSession
import com.fastwork.toefl.data.local.model.Question
import com.fastwork.toefl.data.local.model.ScoreType
import com.fastwork.toefl.databinding.FragmentFullTestBinding
import com.fastwork.toefl.utils.SCORE_TYPE_KEY
import com.fastwork.toefl.utils.SESSION_KEY
import com.fastwork.toefl.utils.TEST_CATEGORY_KEY
import com.fastwork.toefl.utils.launchPeriodicAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class PreAndPostTestFragment : Fragment() {

    private var modelSession: ModelSession? = null
    private lateinit var binding: FragmentFullTestBinding
    private lateinit var timer: CountDownTimer
    private val viewModel by viewModel<PreAndPostTestViewModel>()
    private val questionData = arrayListOf<Question>()
    private var currentQuestionPosition: Int = 0
    private var currentUserAnswer = 0
    private var category: String? = null

    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0

    private val period: Long = 1000
    private var job = CoroutineScope(Dispatchers.Main).launchPeriodicAsync(period) {}
    private val timerDuration = 1500000L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_full_test, container, false)
        binding.apply {
            viewModel = this@PreAndPostTestFragment.viewModel
            lifecycleOwner = this@PreAndPostTestFragment
        }
        setupData()
        setupObserver()
        setupListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timer = object : CountDownTimer(timerDuration, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val tvCount = binding.timer
                val millis: Long = millisUntilFinished
                tvCount.text = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(
                            millis
                        )
                    ),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millis)
                    )
                )
            }

            override fun onFinish() {
                handleNextSession()
            }

        }.start()
    }

    private fun setupData() {
        modelSession = arguments?.getSerializable(SESSION_KEY) as ModelSession
        category = arguments?.getString(
            TEST_CATEGORY_KEY
        )
        if (modelSession != null) {
            val session = modelSession?.session
            if (session == LISTENING_SESSION) {
                binding.parentAudio.visibility = View.VISIBLE
                viewModel.getListeningQuestion(category!!)
                return
            }
            if (session == READING_SESSION) {
                binding.parentPassage.visibility = View.VISIBLE
                viewModel.getReadingQuestion(category!!)
                return
            }
            if (session == STRUCTURE_SESSION) {
                viewModel.getStructureQuestion(category!!)
                return
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
           handleNextSession()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        if (this::mp.isInitialized) {
            mp.release()
            timer.cancel()
        }
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
        if (this::mp.isInitialized) {
            mp.release()
            timer.cancel()
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

    private fun handleNextSession() {
        val session = modelSession?.session
        if (session == LISTENING_SESSION) {
            val modelSession =
                ModelSession(
                    session = modelSession?.session,
                    category = modelSession?.category,
                    dataTest = ModelFullTest(listeningCorrect = getCorrectAnswer())
                )
            val bundle = Bundle().apply {
                putSerializable(SESSION_KEY, modelSession)
            }
            findNavController().navigateUp()
            findNavController().navigate(R.id.sessionFragment, bundle)
            return
        }
        if (session == READING_SESSION) {
            val modelSession =
                ModelSession(
                    session = modelSession?.session,
                    category = modelSession?.category,
                    dataTest = ModelFullTest(
                        listeningCorrect = modelSession?.dataTest?.listeningCorrect,
                        readingCorrect = getCorrectAnswer()
                    )
                )
            val bundle = Bundle().apply {
                putSerializable(SESSION_KEY, modelSession)
            }
            findNavController().navigateUp()
            findNavController().navigate(R.id.sessionFragment, bundle)
            return
        }
        if (session == STRUCTURE_SESSION) {
            val modelSession =
                ModelSession(
                    session = modelSession?.session,
                    category = modelSession?.category,
                    dataTest = ModelFullTest(
                        listeningCorrect = modelSession?.dataTest?.listeningCorrect,
                        readingCorrect = modelSession?.dataTest?.readingCorrect,
                        structureCorrect = getCorrectAnswer()
                    )
                )
            val bundle = Bundle().apply {
                putSerializable(SESSION_KEY, modelSession)
            }
            findNavController().navigateUp()
            findNavController().navigate(R.id.sessionFragment, bundle)
            return
        }
    }

    private fun getCorrectAnswer(): Int {
        var correctAnswer = 0
        for (element in questionData) {
            if (element.userAnswer == element.answer) {
                correctAnswer++
            }
        }
        return correctAnswer
    }

    companion object {
        const val LISTENING_SESSION = "listening"
        const val READING_SESSION = "reading"
        const val STRUCTURE_SESSION = "structure"
    }

}