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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.naughtyspirit.showcasetutorial.ShowcaseTutorial;
import com.naughtyspirit.showcasetutorial.ShowcaseTutorialInterface.ItemPosition;
import com.naughtyspirit.showcasetutorial.targets.Target;
import com.naughtyspirit.showcasetutorial.targets.TargetView;
import com.naughtyspirit.showcasetutorial.targets.TargetView.ShowcaseType;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/10/15.
 *
 * NaughtySpirit 2015
 */
public class MainActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn);

        // Creating a TargetView and assigning a Showcase Type - Circle or Rectangle
        Target target = new TargetView(btn, ShowcaseType.CIRCLE);

        // Creating the ShowcaseTutorial and setting up the target, title and button
        final ShowcaseTutorial showcaseTutorial = new ShowcaseTutorial.Builder(this)
                .setTarget(target)
                .setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", ItemPosition.TOP_CENTER)
                .setButton("Close", ItemPosition.BOTTOM_CENTER)
                .build();

        // Attaching an onClickListener to the button
        showcaseTutorial.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcaseTutorial.hide();
            }
        });

        // Adding the view to the Window
        ((ViewGroup) getWindow().getDecorView()).addView(showcaseTutorial);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
}
