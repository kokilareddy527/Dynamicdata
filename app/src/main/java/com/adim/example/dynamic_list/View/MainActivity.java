package com.adim.example.dynamic_list.View;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.example.dynamic_list.Adapters.ProductAdapter;
import com.adim.example.dynamic_list.Interfaces.MainContract;
import com.adim.example.dynamic_list.Model.ProductObjects;
import com.adim.example.dynamic_list.Presenter.MainPresenter;
import com.adim.example.dynamic_list.R;
import com.adim.example.dynamic_list.utils.SimpleDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.ViewOps {
    ArrayList<ProductObjects> pdtList = new ArrayList<ProductObjects>();
    ProductAdapter pdt_adapter;
    private MainPresenter presenter;
    @BindView(R.id.products_recycleview)
    public RecyclerView pdtlist;

    @BindView(R.id.swipe)
    public SwipeRefreshLayout swipeView;

    @BindView(R.id.tv_title)
    public TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();*/
        presenter = new MainPresenter(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pdtlist.addItemDecoration(new SimpleDividerItemDecoration(this));
        pdt_adapter = new ProductAdapter(this, pdtList);
        pdtlist.setAdapter(pdt_adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        pdtlist.setLayoutManager(mLayoutManager);
        pdtlist.setItemAnimator(new DefaultItemAnimator());


        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                Log.d("Swipe", "Refreshing");
                presenter.getItems(MainActivity.this);
                ( new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);

                    }
                }, 3000);
            }
        });
        
        presenter.getItems(this);

    }



    private void setTilte(String title) {
        tv_title.setText(title);
    }

    @Override
    public void ResSuccess(String response) {
        Log.e("Res>>>>>>>", "" + response);
        try {
            pdtList.clear();
            JSONObject resobj = new JSONObject(response);


            String title=resobj.getString("title");

            setTilte(title);


            JSONArray jarray = resobj.getJSONArray("rows");
            for (int j = 0; j < jarray.length(); j++) {
                JSONObject respdts = jarray.getJSONObject(j);
                ProductObjects pdt_obj = new ProductObjects();


                pdt_obj.setProductname(respdts.getString("title"));
                pdt_obj.setPdt_des(respdts.getString("description"));
                pdt_obj.setProductimg(respdts.getString("imageHref"));


                pdtList.add(pdt_obj);

            }
            Log.e("pdtList>>>>>>>", "" + pdtList);




        } catch (Exception e) {

        }

        pdt_adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(final String message) {
          runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    @Override
    public void ResFailure(String message) {

    }
}
