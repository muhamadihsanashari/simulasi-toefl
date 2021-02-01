package com.sehatq.test.ui.search

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
import com.sehatq.test.databinding.FragmentSearchBinding
import com.sehatq.test.databinding.ItemProductBinding
import com.sehatq.test.ui.home.adapter.ProductListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel by viewModel<SearchViewModel>()
    lateinit var binding: FragmentSearchBinding
    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.viewModel = searchViewModel
        binding.activity = activity
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
        setupSharedElement()
        setupView()
        searchViewModel.dataProduct.observe(viewLifecycleOwner, Observer {
            val products = it ?: return@Observer
            if (products.isNotEmpty()) {
                productListAdapter.clearItems()
                productListAdapter.addItems(products)
            }
        })
    }

    private fun setupView() {
        productListAdapter = ProductListAdapter(ArrayList(), ::seeDetailProduct)
        binding.adapterProduct = productListAdapter
    }

    private fun setupSharedElement() {
        ViewCompat.setTransitionName(binding.llSearch, "searchView")
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
            R.id.action_searchFragment_to_detailProductFragment,
            bundle,
            null,
            extras
        )
    }
}