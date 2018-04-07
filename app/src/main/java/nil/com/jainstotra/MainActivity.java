package nil.com.jainstotra;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer tumseLagiLaganMedia;
    private MediaPlayer namokarMedia;
    private MediaPlayer darshanPathMedia;
    private MediaPlayer currentRunningMedia;
    private Boolean exit = false;
    private AdView adView;

    BroadcastReceiver phonestatereceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String state = extras.getString(TelephonyManager.EXTRA_STATE);
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    if (getCurrentRunningMedia() != null){
                        currentRunningMedia = getCurrentRunningMedia();
                        currentRunningMedia.pause();
                    }else {
                        currentRunningMedia.pause();
                    }
                }
                else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    if (getCurrentRunningMedia() != null){
                        currentRunningMedia = getCurrentRunningMedia();
                        currentRunningMedia.pause();
                    }else {
                        currentRunningMedia.pause();
                    }
                }
                else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    currentRunningMedia.start();
                }

            }
        }
    };

    public MediaPlayer getCurrentRunningMedia() {
         if (namokarMedia.isPlaying()){
             return namokarMedia;
         }else if (tumseLagiLaganMedia.isPlaying()){
             return tumseLagiLaganMedia;
         }else if (darshanPathMedia.isPlaying()){
            return darshanPathMedia;
         }else{
             return null;
         }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        namokarMedia = MediaPlayer.create(MainActivity.this,R.raw.namokar);
        tumseLagiLaganMedia = MediaPlayer.create(MainActivity.this,R.raw.tum_se_lagi_lagan);
        darshanPathMedia = MediaPlayer.create(MainActivity.this,R.raw.darshanam_devasya);
        runNamokarMantra(null);
        MobileAds.initialize(this, "ca-app-pub-2818463437207396~2033448542");
        adView = findViewById(R.id.ad_view);

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
    }

    public  void runNamokarMantra(View view){
        checkForExistingRunningSong("Namokar");
        if(namokarMedia.isPlaying()){
            namokarMedia.stop();
        }else{
            namokarMedia = MediaPlayer.create(MainActivity.this,R.raw.namokar);
            namokarMedia.start();
            namokarMedia.setLooping(true);
        }
    }

    public void runTumseLagiLagan(View view){
        checkForExistingRunningSong("tumpselagilagan");
        if(tumseLagiLaganMedia.isPlaying()){
            tumseLagiLaganMedia.stop();
        }else{
            tumseLagiLaganMedia = MediaPlayer.create(MainActivity.this,R.raw.tum_se_lagi_lagan);
            tumseLagiLaganMedia.start();
            tumseLagiLaganMedia.setLooping(true);
        }
    }

    public void runDarshanPath(View view){
        checkForExistingRunningSong("darshanpath");
        if(darshanPathMedia.isPlaying()){
            darshanPathMedia.stop();
        }else{
            darshanPathMedia = MediaPlayer.create(MainActivity.this,R.raw.darshanam_devasya);
            darshanPathMedia.start();
            darshanPathMedia.setLooping(true);
        }
    }



    public void checkForExistingRunningSong(String mantra){
        if(namokarMedia.isPlaying() && !mantra.equals("Namokar")){
            namokarMedia.stop();
        }else if(tumseLagiLaganMedia.isPlaying() && !mantra.equals("tumpselagilagan")){
            tumseLagiLaganMedia.stop();
        }else if(darshanPathMedia.isPlaying() && !mantra.equals("darshanpath")){
            darshanPathMedia.stop();
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
            System.exit(1);
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }
}
