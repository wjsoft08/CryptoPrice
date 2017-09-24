package com.example.causualninja08.cryptoprice;

import android.app.Activity;
import android.app.LauncherActivity;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by wjK on 8/09/2017.
 */

public class MoreInfo extends Activity {

    private ListItem listItem;

    View _container;

    public MoreInfo(View container) {
        _container = container;
    }

    public void dispMoreInfo(List<ListItem> listItems, int position) {
        listItem = listItems.get(position);


        TextView price = (TextView) _container.findViewById(R.id.textPrice);
        TextView price_btc = (TextView) _container.findViewById(R.id.textPrice_btc);


        TextView change24h = (TextView) _container.findViewById(R.id.textPercent_change_24h);
        TextView change7d = (TextView) _container.findViewById(R.id.textPercent_change_7d);
        TextView change1h = (TextView) _container.findViewById(R.id.textPercent_change_1h);

        String percentInfo24h = listItem.getPercent_change_24h();
        String changeNum24h = listItem.getColor24();
        percentInfoColour(listItems, position, change24h, percentInfo24h, changeNum24h);

        String percentInfo1h = listItem.getChange1h();
        String changeNum1h = listItem.getColor1h();
        percentInfoColour(listItems, position, change1h, percentInfo1h, changeNum1h);

        String changeNum7d = listItem.getColor7d();
        String percentInfo7d = listItem.getChange7d();
        percentInfoColour(listItems, position, change7d, percentInfo7d, changeNum7d);

        TextView rank = (TextView) _container.findViewById(R.id.textRank);
        TextView volume = (TextView) _container.findViewById(R.id.textVolume);
        TextView marketCap = (TextView) _container.findViewById(R.id.textMarketCap);
        TextView avSupply = (TextView) _container.findViewById(R.id.textAvailSupply);
        TextView totSupply = (TextView) _container.findViewById(R.id.textTotalSupply);



        ImageView logo = (ImageView) _container.findViewById(R.id.imageInfoView);


        Picasso.with(getBaseContext())
                .load(listItem.getImageView())
                .into(logo);


        price.setText(listItem.getPrice());
        price_btc.setText(listItem.getPrice_btc());

        rank.setText(listItem.getRank());
        volume.setText(listItem.getVolume());
        marketCap.setText(listItem.getMarketCap());
        avSupply.setText(listItem.getAvailSupply());
        totSupply.setText(listItem.getTotalSupply());
    }



    public void percentInfoColour(List<ListItem> listItems, int position, TextView changeTime, String percentInfo, String changeNum) {

        listItem = listItems.get(position);

        SpannableStringBuilder sb = new SpannableStringBuilder(percentInfo);


        if (percentInfo.contains("nul")){
            changeTime.setTextColor(Color.rgb(158, 158, 158));
        }
        else{
            double change24col = Double.parseDouble(changeNum);

            if(change24col >= 0){
                ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(8, 158, 8));
                sb.setSpan(fcs,25, percentInfo.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                changeTime.setText(sb);

            }else{
                ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(248, 8, 8));
                sb.setSpan(fcs,25, percentInfo.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                changeTime.setText(sb);
            }
        }
    }

}
