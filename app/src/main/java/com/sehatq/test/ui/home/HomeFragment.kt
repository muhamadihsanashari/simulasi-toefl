package com.sehatq.test.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.sehatq.test.R
import com.sehatq.test.data.local.model.Product
import com.sehatq.test.data.remote.model.Category
import com.sehatq.test.databinding.FragmentHomeBinding
import com.sehatq.test.databinding.ItemCategoryBinding
import com.sehatq.test.databinding.ItemProductBinding
import com.sehatq.test.ui.home.adapter.CategoryListAdapter
import com.sehatq.test.ui.home.adapter.ProductListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel by viewModel<HomeViewModel>()
    lateinit var binding: FragmentHomeBinding
    private lateinit var categoryListAdapter: CategoryListAdapter
    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
            sharedElementReturnTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setupSharedElement()
        homeViewModel.loadData();
        homeViewModel.homeData.observe(viewLifecycleOwner, Observer {
            if (it.data?.category != null && it.data.category.isNotEmpty()) {
                categoryListAdapter.addItems(datas = it.data.category)
            }
            if (it.data?.productPromo != null && it.data.productPromo.isNotEmpty()) {
                productListAdapter.addItems(datas = it.data.productPromo)
            }

        })

        homeViewModel.btnSearchEvent.observe(viewLifecycleOwner, Observer {
            navigateToSearch()
        })
    }

    private fun navigateToSearch() {
        val extraSharedElement = FragmentNavigatorExtras(binding.llSearch to "searchView")
        findNavController().navigate(
            R.id.action_homeFragment_to_searchFragment,
            null,
            null,
            extraSharedElement
        )
    }

    private fun setupSharedElement() {
        ViewCompat.setTransitionName(binding.llSearch, "searchView")
    }

    private fun setView() {
        categoryListAdapter = CategoryListAdapter(ArrayList(), ::seeDetailCategory)
        productListAdapter = ProductListAdapter(ArrayList(), ::seeDetailProduct)
        binding.adapterCategory = categoryListAdapter
        binding.adapterProduct = productListAdapter
    }

    fun seeDetailCategory(category: Category, itemCategoryBinding: ItemCategoryBinding) {

    }

    fun seeDetailProduct(product: Product, itemProductBinding: ItemProductBinding) {
        val bundle = Bundle()
        bundle.putSerializable("data", product)
        val extras = FragmentNavigatorExtras(
            itemProductBinding.ivImage to "image",
            itemProductBinding.ivLove to "love",
            itemProductBinding.tvTitle to "title",
            itemProductBinding.tvDescription to "desc",
            itemProductBinding.tvPrice to "price"
        )
        findNavController().navigate(
            R.id.action_homeFragment_to_detailProductFragment,
            bundle,
            null,
            extras
        )
    }
}