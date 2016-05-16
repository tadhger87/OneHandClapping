package com.tadhg.onehandclapping.adapter;

/**
 * Created by Tadhg on 13/11/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
//import com.tadhg.onehandclapping.DetailsTransition;
import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.activity.DetailActivity;
import com.tadhg.onehandclapping.activity.DetailFragment;
import com.tadhg.onehandclapping.activity.MainActivity;
import com.tadhg.onehandclapping.db.ClapDAO;
import com.tadhg.onehandclapping.model.ClapItem;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ClapViewHolder>{


    private static final String TAG = "Falafel";
    private int REQUEST_CODE;



    public interface ItemClickListener {
        void onClick(List<ClapItem> list, View view, int position, boolean isLongClick);
    }

    public interface ItemTouchListener {
        void onTouch(List<ClapItem> list, View view, int position);
    }



    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    private Context context;
    List<ClapItem> items;
    DetailFragment detailFragment;

    public GridAdapter(Context mContext, List<ClapItem> myClap, DetailFragment df){
        this.items = myClap;
        this.context = mContext;
        this.detailFragment = df;
        setHasStableIds(true);
    }


    public static class ClapViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
            ,View.OnTouchListener

    {

        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        public TextView placeName;
        public ImageView placeImage;

        List<ClapItem> mItems;
        ClapItem clapItem;
        public ImageView imgThumbnail;
        public TextView tvClapName;
        private ItemClickListener clickListener;
        private RecyclerView.OnItemTouchListener touchListener;
        final MediaPlayer mp = new MediaPlayer();
        // private int REQUEST_CODE;

        public ClapViewHolder(View itemView) {
            super(itemView);
           // imgThumbnail = (ImageView) itemView.findViewById(R.id.square_picture);
            //tvClapName = (TextView) itemView.findViewById(R.id.tv_species);

            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeName = (TextView) itemView.findViewById(R.id.cardName);
            placeNameHolder = (LinearLayout) itemView.findViewById(R.id.cardNameHolder);
            placeImage = (ImageView) itemView.findViewById(R.id.cardPicture);
            itemView.setTag(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnTouchListener(this);

        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        public void setTouchListener (RecyclerView.OnItemTouchListener itemTouchListener){
            this.touchListener = itemTouchListener;
        }
        @Override
        public void onClick(View view) {

            clickListener.onClick(mItems, view, getAdapterPosition(), false);
           // ((MainActivity) mContext).setFragment(yourFragment);
        }
        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(mItems, view, getAdapterPosition(), true);
            return true;
        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {


            return false;
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ClapViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);
//grid_item_square
        return new ClapViewHolder(v);
    }








    @Override
    public void onBindViewHolder(ClapViewHolder holder, int i) {
        ClapItem clapItem = items.get(i);

        getItemId(i);

        if(items.get(i).getPictureRef() != null){
            Picasso.with(context).load(new File(items.get(i).getPictureRef()))
                    // .error(R.drawable.clap3)
                    // .placeholder(R.drawable.clapping1)
                    //.memoryPolicy(MemoryPolicy.NO_CACHE)
                    .fit()
                    .centerCrop()
                    .into(holder.placeImage);
        } else{
            holder.placeImage.setImageResource(R.drawable.clapping1);
        }
        holder.placeImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        /*if (items.get(i).getId() > -1) {
            holder.tvClapName.setText(items.get(i).getClapName());
        } else {
            holder.tvClapName.setText("");
        }*/

                if (items.get(i).getClapName() != null) {
                    holder.placeName.setText(items.get(i).getClapName());
                } else {
                    holder.placeName.setText(null);
                }

                holder.setClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(List<ClapItem> list, final View view, final int position, boolean isLongClick) {
                        if (isLongClick) {


                            ClapItem clap = (ClapItem) items.get(position);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);
                            // set title
                            alertDialogBuilder.setTitle("Delete Clap");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Are you sure you want to delete this clap?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ClapItem clap = (ClapItem) items.get(position);
                                            ClapDAO databaseHelper = new ClapDAO(view.getContext());
                                            databaseHelper.deleteClap(clap);
                                            remove(clap);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();


                        } else {

                            ClapItem clap = (ClapItem) items.get(position);
                            view.getContext();
                            Transition dialSlide = new Slide();
                            dialSlide.setDuration(300);

                            Fragment fragment = new DetailFragment();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                               // fragment.setSharedElementEnterTransition(new DetailsTransition());
                                fragment.setEnterTransition(dialSlide);
                                fragment.setExitTransition(dialSlide);
                             //   fragment.setSharedElementReturnTransition(new DetailsTransition());
                            }
                            Bundle arguments = new Bundle();
                            arguments.putParcelable("ClapObject", clap);

                            fragment.setArguments(arguments);

                            ((ActionBarActivity)context).getSupportFragmentManager()
                                    .beginTransaction()
                                    .addSharedElement(holder.placeImage, "tImage")
                                    .addSharedElement(holder.placeNameHolder, "tNameHolder")
                                    .replace(R.id.container_body, fragment)
                                    .addToBackStack(null)
                                    .commit();



                        }

                    }
                });

            }

            @Override
            public int getItemCount() {
                return (null != items ? items.size() : 0);
                //return mItems.size();
            }

            public void remove(ClapItem item) {
                int position = items.indexOf(item);
                items.remove(position);
                notifyItemRemoved(position);
            }



        }
