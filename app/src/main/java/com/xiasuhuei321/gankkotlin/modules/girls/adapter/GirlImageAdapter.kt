package com.xiasuhuei321.gankkotlin.modules.girls.adapter

import android.graphics.Bitmap
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.xiasuhuei321.gankkotlin.Global
import com.xiasuhuei321.gankkotlin.R
import com.xiasuhuei321.gankkotlin.data.Data
import com.xiasuhuei321.gankkotlin.extension.getScreenWidth
import com.xiasuhuei321.gankkotlin.util.XLog
import java.util.ArrayList
import java.util.HashMap

class GirlImageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: List<Data> = ArrayList()
    private var notifyCount = 10
    private var currentIndex = 0
    var onItemClick: (Int) -> Unit = {}
    var fragment: Fragment? = null
    val sizeMap = HashMap<Int, Size>()

    fun setData(data: List<Data>?, startIndex: Int = currentIndex) {
        data?.let {
            this.data = it
            if (currentIndex != startIndex) currentIndex = startIndex
            XLog.i("data.size = ${data.size}")
            notifyItemRangeChanged(currentIndex, notifyCount)
            currentIndex += notifyCount
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_welfare, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (position >= data.size) return
        val h = holder as ImageHolder
        val data = data[position]
        h.timeTv.text = data.desc
        h.girlIv.setImageBitmap(null)

        val simpleTarget = object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                if (sizeMap[position] == null) {
                    val width = Global.context.getScreenWidth() / 2
                    resource?.let {
                        val ratio = it.height.toFloat() / it.width.toFloat()
                        val height = width * ratio
                        sizeMap.put(position, Size(width, height.toInt()))
                    }
                }

                resizeView(position, h, resource)
            }

        }

        Glide.with(fragment).load(data.url).asBitmap().into(simpleTarget)

        h.rootRl.setOnClickListener { onItemClick.invoke(position) }
    }

    private fun resizeView(position: Int, holder: ImageHolder, bitmap: Bitmap?) {
        val params = holder.rootRl.layoutParams
        val size = sizeMap[position]
        size?.let {
            params.width = it.width
            params.height = it.height
        }

        holder.rootRl.layoutParams = params
        holder.girlIv.setImageBitmap(bitmap)
    }

}

data class Size(val width: Int, val height: Int)

class ImageHolder : RecyclerView.ViewHolder {
    val girlIv: ImageView
    val timeTv: TextView
    val rootRl: RelativeLayout

    constructor(itemView: View) : super(itemView) {
        girlIv = itemView.findViewById(R.id.girlIv)
        timeTv = itemView.findViewById(R.id.timeTv)
        rootRl = itemView.findViewById(R.id.rootRl)
    }
}