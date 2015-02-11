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

package com.naughtyspirit.showcasetutorial.targets;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/11/15.
 * NaughtySpirit 2015
 */
public class TargetView implements Target {

    private final View view;
    private final ShowcaseType type;

    private float[] location;
    private int margin = 10;

    public static enum ShowcaseType {
        CIRCLE, RECTANGLE
    }

    public TargetView(View target, ShowcaseType showCaseType) {
        this.view = target;
        this.type = showCaseType;

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                int[] location = new int[2];
                view.getLocationInWindow(location);

                if (type.equals(ShowcaseType.CIRCLE)) {
                    float x = location[0] + (view.getWidth() / 2);
                    float y = location[1] + (view.getHeight() / 2);
                    float radius = view.getWidth() >= view.getHeight() ? view.getWidth() / 2 : view.getHeight();

                    setCircleLocation(x, y, radius + margin);
                } else if (type.equals(ShowcaseType.RECTANGLE)) {
                    float left = location[0] - margin;
                    float top = location[1] - margin;
                    float right = location[0] + view.getWidth() + margin;
                    float bottom = location[1] + view.getHeight() + margin;

                    setRectLocation(left, top, right, bottom);
                }
            }
        });
    }

    @Override
    public ShowcaseType getType() {
        return type;
    }

    @Override
    public void setCircleLocation(float x, float y, float radius) {
        location = new float[3];

        location[0] = x;
        location[1] = y;
        location[2] = radius;
    }

    @Override
    public void setRectLocation(float left, float top, float right, float bottom) {
        location = new float[4];

        location[0] = left;
        location[1] = top;
        location[2] = right;
        location[3] = bottom;
    }

    @Override
    public float[] getLocation() {
        return location;
    }

    @Override
    public void setTargetMargin(int margin) {
        this.margin = margin;
    }
}
