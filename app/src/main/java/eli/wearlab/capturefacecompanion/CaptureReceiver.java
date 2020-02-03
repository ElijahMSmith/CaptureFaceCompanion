package eli.wearlab.capturefacecompanion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.vuzix.connectivity.sdk.Connectivity;

import static android.app.Activity.RESULT_OK;

public class CaptureReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isOrderedBroadcast() && Connectivity.get(context).verify(intent)) {
            Log.d("Debug", "Transmission received");
            setResultCode(RESULT_OK);
            setResultData("RECEIVED");

            sendNotification(context, "CaptureFaceCompanion", "Received broadcast from Vuzix!");
            byte[] imageData = intent.getByteArrayExtra("data");
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

            //NEED TO WORK ON HOW THIS SHOULD GET SET UP TO BEST FUNCTION AS I WANT.


            /*File photoFile;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("Debug", "Couldn't create file!");
                return;
            }
            //write the bytes in file
            FileOutputStream fo;
            try {
                fo = new FileOutputStream(photoFile);
                fo.write(byteData);
                fo.close();
            } catch (Exception e) {
                Log.e("Debug", "FILE IO EXCEPTION");
                return;
            }*/
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
                .setContentText(description)
                .setLights(Color.GREEN, 500, 2000)
                .setAutoCancel(true)
                .setVibrate(vibrate)
                .setContentTitle(name)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }

    /*@SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


        //Save a file: path for use with ACTION_VIEW intents
        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }*/
}
