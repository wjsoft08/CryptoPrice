package com.example.causualninja08.cryptoprice;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by wjK on 2017-07-17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;
    private LayoutInflater layoutInflater;

    private Display _display;
    private static int screenWidth;
    private static int screenHeight;

    public ItemAdapter(List<ListItem> listItems, Context context, Display display) {
        this.listItems = listItems;
        this.context = context;
        _display = display;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ListItem listItem = listItems.get(position);

        final int pos = position;

        holder.textViewName.setText(listItem.getName());
        holder.textViewPrice.setText(listItem.getPrice());
        holder.textViewPrice_btc.setText(listItem.getPrice_btc());
        holder.textViewPercent_change_24h.setText(listItem.getPercent_change_24h());
        holder.textViewColor24.setText(listItem.getColor24());

        percentColour(holder, position);

        Picasso.with(context)
                .load(listItem.getImageView())
                .into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                screenSize(_display);
                layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View container = layoutInflater.inflate(R.layout.info,null);

                final PopupWindow popupWindow = new PopupWindow(container, (int)(screenWidth*0.7), (int)(screenHeight*0.8), true);

                MoreInfo coinInfo = new MoreInfo(container);
                coinInfo.dispMoreInfo(listItems, pos);


                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (int)(screenWidth*0.15), (int)(screenHeight*0.2));


                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        popupWindow.dismiss();
                        return false;
                    }
                });
            //    Toast.makeText(context, "hi", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public TextView textViewPrice;
        public TextView textViewPrice_btc;
        public TextView textViewPercent_change_24h;
        public ImageView imageView;
        public TextView textViewColor24;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewPrice = (TextView) itemView.findViewById(R.id.textViewPrice);
            textViewPrice_btc = (TextView) itemView.findViewById(R.id.textViewPrice_btc);
            textViewPercent_change_24h = (TextView) itemView.findViewById(R.id.textViewPercent_change_24h);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

            textViewColor24 = (TextView) itemView.findViewById(R.id.textViewColor24);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

        }
    }

    public void percentColour(ViewHolder holder, int position) {

        ListItem listItem = listItems.get(position);

        String change24Col = listItem.getPercent_change_24h().toString();
        SpannableStringBuilder sb = new SpannableStringBuilder(change24Col);


        if(listItem.getPercent_change_24h().contains("nul")){
            holder.textViewPercent_change_24h.setTextColor(Color.rgb(158, 158, 158));
        }
        else{
            double change24 = Double.parseDouble(listItem.getColor24());

            if(change24 >= 0){
             //   holder.textViewPercent_change_24h.setTextColor(Color.GREEN);
                ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(8, 158, 8));
                sb.setSpan(fcs,26, change24Col.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                holder.textViewPercent_change_24h.setText(sb);

            }else{
          //      holder.textViewPercent_change_24h.setTextColor(Color.RED);
                ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(248, 8, 8));
                sb.setSpan(fcs,26, change24Col.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                holder.textViewPercent_change_24h.setText(sb);
            }
        }
    }

    public void screenSize(Display display){
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

}