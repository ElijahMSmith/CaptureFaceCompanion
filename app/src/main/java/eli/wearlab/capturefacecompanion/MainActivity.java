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

}
