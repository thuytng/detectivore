package project.tnguy190.calpoly.edu.detectivore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import project.tnguy190.calpoly.edu.detectivore.R;

import static project.tnguy190.calpoly.edu.detectivore.activities.HomeActivity.firebase;

/**
 * Created by thuy on 2/26/17.
 */

public class DietActivity extends AppCompatActivity {
    private static final String TAG = "DietActivity";

    private RadioButton veganRB;
    private RadioButton vegetarianRB;
    private RadioButton pescRB;
    private RadioButton noneRB;
    private RadioButton lastChecked;
    private CheckBox glutenFreeCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_selection);

        Button continueButton = (Button) findViewById(R.id.diet_continue_button);

        continueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Utilities.writeToDietFile(getApplicationContext(), checked);
//                        Log.d(TAG, checked.toString());

                        // add to firebase
                        String diet = lastChecked.getTag().toString();
                        firebase.mDatabase.child("users").child(firebase.mUserId).child("diets").push().setValue(diet);

                        if (glutenFreeCB.isChecked())
                            firebase.mDatabase.child("users").child(firebase.mUserId).child("diets").push().setValue("gluten-free");

                        Intent intent = new Intent(getApplicationContext(), AllergiesActivity.class);
                        startActivity(intent);
                    }
                }
        );

        veganRB = (RadioButton) findViewById(R.id.vegan_radiobutton);
        vegetarianRB = (RadioButton) findViewById(R.id.vegetarian_radiobutton);
        pescRB = (RadioButton) findViewById(R.id.pesc_radiobutton);
        noneRB = (RadioButton) findViewById(R.id.none_radiobutton);
        glutenFreeCB = (CheckBox) findViewById(R.id.gf_checkbox);

        lastChecked = noneRB;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.vegan_radiobutton:
                if (checked) {
                    lastChecked.setChecked(false);
                    veganRB.setChecked(true);
                    lastChecked = veganRB;
                }
                break;
            case R.id.vegetarian_radiobutton:
                if (checked) {
                    lastChecked.setChecked(false);
                    vegetarianRB.setChecked(true);
                    lastChecked = vegetarianRB;
                }
                break;
            case R.id.pesc_radiobutton:
                if (checked) {
                    lastChecked.setChecked(false);
                    pescRB.setChecked(true);
                    lastChecked = pescRB;
                }
                break;
            case R.id.none_radiobutton:
                if (checked) {
                    lastChecked.setChecked(false);
                    noneRB.setChecked(true);
                    lastChecked = noneRB;
                }
                break;
        }
    }

}