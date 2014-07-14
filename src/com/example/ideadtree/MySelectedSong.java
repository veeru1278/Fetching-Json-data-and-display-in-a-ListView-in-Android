package com.example.ideadtree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class MySelectedSong  extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_song);

        Intent in = getIntent();
        String name = in.getStringExtra("key_name");

        TextView displayTextView = (TextView) findViewById(R.id.selected_song_tv);
     
        
        displayTextView.setText(name);
       
    }
}

