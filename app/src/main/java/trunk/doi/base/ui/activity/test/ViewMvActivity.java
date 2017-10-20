package trunk.doi.base.ui.activity.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.util.PermissionUtils;

public class ViewMvActivity extends BaseActivity implements View.OnClickListener{


    private Button mReadUserButton;
    private Button mReadEventButton;
    private Button mWriteEventButton;

    private static String calanderURL = "";
    private static String calanderEventURL = "";
    private static String calanderRemiderURL = "";
    //为了兼容不同版本的日历,2.2以后url发生改变
    static{
            calanderURL = "content://com.android.calendar/calendars";
            calanderEventURL = "content://com.android.calendar/events";
            calanderRemiderURL = "content://com.android.calendar/reminders";


    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_view_mv;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        if( !PermissionUtils.hasPermissions(ViewMvActivity.this,android.Manifest.permission.WRITE_CALENDAR)){
            PermissionUtils.requestPermissions(ViewMvActivity.this,100,android.Manifest.permission.WRITE_CALENDAR);
        }
        setupViews();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    private void setupViews(){
        mReadUserButton = (Button)findViewById(R.id.readUserButton);
        mReadEventButton = (Button)findViewById(R.id.readEventButton);
        mWriteEventButton = (Button)findViewById(R.id.writeEventButton);
        mReadUserButton.setOnClickListener(this);
        mReadEventButton.setOnClickListener(this);
        mWriteEventButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mReadUserButton){

            Cursor userCursor = getContentResolver().query(Uri.parse(calanderURL), null,
                    null, null, null);
            if(userCursor.getCount() > 0){
                userCursor.moveToFirst();
                String userName = userCursor.getString(userCursor.getColumnIndex("name"));
                Toast.makeText(ViewMvActivity.this, userName, Toast.LENGTH_LONG).show();
            }
        }else if(v == mReadEventButton){
            Cursor eventCursor = getContentResolver().query(Uri.parse(calanderEventURL), null,
                    null, null, null);
            if(eventCursor.getCount() > 0){
                eventCursor.moveToLast();
                String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                Toast.makeText(ViewMvActivity.this, eventTitle, Toast.LENGTH_LONG).show();
            }
        }else {
            if (v == mWriteEventButton) {
                String calId = "";
                Cursor userCursor = getContentResolver().query(Uri.parse(calanderURL), null,
                        null, null, null);
                if(userCursor.getCount() > 0){
                    userCursor.moveToFirst();
                    calId = userCursor.getString(userCursor.getColumnIndex("_id"));

                }
                ContentValues event = new ContentValues();
                event.put("title", "与苍井空小姐动作交流");
                event.put("description", "Frankie受空姐邀请,今天晚上10点以后将在Sheraton动作交流.lol~");
                //插入hoohbood@gmail.com这个账户
                event.put("calendar_id",calId);

                Calendar mCalendar = Calendar.getInstance();
                mCalendar.set(Calendar.HOUR_OF_DAY,10);
                long start = mCalendar.getTime().getTime();
                mCalendar.set(Calendar.HOUR_OF_DAY,11);
                long end = mCalendar.getTime().getTime();

                event.put("dtstart", start);
                event.put("dtend", end);
                event.put("hasAlarm",1);
                event.put("eventTimezone", TimeZone.getDefault().getID().toString());

                Uri newEvent = getContentResolver().insert(Uri.parse(calanderEventURL), event);
                long id = Long.parseLong( newEvent.getLastPathSegment() );
                ContentValues values = new ContentValues();
                values.put( "event_id", id );
                values.put(CalendarContract.Reminders.MINUTES, 1*60*24);
                values.put(CalendarContract.Reminders.EVENT_ID, id);
                values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                ContentResolver cr1 = getContentResolver(); // 为刚才新添加的event添加reminder
                cr1.insert(CalendarContract.Reminders.CONTENT_URI, values); // 调用这个方法返回值是一个Uri

            }
        }
    }
}