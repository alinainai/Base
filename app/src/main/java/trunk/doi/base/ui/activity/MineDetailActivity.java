package trunk.doi.base.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.base.BaseApplication;
import trunk.doi.base.bean.User;
import trunk.doi.base.dialog.ActionSheetDialog;
import trunk.doi.base.dialog.ApkDownDialog;
import trunk.doi.base.ui.activity.utils.ImageZoomActivity;
import trunk.doi.base.view.CircleImageView;

/**
 * Created by ly on 2016/6/24.
 * 个人信息
 */
public class MineDetailActivity extends BaseActivity {

    private static final String TAG = MineDetailActivity.class.getName();
    private static final int RESULT_CAPTURE = 100;
    private static final int RESULT_PICK = 101;
    private static final int CROP_PHOTO = 102;
    @BindView(R.id.ll_back)
    LinearLayout llBack;//返回键
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;// 标题
    @BindView(R.id.mine_user_icon_img)
    CircleImageView mineUserIconImg;//用户头像
    @BindView(R.id.detail_headIcon_rl)
    RelativeLayout detailHeadIconRl;//头像选择
    @BindView(R.id.detail_nickName_tv)
    TextView detailNickNameTv;  //昵称
    @BindView(R.id.detail_nickName_rl)
    RelativeLayout detailNickNameRl;
    @BindView(R.id.userName)
    TextView userName;  //用户名
    @BindView(R.id.detail_userName_rl)
    RelativeLayout detailUserNameRl;
    @BindView(R.id.detail_sex_tv)
    TextView detailSexTv; //性别
    @BindView(R.id.detail_sex_rl)
    RelativeLayout detailSexRl;
    @BindView(R.id.detail_birthday_tv)
    TextView detailBirthdayTv; //生日
    @BindView(R.id.detail_birthday_rl)
    RelativeLayout detailBirthdayRl;
    @BindView(R.id.detail_phone_tv)
    TextView detailPhoneTv; //手机号
    @BindView(R.id.detail_band_phone_rl)
    RelativeLayout detailBandPhoneRl;
    @BindView(R.id.detail_service_address_rl)
    RelativeLayout detailServiceAddressRl;

    private User user=new User();
    private File tempFile;//图片文件



    /**
     * @param dirPath
     * @return
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mainCartTitle.setText("个人信息");
        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        }
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

        detailNickNameTv.setText(TextUtils.isEmpty(user.getNickname())?"未设置":user.getNickname());
        userName.setText(TextUtils.isEmpty(user.getUsername())?"未设置":user.getUsername());
        detailSexTv.setText(TextUtils.isEmpty(user.getSex())?"未设置":user.getSex());
        detailPhoneTv.setText(TextUtils.isEmpty(user.getPhone())?"未设置":user.getPhone());
        detailBirthdayTv.setText(TextUtils.isEmpty(user.getBirthday())?"未设置":user.getBirthday());

    }

    @OnClick({R.id.ll_back, R.id.mine_user_icon_img,
            R.id.detail_headIcon_rl, R.id.detail_nickName_rl,
            R.id.detail_userName_rl, R.id.detail_sex_tv, R.id.detail_sex_rl,
            R.id.detail_birthday_rl, R.id.detail_band_phone_rl,
            R.id.detail_service_address_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.mine_user_icon_img:
                if(TextUtils.isEmpty(user.getIconImg())){
                    showImgSelectDialog();
                    return;
                }
                Intent intent = new Intent(mContext, ImageZoomActivity.class);
                ArrayList<String> urls = new ArrayList<>();
                intent.putStringArrayListExtra("imgpath", urls);
                startActivity(intent);
                break;
            case R.id.detail_headIcon_rl:
                showImgSelectDialog();
                break;
            case R.id.detail_nickName_rl:

                break;
            case R.id.detail_userName_rl:
                break;
            case R.id.detail_sex_tv:
                break;
            case R.id.detail_sex_rl:
                break;
            case R.id.detail_birthday_rl:
                break;
            case R.id.detail_band_phone_rl:
                break;
            case R.id.detail_service_address_rl:
                break;
        }
    }

    /**
     * 显示选择图片的dialog
     */
    private void showImgSelectDialog() {

        new ActionSheetDialog(MineDetailActivity.this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("从手机相册中选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tempFile = new File(checkDirPath(BaseApplication.AJYFILE_IMG),
                                        "icon" + String.valueOf(System.currentTimeMillis()) + ".jpg");
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(Intent.createChooser(intent, "请选择图片"), RESULT_PICK);

                            }
                        })
                .addSheetItem("通过相机选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tempFile = new File(checkDirPath(BaseApplication.AJYFILE_IMG),
                                        "icon" + String.valueOf(System.currentTimeMillis()) + ".jpg");
                                Uri imageUri;
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    String authority = mContext.getPackageName() + ".provider";
                                    imageUri = FileProvider.getUriForFile(mContext, authority, tempFile);
                                } else {
                                    imageUri = Uri.fromFile(tempFile);
                                }
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intent, RESULT_CAPTURE);
                            }
                        }).show();


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempFile", tempFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {
            case RESULT_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Uri contentUri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        String authority = mContext.getPackageName() + ".provider";
                        contentUri = FileProvider.getUriForFile(mContext, authority, tempFile);
                    } else {
                        contentUri = Uri.fromFile(tempFile);
                    }
                    cropRawPhoto(contentUri);
                }
                break;
            case RESULT_PICK:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    cropRawPhoto(uri);
                }
                break;
            case CROP_PHOTO:

                if (resultCode == RESULT_OK) {
                    if (intent != null) {

                        setImageToHeadView(intent, tempFile);
                    }
                }
                break;
            default:
                break;
        }
    }
    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        // 授予目录临时共享权限  7.0新加的权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, CROP_PHOTO);
    }
    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent, final File f) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            FileOutputStream out = null;
            try {//打开输出流 将图片数据填入文件中
                out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
                Glide.with(mContext)
                        .asBitmap()
                        .load(f)
                        .into(mineUserIconImg);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
