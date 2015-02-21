/*
 *
 *  * Copyright 2015 Atanas Dimitrov <atanas@naughtyspirit.co>
 *  *                 NaughtySpirit 2014
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package co.naughtyspirit.showcaseview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import co.naughtyspirit.showcaseview.targets.Target;
import co.naughtyspirit.showcaseview.targets.TargetView;
import co.naughtyspirit.showcaseview.utils.PositionsUtil;
import co.naughtyspirit.showcaseview.utils.SharedPreferencesUtil;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/10/15.
 *
 * NaughtySpirit 2015
 */
public class ShowcaseView extends RelativeLayout implements ShowcaseViewAPI {

    private static final String TAG = ShowcaseView.class.getName();

    private Context ctx;

    private Bitmap bgBitmap;
    private Canvas tempCanvas;
    private Paint backgroundPaint;
    private Paint transparentPaint;
    private Paint borderPaint;
    private Target target;
    private Button buttonView;
    private TextView descriptionView;
    
    private int targetBorderSize;
    private int targetMargin;
    private String showcaseTag;

    public ShowcaseView(Context ctx) {
        this(ctx, null, R.style.ShowcaseView);
    }

    public ShowcaseView(Context ctx, AttributeSet attrs) {
        this(ctx, attrs, R.style.ShowcaseView);
    }
    
    public ShowcaseView(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        this.ctx = ctx;

        final TypedArray styled = ctx.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ShowcaseView, R.attr.showcaseViewStyle,
                        R.style.ShowcaseView);
        
        initDrawTools();
        initUI();
        
