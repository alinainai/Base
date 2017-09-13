package trunk.doi.base.ui.activity;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.util.PermissionUtils;
import trunk.doi.base.util.ToastUtils;

public class ContactActivity extends BaseActivity {


    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_get)
    TextView tvGet;
    @BindView(R.id.tv_get_permission)
    TextView tvGetPermission;

    private List<Contact> contacts=new ArrayList<>();

    @Override
    protected int initLayoutId() {
        return R.layout.activity_contact;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

        if(!PermissionUtils.hasPermissions(ContactActivity.this, android.Manifest.permission.READ_CONTACTS)) {
            PermissionUtils.requestPermissions(ContactActivity.this,1001,android.Manifest.permission.READ_CONTACTS);
        }else{
            contacts=readContact();
            if(null!=contacts&& contacts.size()>0){
                StringBuilder sb=new StringBuilder();
                for (int i=0;i<contacts.size();i++){
                    sb.append(contacts.get(i).getName()+"///"+contacts.get(i).getPhoneNun());
                }
                tvContact.setText(sb.toString());

            }else{
                tvContact.setText("没有数据");
            }


        }

    }


    @OnClick({R.id.tv_get, R.id.tv_get_permission, R.id.tv_contact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get:
                break;
            case R.id.tv_get_permission:
                break;
            case R.id.tv_contact:
                break;
        }
    }

    /**
     *得到联系人
     **/
    private List<Contact> readContact() {

        ArrayList<Contact> list = new ArrayList<>();
        Cursor cursor=null;
        try{
            cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null );
            Contact contact;
            while(cursor.moveToNext()){
                String  displayName  =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String  number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contact=new Contact(number,displayName);
                list.add(contact);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return list;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001 && grantResults.length > 0) {
            if (permissions.length > 0 && permissions.length == grantResults.length) {
                for (int i = 0; i < grantResults.length; i++) {
                    //do something
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        contacts=readContact();
                        if(null!=contacts&& contacts.size()>0){
                            StringBuilder sb=new StringBuilder();
                            for (int j=0;j<contacts.size();j++){
                                sb.append(contacts.get(j).getName()+"///"+contacts.get(j).getPhoneNun());
                            }
                            tvContact.setText(sb.toString());

                        }else{
                            tvContact.setText("没有数据");
                        }

                    } else {
                        ToastUtils.showShort(ContactActivity.this,"请去设置中开启软件读取文件信息的权限，否则软件不能正常使用");
                    }
                }
            }
        }

    }


    public class Contact{

        public Contact(String phoneNun, String name) {
            this.phoneNun = phoneNun;
            this.name = name;
        }

        private String phoneNun;
        private String name;

        public String getPhoneNun() {
            return phoneNun;
        }

        public void setPhoneNun(String phoneNun) {
            this.phoneNun = phoneNun;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
