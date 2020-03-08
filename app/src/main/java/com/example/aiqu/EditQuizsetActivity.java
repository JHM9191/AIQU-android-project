package com.example.aiqu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EditQuizsetActivity extends AppCompatActivity {

    static String TAG = "===";

    EditText et_show_quizset_name;

    // recyclerview
    EditQuizsetActivity.QuestionAddedItemAdapter questionAddedItemAdapter;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.layout_show_quizeset);
        Bundle bundle = getIntent().getExtras();
        Quiz quiz = (Quiz) bundle.getSerializable("quiz");
        Log.d(TAG, "quiz.toString() : " + quiz.toString());
        recyclerView = findViewById(R.id.rv_questions_add);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        questionAddedItemAdapter = new QuestionAddedItemAdapter(quiz.getQuestionList());
        recyclerView.setAdapter(questionAddedItemAdapter);
        setupQuiz(quiz);
    }

    public void setupQuiz(Quiz quiz) {
        et_show_quizset_name = findViewById(R.id.et_show_quizset_name);
        et_show_quizset_name.setText(quiz.getName());
    }

    private class QuestionAddedItemAdapter extends RecyclerView.Adapter<QuestionAddedItemAdapter.ViewHolder> {

        ArrayList<Question> questions;


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_question_num;

            public ViewHolder(View itemView) {
                super(itemView);

                tv_question_num = itemView.findViewById(R.id.tv_question_added);
            }

        }

        public QuestionAddedItemAdapter(ArrayList<Question> questions) {
            this.questions = questions;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_question_added, parent, false);
            QuestionAddedItemAdapter.ViewHolder viewHolder = new QuestionAddedItemAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tv_question_num.setText("Q" + questions.get(position).getNumber());
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }

    }
}
