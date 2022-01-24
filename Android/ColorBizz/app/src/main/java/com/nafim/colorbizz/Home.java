package com.nafim.colorbizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView ph,Amm_sea,Amm_fresh;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        ph=findViewById(R.id.ph_btn);
        Amm_fresh=findViewById(R.id.fresh_amm);
        Amm_sea=findViewById(R.id.sea_amm);

    }

    public void ph(View view)
    {
        Intent intent=new Intent(getApplicationContext(),PH_detection.class);
        startActivity(intent);
    }

    public void Sea_amm(View view)
    {
        Intent intent=new Intent(getApplicationContext(),Ammo_Sea.class);
        startActivity(intent);

    }

    public void Fresh_amm(View view)
    {
        Intent intent=new Intent(getApplicationContext(),Ammo_fresh.class);
        startActivity(intent);

    }

    public void ug(View view)
    {
        Intent intent=new Intent(getApplicationContext(),menual.class);
        startActivity(intent);
    }

}