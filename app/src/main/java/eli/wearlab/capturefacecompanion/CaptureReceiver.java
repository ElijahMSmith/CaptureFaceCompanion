package eli.wearlab.capturefacecompanion;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.vuzix.connectivity.sdk.Connectivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class CaptureReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isOrderedBroadcast() && Connectivity.get(context).verify(intent)) {
            Log.d("Debug", "Transmission received in CaptureReceiver");
            setResultCode(RESULT_OK);

            sendNotification(context, "CaptureFaceCompanion", "Received broadcast from Vuzix!");
            byte[] imageData = intent.getByteArrayExtra("data");
            if(imageData == null)
                return;

            MainActivity.updateImageData(imageData);

            if(MainActivity.isRunning){
                //Send broadcast from here
                Intent i = new Intent();
                i.setAction(MainActivity.CAPTURE_FACE_FILTER);
                context.sendBroadcast(i);
            }

            Bundle extras = new Bundle();
            extras.putString("toast", "WE'VE DONE IT");
            setResultExtras(extras);

            File photoFile;
            try {
                photoFile = createImageFile(context);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("Debug", "Couldn't create file!");
                return;
            }
            //write the bytes in file
            FileOutputStream fo;
            try {
                fo = new FileOutputStream(photoFile);
                fo.write(imageData);
                fo.close();
            } catch (Exception e) {
                Log.e("Debug", "FILE IO EXCEPTION");
                return;
            }
            galleryAddPic(photoFile, context);
        }
    }

    //For now just temporary, could be used down the line for real purposes
    private void sendNotification(Context context, String name, String description){
        long[] vibrate = new long[2];
        vibrate[0] = 500;
        vibrate[1] = 1000;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNELID)
                .setContentTitle(name)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_stat_face)
                .setLights(Color.GREEN, 500, 2000)
                .setAutoCancel(true)
                .setVibrate(vibrate)
                .setContentTitle(name)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(context,4986274,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi); //Lets us click the notification and enter tha pp

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }

    @SuppressLint("SimpleDateFormat")
    private File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);


        //Save a file: path for use with ACTION_VIEW intents
        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }

    private void galleryAddPic(File photoFile, Context context) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photoFile);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
}
