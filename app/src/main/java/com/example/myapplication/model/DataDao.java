package com.example.myapplication.model;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.view.MainActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DataDao {


    private final DatabaseReference databaseReference;
    private static long maxId = 0;


    public DataDao() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    maxId = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Task<Void> add(Data data){
        return databaseReference.child("data").child(String.valueOf(maxId + 1)).setValue(data);
    }

    public void update(String key, Object value){
        databaseReference.child(key).setValue(value);
    }

    public int getDeviceStatus(){
        ArrayList<Integer> data = new ArrayList<>();
        data.add(0);
        FirebaseDatabase.getInstance().getReference().child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.set(0,(Integer) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                data.set(0,0);
            }
        });
        return data.get(0);
    }

    public <T> ArrayList<Data> getAllData(ProgressBar progressBar, T content){
        ArrayList<Data> userArrayList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Data data = dataSnapshot.getValue(Data.class);
                    if (data != null) {
                        userArrayList.add(data);
                    }
                }
                if (progressBar != null){
                    progressBar.setVisibility(View.GONE);
                    if (content != null){
                        if (content instanceof ConstraintLayout){
                            ((ConstraintLayout) content).setVisibility(View.VISIBLE);
                        }else if (content instanceof FrameLayout){
                            ((FrameLayout) content).setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return userArrayList;

    }

    public void dataCheck(){
        DataCheckThread dataCheckThread = new DataCheckThread();
        dataCheckThread.start();
    }

    static class DataCheckThread extends Thread{
        @Override
        public void run() {
            DataDao dataDao = new DataDao();
            while (true) {
                try {
                    Thread.sleep(500);
                    Data.deviceStatus = dataDao.getDeviceStatus();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
