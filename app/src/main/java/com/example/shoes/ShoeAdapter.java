package com.example.shoes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class ShoeAdapter extends BaseAdapter {

    private Context context;
    private List<Shoe> shoeList;

    public ShoeAdapter(Context context, List<Shoe> shoeList) {
        this.context = context;
        this.shoeList = shoeList;
    }

    @Override
    public int getCount() {
        return shoeList.size();
    }

    @Override
    public Object getItem(int i) {
        return shoeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View newView = inflater.inflate(R.layout.item_shoe, null);

        TextView baslik = newView.findViewById(R.id.shoe_name);
        TextView fiyat = newView.findViewById(R.id.shoe_price);
        TextView numara = newView.findViewById(R.id.shoe_size);
        ImageView img = newView.findViewById(R.id.shoe_image);

        Shoe shoe = shoeList.get(i);

        baslik.setText(shoe.getName());
        fiyat.setText( String.valueOf(shoe.getPrice()));
        numara.setText(String.valueOf(shoe.getSize()));

        Picasso.get().load(shoe.getImageUrl()).into(img);




        return newView;
    }
}
