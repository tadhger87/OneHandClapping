package com.tadhg.onehandclapping.adapter;

/**
 * Created by Tadhg on 13/11/2015.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.activity.SecondPage;
import com.tadhg.onehandclapping.db.ClapDAO;
import com.tadhg.onehandclapping.model.ClapItem;

import java.util.List;


public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ClapViewHolder>{

    ClapDAO clapDAO;
    private static final String TAG = "Falafel";

    public interface ItemClickListener {
        void onClick(List<ClapItem> list, View view, int position, boolean isLongClick);
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }


    private Context context;
    List<ClapItem> items;

    public GridAdapter(Context mContext, List<ClapItem> myClap){
        this.items = myClap;
        this.context = mContext;
    }


    public static class ClapViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener

    {

        List<ClapItem> mItems;
        ClapItem clapItem;
        public ImageView imgThumbnail;
        public TextView tvClapName;
        private ItemClickListener clickListener;

         public ClapViewHolder(View itemView) {
            super(itemView);
            //imgThumbnail = (ImageView) itemView.findViewById(R.id.picture);
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
        /*viewHolder.tvClapName.setText(nature.getClapName());
        viewHolder.imgThumbnail.setImageResource(nature.getClapThumbnail());
        viewHolder.imgThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        */
        holder.tvClapName.setText(items.get(i).getClapName());
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
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    ClapItem clap = (ClapItem) items.get(position);
                                    ClapDAO databaseHelper = new ClapDAO(view.getContext());
                                    databaseHelper.deleteClap(clap);
                                    remove(clap);
                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
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

                    Intent intent = new Intent(context, SecondPage.class);
                    intent.putExtra(ClapItem.EXTRA_CONTENT_DETAIL, clap);
                    ClapItem readbackCi = intent.getParcelableExtra(ClapItem.EXTRA_CONTENT_DETAIL);
                    Log.d(TAG, "\n\n\t" + readbackCi.getClapName() );
                    context.startActivity(intent);

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




