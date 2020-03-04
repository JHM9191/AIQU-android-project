package com.example.aiqu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aiqu.Question;
import com.example.aiqu.QuestionActivity;
import com.example.aiqu.Quiz;
import com.example.aiqu.QuizsetItem;
import com.example.aiqu.R;
import com.example.aiqu.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ArrayList<QuizsetItem> lists = new ArrayList<>();
    QuizsetItemAdapter quizsetItemAdapter;
    LinearLayout container;
    ArrayList<Quiz> quizzes;

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
        ListView listView = root.findViewById(R.id.lv_quizset);
        getData();
        quizsetItemAdapter = new QuizsetItemAdapter(inflater, quizzes);
        listView.setAdapter(quizsetItemAdapter);
        return root;
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
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"quiz_summary\": {\n" +
                "\t\t\t\"unanswered\": [6,7,8,9,10],\n" +
                "\t\t\t\"answered\": [1,2,3,4,5],\n" +
                "\t\t\t\"correct\": [1,3,5],\n" +
                "\t\t\t\"incorrect\": [2,4]\n" +
                "\t\t}\n" +
                "\t}]\n" +
                "}");
        editor.commit();


        // SharedPreferences에 있는 data로 리스트뷰 값 세팅하기.
        SharedPreferences sp = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
        String data = "";
        if (sp != null && sp.contains("data")) {
            data = sp.getString("data", "");
            Toast.makeText(getActivity(), sp.getString("data", "None"), Toast.LENGTH_SHORT).show();
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

    class QuizsetItemAdapter extends BaseAdapter {

        ArrayList<Quiz> quizlist;

        LayoutInflater myInflater;

        public QuizsetItemAdapter() {

        }

        public QuizsetItemAdapter(LayoutInflater inflater, ArrayList<Quiz> quizlist) {
            this.myInflater = inflater;
            this.quizlist = quizlist;
        }

        @Override
        public int getCount() {
            return quizlist.size();
        }

        @Override
        public Object getItem(int position) {
            return quizlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.item_quizset, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.tv_item_quizset_name);
                holder.questions_count = (TextView) convertView.findViewById(R.id.tv_item_questions_count);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.name.setText(quizlist.get(position).getName() + "");
            holder.questions_count.setText(quizlist.get(position).getQuestionList().size() + " 문제");

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Question Layout 으로 가는 Intent 를 시작 해줘야함.
                    Intent intent = new Intent(getContext(), QuestionActivity.class);
//                    intent.putExtra("quizlist", quizlist.get(position));

                    Log.d("===", "position : " + position);
                    Log.d("===", quizlist.get(position).toString());

                    Quiz quiz = quizlist.get(position);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("quiz", quiz);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    static class ViewHolder {
        TextView name, questions_count;
    }


}