        updateStyle(styled, false);
    }
    
    private void initDrawTools() {
        tempCanvas = new Canvas();

        backgroundPaint = new Paint();
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

        transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(porterDuffXfermode);

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);

        setWillNotDraw(false);
    }

    private void initUI() {
        descriptionView = (TextView) LayoutInflater.from(ctx).inflate(R.layout.showcase_description, null);
        addView(descriptionView, PositionsUtil.configureParams(ctx, PositionsUtil.ItemPosition.TOP_CENTER));

        buttonView = (Button) LayoutInflater.from(ctx).inflate(R.layout.showcase_button, null);
        buttonView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        addView(buttonView, PositionsUtil.configureParams(ctx, PositionsUtil.ItemPosition.BOTTOM_CENTER));
    }

    public void setStyle(int style) {
        TypedArray array = getContext().obtainStyledAttributes(style, R.styleable.ShowcaseView);

        updateStyle(array, true);
    }

    public void updateStyle(TypedArray styled, boolean invalidate) {
        int showcaseBackground = styled.getColor(R.styleable.ShowcaseView_showcase_background,
                ctx.getResources().getColor(R.color.showcase_bg));

        int targetBorderColor = styled.getColor(R.styleable.ShowcaseView_showcase_target_border_color,
                ctx.getResources().getColor(R.color.showcase_target_border_color));
        targetBorderSize = styled.getDimensionPixelSize(R.styleable.ShowcaseView_showcase_target_border_size,
                ctx.getResources().getDimensionPixelSize(R.dimen.showcase_target_border_size));
        targetMargin = styled.getDimensionPixelSize(R.styleable.ShowcaseView_showcase_target_margin,
                ctx.getResources().getDimensionPixelSize(R.dimen.showcase_target_margin));

        float descTextSize = styled.getDimension(R.styleable.ShowcaseView_showcase_desc_text_size,
                ctx.getResources().getDimensionPixelSize(R.dimen.showcase_desc_text_size));
        int descTextColor = styled.getColor(R.styleable.ShowcaseView_showcase_desc_text_color,
                ctx.getResources().getColor(R.color.showcase_desc_text));

        float btnTextSize = styled.getDimension(R.styleable.ShowcaseView_showcase_btn_text_size,
                ctx.getResources().getDimension(R.dimen.showcase_btn_text));
        int btnTextColor = styled.getColor(R.styleable.ShowcaseView_showcase_btn_text_color,
                ctx.getResources().getColor(R.color.showcase_btn_text));
        int btnBackground = styled.getColor(R.styleable.ShowcaseView_showcase_btn_background,
                ctx.getResources().getColor(R.color.showcase_btn_background));

        styled.recycle();
        
        setBackgroundColor(showcaseBackground);
        setBorderColor(targetBorderColor);

        descriptionView.setTextColor(descTextColor);
        descriptionView.setTextSize(PositionsUtil.floatToSP(ctx, descTextSize));
        descriptionView.setText(TextUtils.isEmpty(descriptionView.getText().toString()) ? ctx.getString(R.string.showcase_description) : descriptionView.getText().toString());

        buttonView.setTextColor(btnTextColor);
        buttonView.setTextSize(PositionsUtil.floatToSP(ctx, btnTextSize));
        buttonView.setText(TextUtils.isEmpty(buttonView.getText().toString()) ? ctx.getString(R.string.showcase_button) : buttonView.getText().toString());
        buttonView.setBackgroundColor(btnBackground);

        if (invalidate) {
            invalidate();
        }
    }

    @Override
    public void setBackgroundColor(String color) {
        backgroundPaint.setColor(Color.parseColor(color));
    }

    @Override
    public void setBackgroundColor(int color) {
        backgroundPaint.setColor(color);
    }

    @Override
    public void setBorderColor(String color) {
        borderPaint.setColor(Color.parseColor(color));
    }

    @Override
    public void setBorderColor(int color) {
        borderPaint.setColor(color);
    }

    @Override
    public void setDescription(String description, PositionsUtil.ItemPosition position) {
        if (!TextUtils.isEmpty(description)) {
            descriptionView.setText(description);
        }
        
        descriptionView.setLayoutParams(PositionsUtil.configureParams(ctx, position));
        invalidate();
    }

    @Override
    public void setButton(String text, PositionsUtil.ItemPosition position) {
        if (!TextUtils.isEmpty(text)) {
            buttonView.setText(text);
        }
        
        buttonView.setLayoutParams(PositionsUtil.configureParams(ctx, position));
        invalidate();
    }

    @Override
    public void setShowcaseTag(String tag) {
        this.showcaseTag = tag;
    }

    @Override
    public String getShowcaseTag() {
        return showcaseTag;
    }

    @Override
    public void setOneShot(boolean isOneShot) {
        SharedPreferencesUtil.setPreference(ctx, showcaseTag, isOneShot);
    }

    @Override
    public boolean hasShot() {
        return SharedPreferencesUtil.getBooleanPreference(ctx, showcaseTag);
    }

    @Override
    public void setTarget(Target target) {
        this.target = target;
    }

    @Override
    public Target getTarget() {
        return target;
    }

    @Override
    public void setButtonOnClickListener(OnClickListener listener) {
        try {
            buttonView.setOnClickListener(listener);
        } catch (Exception e) {
            Log.e(TAG, "No set buttonView.");
        }
    }

    @Override
    public void hide() {
        setVisibility(GONE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (target == null) {
            Log.i(TAG, "No target set...");
            return;
        }

        bgBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

        tempCanvas.setBitmap(bgBitmap);
        tempCanvas.drawRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight(), backgroundPaint);

        if (target.getType().equals(TargetView.ShowcaseType.CIRCLE)) {
            float x = target.getLocation()[0];
            float y = target.getLocation()[1];
            float radius = target.getLocation()[2];

            tempCanvas.drawCircle(x, y, radius + targetMargin + targetBorderSize, transparentPaint);
            tempCanvas.drawCircle(x, y, radius + targetMargin + targetBorderSize, borderPaint);
            tempCanvas.drawCircle(x, y, radius + targetMargin, transparentPaint);
        } else if (target.getType().equals(TargetView.ShowcaseType.RECTANGLE)) {
            float left = target.getLocation()[0] - targetMargin;
            float top = target.getLocation()[1] - targetMargin;
            float right = target.getLocation()[2] + targetMargin;
            float bottom = target.getLocation()[3] + targetMargin;

            tempCanvas.drawRect(left - targetBorderSize, top - targetBorderSize, 
                    right + targetBorderSize, bottom + targetBorderSize, transparentPaint);
            tempCanvas.drawRect(left - targetBorderSize, top - targetBorderSize, 
                    right + targetBorderSize, bottom + targetBorderSize, borderPaint);
            tempCanvas.drawRect(left, top, right, bottom, transparentPaint);
        }

        canvas.drawBitmap(bgBitmap, 0, 0, backgroundPaint);
    }

    public static class Builder {
        private final Activity activity;
        private ShowcaseView showcaseView;
        private boolean isOneShot;

        /**
         * Builder class for easier ShowcaseView creation.
         *
         * @param activity host activity reference
         * @param tag showcaseView tag
         */
        public Builder(Activity activity, String tag) {
            this.activity = activity;
            showcaseView = new ShowcaseView(activity);
            showcaseView.setShowcaseTag(tag);
        }

        /**
         * Setting up the current ShowcaseView whether to show once or always.
         *
         * @param isOneShot
         * @return
         */
        public Builder setOneShot(boolean isOneShot) {
            this.isOneShot = isOneShot;
            return this;
        }
        
        /**
         * Setting up a custom theme defined in your application's style.xml file.
         *
         * @param style reference to your custom style theme
         */
        public Builder setCustomTheme(int style) {
            showcaseView.setStyle(style);
            return this;
        }

        /**
         * Setting up the the showcase target view.
         *
         * @param target {@link co.naughtyspirit.showcaseview.targets.TargetView} to be showcased
         */
        public Builder setTarget(Target target) {
            showcaseView.setTarget(target);
            return this;
        }

        /**
         * Setting up the description position on the screen.
         * Default position is TOP_CENTER {@link co.naughtyspirit.showcaseview.utils.PositionsUtil.ItemPosition}
         *
         * @param position desired {@link co.naughtyspirit.showcaseview.utils.PositionsUtil.ItemPosition}
         */
        public Builder setDescription(PositionsUtil.ItemPosition position) {
            setDescription(null, position);
            return this;
        }

        /**
         * Setting up the text's text and its position on the screen.
         * Default position is TOP_CENTER {@link co.naughtyspirit.showcaseview.utils.PositionsUtil.ItemPosition}
         *
         * @param text description text
         * @param position desired {@link co.naughtyspirit.showcaseview.utils.PositionsUtil.ItemPosition}
         */
        public Builder setDescription(String text, PositionsUtil.ItemPosition position) {
            showcaseView.setDescription(text, position);
            return this;
        }

        /**
         * Setting up the button position on the screen.
         * Default position is BOTTOM_CENTER {@link co.naughtyspirit.showcaseview.utils.PositionsUtil.ItemPosition}
         *
         * @param position desired {@link co.naughtyspirit.showcaseview.utils.PositionsUtil.ItemPosition}
         */
        public Builder setButton(PositionsUtil.ItemPosition position) {
            setButton(null, position);
            return this;
        }

        /**
         * Setting up the button's text and its position on the screen.
         * Default position is BOTTOM_CENTER {@link co.naughtyspirit.showcaseview.utils.PositionsUtil.ItemPosition}
         *
         * @param text button text
         * @param position desired {@link co.naughtyspirit.showcaseview.utils.PositionsUtil.ItemPosition}
         */
        public Builder setButton(String text, PositionsUtil.ItemPosition position) {
            showcaseView.setButton(text, position);
            return this;
        }

        /**
         * Building and presenting the {@link ShowcaseView} on the screen.
         */
        public ShowcaseView build() {
            if (!showcaseView.hasShot()) {
                ((ViewGroup) activity.getWindow().getDecorView()).addView(showcaseView);
                showcaseView.setOneShot(isOneShot);
            }
            return showcaseView;
        }
    }
}