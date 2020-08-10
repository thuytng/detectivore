package project.tnguy190.calpoly.edu.detectivore.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by thuy on 5/9/17.
 */

public class Firebase {
    public FirebaseAuth mFirebaseAuth;
    public FirebaseUser mFirebaseUser;

    public DatabaseReference mDatabase;
    public String mUserId;

    public Firebase() {
        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
}
