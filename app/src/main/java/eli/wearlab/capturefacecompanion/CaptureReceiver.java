package eli.wearlab.capturefacecompanion;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.vuzix.connectivity.sdk.Connectivity;

import static android.app.Activity.RESULT_OK;

public class CaptureReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isOrderedBroadcast() && Connectivity.get(context).verify(intent)) {
            setResultCode(RESULT_OK);
            setResultData("RECEIVED");

            /*Bundle extras = new Bundle();
            extras.putString("key", "value");
            setResultExtras(extras);*/

            sendNotification(context, "CaptureFaceCompanion", "Received broadcast from Vuzix!");
        }
    }

    private void sendNotification(Context context, String name, String description){
        long[] vibrate = new long[2];
        vibrate[0] = 500;
        vibrate[1] = 1000;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_received)
                .setLights(Color.GREEN, 500, 2000)
                .setAutoCancel(true)
                .setVibrate(vibrate)
                .setContentTitle(name);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
