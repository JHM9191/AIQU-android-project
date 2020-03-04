package com.example.aiqu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.aiqu.common.view.SlidingTabLayout;

public class QuestionSlidePageFragment extends Fragment {


    private SlidingTabLayout mSlidingTabLayout;

    private ViewPager mViewPager;


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
            return 3;
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

            TextView num = view.findViewById(R.id.tv_question_num);
            TextView question = view.findViewById(R.id.tv_question_question);
            TextView selections = view.findViewById(R.id.tv_question_selections);
            TextView answer = view.findViewById(R.id.tv_question_answer);

            num.setText("문제 " + position + 1);
            // to be added.

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
