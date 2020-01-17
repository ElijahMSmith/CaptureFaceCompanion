package eli.wearlab.capturefacecompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.vuzix.connectivity.sdk.Connectivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up background task to receive broadcast
        //  and create notification if found

        //Use broadcastreceiver in the same way as jitter alarm
    }

}
