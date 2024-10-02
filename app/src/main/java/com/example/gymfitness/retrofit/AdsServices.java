package com.example.gymfitness.retrofit;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdsServices {
    static InterstitialAd mInterstitialAd;
    public static void loadFullscreenADS(Context context)
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("checkkkkkk", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("checkkkkkk", loadAdError.toString());
                        mInterstitialAd = null;
                        loadFullscreenADS(context);
                    }
                });
    }

    public static void showADSFullscreen(Context context)
    {
        if (mInterstitialAd != null) {
            mInterstitialAd.show((Activity) context);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }
}
