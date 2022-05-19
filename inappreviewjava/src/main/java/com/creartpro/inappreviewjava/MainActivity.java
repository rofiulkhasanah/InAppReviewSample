package com.creartpro.inappreviewjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.creartpro.inappreviewjava.databinding.ActivityMainBinding;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ReviewInfo reviewInfo;
    ReviewManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        manager = ReviewManagerFactory.create(this);
        reviewInfo();
        binding.btnRate.setOnClickListener( view -> showRateDialog());
    }
    private void reviewInfo(){
        Task<ReviewInfo> managerInfoTask = manager.requestReviewFlow();
        managerInfoTask.addOnCompleteListener ( task -> {
            if(task.isSuccessful())
                reviewInfo = task.getResult();
            else
                Log.e("MSG", "Review failed to start");
        });
    }

    private void showRateDialog() {
        if(reviewInfo != null){
            Task<Void> flow = manager.launchReviewFlow(this, reviewInfo);
            flow.addOnCompleteListener ( task -> Log.i("MSG", "Rating Success"));
        }
    }
}