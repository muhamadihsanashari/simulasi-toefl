package com.sehatq.test.ui.detailProduct

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.sehatq.test.R
import com.sehatq.test.data.local.model.Product
import com.sehatq.test.databinding.FragmentDetailProductBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailProductFragment : Fragment() {

    private val detailProductViewModel by viewModel<DetailProductViewModel>()
    private lateinit var binding: FragmentDetailProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_product, container, false)
        binding.activity = activity
        binding.viewModel = detailProductViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailProductViewModel.product.set(arguments?.get("data") as Product)
        detailProductViewModel.init()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
            sharedElementReturnTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSharedElement()
        detailProductViewModel.shareAction.observe(viewLifecycleOwner, Observer {
            startActivity(Intent.createChooser(it, "Share it Via"))
        })

        detailProductViewModel.successInsert.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupSharedElement() {
        setSharedElement(binding.ivImage, "image")
        setSharedElement(binding.ivLove, "love")
        setSharedElement(binding.tvTitle, "title")
        setSharedElement(binding.tvDescription, "desc")
        setSharedElement(binding.btnLogin, "price")
    }

    fun setSharedElement(view: View, tag: String) {
        ViewCompat.setTransitionName(view, tag)
    }


}