package project.tnguy190.calpoly.edu.detectivore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import project.tnguy190.calpoly.edu.detectivore.R;
import project.tnguy190.calpoly.edu.detectivore.helper.Firebase;
import project.tnguy190.calpoly.edu.detectivore.helper.Utilities;


public class HomeActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    protected static Firebase firebase;

//    private SQLiteHandler db;
//    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebase = new Firebase();

        if (firebase.mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            firebase.mUserId = firebase.mFirebaseUser.getUid();
        }

        // add ingredients to firebase
//        String[] files = {"allergens", "animalderived", "dairy", "gluten", "meat", "seafood", "vegan"};
//        String f;
//        String line;
//
//        try {
//
//            for (int i = 0; i < files.length; i++) {
//                f = files[i] + ".txt";
//                BufferedReader reader = new BufferedReader(new InputStreamReader(getApplicationContext().getAssets().open(f)));
//
//                while ((line = reader.readLine()) != null) {
//                    Log.d("FIREBASE", "add");
//                    firebase.mDatabase.child("ingredients").child(files[i]).push().setValue(line.toLowerCase());
//                }
//            }
//        } catch (IOException e) {
//
//        }

        ImageView camera = (ImageView) findViewById(R.id.camera_icon);
        ImageView userProfile = (ImageView) findViewById(R.id.user_profile_icon);
        ImageView settings = (ImageView) findViewById(R.id.settings_icon);

        camera.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
            }
        });

        userProfile.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DietActivity.class);
                startActivity(intent);
            }
        });

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);

        // write a temp script to add diets to database
        Utilities.getRestrictions(getApplicationContext());

//        // SqLite database handler
//        db = new SQLiteHandler(getApplicationContext());
//
//        // session manager
//        session = new SessionManager(getApplicationContext());
//
//        if (!session.isLoggedIn()) {
//            logoutUser();
//        }

        // Fetching user details from sqlite
//        HashMap<String, String> user = db.getUserDetails();

//        String name = user.get("name");
//        String email = user.get("email");

        // Displaying the user details on the screen
//        txtName.setText(name);
//        txtEmail.setText(email);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home_menu_logout) {
            firebase.mFirebaseAuth.signOut();
            loadLogInView();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
//    private void logoutUser() {
//        session.setLogin(false);
//
//        db.deleteUsers();
//
//        // Launching the login activity
//        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//    }

    public void loadLogInView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
