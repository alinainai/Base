package com.gas.app.ui.activity.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.gas.app.R
import com.lib.commonsdk.constants.RouterHub
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_trans.*
import org.joda.time.DateTime
import java.util.*


@Route(path = RouterHub.TEST_HOMEACTIVITY)
class TransActivity : AppCompatActivity() {

    companion object {
        const val CLOUD_RECORD_TAG = "cloud_fragment"
        const val BASE_JS_WEBVIEW_TAG = "new_user_web_fragment"
        const val RECORD_VIDEO_PLAY_TAG = "record_video"
    }

    private val queryOrderDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans)
        addOrAttachFragment1.setOnClickListener {
//            var dateTime = LocalDate(2020, 2, 14)
//            Log.e("TAG", "${dateTime.year}/${dateTime.monthOfYear}/${dateTime.dayOfMonth}")
//            Log.e("TAG", "dayCount=${dateTime.dayOfMonth().withMaximumValue().dayOfMonth}")
//            Log.e("TAG", "weekNum=${dateTime.dayOfWeek}")
//            val calendarToday = Calendar.getInstance()
//            calendarToday.time =dateTime.toDate()
//            Log.e("TAG", "weekNumInCalendar=${calendarToday.get(Calendar.DAY_OF_WEEK)}")
//            val theFirstDateOfMonth = dateTime.dayOfMonth().withMinimumValue()
//            Log.e("TAG", "theFirstDateOfMonth=${theFirstDateOfMonth.year}/${theFirstDateOfMonth.monthOfYear}/${theFirstDateOfMonth.dayOfMonth}")
//            dateTime=dateTime.plusMonths(1)
//            Log.e("TAG", "now=${dateTime.year}/${dateTime.monthOfYear}/${dateTime.dayOfMonth}")
////            dateTime = dateTime.plusMonths(1)
////            Log.e("TAG", "plusMonths+1=${dateTime.year}/${dateTime.monthOfYear}/${dateTime.dayOfMonth}")
//            dateTime = dateTime.plusMonths(-1)
//            Log.e("TAG", "plusMonths-1=${dateTime.year}/${dateTime.monthOfYear}/${dateTime.dayOfMonth}")

//            dateTime = dateTime.plusMonths(-1)
//            Log.e("TAG","now=${dateTime.year()}/${dateTime.monthOfYear()}/${dateTime.dayOfMonth()}")
//            Log.e("time", "time start")
//            queryOrderDisposable.add(Observable.interval(1, 1, TimeUnit.SECONDS)
//                    .take(6)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread()).subscribe({ time ->
//                        Log.e("time", "time=${time}")
//                    }, { e -> e.printStackTrace() }))


//            val data = CalendarDataRepo()
//            val list = data.getDataInMonthOffsetNow(System.currentTimeMillis(), -2)
//            Log.e("TAG", "list.size=${list.size}")
//            list.forEachIndexed{i,date->
//            Log.e("TAG", "${i}=${date.year}/${date.monthOfYear}/${date.dayOfMonth}")
//            val timeZone = TimeZone.getDefault()
//            val dt = DateTime(DateTimeZone.forTimeZone(timeZone))
//            val dt = DateTime()
//            val year = dt.year   //年
//            val month = dt.monthOfYear //月
//            val day = dt.dayOfMonth//日
//            val week = dt.dayOfWeek  //星期
//            val hour = dt.hourOfDay //小时数
//            val min = dt.minuteOfHour //分钟数
//            val sec = dt.secondOfMinute //秒
//            val msec = dt.millisOfSecond //毫秒
//            Log.e("TAG", "year=${year}/month=${month}/day=${day}/week=${week}/hour=${hour}/minute=${min}/sec=${sec}/msec=${msec}")

//            Calendar.SUNDAY
//            val dt = DateTime()
//            when (dt.dayOfWeek) {
//                DateTimeConstants.SUNDAY -> println("星期日")
//                DateTimeConstants.MONDAY -> println("星期一")
//                DateTimeConstants.TUESDAY -> println("星期二")
//                DateTimeConstants.WEDNESDAY -> println("星期三")
//                DateTimeConstants.THURSDAY -> println("星期四")
//                DateTimeConstants.FRIDAY -> println("星期五")
//                DateTimeConstants.SATURDAY -> println("星期六")
//            }
//
//            val dt = DateTime()
//            //转换成java.util.Date对象
//            val d1 = Date(dt.millis)
//            val d2: Date = dt.toDate()
//            //转换成java.util.Calendar对象
//            val c1 = Calendar.getInstance(TimeZone.getDefault())
//            c1.timeInMillis = dt.millis
//            val c2: Calendar = dt.toCalendar(Locale.getDefault())
        }
    }

    override fun onPause() {
        overridePendingTransition(0, 0)
        super.onPause()
    }

}