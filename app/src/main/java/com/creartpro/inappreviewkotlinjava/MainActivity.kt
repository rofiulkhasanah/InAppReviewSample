package com.creartpro.inappreviewkotlinjava

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.creartpro.inappreviewkotlinjava.databinding.ActivityMainBinding
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.*


class MainActivity : AppCompatActivity() {
    private var reviewInfo: ReviewInfo? = null
    private lateinit var manager: ReviewManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        manager = ReviewManagerFactory.create(this)
        reviewInfo()
        binding.btnRate.setOnClickListener { showRateDialog() }
    }

    private fun reviewInfo(){
        val managerInfoTask = manager.requestReviewFlow()
        managerInfoTask.addOnCompleteListener { task ->
            if(task.isSuccessful)
                reviewInfo = task.result
            else
                Toast.makeText(this, "Review failed to start", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showRateDialog() {
        if(reviewInfo != null){
            val flow = manager.launchReviewFlow(this, reviewInfo!!)
            flow.addOnCompleteListener { task ->
                Toast.makeText(this, "Rating is complete", Toast.LENGTH_SHORT).show()
            }
        }
    }


}