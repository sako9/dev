package com.example.javalayout;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class JavaLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button myButton = new Button(this);
        myButton.setText("Press me");
        myButton.setBackgroundColor(Color.YELLOW);
        
        RelativeLayout myLayout = new RelativeLayout(this);
        myLayout.setBackgroundColor(Color.BLUE);
        
        EditText myEditText = new EditText(this);
        
        myButton.setId(1);
        myEditText.setId(2);
        
        RelativeLayout.LayoutParams buttonParams =
        		new RelativeLayout.LayoutParams(
        				RelativeLayout.LayoutParams.WRAP_CONTENT,
        				RelativeLayout.LayoutParams.WRAP_CONTENT);
        
        RelativeLayout.LayoutParams textParams = 
        		new RelativeLayout.LayoutParams(
        				RelativeLayout.LayoutParams.WRAP_CONTENT,
        				RelativeLayout.LayoutParams.WRAP_CONTENT);
        
        textParams.addRule(RelativeLayout.ABOVE, myButton.getId());
        textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textParams.setMargins(0,0,0,80);
        
        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);
        
        
        myLayout.addView(myButton,buttonParams);
        myLayout.addView(myEditText, textParams);
        setContentView(myLayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.java_layout, menu);
        return true;
    }
    
}
