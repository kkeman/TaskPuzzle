package com.service.codingtest.view.activitys

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bumptech.glide.Glide
import com.service.codingtest.R
import com.service.codingtest.manager.AppDB
import com.service.codingtest.model.DetailSerializable
import com.service.codingtest.model.FavoriteEntity
import com.service.codingtest.network.Constant.DETAILACTIVITY_EXTRA_DETAILSERIALIZABLE
import com.service.codingtest.view.fragments.FavoriteFragment
import com.service.codingtest.view.fragments.ProductFragment
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : BaseActivity() {

//    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
//        mBinding.viewModel = MainViewModel()
        setContentView(R.layout.activity_detail)


        val serializable: DetailSerializable? = intent.getSerializableExtra(DETAILACTIVITY_EXTRA_DETAILSERIALIZABLE) as DetailSerializable?
        Glide.with(this).load(serializable!!.imagePath)
//            .transition(DrawableTransitionOptions.withCrossFade())
            .into(iv_image)

        cb_favorite.isChecked = AppDB.getInstance(this).favoriteDao().exist(serializable.id)


//        cb_favorite.isChecked = true
        cb_favorite.setOnCheckedChangeListener { compoundButton, b ->
            val favoriteEntity = FavoriteEntity(serializable.id, serializable.name, serializable.thumbnail, serializable.imagePath, serializable.subject, serializable.price, serializable.rate, System.currentTimeMillis())
            if(b)
                AppDB.getInstance(this).favoriteDao().insert(favoriteEntity)
            else
                AppDB.getInstance(this).favoriteDao().delete(favoriteEntity)
        }

        tv_title.text = serializable.name

        tv_rate.text = serializable.rate.toString()

        tv_time.visibility = View.VISIBLE
        tv_time.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(serializable.saveTime)
    }
}
