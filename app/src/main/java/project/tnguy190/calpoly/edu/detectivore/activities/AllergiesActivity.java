package project.tnguy190.calpoly.edu.detectivore.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import project.tnguy190.calpoly.edu.detectivore.R;
import project.tnguy190.calpoly.edu.detectivore.helper.Utilities;

import static project.tnguy190.calpoly.edu.detectivore.activities.HomeActivity.firebase;

/**
 * Created by thuy on 2/26/17.
 */

public class AllergiesActivity extends AppCompatActivity {
    private static final String TAG = "AllergiesActivity";

    private static ArrayList<String> checked;
    private ArrayList<String> allergies;
    private AllergiesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergies);

        RecyclerView allergiesRV = (RecyclerView) findViewById(R.id.allergies_rv);
        allergiesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        checked = new ArrayList<>();
        allergies = new ArrayList<>();
        Utilities.initFoodItems(getApplicationContext(), "allergens.txt", allergies);

        adapter = new AllergiesActivity.AllergiesAdapter(allergies);
        allergiesRV.setAdapter(adapter);

        Button continueButton = (Button) findViewById(R.id.allergies_continue_button);

        continueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < checked.size(); i++) {
                            firebase.mDatabase.child("users").child(firebase.mUserId)
                                    .child("allergies").push().setValue(checked.get(i));
                        }

                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public static class AllergiesAdapter extends RecyclerView.Adapter<AllergiesActivity.AllergiesViewHolder> {
        private ArrayList<String> mList;

        public AllergiesAdapter(ArrayList<String> items) {
            this.mList = items;
        }

        @Override
        public int getItemViewType(int position) {
            return R.layout.list_entry_allergies;
        }

        @Override
        public AllergiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AllergiesViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
        }

        @Override
        public void onBindViewHolder(AllergiesViewHolder holder, int position) {
            holder.bind(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    public static class AllergiesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView allergiesTV;
        private CheckBox cb;

        public AllergiesViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            itemView.setOnClickListener(this);
            cb = (CheckBox) itemView.findViewById(R.id.allergies_checkbox);
            cb.setOnClickListener(this);
            allergiesTV = (TextView) itemView.findViewById(R.id.allergies_text);
        }

        public void bind(String r) {
            allergiesTV.setText(r);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() != R.id.allergies_checkbox)
                cb.setChecked(!cb.isChecked());

            if (cb.isChecked())
                checked.add(allergiesTV.getText().toString());
            else
                checked.remove(checked.indexOf(allergiesTV.getText().toString()));
        }
    }

}
