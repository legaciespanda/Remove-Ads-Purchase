package com.ernest.removeadspurchase.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.developer.kalert.KAlertDialog;
import com.ernest.removeadspurchase.R;
import com.ernest.removeadspurchase.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler{

    //An instance of the MainActivityViewModel
    private MainActivityViewModel mViewModel;

    //Creating an instance of the BillingProcessor class
    BillingProcessor bp;

    /**
     * Before any usage we have to check for the in-app billing services availability.
     * In some older devices or chinese ones it may happen that Play Market is unavailable
     * or is deprecated and doesn't support in-app billing.
     * */
    boolean isOneTimePurchaseSupported = bp.isOneTimePurchaseSupported();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        bp = new BillingProcessor(this, mViewModel.getGooglePlayConsolLicenseKey(), this);
        bp.initialize();

        /**
         * IMPORTANT: when you provide a payload, internally the library prepends a string to your payload.
         * For subscriptions, it prepends "subs:\<productId\>:", and for products, it prepends "inapp:\<productId\>:\<UUID\>:".
         * This is important to know if you do any validation on the payload returned from Google Play after a successful purchase.
         * */
        bp.purchase(this,mViewModel.getPRODUCT_SKU(),mViewModel.getDeveloperPayload() );
    }

    //overiding callback methods of the BillingProcessor class

    @Override
    public void onBillingInitialized() {
        /**
         * Called when BillingProcessor was initialized and it's ready to purchase
         */

    }
    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        /**
         * Called when requested PRODUCT ID was successfully purchased
         */

        //always consume made purchase and allow to buy same product multiple times
        bp.consumePurchase(mViewModel.getPRODUCT_SKU());
        // bought the premium upgrade! so we have to remove ads
        removeAds();
        showSuccessPurchase();


    }

    private void removeAds() {

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
}
