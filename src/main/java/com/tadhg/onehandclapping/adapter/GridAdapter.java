package com.tadhg.onehandclapping.adapter;

/**
 * Created by Tadhg on 13/11/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.activity.SecondPage;
import com.tadhg.onehandclapping.db.ClapDAO;
import com.tadhg.onehandclapping.model.ClapItem;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ClapViewHolder>{


    private static final String TAG = "Falafel";
    private int REQUEST_CODE;

    private CallbackInterface mCallback;

    public interface ItemClickListener {
        void onClick(List<ClapItem> list, View view, int position, boolean isLongClick);
    }

    public interface CallbackInterface{

        /**
         * Callback invoked when clicked
         * @param position - the position
         * @param text - the text to pass back
         */
        void onHandleSelection(int position, ClapItem text);
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

    public GridAdapter(Context mContext, List<ClapItem> myClap){
        this.items = myClap;
        this.context = mContext;
        setHasStableIds(true);
       /* try{
            mCallback = (CallbackInterface) context;
        }catch(ClassCastException ex){
            //.. should log the error or throw and exception
            Log.e("MyAdapter","Must implement the CallbackInterface in the Activity", ex);
        }*/
    }


    public static class ClapViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener

    {

        List<ClapItem> mItems;
        ClapItem clapItem;
        public ImageView imgThumbnail;
        public TextView tvClapName;
        private ItemClickListener clickListener;
       // private int REQUEST_CODE;

         public ClapViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.square_picture);
            tvClapName = (TextView) itemView.findViewById(R.id.tv_species);
            itemView.setTag(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
             itemView.setOnLongClickListener(this);

        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {

            clickListener.onClick(mItems, view, getAdapterPosition(), false);
        }
        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(mItems, view, getAdapterPosition(), true);
            return true;
        }




    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ClapViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item_square, viewGroup, false);

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
                  .into(holder.imgThumbnail);
    } else{
          holder.imgThumbnail.setImageResource(R.drawable.clapping1);
      }
        holder.imgThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        /*if (items.get(i).getId() > -1) {
            holder.tvClapName.setText(items.get(i).getClapName());
        } else {
            holder.tvClapName.setText("");
        }*/
        if (items.get(i).getClapName() != null)
        {
            holder.tvClapName.setText(items.get(i).getClapName());
        }else {
            holder.tvClapName.setText(null);
        }

       // holder.tvClapName.setText(items.get(i).getClapName());

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(List<ClapItem> list, final View view, final int position, boolean isLongClick) {
                if (isLongClick) {
                    // Toast.makeText(context, "#" + position + " - " + items.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
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
//if(mCallback != null)

                    ClapItem clap = (ClapItem) items.get(position);
//                    mCallback.onHandleSelection(position, items.get(position));
                    view.getContext();

                    Intent intent = new Intent(context, SecondPage.class);
                    //((Activity) context).startActivityForResult(intent,);
                    intent.putExtra(ClapItem.EXTRA_CONTENT_DETAIL, clap);
                    ClapItem readbackCi = intent.getParcelableExtra(ClapItem.EXTRA_CONTENT_DETAIL);
                    Log.d(TAG, "\n\n\t" + readbackCi.getClapName());
                    context.startActivity(intent);
                   // ((Activity) context).startActivityForResult(intent, REQUEST_CODE);

                }
            }
        });

    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ClapItem updatedItem = (ClapItem)data.getExtras().get("passed_item");
            // deal with the item yourself

        }
    }*/
    /*@Override
    public void onFinishDialog() {
        if (employeeListFragment != null) {
            employeeListFragment.updateView();
        }
    }*/
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




