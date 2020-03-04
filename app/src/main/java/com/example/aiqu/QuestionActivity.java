package com.example.aiqu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Log.d("===", "QuestionActivity");

        if (savedInstanceState == null) {
            Log.d("===", "savedInstanceState == null");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            QuestionSlidePageFragment fragment = new QuestionSlidePageFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
        Bundle bundle = getIntent().getExtras();
        Quiz quiz = (Quiz) bundle.getSerializable("quiz");
        Log.d("===", quiz.toString());

    }
}
