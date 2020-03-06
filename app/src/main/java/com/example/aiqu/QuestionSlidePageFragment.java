package com.example.aiqu;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    public void setData(Quiz quiz) {
        this.quiz = quiz;
        Log.d(TAG, quiz.toString());
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
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_question, container, false);
            container.addView(view);

            TextView question = view.findViewById(R.id.tv_question_question);
            final TextView[] selections = {
                    view.findViewById(R.id.tv_question_selection1),
                    view.findViewById(R.id.tv_question_selection2),
                    view.findViewById(R.id.tv_question_selection3),
                    view.findViewById(R.id.tv_question_selection4),
                    view.findViewById(R.id.tv_question_selection5)
            };

            EditText input = null;

            TextView answer = view.findViewById(R.id.tv_question_answer);

            question.setText(quiz.getQuestionList().get(position).getQuestion() + "");

            for (int i = 0; i < quiz.getQuestionList().get(position).getSelections().length; i++) {
                Log.d(TAG, "selections length : " + quiz.getQuestionList().get(position).getSelections().length + "");
                final String sel = quiz.getQuestionList().get(position).getSelections()[i];
                if (sel.equals("null")) {
                    for (int j = 0; j < selections.length; j++){
                        selections[j].setVisibility(View.GONE);
                        selections[j].setVisibility(View.INVISIBLE);

                    }
                    input = view.findViewById(R.id.et_question_input);
                    input.setVisibility(View.VISIBLE);
                    break;
                } else {
                    int num = i + 1;
                    selections[i].setText("(" + num + ")   " + sel);
                    final int finalI = i;
                    final int finalI1 = i;
                    selections[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), sel, Toast.LENGTH_SHORT).show();
                            resetColor(selections);
//                            selections[finalI1].setTextColor(Color.BLUE);
                            selections[finalI1].setBackgroundColor(Color.CYAN);
                        }
                    });
                }
            }

            answer.setText("답: " + quiz.getQuestionList().get(position).getAnswer() + "");


            // to be added.

            return view;
        }


        public void resetColor(TextView[] selections) {
            for (int i = 0; i < selections.length; i++) {
//                selections[i].setTextColor(Color.BLACK);
                selections[i].setBackgroundColor(Color.parseColor("#EAEAEA"));
            }
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    class SelectionAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
