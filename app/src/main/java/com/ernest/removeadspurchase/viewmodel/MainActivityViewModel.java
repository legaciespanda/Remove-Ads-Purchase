package com.ernest.removeadspurchase.viewmodel;

import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    //Ads Configuration
    //set true to enable or set false to disable
    private  final boolean ENABLE_ADMOB_BANNER_ADS = false;
    private  final boolean ENABLE_ADMOB_INTERSTITIAL_ADS = true;
    public   final int ADMOB_INTERSTITIAL_ADS_INTERVAL = 3;

    //YOUR LICENSE KEY FROM GOOGLE PLAY CONSOLE HERE
    private  final String GOOGLE_PLAY_CONSOL_LICENSE_KEY = "GOOGLE_PLAY_CONSOL_LICENSE_KEY HERE";

    //YOUR SUBSCRIPTION ID FROM GOOGLE PLAY CONSOLE HERE
    private  final String GOOGLE_PLAY_CONSOL_SUBSCRIPTION_ID = "GOOGLE_PLAY_CONSOL_SUBSCRIPTION_ID HERE";

    //DEVELOPER PAYLOAD HERE
    private  final String DEVELOPER_PAYLOAD = "DEVELOPER_PAYLOAD HERE";

    //PRODUCT ID OR SKU
    private  final String PRODUCT_SKU = "PRODUCT_SKU HERE";

    private String ispuduct_puchase = "ispuduct_puchase";


    public  String getGooglePlayConsolLicenseKey() {
        return GOOGLE_PLAY_CONSOL_LICENSE_KEY;
    }

    public  String getGooglePlayConsolSubscriptionId() {
        return GOOGLE_PLAY_CONSOL_SUBSCRIPTION_ID;
    }

    public  String getDeveloperPayload() {
        return DEVELOPER_PAYLOAD;
    }

    public  boolean isEnableAdmobBannerAds() {
        return ENABLE_ADMOB_BANNER_ADS;
    }
    public String getPRODUCT_SKU() {
        return PRODUCT_SKU;
    }

    public String getIspuduct_puchase() {
        return ispuduct_puchase;
    }

    public boolean isENABLE_ADMOB_INTERSTITIAL_ADS() {
        return ENABLE_ADMOB_INTERSTITIAL_ADS;
    }

    public int getADMOB_INTERSTITIAL_ADS_INTERVAL() {
        return ADMOB_INTERSTITIAL_ADS_INTERVAL;
    }
}
