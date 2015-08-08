package com.hhpn.tata.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhpn.tata.R;
import com.hhpn.tata.util.Util;

/**
 * Created by hiantohendry on 8/7/15.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 1;

    private String mNavTitles[][]; // String Array to store the passed titles Value from MainActivity.java
    private Context mContext;
    public OnMyListItemClick listener;


    public static class ViewHolder extends RecyclerView.ViewHolder {

       public static TextView textView;
       public static ImageView imageView;
        public static CardView cardView;



        public ViewHolder(View itemView,int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
                textView = (TextView) itemView.findViewById(R.id.info_text); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.event_image);// Creating ImageView object with the id of ImageView from item_row.xml
                cardView= (CardView)itemView.findViewById(R.id.card_view);
        }
    }

    public interface OnMyListItemClick {
       public void onMyListItemClick(View itemView,int position);
    }

    public EventsAdapter(String Titles[][],Context context){ // EventsAdapter Constructor with titles and icons parameter
        mContext = context;
        mNavTitles = Titles;                //have seen earlier
    }


    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_event_adapter,parent,false); //Inflating the layout
            final ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view
            v.setTag(vhItem);

            return vhItem;
    }

    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder, int position) {

               holder.textView.setText(mNavTitles[position][0]);
               Glide.with(mContext)
                       .load(mNavTitles[position][1])
                       .into(holder.imageView);
        final int pos = position;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onMyListItemClick(view,pos);

                Util.Log("Position : "+pos);
                notifyDataSetChanged();
            }
        });


    }

    public void setListener(OnMyListItemClick listener) {
        this.listener = listener;
    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return mNavTitles.length; // the number of items in the list will be +1 the titles including the header view.
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

}