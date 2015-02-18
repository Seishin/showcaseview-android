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

package com.naughtyspirit.showcaseview.utils;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/18/15.
 * *
 * NaughtySpirit 2015
 */
public class PositionsUtil {

    public static enum ItemPosition {
        TOP_LEFT, TOP_CENTER, TOP_RIGHT,
        CENTER_LEFT, CENTER, CENTER_RIGHT,
        BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
    }

    public static RelativeLayout.LayoutParams configureParams(Context ctx, ItemPosition position) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        int topMargin = getStatusBarHeight(ctx) + 50;

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

    public static int getStatusBarHeight(Context ctx) {
        int result = 0;
        int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = ctx.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static float floatToSP(Context ctx, float size) {
        return (size / ctx.getResources().getDisplayMetrics().scaledDensity) + 1;
    }
}
