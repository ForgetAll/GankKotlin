package com.xiasuhuei321.gankkotlin

import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.xiasuhuei321.gankkotlin.base.BaseActivity
import com.xiasuhuei321.gankkotlin.base.BaseFragment
import com.xiasuhuei321.gankkotlin.base.Presenter
import com.xiasuhuei321.gankkotlin.base.View
import com.xiasuhuei321.gankkotlin.modules.infobrowser.DateInfoFragment
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
class MainActivity : BaseActivity(), MainView {
    private val TAG = "MainActivity"
    private lateinit var presenter: MainPresenter

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
            if (res.isSuccess()) res.results?.forEach {
                XLog.i(TAG, it)
            }
        }
    }

    override fun initEvent() {
        toolbar.setNavigationOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        menuNv.setNavigationItemSelectedListener { item ->
            presenter.drawerClick(item)
            true
        }
    }

    override fun initPresenter() {
        presenter = MainPresenter(this)
    }

    override fun getPresenter(): Presenter? {
        return presenter
    }

    override fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    override fun addFragment(fragment: BaseFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.contentFl, fragment, "DateInfoFragment")
        transaction.show(fragment)
        transaction.commit()
    }
}

class MainPresenter(var view: MainView?) : Presenter {

    fun drawerClick(item: MenuItem) {
        when (item.itemId) {
            R.id.welfareIt -> {
            }

            R.id.timeBroswerIt -> {
                view?.addFragment(DateInfoFragment())
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

        view?.closeDrawer()
    }

    override fun release() {
        view = null
    }

}

interface MainView : View {
    fun closeDrawer()

    fun addFragment(fragment: BaseFragment)
}