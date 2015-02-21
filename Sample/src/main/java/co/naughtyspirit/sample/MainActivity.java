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

package co.naughtyspirit.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import co.naughtyspirit.showcaseview.ShowcaseView;
import co.naughtyspirit.showcaseview.targets.Target;
import co.naughtyspirit.showcaseview.targets.TargetView;
import co.naughtyspirit.showcaseview.targets.TargetView.ShowcaseType;
import co.naughtyspirit.showcaseview.utils.PositionsUtil.ItemPosition;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/10/15.
 *
 * NaughtySpirit 2015
 */
public class MainActivity extends ActionBarActivity implements OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

        // Creating a TargetView and assigning a Showcase Type - Circle or Rectangle
        Target target = new TargetView(btn, ShowcaseType.CIRCLE);

        // Creating the ShowcaseTutorial and setting up the target, title and button
        new ShowcaseView.Builder(this, MainActivity.this.getClass().getName())
                .setTarget(target)
                .setOneShot(false)
                .setDescription("This is the super-duper-mega-cool app! Click on this button to start it now!",
                        ItemPosition.TOP_CENTER)
                .setButton(ItemPosition.BOTTOM_CENTER)
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Intent newIntent = new Intent(MainActivity.this, CustomThemeShowcase.class);
                startActivity(newIntent);
                break;
        }
    }
}
