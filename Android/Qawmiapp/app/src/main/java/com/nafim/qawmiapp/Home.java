package com.nafim.qawmiapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void chooser(View v)
    {
        Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1001 && resultCode==RESULT_OK)
        {
            if(data!=null)
            {


                Uri uri=data.getData();

                Toast.makeText(getApplicationContext(),uri.toString(),Toast.LENGTH_SHORT).show();

                String text=read(uri);



                //tv.setText( uri.toString());

                Intent intent = new Intent(getApplicationContext(),Read.class);
                //intent.setDataAndType(uri,"application/*");
                intent.putExtra("txt", text);
                startActivity(intent);


            }
        }


    }

    public String read(Uri uri) {

        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        String line = " ";


        try {
            reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));

            while ((line = reader.readLine()) != null)
            {
                builder.append(line);

            }
        } catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return builder.toString();

    }


}