package com.example.aiqu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aiqu.CreateQuizsetActivity;
import com.example.aiqu.EditQuizsetActivity;
import com.example.aiqu.Question;
import com.example.aiqu.QuestionActivity;
import com.example.aiqu.Quiz;
import com.example.aiqu.QuizsetItem;
import com.example.aiqu.R;
import com.example.aiqu.SwipeController;
import com.example.aiqu.SwipeControllerActions;
import com.example.aiqu.User;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ArrayList<QuizsetItem> lists = new ArrayList<>();
    QuizsetItemAdapter quizsetItemAdapter;
    LinearLayout container;
    ArrayList<Quiz> quizzes;
    SwipeController swipeController;

    static int count;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        getData();
        quizsetItemAdapter = new QuizsetItemAdapter(quizzes);

        // Swipe 기능 추가하기
        setupRecyclerView(root);


//        SwipeableRecyclerViewTouchListener swipeTouchListener =
//                new SwipeableRecyclerViewTouchListener(recyclerView, new SwipeableRecyclerViewTouchListener.SwipeListener() {
//                    @Override
//                    public boolean canSwipeLeft(int position) {
//                        return true;
//                    }
//
//                    @Override
//                    public boolean canSwipeRight(int position) {
//                        return true;
//                    }
//
//                    @Override
//                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] ints) {
//
//                    }
//
//                    @Override
//                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] ints) {
//
//                    }
//                });
//
//        recyclerView.addOnItemTouchListener(swipeTouchListener);

        return root;
    }

    private void setupRecyclerView(View root) {

        RecyclerView recyclerView = root.findViewById(R.id.rv_quizset);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(quizsetItemAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                Toast.makeText(getContext(), quizsetItemAdapter.quizlist.get(position).getName() + " is removed", Toast.LENGTH_SHORT).show();
                quizsetItemAdapter.quizlist.remove(position);
                quizsetItemAdapter.notifyItemRemoved(position);
                quizsetItemAdapter.notifyItemRangeRemoved(position, quizsetItemAdapter.getItemCount());

            }

            @Override
            public void onLeftClicked(int position) {
                Toast.makeText(getContext(), quizsetItemAdapter.quizlist.get(position).getName() + " in edit mode", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), EditQuizsetActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("quiz", quizsetItemAdapter.quizlist.get(position));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }


    private void getData() {
        // SharedPreferences에 퀴즈셋 정보 저장하기.
        SharedPreferences sp0 = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp0.edit();
        editor.putString("data", "{\n" +
                "\t\"user\": {\n" +
                "\t\t \"id\": \"id01\",\n" +
                "\t\t \"pw\": \"pw01\",\n" +
                "\t\t \"name\": \"name01\",\n" +
                "\t\t \"email\": \"id01@naver.com\"\n" +
                "\t},\t\n" +
                "\t\"quizlist\": [{\n" +
                "\t\t\"quiz_name\": \"math101\",\n" +
                "\t\t\"quiz_set\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 1,\n" +
                "\t\t\t\t\"question\": \"What is 1+1?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"1\",\n" +
                "\t\t\t\t\t\"2\",\n" +
                "\t\t\t\t\t\"3\",\n" +
                "\t\t\t\t\t\"4\",\n" +
                "\t\t\t\t\t\"5\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 2,\n" +
                "\t\t\t\t\"question\": \"What is your 2 times 2?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"4\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"quiz_name\": \"science101\",\n" +
                "\t\t\"quiz_set\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 1,\n" +
                "\t\t\t\t\"question\": \"What is atom?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"aa\",\n" +
                "\t\t\t\t\t\"bb\",\n" +
                "\t\t\t\t\t\"cc\",\n" +
                "\t\t\t\t\t\"dd\",\n" +
                "\t\t\t\t\t\"ee\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 2,\n" +
                "\t\t\t\t\"question\": \"What is your name?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"aa\",\n" +
                "\t\t\t\t\t\"bb\",\n" +
                "\t\t\t\t\t\"cc\",\n" +
                "\t\t\t\t\t\"dd\",\n" +
                "\t\t\t\t\t\"ee\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 3,\n" +
                "\t\t\t\t\"question\": \"What is my name?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 4,\n" +
                "\t\t\t\t\"question\": \"What is atom?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"aa\",\n" +
                "\t\t\t\t\t\"bb\",\n" +
                "\t\t\t\t\t\"cc\",\n" +
                "\t\t\t\t\t\"dd\",\n" +
                "\t\t\t\t\t\"ee\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 5,\n" +
                "\t\t\t\t\"question\": \"What is your name?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"aa\",\n" +
                "\t\t\t\t\t\"bb\",\n" +
                "\t\t\t\t\t\"cc\",\n" +
                "\t\t\t\t\t\"dd\",\n" +
                "\t\t\t\t\t\"ee\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 6,\n" +
                "\t\t\t\t\"question\": \"What is my name?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 7,\n" +
                "\t\t\t\t\"question\": \"What is atom?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"aa\",\n" +
                "\t\t\t\t\t\"bb\",\n" +
                "\t\t\t\t\t\"cc\",\n" +
                "\t\t\t\t\t\"dd\",\n" +
                "\t\t\t\t\t\"ee\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 8,\n" +
                "\t\t\t\t\"question\": \"What is your name?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"aa\",\n" +
                "\t\t\t\t\t\"bb\",\n" +
                "\t\t\t\t\t\"cc\",\n" +
                "\t\t\t\t\t\"dd\",\n" +
                "\t\t\t\t\t\"ee\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 9,\n" +
                "\t\t\t\t\"question\": \"What is my name?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 10,\n" +
                "\t\t\t\t\"question\": \"What is atom?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"aa\",\n" +
                "\t\t\t\t\t\"bb\",\n" +
                "\t\t\t\t\t\"cc\",\n" +
                "\t\t\t\t\t\"dd\",\n" +
                "\t\t\t\t\t\"ee\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 11,\n" +
                "\t\t\t\t\"question\": \"What is your name?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"aa\",\n" +
                "\t\t\t\t\t\"bb\",\n" +
                "\t\t\t\t\t\"cc\",\n" +
                "\t\t\t\t\t\"dd\",\n" +
                "\t\t\t\t\t\"ee\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 12,\n" +
                "\t\t\t\t\"question\": \"What is my name?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 13,\n" +
                "\t\t\t\t\"question\": \"What is atom?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"aa\",\n" +
                "\t\t\t\t\t\"bb\",\n" +
                "\t\t\t\t\t\"cc\",\n" +
                "\t\t\t\t\t\"dd\",\n" +
                "\t\t\t\t\t\"ee\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 14,\n" +
                "\t\t\t\t\"question\": \"What is your name?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"aa\",\n" +
                "\t\t\t\t\t\"bb\",\n" +
                "\t\t\t\t\t\"cc\",\n" +
                "\t\t\t\t\t\"dd\",\n" +
                "\t\t\t\t\t\"ee\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 15,\n" +
                "\t\t\t\t\"question\": \"What is my name?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"aa\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"quiz_name\": \"math101\",\n" +
                "\t\t\"quiz_set\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 1,\n" +
                "\t\t\t\t\"question\": \"What is 1+1?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"1\",\n" +
                "\t\t\t\t\t\"2\",\n" +
                "\t\t\t\t\t\"3\",\n" +
                "\t\t\t\t\t\"4\",\n" +
                "\t\t\t\t\t\"5\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 2,\n" +
                "\t\t\t\t\"question\": \"What is your 2 times 2?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"4\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"quiz_name\": \"math101\",\n" +
                "\t\t\"quiz_set\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 1,\n" +
                "\t\t\t\t\"question\": \"What is 1+1?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"1\",\n" +
                "\t\t\t\t\t\"2\",\n" +
                "\t\t\t\t\t\"3\",\n" +
                "\t\t\t\t\t\"4\",\n" +
                "\t\t\t\t\t\"5\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 2,\n" +
                "\t\t\t\t\"question\": \"What is your 2 times 2?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"4\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"quiz_name\": \"math101\",\n" +
                "\t\t\"quiz_set\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 1,\n" +
                "\t\t\t\t\"question\": \"What is 1+1?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"1\",\n" +
                "\t\t\t\t\t\"2\",\n" +
                "\t\t\t\t\t\"3\",\n" +
                "\t\t\t\t\t\"4\",\n" +
                "\t\t\t\t\t\"5\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 2,\n" +
                "\t\t\t\t\"question\": \"What is your 2 times 2?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"4\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"quiz_name\": \"math101\",\n" +
                "\t\t\"quiz_set\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 1,\n" +
                "\t\t\t\t\"question\": \"What is 1+1?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"1\",\n" +
                "\t\t\t\t\t\"2\",\n" +
                "\t\t\t\t\t\"3\",\n" +
                "\t\t\t\t\t\"4\",\n" +
                "\t\t\t\t\t\"5\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 2,\n" +
                "\t\t\t\t\"question\": \"What is your 2 times 2?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"4\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"quiz_name\": \"math101\",\n" +
                "\t\t\"quiz_set\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 1,\n" +
                "\t\t\t\t\"question\": \"What is 1+1?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"1\",\n" +
                "\t\t\t\t\t\"2\",\n" +
                "\t\t\t\t\t\"3\",\n" +
                "\t\t\t\t\t\"4\",\n" +
                "\t\t\t\t\t\"5\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 2,\n" +
                "\t\t\t\t\"question\": \"What is your 2 times 2?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"4\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"quiz_name\": \"math101\",\n" +
                "\t\t\"quiz_set\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 1,\n" +
                "\t\t\t\t\"question\": \"What is 1+1?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"1\",\n" +
                "\t\t\t\t\t\"2\",\n" +
                "\t\t\t\t\t\"3\",\n" +
                "\t\t\t\t\t\"4\",\n" +
                "\t\t\t\t\t\"5\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 2,\n" +
                "\t\t\t\t\"question\": \"What is your 2 times 2?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"4\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"quiz_name\": \"math101\",\n" +
                "\t\t\"quiz_set\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 1,\n" +
                "\t\t\t\t\"question\": \"What is 1+1?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"1\",\n" +
                "\t\t\t\t\t\"2\",\n" +
                "\t\t\t\t\t\"3\",\n" +
                "\t\t\t\t\t\"4\",\n" +
                "\t\t\t\t\t\"5\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 2,\n" +
                "\t\t\t\t\"question\": \"What is your 2 times 2?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"4\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"quiz_name\": \"math101\",\n" +
                "\t\t\"quiz_set\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 1,\n" +
                "\t\t\t\t\"question\": \"What is 1+1?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"1\",\n" +
                "\t\t\t\t\t\"2\",\n" +
                "\t\t\t\t\t\"3\",\n" +
                "\t\t\t\t\t\"4\",\n" +
                "\t\t\t\t\t\"5\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 2,\n" +
                "\t\t\t\t\"question\": \"What is your 2 times 2?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"4\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"quiz_name\": \"math101\",\n" +
                "\t\t\"quiz_set\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 1,\n" +
                "\t\t\t\t\"question\": \"What is 1+1?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"1\",\n" +
                "\t\t\t\t\t\"2\",\n" +
                "\t\t\t\t\t\"3\",\n" +
                "\t\t\t\t\t\"4\",\n" +
                "\t\t\t\t\t\"5\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 2,\n" +
                "\t\t\t\t\"question\": \"What is your 2 times 2?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"4\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"quiz_name\": \"math101\",\n" +
                "\t\t\"quiz_set\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 1,\n" +
                "\t\t\t\t\"question\": \"What is 1+1?\",\n" +
                "\t\t\t\t\"question_type\": \"multiplechoice\",\n" +
                "\t\t\t\t\"selections\": [\n" +
                "\t\t\t\t\t\"1\",\n" +
                "\t\t\t\t\t\"2\",\n" +
                "\t\t\t\t\t\"3\",\n" +
                "\t\t\t\t\t\"4\",\n" +
                "\t\t\t\t\t\"5\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"answer\": \"2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"#\": 2,\n" +
                "\t\t\t\t\"question\": \"What is your 2 times 2?\",\n" +
                "\t\t\t\t\"question_type\": \"shortanswer\",\n" +
                "\t\t\t\t\"selections\": [null],\n" +
                "\t\t\t\t\"answer\": \"4\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\t]\n" +
                "}");
        editor.commit();


        // SharedPreferences에 있는 data로 리스트뷰 값 세팅하기.
        SharedPreferences sp = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
        String data = "";
        if (sp != null && sp.contains("data")) {
            data = sp.getString("data", "");
//            Toast.makeText(getActivity(), sp.getString("data", "None"), Toast.LENGTH_SHORT).show();
            Log.d("===", sp.getString("data", "None"));
        }

        try {
            JSONObject jo_userInfo = new JSONObject(data);
            User user = new User();
            ArrayList<Quiz> quizlist = new ArrayList<>();
            // user
            JSONObject jo_user = jo_userInfo.getJSONObject("user");
            user.setId(jo_user.getString("id"));
            user.setPw(jo_user.getString("pw"));
            user.setName(jo_user.getString("name"));
            user.setEmail(jo_user.getString("email"));
            Log.d("===", user.toString());

            // quizlist
            quizzes = new ArrayList<>();
            JSONArray ja_quizlist = jo_userInfo.getJSONArray("quizlist");
            Log.d("===", "ja_quizlist.length(): " + ja_quizlist.length());
            for (int j = 0; j < ja_quizlist.length(); j++) {
                JSONObject jo_quiz = ja_quizlist.getJSONObject(j);

                //
                String quiz_name = jo_quiz.getString("quiz_name");

                //
                JSONArray ja_quiz_set = jo_quiz.getJSONArray("quiz_set");
                ArrayList<Question> question_list = new ArrayList<>();
                for (int k = 0; k < ja_quiz_set.length(); k++) {
                    JSONObject jo_question = ja_quiz_set.getJSONObject(k);

                    String n = jo_question.getString("#");
                    String q = jo_question.getString("question");
                    String qt = jo_question.getString("question_type");

//                        JSONObject jo_selections = (JSONObject) jo_question.get("selections");
//                        String strrr = jo_question.getString("selections");
//                        JSONArray jasdf = new JSONArray(strrr);

//                        Log.d("===", "string : " + strrr);
//                        String str = jo_question.getJSONObject("selections").toString();
//                        Log.d("===", "str : " + str);
                    JSONArray ja_selections = jo_question.getJSONArray("selections");
//                        JSONArray ja_selections =  (JSONArray) jo_question.getJSONArray("selections");

                    String[] selections = new String[ja_selections.length()];
                    for (int p = 0; p < ja_selections.length(); p++) {
                        selections[p] = ja_selections.get(p) + "";
                    }
                    String answer = jo_question.getString("answer");
                    Question question = new Question(n, q, qt, selections, answer);
                    question_list.add(question);
                }

                //
                JSONObject quiz_summary = jo_quiz.getJSONObject("quiz_summary");


                Quiz quiz = new Quiz(quiz_name, question_list, null);
                quizzes.add(quiz);

                Log.d("===", quiz.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        count += 1;
//
//        for (int i = 0; i < count; i++) {
//            QuizsetItem quizset = new QuizsetItem();
//            quizset.setQuizset_name("hello" + i);
//            quizset.setSubject("math" + i);
//            lists.add(quizset);
//            Log.d("===", lists.get(i).toString() + "");
//        }
    }

    class QuizsetItemAdapter extends RecyclerView.Adapter<QuizsetItemAdapter.ViewHolder> {

        ArrayList<Quiz> quizlist;


        // ViewHolder class that saves itemViews
        // 아이템 뷰를 저장하는 뷰홀더 클래스
        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView name, questions_count;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                // reference to view instances
                // 뷰 객체에 대한 참조
                name = itemView.findViewById(R.id.tv_item_quizset_name);
                questions_count = itemView.findViewById(R.id.tv_item_questions_count);

            }
        }

        // hand-over data using constructor
        // 생성자에서 데이터 리스트 객체를 전달받는다.
        public QuizsetItemAdapter(ArrayList<Quiz> quizlist) {
            this.quizlist = quizlist;
        }

        // return the viewHolder instance for itemView
        // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴한다.
        @NonNull
        @Override
        public QuizsetItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_quizset, parent, false);
            QuizsetItemAdapter.ViewHolder viewHolder = new QuizsetItemAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.name.setText(quizlist.get(position).getName() + "");
            holder.questions_count.setText(quizlist.get(position).getQuestionList().size() + " 문제");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Question Layout 으로 가는 Intent 를 시작 해줘야함.
                    Intent intent = new Intent(getContext(), QuestionActivity.class);
                    Log.d("===", "position : " + position);
                    Log.d("===", quizlist.get(position).toString());
                    Quiz quiz = quizlist.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("quiz", quiz);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return quizlist.size();
        }
    }
}