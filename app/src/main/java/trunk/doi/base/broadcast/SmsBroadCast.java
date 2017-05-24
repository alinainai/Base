package trunk.doi.base.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：Mr.Lee on 2016-8-2 16:15
 * 邮箱：569932357@qq.com
 */
public abstract class SmsBroadCast extends BroadcastReceiver {


    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private static String patternCoder = "(?<!\\d)\\d{6}(?!\\d)";


    public abstract void getReceiveredMsg (String msg,String from);


    /**
     * what：匹配短信中间的6个数字（验证码等）
     * who： xf
     * when：2016/3/11 10:30
     */
    public static String patternCode(String patternContent) {
        if (TextUtils.isEmpty(patternContent)) {
            return null;
        }
        Pattern p = Pattern.compile(patternCoder);
        Matcher matcher = p.matcher(patternContent);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] objs = (Object[]) intent.getExtras().get("pdus");
        if (objs != null) {
            for (Object obj : objs) {
                byte[] pdu = (byte[]) obj;
                SmsMessage sms = SmsMessage.createFromPdu(pdu);
                // 短信的内容
                String message = sms.getMessageBody();
                Log.d("logo", "message     " + message);
                // 短息的手机号。。+86开头？
                String from = sms.getOriginatingAddress();
                getReceiveredMsg(message,from);
                Log.d("logo", "from     " + from);
            }
        }
    }


}
