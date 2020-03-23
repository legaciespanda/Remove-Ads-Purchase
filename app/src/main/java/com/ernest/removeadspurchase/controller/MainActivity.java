package com.ernest.removeadspurchase.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.developer.kalert.KAlertDialog;
import com.ernest.removeadspurchase.R;
import com.ernest.removeadspurchase.viewmodel.MainActivityViewModel;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import butterknife.BindView;

/**
 * Created: 23/03/2020
 * Author: Ernest Obot
 * */
public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler{

    //An instance of the MainActivityViewModel
    private MainActivityViewModel mViewModel;

    //Creating an instance of the BillingProcessor class
    BillingProcessor bp;
    private InterstitialAd interstitialAd;
    //adview view injection
    @BindView(R.id.adView)
    private AdView mAdView;
    @BindView(R.id.disableAdsBtn)
    Button purchase_btn;
    SharedPreferences sharedPreferences;
    IOSDialog iosDialog;
    public static boolean purduct_purchase=false;
    int counter = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        sharedPreferences = getSharedPreferences("disable_ad", MODE_PRIVATE);

        initializeBilling();
        purchase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disable_ads();
            }
        });

        //instance of InterstitialAd class
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        interstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        interstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        //create an instance of AdRequest and call the build method
        AdRequest adRequest = new AdRequest.Builder()
                    .build();
        // Load ads into Interstitial Ads
        interstitialAd.loadAd(adRequest);

        loadInterstitialAd();

    }

    private void Disable_ads() {
        /**
         * Before any usage we have to check for the in-app billing services availability.
         * In some older devices or chinese ones it may happen that Play Market is unavailable
         * or is deprecated and doesn't support in-app billing.
         * */
        boolean isAvailable = BillingProcessor.isIabServiceAvailable(this);
        if(isAvailable)
        /**
         * IMPORTANT: when you provide a payload, internally the library prepends a string to your payload.
         * For subscriptions, it prepends "subs:\<productId\>:", and for products, it prepends "inapp:\<productId\>:\<UUID\>:".
         * This is important to know if you do any validation on the payload returned from Google Play after a successful purchase.
         * */
            bp.purchase(this,mViewModel.getPRODUCT_SKU(),mViewModel.getDeveloperPayload() );
    }
    public void initializeBilling(){
        // intialize the billing process
        iosDialog = new IOSDialog.Builder(this)
                .setCancelable(false)
                .setSpinnerClockwise(false)
                .setMessageContentGravity(Gravity.END)
                .build();
        iosDialog.show();

        bp = new BillingProcessor(this, mViewModel.getGooglePlayConsolLicenseKey(), this);
        bp.initialize();
    }

    //overiding callback methods of the BillingProcessor class

    @Override
    public void onBillingInitialized() {
        /**
         * Called when BillingProcessor is  initialized and it's ready to purchase
         */
        // on billing intialize we will get the data from google
        if(bp.loadOwnedPurchasesFromGoogle()){
            // check user is already subscribe or not
            if(bp.isPurchased(mViewModel.getPRODUCT_SKU())){
                /** if already subscribe then we will change the static variable
                 * and call billingrpocessor release() method.
                 * */
                purduct_purchase = true;
                bp.release();
                iosDialog.cancel();
            }
            else {
                sharedPreferences.edit().putBoolean(mViewModel.getIspuduct_puchase(),false).commit();
                purduct_purchase = false;

                iosDialog.cancel();
            }
        }


    }
    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        /**
         * Called when requested PRODUCT ID was successfully purchased
         */
        // if the user is purchase successfully then we will store the data in local
        sharedPreferences.edit().putBoolean(mViewModel.getIspuduct_puchase(),true).commit();
        purduct_purchase = true;
        //always consume made purchase and allow to buy same product multiple times
        bp.consumePurchase(mViewModel.getPRODUCT_SKU());
        boolean fa = mViewModel.isENABLE_ADMOB_INTERSTITIAL_ADS() == false;
        showSuccessPurchase();


    }

    @Override
    public void onPurchaseHistoryRestored() {
        /**
         * Called when purchase history was restored and the list of all owned PRODUCT ID's
         * was loaded from Google Play
         */

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        /**
         * Called when some error occurred. See Constants class for more details
         *
         * Note - this includes handling the case where the user canceled the buy dialog:
         * errorCode = Constants.BILLING_RESPONSE_RESULT_USER_CANCELED
         */

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showSuccessPurchase(){
        new KAlertDialog(this, KAlertDialog.SUCCESS_TYPE)
                .setTitleText("Congratulations!")
                .setContentText("Purchase Successfully made! Ads are now disabled!")
                .show();
    }


    /**
     *  release your BillingProcessor instance!
     * */
    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }



    private void loadInterstitialAd() {
        if (mViewModel.isENABLE_ADMOB_INTERSTITIAL_ADS()) {
            MobileAds.initialize(this, getResources().getString(R.string.admob_app_id));
            interstitialAd = new InterstitialAd(this);
            interstitialAd.setAdUnitId(getResources().getString(R.string.admob_interstitial_unit_id));
            interstitialAd.loadAd(getAdRequest());
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });
        }
    }


    private void showInterstitialAd() {
        if (mViewModel.isENABLE_ADMOB_INTERSTITIAL_ADS()) {
            if (interstitialAd != null && interstitialAd.isLoaded()) {
                if (counter == mViewModel.getADMOB_INTERSTITIAL_ADS_INTERVAL()) {
                    interstitialAd.show();
                    counter = 1;
                } else {
                    counter++;
                }
            }
        }
    }

    public static AdRequest getAdRequest() {
        return new AdRequest.Builder()
               // .addNetworkExtrasBundle(AdMobAdapter.class, GDPR.getBundleAd(activity))
                .build();
    }
}
