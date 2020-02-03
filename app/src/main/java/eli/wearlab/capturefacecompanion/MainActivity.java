package eli.wearlab.capturefacecompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.vuzix.connectivity.sdk.Connectivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    protected static final String CHANNELID = "CAPTUREFACECOMPANION";
    protected static final String CAPTURE_FACE_FILTER = "eli.wearlab.captureface.SEND_IMAGE";
    private static byte[] lastData;
    protected static boolean isRunning = false;

    private BroadcastReceiver commandReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Debug", "Received communication from broadcast receiver!");
            Toast.makeText(getApplicationContext(), "Received intent in main!", Toast.LENGTH_SHORT).show();
            updateImageView();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(commandReceiver, new IntentFilter(CAPTURE_FACE_FILTER));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "CaptureFace Companion Notification Channel";
            String channelDescription = "Notification channel for CaptureFace Companion";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNELID, channelName, importance);
            channel.setDescription(channelDescription);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }

        isRunning = true;
        updateImageView();

        /*Set up activity to work with DB Helper
               Any inputs with empty descriptions/names, bring to user the image and let them either change/add the name/info of that person
               If no name/info yet, blank field. Otherwise, use the info they all ready have in those fields.

               Submit button when finished to put everything back in the database

               Also need option to search for a particular name
                    Don't need to search for those without names, since they will eventually be brought up by the activity for filling in info
                    Do try to show picture of person next to the name though
                        Use first picture provided

        */

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        isRunning = false;
    }

    @Override
    protected void onStart(){
        super.onStart();
        isRunning = true;
        updateImageView();
    }

    @Override
    protected void onStop(){
        super.onStop();
        isRunning = false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        isRunning = true;
        updateImageView();
    }

    @Override
    protected void onPause(){
        super.onPause();
        isRunning = false;
    }

    protected static void updateImageData(byte[] imageData){
        lastData = imageData;
    }

    private void updateImageView(){
        if(lastData == null)
            return;
        ImageView lastImage = findViewById(R.id.lastImage);
        Bitmap bitmap = BitmapFactory.decodeByteArray(lastData, 0, lastData.length);
        lastImage.setImageBitmap(bitmap);
        lastImage.setVisibility(View.VISIBLE);
    }

}
