package com.service.codingtest.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.service.codingtest.R
import com.service.codingtest.databinding.FragProductBinding
import com.service.codingtest.viewmodel.FavoriteViewModel
import com.service.codingtest.viewmodel.MainViewModel
import com.service.codingtest.viewmodel.ProductViewModel
import com.service.codingtest.network.MLog
import com.service.codingtest.view.adapters.ProductAdapter
import kotlinx.android.synthetic.main.frag_product.*


class ProductFragment : Fragment() {

    private val mTAG = ProductFragment::class.java.name
    private lateinit var mBinding: FragProductBinding
    private val viewModel by viewModels<FavoriteViewModel>()

    private val mainViewModel by activityViewModels<MainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_product, container, false)
        mBinding = FragProductBinding.bind(view)
//        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_product, container, false)
        mBinding.vm = ProductViewModel()

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        initView()
    }

    private fun initView() {
        initListView()
    }

    private fun initListView() {

        val adapter = ProductAdapter(requireActivity(), mainViewModel)
        rv_product.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        rv_product.adapter = adapter

        mBinding.vm!!.liveDataProduct.observe(viewLifecycleOwner, Observer {

            it.let { productList ->
                MLog.d(mTAG, "submitList size : " + productList!!.size)
                adapter.submitList(productList)

                productList.addWeakCallback(null, object : PagedList.Callback() {
                    override fun onChanged(position: Int, count: Int) {}
                    override fun onInserted(position: Int, count: Int) {
                        println("count: $count")
                        mainViewModel.list.observe(activity!!, Observer { favoriteEntity ->
                            for (productItem in productList.iterator()) {
                                productItem.isFavorite = false

                                for (favoriteItem in favoriteEntity)
                                    if (favoriteItem.id == productItem.id) {
                                        productItem.isFavorite = true
                                        break
                                    }
                            }
                            adapter.notifyDataSetChanged()
                        })
                    }

                    override fun onRemoved(position: Int, count: Int) {}
                })
            }
        })
    }
}