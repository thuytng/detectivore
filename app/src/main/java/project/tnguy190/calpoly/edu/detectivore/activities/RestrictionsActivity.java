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

public class RestrictionsActivity extends AppCompatActivity {

    private ArrayList<String> restrictions;
    private static ArrayList<String> checked;
    private RestrictionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restrictions);

        RecyclerView restrictionsRV = (RecyclerView) findViewById(R.id.restrictions_rv);
        restrictionsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        checked = new ArrayList<>();
        restrictions = new ArrayList<>();
        Utilities.initFoodItems(getApplicationContext(), "animalderived.txt", restrictions);

        adapter = new RestrictionAdapter(restrictions);
        restrictionsRV.setAdapter(adapter);

        Button continueButton = (Button) findViewById(R.id.restrictions_continue_button);

        continueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Utilities.writeToRestrictionsFile(getApplicationContext(), checked);
                        for (int i = 0; i < checked.size(); i++) {
//                            firebase.mDatabase.child(user)
                            firebase.mDatabase.child("users").child(firebase.mUserId)
                                    .child("restrictions").push().setValue(checked.get(i));
                        }
                        Intent intent = new Intent(getApplicationContext(), AllergiesActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public static class RestrictionAdapter extends RecyclerView.Adapter<RestrictionViewHolder> {
        private ArrayList<String> mList;

        public RestrictionAdapter(ArrayList<String> items) {
            this.mList = items;
        }

        @Override
        public int getItemViewType(int position) {
            return R.layout.list_entry_restrictions;
        }

        @Override
        public RestrictionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RestrictionViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
        }

        @Override
        public void onBindViewHolder(RestrictionViewHolder holder, int position) {
            holder.bind(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    public static class RestrictionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView restrictionTV;
        private CheckBox cb;

        public RestrictionViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            itemView.setOnClickListener(this);
            cb = (CheckBox) itemView.findViewById(R.id.restrictions_checkbox);
            cb.setOnClickListener(this);
            restrictionTV = (TextView) itemView.findViewById(R.id.restrictions_text);
        }

        public void bind(String r) {
            restrictionTV.setText(r);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() != R.id.restrictions_checkbox)
                cb.setChecked(!cb.isChecked());

            if (cb.isChecked())
                checked.add(restrictionTV.getText().toString());
            else
                checked.remove(checked.indexOf(restrictionTV.getText().toString()));
        }
    }
}
