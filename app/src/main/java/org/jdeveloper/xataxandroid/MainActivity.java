package org.jdeveloper.xataxandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstance){

        super.onCreate(savedInstance);

        //set the general View
        setContentView(R.layout.activity_main);
        final Button buttonPlay = (Button)findViewById(R.id.playBtn);
        buttonPlay.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        //new Intent object to switch between activity
        Intent i=new Intent(this,GameActivity.class);
        startActivity(i);

        //finish current activity
        finish();

    }
}
