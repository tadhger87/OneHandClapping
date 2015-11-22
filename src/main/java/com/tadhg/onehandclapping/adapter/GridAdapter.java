package com.tadhg.onehandclapping.adapter;

/**
 * Created by Tadhg on 13/11/2015.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tadhg.onehandclapping.R;
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
        ClapItem species = new ClapItem();
        species.setName("Amur Leopard");
        species.setThumbnail(R.drawable.leopard);
        mItems.add(species);

        species = new ClapItem();
        species.setName("Black Rhino");
        species.setThumbnail(R.drawable.clapping1);
        mItems.add(species);

        species = new ClapItem();
        species.setName("Orangutan");
        species.setThumbnail(R.drawable.clapping2);
        mItems.add(species);

        species = new ClapItem();
        species.setName("Sea Lions");
        species.setThumbnail(R.drawable.clap3);
        mItems.add(species);

        species = new ClapItem();
        species.setName("Indian Elephant");
        species.setThumbnail(R.drawable.elephant);
        mItems.add(species);

        species = new ClapItem();
        species.setName("Giant Panda");
        species.setThumbnail(R.drawable.fish);
        mItems.add(species);

        species = new ClapItem();
        species.setName("Snow Leopard");
        species.setThumbnail(R.drawable.fish);
        mItems.add(species);

        species = new ClapItem();
        species.setName("Dolphin");
        species.setThumbnail(R.drawable.dolphin);
        mItems.add(species);
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
        viewHolder.tvspecies.setText(nature.getName());
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView tvspecies;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvspecies = (TextView)itemView.findViewById(R.id.tv_species);
        }
    }
}
