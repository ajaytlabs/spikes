package com.novoda.peepz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.cameraview.CameraView;

import butterknife.BindView;
import butterknife.ButterKnife;

class SelfieView extends FrameLayout {

    @BindView(R.id.selfie_camera_preview)
    CameraView previewCameraView;

    @BindView(R.id.selfie_image)
    ImageView imageView;

    @BindView(R.id.selfie_button_swap)
    Button swapButton;

    @BindView(R.id.selfie_button_take_picture)
    Button takePictureButton;

    private Listener listener;

    public SelfieView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_selfie, this);
        ButterKnife.bind(this);

        swapButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getVisibility() == INVISIBLE) {
                    imageView.setVisibility(VISIBLE);
                    previewCameraView.setVisibility(INVISIBLE);
                } else {
                    imageView.setVisibility(INVISIBLE);
                    previewCameraView.setVisibility(VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        previewCameraView.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        previewCameraView.stop();
        super.onDetachedFromWindow();
    }

    public void attach(Listener listener) {
        this.listener = listener;
        previewCameraView.addCallback(callback);
    }

    public void detachListeners() {
        previewCameraView.removeCallback(callback);
        this.listener = null;
    }

    private final CameraView.Callback callback = new CameraView.Callback() {
        @Override
        public void onPictureTaken(CameraView cameraView, byte[] data) {
            super.onPictureTaken(cameraView, data);
            listener.onPictureTaken(data);
        }
    };

    public void bind(Peep peep) {
        Glide.with(getContext()).load(peep.image().payload()).into(imageView);
    }

    public void takePicture() {
        previewCameraView.takePicture();
    }

    public interface Listener {

        void onPictureTaken(byte[] data);

    }

}
