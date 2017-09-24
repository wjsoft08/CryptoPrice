package com.example.causualninja08.cryptoprice;

import java.text.DecimalFormat;

/**
 * Created by wjK on 5/09/2017.
 */

public class ListItem {

    private String name;
    private String price;
    private String price_btc;
    private String percent_change_24h;
    private String imageView;
    private String color24;
    private String rank;
    private String volume;
    private String marketCap;
    private String availSupply;
    private String totalSupply;
    private String change1h;
    private String change7d;
    private String color1h;
    private String color7d;

    public ListItem(String name, String price, String price_btc, String percent_change_24h, String imageView,
                    String color24, String rank, String volume, String marketCap, String availSupply,
                    String totalSupply, String change1h, String change7d, String color1h, String color7d) {
        this.name = name;
        this.price = price;
        this.price_btc = price_btc;
        this.percent_change_24h = percent_change_24h;
        this.imageView = imageView;
        this.color24 = color24;
        this.rank = rank;
        this.volume = volume;
        this.marketCap = marketCap;
        this.availSupply = availSupply;
        this.totalSupply = totalSupply;
        this.change1h = change1h;
        this.change7d = change7d;
        this.color1h = color1h;
        this.color7d = color7d;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPrice_btc() {
        return price_btc;
    }

    public String getPercent_change_24h() {

        return percent_change_24h;
    }

    public String getImageView() {
        return imageView;
    }

    public String getColor24() { return color24; }

    public String getRank() { return rank; }

    public String getVolume() {
        if(volume.contains("nul")){
            return "24h volume: $" + volume;
        }
        double amount = Double.parseDouble(volume);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String volumeSep = "24h volume: $" + formatter.format(amount);
        return volumeSep;
    }

    public String getMarketCap() {
        if(marketCap.contains("nul")){
            return "Market cap: $" +marketCap;
        }
        double amount = Double.parseDouble(marketCap);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String marketCapSep = "Market cap: $" + formatter.format(amount);
        return marketCapSep;
    }

    public String getAvailSupply() {
        if(availSupply.contains("nul")){
            return "Available supply: " + availSupply;
        }
        double amount = Double.parseDouble(availSupply);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String availSupplySep = "Available supply: " + formatter.format(amount);
        return availSupplySep;

    }

    public String getTotalSupply() {
        if(totalSupply.contains("nul")){
            return "Total supply: " + totalSupply;
        }
        double amount = Double.parseDouble(totalSupply);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String totalSupplySep = "Total supply: " + formatter.format(amount);
        return totalSupplySep;
    }

    public String getChange1h() { return change1h; }

    public String getChange7d() { return change7d; }

    public String getColor1h() { return color1h; }

    public String getColor7d() { return color7d; }


}