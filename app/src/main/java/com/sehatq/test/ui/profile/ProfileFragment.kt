package com.sehatq.test.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.sehatq.test.R
import com.sehatq.test.data.local.model.Product
import com.sehatq.test.databinding.FragmentProfileBinding
import com.sehatq.test.databinding.ItemPurchaseBinding
import com.sehatq.test.ui.profile.adapter.PurchaseListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val profileViewModel by viewModel<ProfileViewModel>()
    private lateinit var binding: FragmentProfileBinding
    private lateinit var adapterProduct: PurchaseListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.viewModel = profileViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        profileViewModel.products.observe(viewLifecycleOwner, Observer {
            val products = it ?: return@Observer
            if (products.isNotEmpty()) {
                profileViewModel.emptyShow.set(false)
                adapterProduct.addItems(products)
            } else {
                profileViewModel.emptyShow.set(true)
            }
        })
    }

    private fun setupView() {
        adapterProduct = PurchaseListAdapter(ArrayList(), ::seeDetailProduct)
        binding.adapter = adapterProduct
    }

    fun seeDetailProduct(product: Product, itemPurchaseBinding: ItemPurchaseBinding) {
        val bundle = Bundle()
        bundle.putSerializable("data", product)
        val extras = FragmentNavigatorExtras(
            itemPurchaseBinding.ivImage to "image",
            itemPurchaseBinding.ivLove to "love",
            itemPurchaseBinding.tvTitle to "title",
            itemPurchaseBinding.tvDescription to "desc",
            itemPurchaseBinding.tvPrice to "price"
        )
        findNavController().navigate(
            R.id.action_profileFragment_to_detailProductFragment,
            bundle,
            null,
            extras
        )
    }


}