package com.adim.example.dynamic_list.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adim.example.dynamic_list.View.MainActivity;
import com.adim.example.dynamic_list.Model.ProductObjects;
import com.adim.example.dynamic_list.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Adim on 2/25/2019.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    MainActivity mContext;
    ArrayList<ProductObjects> pdtList;


    public ProductAdapter(MainActivity products, ArrayList<ProductObjects> pdtList) {
        this.mContext = products;
        this.pdtList = pdtList;
        Log.e("pdtList", "" + pdtList);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productsdesign, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductObjects pdt_obj = pdtList.get(position);
        Log.e("pdtList", "" + pdtList);

        if(pdt_obj.getProductname()!=null)
        {
            holder.product_name.setText(pdt_obj.getProductname());
        }

        if(pdt_obj.getPdt_des()!=null) {
            holder.product_des.setText(pdt_obj.getPdt_des());
        }
        if(pdt_obj.getProductimg()!=null) {
            Glide.with(mContext).load(pdt_obj.getProductimg()).placeholder(R.drawable.waiting).dontAnimate().into(holder.product_img);
        }


    }


    @Override
    public int getItemCount() {
        return pdtList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.product_name)
        TextView product_name;
        @BindView(R.id.product_des)
        TextView product_des;
        @BindView(R.id.product_img)
        ImageView product_img;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);



        }


    }


}
