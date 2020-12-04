package com.example.speedtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.jignesh13.speedometer.SpeedoMeterView;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.SpeedTestTask;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

public class MainActivity extends AppCompatActivity {
    SpeedoMeterView speedoMeterView;
    TextView textView;

    Button button = findViewById(R.id.hızolc);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
speedoMeterView=findViewById(R.id.speedometerview);
        new SpeedTask().execute();


    }


}


class speedTestTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {

        final SpeedTestSocket speedTestSocket = new SpeedTestSocket();
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {
            @Override
            public void onCompletion(SpeedTestReport report) {
                Log.v("speedtest", "[COMPLETED] rate in octet/s : " + report.getTransferRateOctet());
                Log.v("speedtest", "[COMPLETED] rate in bit/s   : " + report.getTransferRateBit());
            }

            @Override
            public void onProgress(float percent, SpeedTestReport report) {
                final int hiz =  report.getTransferRateBit().intValue() / 100000;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Location speedoMeterView = null;
                        speedoMeterView.setSpeed(hiz);

                    }
                });

                Log.v("speedtest", "[PROGRESS] progress : " + percent + "%");
                Log.v("speedtest", "[PROGRESS] rate in octet/s : " + report.getTransferRateOctet());
                Log.v("speedtest", "[PROGRESS] rate in bit/s   : " + report.getTransferRateBit());


            }

            private void runOnUiThread(Runnable runnable) {
            }

            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {

            }
        });
        final SpeedTestSocket speedTestSocket1 = new SpeedTestSocket();
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {


            @Override
            public void onCompletion(SpeedTestReport report) {

            }

            @Override
            public void onProgress(float percent, SpeedTestReport report) {

            }

            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {

            }
        });
        speedTestSocket.startDownload("http://ipv4.ikoula.testdebit.info/1M.iso");


        return null;


    }
}





