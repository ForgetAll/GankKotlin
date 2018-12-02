package com.xiasuhuei321.gankkotlin

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.xiasuhuei321.gankkotlin.base.BaseActivity
import com.xiasuhuei321.gankkotlin.base.BaseFragment
import com.xiasuhuei321.gankkotlin.base.Presenter
import com.xiasuhuei321.gankkotlin.base.View
import com.xiasuhuei321.gankkotlin.modules.girls.WelfareFragment
import com.xiasuhuei321.gankkotlin.modules.infobrowser.DateInfoFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toobar.*
import org.jetbrains.anko.startActivity

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
        startActivity<TestActivity>()
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
    }

    override fun initEvent() {
        toolbar.setNavigationOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        menuNv.setNavigationItemSelectedListener { item ->
            presenter.drawerClick(item)
            true
        }

        menuNv.menu.getItem(0).isCheckable = true
//        presenter.addFragment(WelfareFragment.TAG)
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

    override fun showFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.contentFl, fragment, tag)
        transaction.commit()
    }

    override fun getSupportManager(): FragmentManager {
        return supportFragmentManager
    }

    override fun getContentId() = R.id.contentFl
}

class MainPresenter(var view: MainView?) : Presenter {
    private val fragmentMap = HashMap<String, BaseFragment>()

    fun drawerClick(item: MenuItem) {
        when (item.itemId) {
            R.id.welfareIt -> {
                addFragment(WelfareFragment.TAG)
            }

            R.id.timeBroswerIt -> {
                addFragment(DateInfoFragment.TAG)
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

    private fun getFragment(tag: String): BaseFragment {
        var fragment = fragmentMap[tag]
        if (fragment == null) {
            when (tag) {
                DateInfoFragment.TAG -> {
                    fragment = DateInfoFragment()
                    fragmentMap[DateInfoFragment.TAG] = fragment
                }
                WelfareFragment.TAG -> {
                    fragment = WelfareFragment()
                    fragmentMap[WelfareFragment.TAG] = fragment
                }
            }
        }

        return fragment!!
    }

    fun addFragment(tag: String) {
        view?.let {
            val manager = it.getSupportManager()
            var fragment = manager.findFragmentByTag(tag)

            hideAll(manager.beginTransaction())
            if (fragment == null) {
                fragment = getFragment(tag)
                manager.beginTransaction().apply {
                    add(it.getContentId(), fragment, tag)
                }.commit()
            } else {
                val transaction = manager.beginTransaction()
                transaction.show(fragment).commit()
            }
        }
    }

    private fun hideAll(transaction: FragmentTransaction) {
        for (pair in fragmentMap) {
            transaction.hide(pair.value)
        }

        transaction.commit()
    }

}

interface MainView : View {
    fun closeDrawer()

    fun showFragment(fragment: Fragment, tag: String)

    fun getSupportManager(): FragmentManager

    fun getContentId(): Int
}