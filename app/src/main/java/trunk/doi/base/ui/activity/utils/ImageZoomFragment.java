package trunk.doi.base.ui.activity.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;
import uk.co.senab.photoview.PhotoView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ImageZoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageZoomFragment extends BaseFragment {


    private static final String TAG = "ImageZoomFragment";
    private static final String URL_TAG = "URL_TAG";
    @BindView(R.id.photo_view)
    PhotoView photoView;
    private String url;


    public ImageZoomFragment() {
    }


    public static ImageZoomFragment newInstance(String url) {
        ImageZoomFragment fragment = new ImageZoomFragment();
        Bundle args = new Bundle();
        args.putString(URL_TAG, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_image_zoom;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    }

    @Override
    public void initData() {
        if (getArguments() == null && getArguments().getString(URL_TAG) == null) {
            return;
        }
        url = getArguments().getString(URL_TAG);
        Glide.with(mContext)
                .load(url).into(photoView);
        photoView.setOnPhotoTapListener((view, x, y) -> {
            getActivity().finish();
            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    public void saveDrawble() {


    }


}
