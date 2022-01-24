package com.nafim.qawmiapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class List extends AppCompatActivity implements Adapter.OnNoteListener {

    RecyclerView recyclerView;
    Adapter adapter;
    //RecyclerView.LayoutManager layoutManager;
    ArrayList<Item> arrayList;

    //Adapter.OnNoteListener onNoteListener=this;

    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView=findViewById(R.id.rcvw);

        recyclerView.setClickable(true);

        arrayList=new ArrayList<>();

        Adapter.OnNoteListener onNoteListener=this;


        /*arrayList.add(new Item("Arabic.txt","Nafim","125","content://com.android.exteralstorage.documents/document/9C336BBD%3Atxttospeeck%2FArabic.txt"));
        arrayList.add(new Item("efg","fdswew","123","content://com.android.exteralstorage.documents/document/9C336BBD%3Atxttospeeck%2FArabic2.txt"));
        arrayList.add(new Item("hij","dfdsf","475","content://com.android.exteralstorage.documents/document/9C336BBD%3Atxttospeeck%2Fsample.txt"));
        arrayList.add(new Item("klm","qewrf","785","content://com.android.exteralstorage.documents/document/9C336BBD%3Atxttospeeck%2Fsample3.txt"));
        arrayList.add(new Item("nop","erhjfei","985","uihfdsj"));
        arrayList.add(new Item("qrs","ewhhdfsh","136","jifer"));

        adapter=new Adapter(arrayList,onNoteListener);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);*/


        /////////////////////////////////////////////////

        String path = Environment.getExternalStorageDirectory().toString()+"/Pictures";
       // Log.d("Files", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        //Log.d("Files", "Size: "+ file.length);
        for (int i=0; i < file.length; i++)
        {
            //here populate your listview
            //Log.d("Files", "FileName:" + file[i].getName());

            arrayList.add(new Item("qrs","ewhhdfsh","136",file[i].getName()));

            adapter=new Adapter(arrayList,onNoteListener);
            layoutManager=new LinearLayoutManager(getApplicationContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        }








        /////////////////////////////////////////////////






    }

    @Override
    public Void onNoteclicl(int position) {

        Item item=arrayList.get(position);
        String Bkname=item.getBookName();
        String riter=item.getWriter();
        String bkid=item.getId();
        String lnk=item.getLink();


        Toast.makeText(getApplicationContext(),Bkname+riter+bkid+lnk,Toast.LENGTH_LONG).show();

        /*Intent intent = new Intent(getApplicationContext(),Read.class);
        //intent.setDataAndType(uri,"application/*");
        intent.putExtra("txt",read(Uri.parse("content://com.android.exteralstorage.documents/document/9C33-6BBD%3Atxttospeeck%2FArabic2.txt")) );
        startActivity(intent);*/


        // return null;

        ///////////////////////////////////////////////////////////////////




        return null;


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