package project.tnguy190.calpoly.edu.detectivore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import project.tnguy190.calpoly.edu.detectivore.R;
import project.tnguy190.calpoly.edu.detectivore.helper.Utilities;

/**
 * Created by thuy on 2/26/17.
 */

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView dietsTV = (TextView) findViewById(R.id.profile_diets);
        Log.d(TAG, "diets: " + Utilities.getDiets(getApplicationContext()));
        dietsTV.setText(Utilities.getDiets(getApplicationContext()));

//        TextView restrictionsTV = (TextView) findViewById(R.id.profile_restrictions);
//        Log.d(TAG, "restrictions: " + Utilities.getRestrictions(getApplicationContext()));
//        restrictionsTV.setText(Utilities.getRestrictions(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile_menu_home_icon) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.profile_menu_settings_icon) {
            Intent intent = new Intent(getApplicationContext(), DietActivity.class);
            startActivity(intent);
        }
        else {
            return false;
        }

        return true;
    }

}
