package com.fastwork.toefl.ui.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fastwork.toefl.R
import com.fastwork.toefl.databinding.FragmentDownloadBinding

class FragmentDownload : Fragment() {

    private lateinit var binding: FragmentDownloadBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_download, container, false)
        binding.btnListening.setOnClickListener {
            binding.pdfView.visibility = View.VISIBLE
            binding.pdfView.fromAsset("listening.pdf").load()
        }
        binding.btnReading.setOnClickListener {
            binding.pdfView.visibility = View.VISIBLE
            binding.pdfView.fromAsset("reading.pdf").load()
        }
        binding.btnStructure.setOnClickListener {
            binding.pdfView.visibility = View.VISIBLE
            binding.pdfView.fromAsset("structure.pdf").load()
        }
        binding.back.setOnClickListener {
            if (binding.pdfView.isVisible) {
                binding.pdfView.visibility = View.GONE

            } else {
                activity?.onBackPressed()
            }
        }
        return binding.root
    }
}