package com.xiasuhuei321.gankkotlin

import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import com.xiasuhuei321.gankkotlin.base.BaseActivity
import com.xiasuhuei321.gankkotlin.network.asyncUI
import com.xiasuhuei321.gankkotlin.network.gankService
import com.xiasuhuei321.gankkotlin.util.XLog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toobar.*

/**
 * Created by xiasuhuei321 on 2018/8/16.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
class MainActivity : BaseActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initView() {
        // 初始化 toolbar
        toolbar.setTitle(R.string.app_name)
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"))
        toolbar.collapseActionView()
        toolbar.setBackgroundColor(Color.parseColor("#4aa5e2"))

        val drawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, 0, 0)
        drawer_layout.addDrawerListener(drawerToggle)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        drawerToggle.syncState()

        asyncUI {
            val res = gankService { getHistory() }.await().body()
            if (res.isSuccess()) res.result?.forEach {
                XLog.i(TAG, it)
            }
        }
    }

    override fun initEvent() {
        toolbar.setNavigationOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        menuNv.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.welfareIt -> {
                    XLog.i(TAG, "welfareIt")
                }

                R.id.timeBroswerIt -> {

                }

                R.id.typeBroswerIt -> {

                }

                R.id.starIt -> {

                }

                R.id.aboutMeIt -> {

                }

                else -> {
                }
            }

            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }
}