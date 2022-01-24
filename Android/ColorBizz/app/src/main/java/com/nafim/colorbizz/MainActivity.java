package com.nafim.colorbizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView=findViewById(R.id.img);
        textView=findViewById(R.id.tv);


        Animation rotateAnimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotat);
        Animation textanim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.text_anim);

        imageView.setAnimation(rotateAnimation);
        textView.setAnimation(textanim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                Intent intent=new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();




            }
        },2000);

    }
}