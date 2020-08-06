package com.service.codingtest.view.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.service.codingtest.R
import com.service.codingtest.databinding.FragFavoriteBinding
import com.service.codingtest.manager.AppDB
import com.service.codingtest.manager.PreferenceManager
import com.service.codingtest.model.FavoriteSortTypeEnum
import com.service.codingtest.network.Constant
import com.service.codingtest.network.MLog
import com.service.codingtest.view.adapters.FavoriteAdapter
import com.service.codingtest.viewmodel.FavoriteViewModel
import com.service.codingtest.viewmodel.MainViewModel

class FavoriteFragment : Fragment() {

    private val mTAG = FavoriteFragment::class.java.name

    private var mMediaFileAdapter: FavoriteAdapter? = null

    private var mLikeReceiver: LikeReceiver? = null

    private lateinit var viewDataBinding: FragFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel>()

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragFavoriteBinding.bind(inflater.inflate(R.layout.frag_favorite, container, false)).apply { viewmodel = viewModel  }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_menu_favorite, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_sort -> {
                showFilteringPopUpMenu()
                true
            }
            else -> false
        }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_sort) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.sort_favorite, menu)

            setOnMenuItemClickListener {
//                viewModel.setFiltering(
//                    when (it.itemId) {
//                        R.id.latest -> TasksFilterType.ACTIVE_TASKS
//                        R.id.rate -> TasksFilterType.COMPLETED_TASKS
//                        else -> TasksFilterType.ALL_TASKS
//                    }
//                )
                when (it.itemId) {
                    R.id.sort_latest -> {
                        PreferenceManager.getInstance(activity)!!.prefKeyFavoriteSort = FavoriteSortTypeEnum.LATEST
                        setMediaDBList()
                    }
                    R.id.sort_rate -> {
                        PreferenceManager.getInstance(activity)!!.prefKeyFavoriteSort = FavoriteSortTypeEnum.RATE
                        setMediaDBList()
                    }
//                    else -> TasksFilterType.ALL_TASKS
                }
                true
            }
            show()
        }
    }

    private fun init() {
        initData()
        setList()
        initSwipeRefresh()
    }

    private fun initData() {
        mLikeReceiver = LikeReceiver()
        requireActivity().registerReceiver(mLikeReceiver,
                IntentFilter(Constant.LIKE_RECEIVER))
//        mainVM.inputNumber.observe(viewLifecycleOwner, Observer {
////            it?.let {
////                // do some thing with the number
////            }
//            setMediaDBList()
//        })
    }

    private fun setList() {
        setRecyclerViewLayoutManager()

//        mMediaFileAdapter = LikeAdapter(ArrayList<LikeEntity>(), activity!!)
//        mBinding.rvMediaFileList.adapter = mMediaFileAdapter

        setMediaDBList()
    }

    fun setMediaDBList() {
//        val folder = File(Util.getPicturesDir(activity!!))
//        val fileList = folder.listFiles()
//        val docList = mutableListOf<String>()
//
//        Observable.just(fileList)
//                .map {
//                    for (file in it)
//                        docList.add(file.path)
//                    docList
//                }
//                .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    mMediaFileAdapter!!.submitList(it)
//                }.addTo((activity as BaseActivity).cDisposable)


        mainViewModel.list = when (PreferenceManager.getInstance(activity)!!.prefKeyFavoriteSort) {
            FavoriteSortTypeEnum.LATEST -> AppDB.getInstance(requireContext()).favoriteDao().loadAllSortSaveTime()
            FavoriteSortTypeEnum.RATE ->  AppDB.getInstance(requireContext()).favoriteDao().loadAllSortRate()
        }

        mainViewModel.list.observe(viewLifecycleOwner, Observer {
            mMediaFileAdapter = FavoriteAdapter(it, requireActivity(), mainViewModel)
            viewDataBinding.rvMediaFileList.adapter = mMediaFileAdapter
//            mMediaFileAdapter!!.setData(it)
        })
    }

    private fun setRecyclerViewLayoutManager() {
        var scrollPosition = 0

        if (viewDataBinding.rvMediaFileList!!.layoutManager != null) {
            scrollPosition = (viewDataBinding.rvMediaFileList!!.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
        }

        val llm = LinearLayoutManager(activity)
        viewDataBinding.rvMediaFileList!!.layoutManager = llm
        val dividerItemDecoration = DividerItemDecoration(activity, llm.orientation)
        viewDataBinding.rvMediaFileList.addItemDecoration(dividerItemDecoration)

        viewDataBinding.rvMediaFileList!!.scrollToPosition(scrollPosition)
    }

    private fun initSwipeRefresh() {
        viewDataBinding.layoutSwipeRefresh.setColorSchemeColors(Color.parseColor("#58be17"))
        viewDataBinding.layoutSwipeRefresh.setOnRefreshListener {
            viewDataBinding.layoutSwipeRefresh.isRefreshing = false
            setMediaDBList()
        }
    }

    override fun onDestroy() {
        MLog.d(mTAG, "onDestroy()")

        if (mLikeReceiver != null) {
            requireActivity().unregisterReceiver(mLikeReceiver)
            mLikeReceiver = null
        }

        super.onDestroy()
    }

    inner class LikeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
//            setMediaDBList()
        }
    }
}