package org.jdeveloper.xataxandroid;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class GameActivity extends Activity {

    private XataxView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);

        Display display=getWindowManager().getDefaultDisplay();

        //Load the resolution into the point object
        Point size=new Point();
        display.getSize(size);


        gameView = new XataxView(this,size.x,size.y);
        setContentView(gameView);
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        gameView.resume();
    }
}
