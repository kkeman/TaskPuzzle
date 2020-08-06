package com.service.codingtest.view.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.service.codingtest.R
import com.service.codingtest.manager.AppDB
import com.service.codingtest.model.*
import com.service.codingtest.model.DetailSerializable
import com.service.codingtest.model.response.ProductData
import com.service.codingtest.network.Constant
import com.service.codingtest.network.MLog
import com.service.codingtest.view.activitys.DetailActivity
import com.service.codingtest.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(private val mAct: FragmentActivity, private val mainViewModel: MainViewModel) :
    PagedListAdapter<ProductData, ProductAdapter.ViewHolder>(ChatDiffCallback) {
    //    private val mainViewModel: MainViewModel
//private val favoriteViewModel: FavoriteViewModel
    private val mInflater = LayoutInflater.from(mAct)

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val iv_thumb = itemView.iv_thumb
        val cb_favorite = itemView.cb_favorite
        val tv_title = itemView.tv_title
        val tv_rate = itemView.tv_rate
        val tv_time = itemView.tv_time
    }

//    override fun on


//    override fun onCurrentListChanged(
//        previousList: PagedList<ProductData>?,
//        currentList: PagedList<ProductData>?
//    ) {
//        Log.e("111111111", "onCurrentListChanged previousListSize:${previousList?. size} currentList:${currentList?. size}")
//        currentList!!.addWeakCallback(null, object: PagedList.Callback() {
//            override fun onChanged(position: Int, count: Int) {}
//            override fun onInserted(position: Int, count: Int) {
//                println("count: $count")
//                            mainViewModel.list.observe(mAct, Observer {
//                Log.e("111111111", "${it.size}")
//                for (productItem in currentList!!.iterator()) {
//                    productItem.isFavorite = false
//
//                    for (favoriteItem in it)
//                        if (favoriteItem.id == productItem.id) {
//                            productItem.isFavorite = true
//                            break
//                        }
//                }
//                notifyDataSetChanged()
//                    })
//            }
//            override fun onRemoved(position: Int, count: Int) {}
//        })
//
//
////        mainViewModel.list.observe(mAct, Observer {
////            Log.e("111111111", "${it.size}")
////            for (productItem in currentList!!.iterator()) {
////                productItem.isFavorite = false
////
////                for (favoriteItem in it)
////                    if (favoriteItem.id == productItem.id) {
////                        productItem.isFavorite = true
////                        break
////                    }
////            }
////            notifyDataSetChanged()
////        })
//        super.onCurrentListChanged(previousList, currentList)
//    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = mInflater.inflate(R.layout.item_product, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)!!

        Glide.with(mAct).load(data.thumbnail)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.iv_thumb)

        holder.tv_title.text = data.name

        holder.tv_rate.text = data.rate.toString()

        holder.tv_time.visibility = View.GONE

        holder.itemView.tag = data
//        viewHolder.itemView.setOnClickListener(this)
//        viewHolder.cb_like.visibility = if(data.like) View.VISIBLE else View.GONE
        holder.cb_favorite.setOnCheckedChangeListener { compoundButton, b ->
            val favoriteEntity = FavoriteEntity(
                data.id,
                data.name,
                data.thumbnail,
                data.description.imagePath,
                data.description.subject,
                data.description.price,
                data.rate,
                System.currentTimeMillis()
            )
            if (b)
                AppDB.getInstance(mAct).favoriteDao().insert(favoriteEntity)
            else
                AppDB.getInstance(mAct).favoriteDao().delete(favoriteEntity)
        }

        holder.itemView.setOnClickListener {
            val serializable = DetailSerializable(
                data.id,
                data.name,
                data.thumbnail,
                data.description.imagePath,
                data.description.subject,
                data.description.price,
                data.rate,
                0
            )

            val intent = Intent(mAct, DetailActivity::class.java)
            intent.putExtra(Constant.DETAILACTIVITY_EXTRA_DETAILSERIALIZABLE, serializable)
            mAct.startActivity(intent)
        }

//        for(item in favoriteViewModel.list.value)
//            holder.cb_favorite.isChecked = item.id == data.id

        Log.e("111111111", "position : $position")
//        mainViewModel.list.observe(mAct, Observer{
//            Log.e("111111111", "${it.size}")
//            for(item in it)
//                holder.cb_favorite.isChecked = item.id == data.id
//        })
        holder.cb_favorite.isChecked = data.isFavorite



        MLog.d("SearchAdapter", data.thumbnail)
    }

//    private fun setDownloadStart(data: items) {
//        Observable.just(data)
//                .subscribeOn(Schedulers.newThread()).map {
//                    val folder = Util.getPicturesDir(mAct)
//                    var file = File(folder)
//                    if (!file.exists())
//                        file.mkdirs()
//
//                    file = File("${folder}/" + data.avatar_url.split("/").last().replace("?v=4", "") + ".jpg")
//                    if (!file.exists())
//                        file.createNewFile()
//
//                    val output = RandomAccessFile(file.absolutePath, "rw")
//                    val fileSize = output.length()
//
//                    val url = URL(data.avatar_url)
//
//                    val conn = url.openConnection() as HttpURLConnection
//                    conn.setRequestProperty("Range", "bytes=" + fileSize.toString() + '-'.toString())
//                    conn.connect()
//                    conn.connectTimeout = 60000
//                    conn.readTimeout = 60000
//
//                    val byteArray = ByteArray(1048576)
//
//                    val input = conn.inputStream
//                    output.seek(fileSize)
//
//                    if (fileSize < conn.contentLength) {
//                        var count: Int
//                        while (true) {
//                            count = input.read(byteArray)
//                            if (count == -1)
//                                break
//
//                            output.write(byteArray, 0, count)
//                        }
//                    }
//
//                    AppDB.getInstance(mAct).likeDao().insert(LikeEntity(0, file.path))
//                }
//                .observeOn(AndroidSchedulers.mainThread()).subscribe {
//
//                    Toast.makeText(mAct, R.string.download_complete, Toast.LENGTH_LONG).show()
////                    mAct.sendBroadcast(Intent(Constant.LIKE_RECEIVER))
////                    mainVM.inputNumber.postValue(0)
//                }.addTo((mAct as BaseActivity).cDisposable)
//    }

    companion object {
        val ChatDiffCallback = object : DiffUtil.ItemCallback<ProductData>() {
            override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
                return oldItem == newItem
            }
        }
    }
}