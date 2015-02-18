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

package com.naughtyspirit.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;

import com.naughtyspirit.showcaseview.ShowcaseView;
import com.naughtyspirit.showcaseview.targets.Target;
import com.naughtyspirit.showcaseview.targets.TargetView;
import com.naughtyspirit.showcaseview.utils.PositionsUtil;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/18/15.
 * NaughtySpirit 2015
 */
public class CustomThemeShowcase extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_theme);

        Button btn = (Button) findViewById(R.id.btn);

        Target target = new TargetView(btn, TargetView.ShowcaseType.RECTANGLE);
        
        new ShowcaseView.Builder(this)
                .setTarget(target)
                .setDescription("Showcase with custom theme! Yahoo! :)", PositionsUtil.ItemPosition.CENTER)
                .setButton(PositionsUtil.ItemPosition.TOP_CENTER)
                .setCustomTheme(R.style.CustomShowcaseViewTheme)
                .build();
    }
}
