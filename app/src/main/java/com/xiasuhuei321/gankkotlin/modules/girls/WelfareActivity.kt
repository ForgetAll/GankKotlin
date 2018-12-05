package com.xiasuhuei321.gankkotlin.modules.girls

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.xiasuhuei321.gankkotlin.R
import com.xiasuhuei321.gankkotlin.base.BaseToolbarActivity
import com.xiasuhuei321.gankkotlin.util.IntentKey
import kotlinx.android.synthetic.main.activity_welfare.*
import kotlinx.android.synthetic.main.item_big_img.view.*

class WelfareActivity : BaseToolbarActivity() {
    override val hideActionBar: Boolean
        get() = true

    private var position: Int = 0
    private var data: Array<String>? = null
    private var views = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welfare)
    }

    override fun initData(savedBundle: Bundle) {
        position = savedBundle.getInt(IntentKey.IMG_POSITION, 0)
        data = savedBundle.getStringArray(IntentKey.IMG_URL_ARRAY)
    }

    override fun initView() {
        setBackBtn()
        initToolbar("图片们")
        initCountTv(data?.size ?: 0)
        setCurrent(position + 1)

        contentVp.adapter = adapter
        contentVp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                setCurrent(position + 1)
            }

        })

        contentVp.currentItem = position
    }

    private val adapter = object : PagerAdapter() {
        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return data?.size ?: 0
        }

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            if (views.size <= position) {
                val v = LayoutInflater.from(this@WelfareActivity).inflate(R.layout.item_big_img, null)
                views.add(v)
            }
            data?.get(position)?.let {
                Glide.with(this@WelfareActivity)
                        .load(it)
                        .into(views[position].imagePv)
            }

            container?.addView(views[position])
            return views[position]
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container?.removeView(views[position])
        }
    }
}