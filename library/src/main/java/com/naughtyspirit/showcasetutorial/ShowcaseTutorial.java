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

package com.naughtyspirit.showcasetutorial;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naughtyspirit.showcasetutorial.targets.Target;
import com.naughtyspirit.showcasetutorial.targets.TargetView;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/10/15.
 *
 * NaughtySpirit 2015
 */
public class ShowcaseTutorial extends RelativeLayout implements ShowcaseTutorialInterface {

    private static final String TAG = ShowcaseTutorial.class.getName();
    private Activity activity;

    private Bitmap bgBitmap;
    private Canvas tempCanvas;
    private Paint bgPaint;
    private Paint firstTranspRingPaint;
    private Paint secRingPaint;
    private PorterDuffXfermode porterDuffXfermode;
    private Target target;
    private Button button;

    public ShowcaseTutorial(Activity activity) {
        super(activity);

        this.activity = activity;

        tempCanvas = new Canvas();

        bgPaint = new Paint();
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

        firstTranspRingPaint = new Paint();
        firstTranspRingPaint.setAntiAlias(true);
        firstTranspRingPaint.setColor(getResources().getColor(android.R.color.transparent));
        firstTranspRingPaint.setXfermode(porterDuffXfermode);

        secRingPaint = new Paint();
        secRingPaint.setAntiAlias(true);

        setWillNotDraw(false);

//        setAnimation(AnimationUtils.loadAnimation(activity, android.R.anim.fade_in));
//        setAnimationCacheEnabled(true);
    }

    public ShowcaseTutorial(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }

    @Override
    public void setBackgroundColor(String color) {
        bgPaint.setColor(Color.parseColor(color));
        bgPaint.setAlpha(240);
        secRingPaint.setColor(Color.parseColor(color));
        secRingPaint.setAlpha(180);
    }

    @Override
    public void setBackgroundColor(int color) {
        bgPaint.setColor(color);
        secRingPaint.setColor(color);
        secRingPaint.setAlpha(180);
    }

    @Override
    public void setDescription(String description, ItemPosition position) {
        TextView descriptionView = new TextView(activity);

        descriptionView.setText(description);
        descriptionView.setTextColor(Color.WHITE);
        descriptionView.setTextSize(20);
        descriptionView.setGravity(Gravity.CENTER);
        addView(descriptionView, configureParams(position));
    }

    @Override
    public void setButton(String text, ItemPosition position) {
        button = new Button(activity);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setTextSize(18);
        button.setBackgroundColor(Color.TRANSPARENT);

        addView(button, configureParams(position));
    }

    @Override
    public void setTarget(Target target) {
        this.target = target;
    }

    @Override
    public Target getTarget() {
        return target;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private LayoutParams configureParams(ItemPosition position) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        int topMargin = getStatusBarHeight() + 50;

        switch (position) {
            case TOP_LEFT:
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.setMargins(80, topMargin, 80, 0);
                break;

            case TOP_CENTER:
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.setMargins(0, topMargin, 0, 0);
                break;

            case TOP_RIGHT:
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.setMargins(80, topMargin, 80, 0);
                break;

            case CENTER_LEFT:
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.setMargins(80, 0, 80, 0);
                break;

            case CENTER:
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                params.setMargins(80, 0, 80, 0);
                break;

            case CENTER_RIGHT:
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.setMargins(80, 0, 80, 0);
                break;

            case BOTTOM_LEFT:
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.setMargins(80, 0, 80, 200);
                break;

            case BOTTOM_CENTER:
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.setMargins(80, 0, 80, 200);
                break;

            case BOTTOM_RIGHT:
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.setMargins(80, 0, 80, 200);
                break;
        }

        return params;
    }

    @Override
    public void setButtonOnClickListener(OnClickListener listener) {
        try {
            button.setOnClickListener(listener);
        } catch (Exception e) {
            Log.e(TAG, "No set button.");
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
        tempCanvas.drawRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight(), bgPaint);

        if (target.getType().equals(TargetView.ShowcaseType.CIRCLE)) {
            float x = target.getLocation()[0];
            float y = target.getLocation()[1];
            float radius = target.getLocation()[2];

            tempCanvas.drawCircle(x, y, radius + 30, firstTranspRingPaint);
            tempCanvas.drawCircle(x, y, radius + 30, secRingPaint);
            tempCanvas.drawCircle(x, y, radius, firstTranspRingPaint);
        } else if (target.getType().equals(TargetView.ShowcaseType.RECTANGLE)) {
            float left = target.getLocation()[0];
            float top = target.getLocation()[1];
            float right = target.getLocation()[2];
            float bottom = target.getLocation()[3];

            tempCanvas.drawRect(left - 30, top - 30, right + 30, bottom + 30, firstTranspRingPaint);
            tempCanvas.drawRect(left - 30, top - 30, right + 30, bottom + 30, secRingPaint);
            tempCanvas.drawRect(left, top, right, bottom, firstTranspRingPaint);
        }

        canvas.drawBitmap(bgBitmap, 0, 0, bgPaint);
    }

    public static class Builder {
        private ShowcaseTutorial showcaseTutorial;

        public Builder(Activity activity) {
            showcaseTutorial = new ShowcaseTutorial(activity);
            showcaseTutorial.setBackgroundColor("#23a2eb");
        }

        public Builder setTarget(Target target) {
            showcaseTutorial.setTarget(target);
            return this;
        }

        public Builder setDescription(String description, ItemPosition position) {
            showcaseTutorial.setDescription(description, position);
            return this;
        }

        public Builder setButton(String text, ItemPosition position) {
            showcaseTutorial.setButton(text, position);
            return this;
        }

        public ShowcaseTutorial build() {
            return showcaseTutorial;
        }
    }
}
