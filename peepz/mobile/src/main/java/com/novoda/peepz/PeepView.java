package com.novoda.peepz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeepView extends FrameLayout {

    @BindView(R.id.peep_text_name)
    TextView nameTextView;

    @BindView(R.id.peep_image)
    ImageView imageTextView;

    public PeepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_peep, this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthPixels = getResources().getDisplayMetrics().widthPixels;

        int peekPx = getResources().getDimensionPixelSize(R.dimen.size_peek);
        int leftOverWidthPx = widthPixels - peekPx;
        int rows = getResources().getInteger(R.integer.spans);
        int desiredWidth = leftOverWidthPx / rows;

        int desiredWidthMeasureSpec = MeasureSpec.makeMeasureSpec(desiredWidth, MeasureSpec.EXACTLY);
        super.onMeasure(desiredWidthMeasureSpec, heightMeasureSpec);
    }

    public void bind(Peep peep) {
        if (peep.name().isPresent()) {
            nameTextView.setText(peep.name().get());
            nameTextView.setVisibility(View.VISIBLE);
        } else {
            nameTextView.setVisibility(View.INVISIBLE);
        }

        imageTextView.setImageBitmap(peep.image());
    }

}
