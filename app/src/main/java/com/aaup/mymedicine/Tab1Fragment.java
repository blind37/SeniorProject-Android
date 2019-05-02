package com.aaup.mymedicine;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Tab1Fragment extends Fragment {

    private ArrayList<Medicine> medList;


    private RecyclerView recyclerView;
    private DrugAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();

            Medicine thisItem = medList.get(position);
            Toast.makeText(getContext(), "You Clicked: " + thisItem.getDesc(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list1);

        setHasOptionsMenu(true);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        medList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Drugs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Medicine med = new Medicine(document.toObject(Medicine.class).getName(), document.toObject(Medicine.class).getDesc());
                                medList.add(med);
                                Log.d("TEST", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("TEST", "Error getting documents.", task.getException());
                        }
                        mAdapter = new DrugAdapter(medList);
                        recyclerView.setAdapter(mAdapter);

                        mAdapter.setOnItemClickListener(onItemClickListener);
                    }
                });

        return rootView;
    }

    // returns this fragment's adapter.
    public DrugAdapter getAdapter() {
        return mAdapter;
    }
}