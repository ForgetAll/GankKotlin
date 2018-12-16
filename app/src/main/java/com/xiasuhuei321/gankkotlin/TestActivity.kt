package com.xiasuhuei321.gankkotlin

import android.os.Bundle
import android.text.Html
import com.xiasuhuei321.gankkotlin.base.BaseActivity
import com.xiasuhuei321.gankkotlin.data.DataManager
import com.xiasuhuei321.gankkotlin.network.asyncUI
import com.xiasuhuei321.gankkotlin.network.gankService
import com.xiasuhuei321.gankkotlin.util.XLog
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import kotlinx.android.synthetic.main.activity_test.*

/**
 * Created by xiasuhuei321 on 2018/8/24.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
class TestActivity : BaseActivity() {
    var test = Test(content = "你好！", ddd = 1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    override fun initView() {
        updateBtn.setOnClickListener {
            test.content = "你好啊啊啊"
            test.id = 1
            test.ddd = 2
        }

        saveData.setOnClickListener {

        }

        getDataBtn.setOnClickListener {

        }
    }
}

@Entity
class Test(@Id var id: Long = 0, var content: String, @Index var ddd: Long = 1)