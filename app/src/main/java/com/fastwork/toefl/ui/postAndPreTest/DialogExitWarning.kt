package com.fastwork.toefl.ui.postAndPreTest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.databinding.DialogExitSessionBinding

class DialogExitWarning : DialogFragment() {

    private lateinit var binding: DialogExitSessionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_exit_session, container, false)

        binding.btnNo.setOnClickListener {
            dismiss()
        }
        binding.btnYes.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.mainFragment)
        }
        return binding.root
    }
}