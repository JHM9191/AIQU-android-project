package com.example.aiqu;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.aiqu.common.view.SlidingTabLayout;

public class QuestionSlidePageFragment extends Fragment {

    //    static String TAG = "QuestionSlidePageFragment";
    static String TAG = "===";
    Quiz quiz;

    private SlidingTabLayout mSlidingTabLayout;

    private ViewPager mViewPager;

    public QuizResult[] quizResults;

    public void setData(Quiz quiz) {
        this.quiz = quiz;
        Log.d(TAG, quiz.toString());
        quizResults = new QuizResult[quiz.getQuestionList().size()];
        for (int i = 0; i < quizResults.length; i++) {
            QuizResult qr = new QuizResult(Integer.parseInt(quiz.getQuestionList().get(i).getNumber()), "unanswered", quiz.getQuestionList().get(i).getAnswer());
            quizResults[i] = qr;
        }
        Log.d(TAG, "quizResults.length : " + quizResults.length);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewPager = view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());


        mSlidingTabLayout = view.findViewById(R.id.sliding_tabs);

        // modify color of slidingtab
        SlidingTabLayout.TabColorizer tabColorizer = new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.RED;
            }

            @Override
            public int getDividerColor(int position) {
                return Color.parseColor("#EAEAEA");
            }
        };
        mSlidingTabLayout.setCustomTabColorizer(tabColorizer);
        mSlidingTabLayout.setViewPager(mViewPager);
    }


    class SamplePagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return quiz.getQuestionList().size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return object == view;
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "Q" + (position + 1);
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_question, container, false);
            container.addView(view);

            // Question
            final TextView question = view.findViewById(R.id.tv_question_question);
            question.setText(quiz.getQuestionList().get(position).getQuestion() + "");

            // Selections
            final TextView[] selections = {
                    view.findViewById(R.id.tv_question_selection1),
                    view.findViewById(R.id.tv_question_selection2),
                    view.findViewById(R.id.tv_question_selection3),
                    view.findViewById(R.id.tv_question_selection4),
                    view.findViewById(R.id.tv_question_selection5)
            };
            EditText input = null;
            for (int i = 0; i < quiz.getQuestionList().get(position).getSelections().length; i++) {
                Log.d(TAG, "selections length : " + quiz.getQuestionList().get(position).getSelections().length + "");
                final String sel = quiz.getQuestionList().get(position).getSelections()[i];
                if (sel.equals("null")) {
                    for (int j = 0; j < selections.length; j++) {
                        selections[j].setVisibility(View.GONE);
                        selections[j].setVisibility(View.INVISIBLE);
                    }
                    input = view.findViewById(R.id.et_question_input);
                    input.setVisibility(View.VISIBLE);
                    input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            quizResults[position].setChoice(s.toString());
                        }
                    });
                } else {
                    int num = i + 1;
                    selections[i].setText("(" + num + ")   " + sel);
                    final int finalI = i;
                    final int finalI1 = i;
                    selections[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(getContext(), sel, Toast.LENGTH_SHORT).show();

                            // change background color on selected choice
                            resetBackgroundColor(selections);
                            selections[finalI1].setBackgroundColor(Color.CYAN);

                            // save my choice for quiz result at the end.
                            quizResults[position].setChoice(sel);
                        }
                    });

                    selections[i].setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == (MotionEvent.ACTION_DOWN|MotionEvent.ACTION_HOVER_ENTER)){
                                selections[finalI1].setBackgroundColor(Color.CYAN);

                            }

                            if (event.getAction() == MotionEvent.ACTION_UP) {
//                                resetBackgroundColor(selections);
                                selections[finalI1].setBackgroundColor(Color.CYAN);
                            }
                            return false;
                        }
                    });
                }
            }

            // Submit button at the end of the question
            Button submit = view.findViewById(R.id.btn_question_submit);
            if (position == (getCount() - 1)) {
                submit.setVisibility(View.VISIBLE);
            }
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "quizResults.toString() : " + quizResults.toString());
                    int sum = 0;
                    for (int i = 0; i < quizResults.length; i++) {
                        Log.d(TAG, "" + quizResults[i].getNumber() + " " + quizResults[i].getChoice());
                        if (quizResults[i].getChoice().equals(quizResults[i].getAnswer())) {
                            sum += 1;
                        }
                    }
                    Toast.makeText(getContext(), "결과: " + sum + "/" +quizResults.length, Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }

        public void resetBackgroundColor(TextView[] selections) {
            for (int i = 0; i < selections.length; i++) {
                selections[i].setBackgroundColor(Color.parseColor("#EAEAEA"));
            }
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
