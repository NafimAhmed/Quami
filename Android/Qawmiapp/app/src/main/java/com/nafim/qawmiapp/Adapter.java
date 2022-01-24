package com.nafim.qawmiapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ExampleViewHolder>
{


    OnNoteListener onNoteListener;

    ArrayList<Item> arrayList;
    public Adapter(ArrayList<Item> arrayList,OnNoteListener onNoteListener)
    {
        this.arrayList = arrayList;
        this.onNoteListener=onNoteListener;
    }



    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ExampleViewHolder evh=new ExampleViewHolder(v,onNoteListener);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position)
    {
        Item item=arrayList.get(position);

        holder.bookname.setText(item.getBookName());
        holder.writer.setText(item.getWriter());
        holder.link.setText(item.getLink());
        holder.id.setText(item.getId());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView bookname,writer,link,id;
        OnNoteListener onNoteListener;
        public ExampleViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);

            bookname=itemView.findViewById(R.id.bookname);
            writer=itemView.findViewById(R.id.writer);
            link=itemView.findViewById(R.id.link);
            id=itemView.findViewById(R.id.id);
            this.onNoteListener=onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            onNoteListener.onNoteclicl(getAdapterPosition());

        }
    }

    public interface OnNoteListener
    {
        Void onNoteclicl(int position);
    }
    //OnNoteListener onNoteListener;

}
