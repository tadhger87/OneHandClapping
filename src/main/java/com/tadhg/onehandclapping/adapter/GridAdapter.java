package com.tadhg.onehandclapping.adapter;

/**
 * Created by Tadhg on 13/11/2015.
 */
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.SecondPage;
import com.tadhg.onehandclapping.activity.SaveClapDialog;
import com.tadhg.onehandclapping.model.ClapItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 28/02/2015.
 */
public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
//blah
    List<ClapItem> mItems;

    public GridAdapter() {
        super();
        mItems = new ArrayList<ClapItem>();
        ClapItem clap = new ClapItem();
        clap.setName("Thursday clap");
        clap.setThumbnail(R.drawable.clapping_icon);
        mItems.add(clap);

        clap = new ClapItem();
        clap.setName("Polite Applause");
        clap.setThumbnail(R.mipmap.bw_applause);
        mItems.add(clap);

        clap = new ClapItem();
        clap.setName("Business Clap");
        clap.setThumbnail(R.mipmap.business_clap);
        mItems.add(clap);

        clap = new ClapItem();
        clap.setName("Big Clap");
        clap.setThumbnail(R.mipmap.big_applause);
        mItems.add(clap);

        clap = new ClapItem();
        clap.setName("Slow Clap");
        clap.setThumbnail(R.mipmap.slow_clap);
        mItems.add(clap);

        clap = new ClapItem();
        clap.setName("Clap");
        clap.setThumbnail(R.mipmap.clap_emoji);
        mItems.add(clap);

        clap = new ClapItem();
        clap.setName("Applause");
        clap.setThumbnail(R.drawable.clapping1);
        mItems.add(clap);

        clap = new ClapItem();
        clap.setName("Clap dat");
        clap.setThumbnail(R.drawable.clapping2);
        mItems.add(clap);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ClapItem nature = mItems.get(i);
        viewHolder.tvClapName.setText(nature.getName());
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
        viewHolder.imgThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView tvClapName;
        ClapItem clapItem;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.picture);
            tvClapName = (TextView)itemView.findViewById(R.id.tv_species);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(v.getContext(), SecondPage.class);
                    v.getContext().startActivity(intent);
                    //  Toast.makeText(v.getContext(), "os version is: " + clapItem.getName(), Toast.LENGTH_SHORT).show();
                }
            });


        }

    }


    }

