package project.tnguy190.calpoly.edu.detectivore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import project.tnguy190.calpoly.edu.detectivore.R;
import project.tnguy190.calpoly.edu.detectivore.helper.Utilities;

/**
 * Created by thuy on 2/26/17.
 */

public class ReportActivity extends AppCompatActivity {
    private String ingStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        StringBuilder canEat = new StringBuilder();
        StringBuilder cantEat = new StringBuilder();
        StringBuilder uncertain = new StringBuilder();

        Intent intent = getIntent();
        ingStr = intent.getStringExtra(CameraActivity.IngredientsExtra);

        String[] ingArr = Utilities.parseList(ingStr);
        for (int i = 0; i < ingArr.length; i++) {
            Log.d("ingreds", ingArr[i]);

            // classify
            if (Utilities.ingredientInFile(ingArr[i], "vegan.txt", getApplicationContext()))
                canEat.append(ingArr[i] + ", ");
            else if (Utilities.ingredientInFile(ingArr[i], "meat.txt", getApplicationContext()) &&
                    Utilities.ingredientInFile(ingArr[i], "animalderived.txt", getApplicationContext()))
                cantEat.append(ingArr[i] + ", ");
            else
                uncertain.append(ingArr[i] + ", ");

        }

        TextView can = (TextView) findViewById(R.id.can_eat);
        TextView cant = (TextView) findViewById(R.id.cant_eat);
        TextView uncert = (TextView) findViewById(R.id.uncertain);

        can.setText(canEat.toString());
        cant.setText(cantEat.toString());
        uncert.setText(uncertain.toString());
    }
}
