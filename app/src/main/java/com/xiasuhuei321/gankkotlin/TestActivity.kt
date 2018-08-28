package com.xiasuhuei321.gankkotlin

import android.os.Bundle
import android.text.Html
import com.xiasuhuei321.gankkotlin.base.BaseActivity
import com.xiasuhuei321.gankkotlin.network.asyncUI
import com.xiasuhuei321.gankkotlin.network.gankService
import com.xiasuhuei321.gankkotlin.util.XLog
import kotlinx.android.synthetic.main.activity_test.*

/**
 * Created by xiasuhuei321 on 2018/8/24.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
class TestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    override fun initView() {
        val str = "<h3><img alt=\\\"\\\" src=\\\"http://ww2.sinaimg.cn/large/610dc034jw1f3rbikc83dj20dw0kuadt.jpg\\\" /></h3>\\r\\n\\r\\n<h3>Android</h3>\\r\\n\\r\\n<ul>\\r\\n\\t<li><a href=\\\"http://www.jianshu.com/p/d9143a92ad94\\\" target=\\\"_blank\\\">Fragment完全解析三步曲</a>&nbsp;(AndWang)</li>\\r\\n\\t<li><a href=\\\"http://blog.csdn.net/dd864140130/article/details/51313342\\\" target=\\\"_blank\\\">送给小白的设计说明书</a>&nbsp;(Dong dong Liu)</li>\\r\\n\\t<li><a href=\\\"http://www.jcodecraeer.com/a/anzhuokaifa/2016/0508/4222.html\\\" target=\\\"_blank\\\">Material 风格的干货客户端</a>&nbsp;(None)</li>\\r\\n\\t<li><a href=\\\"https://github.com/jiang111/ScalableTabIndicator\\\" target=\\\"_blank\\\">可定制的Indicator,结合ViewPager使用</a>&nbsp;(NewTab)</li>\\r\\n\\t<li><a href=\\\"https://github.com/north2014/T-MVP\\\" target=\\\"_blank\\\">T-MVP：泛型深度解耦下的MVP大瘦身</a>&nbsp;(Bai xiaokang)</li>\\r\\n\\t<li><a href=\\\"https://github.com/GrenderG/Prefs\\\" target=\\\"_blank\\\">Simple Android SharedPreferences wrapper</a>&nbsp;(蒋朋)</li>\\r\\n\\t<li><a href=\\\"https://github.com/shiraji/permissions-dispatcher-plugin\\\" target=\\\"_blank\\\">IntelliJ plugin for supporting PermissionsDispatcher</a>&nbsp;(蒋朋)</li>\\r\\n</ul>\\r\\n\\r\\n<h3>iOS</h3>\\r\\n\\r\\n<ul>\\r\\n\\t<li><a href=\\\"http://mp.weixin.qq.com/s?__biz=MzIwMTYzMzcwOQ==&amp;mid=2650948304&amp;idx=1&amp;sn=f76e7b765a7fcabcb71d37052b46e489&amp;scene=0#wechat_redirect\\\" target=\\\"_blank\\\">别人的&nbsp;App 不好用？自己改了便是。Moves 篇（上）</a>&nbsp;(tripleCC)</li>\\r\\n\\t<li><a href=\\\"https://github.com/netease-im/NIM_iOS_UIKit\\\" target=\\\"_blank\\\">网易云信iOS UI组件源码仓库</a>&nbsp;(__weak_Point)</li>\\r\\n\\t<li><a href=\\\"https://github.com/openshopio/openshop.io-ios\\\" target=\\\"_blank\\\">OpenShop 开源</a>&nbsp;(代码家)</li>\\r\\n\\t<li><a href=\\\"https://github.com/garnele007/SwiftOCR\\\" target=\\\"_blank\\\">Swift 实现的 OCR 识别库</a>&nbsp;(代码家)</li>\\r\\n\\t<li><a href=\\\"http://yulingtianxia.com/blog/2016/05/06/Let-your-WeChat-for-Mac-never-revoke-messages/\\\" target=\\\"_blank\\\">让你的微信不再被人撤回消息</a>&nbsp;(CallMeWhy)</li>\\r\\n\\t<li><a href=\\\"http://drops.wooyun.org/mobile/15406\\\" target=\\\"_blank\\\">微信双开还是微信定时炸弹？</a>&nbsp;(CallMeWhy)</li>\\r\\n</ul>\\r\\n\\r\\n<h3>瞎推荐</h3>\\r\\n\\r\\n<ul>\\r\\n\\t<li><a href=\\\"http://blog.csdn.net/shenyisyn/article/details/50056319\\\" target=\\\"_blank\\\">阻碍新手程序员提升的8件小事</a>&nbsp;(LHF)</li>\\r\\n\\t<li><a href=\\\"http://36kr.com/p/5046775.html\\\" target=\\\"_blank\\\">程序员、黑客与开发者之别</a>&nbsp;(LHF)</li>\\r\\n</ul>\\r\\n\\r\\n<h3>拓展资源</h3>\\r\\n\\r\\n<ul>\\r\\n\\t<li><a href=\\\"http://www.wxtlife.com/2016/04/25/java-jvm-gc/\\\" target=\\\"_blank\\\">详细介绍了java jvm 垃圾回收相关的知识汇总</a>&nbsp;(Aaron)</li>\\r\\n\\t<li><a href=\\\"http://arondight.me/2016/04/17/ä½¿ç\u0094¨GPGç\u00AD¾å\u0090\u008DGitæ\u008F\u0090äº¤å\u0092\u008Cæ \u0087ç\u00AD¾/\\\" target=\\\"_blank\\\">使用GPG签名Git提交和标签</a>&nbsp;(蒋朋)</li>\\r\\n</ul>\\r\\n\\r\\n<h3>休息视频</h3>\\r\\n\\r\\n<ul>\\r\\n\\t<li><a href=\\\"http://weibo.com/p/2304443956b04478364a64185f196f0a89266d\\\" target=\\\"_blank\\\">秒拍牛人大合集，[笑cry]目测膝盖根本不够用啊。</a>&nbsp;(LHF)</li>\\r\\n</ul>\\r\\n\\r\\n<p><iframe frameborder=\\\"0\\\" height=\\\"498\\\" src=\\\"http://v.qq.com/iframe/player.html?vid=w0198nyi5x5&amp;tiny=0&amp;auto=0\\\" width=\\\"640\\\"></iframe></p>\\r\\n\\r\\n<p>感谢所有默默付出的编辑们,愿大家有美好一天.</p>\\r\\n"
        testTv.text = Html.fromHtml(str)

        asyncUI {
            val res = gankService { getDaily("2015", "08", "06") }.await().body()
//            XLog.i(res.results.toString())
            XLog.i(res.results?.福利.toString())
        }
    }
}