package com.example.aiqu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreateQuizsetActivity extends AppCompatActivity {
    //    final static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TestLog/logfile.txt";
//    final static String filePath = "/etc/public.libraries.txt";
//    TextView textView;
//    TextView detail;

    static String TAG = "===";

    EditText et_filepath;
    EditText et_quizset_name;
    EditText et_subject;

    Intent fileIntent;


    // create quizset
    int count = 0;
    View layout_selections;
    View layout_shortanswer;
    Button bt_manual_add_question1, bt_manual_add_question2;
    EditText et_manual_quizset_name;
    EditText et_manual_selections_question, et_manual_selections_answer;
    RadioGroup radioGroup;
    RadioButton rb_question_selection1, rb_question_selection2, rb_question_selection3, rb_question_selection4, rb_question_selection5;
    EditText et_question_selection1, et_question_selection2, et_question_selection3, et_question_selection4, et_question_selection5;
    EditText et_manual_shortanswer_question, et_manual_shortanswer_answer;
    ArrayList<Question> questions = new ArrayList<>();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquizset);
        // ActionBar 숨기기
        getSupportActionBar().hide();
//        Log.i("-----", filePath);


    }


    // 사진으로 퀴즈셋 만들기 버튼 클릭 이벤트
    public void createQuizsetByImages(View v) {
        if (v.getId() == R.id.bt_choose_file) {
            fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
            fileIntent.setType("*/*");
            startActivityForResult(fileIntent, 100);
        } else if (v.getId() == R.id.bt_register_quizset) {
            et_quizset_name = findViewById(R.id.et_quizset_name);
            et_subject = findViewById(R.id.et_subject);
            Log.d("---", et_filepath.getText() + " " + et_quizset_name.getText() + " " + et_subject.getText());
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

            intent.putExtra("name", et_quizset_name.getText() + "");
            intent.putExtra("subject", et_subject.getText() + "");

            // SharedPreferences에 퀴즈셋 정보 저장하기.
            SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
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

            startActivity(intent);
        }
    }

    // 직접 퀴즈셋 만들기 버튼 클릭 이벤트
    public void createQuizsetManually(View v) {
        switch (v.getId()) {
            case R.id.bt_create_quizset_manually:
                setContentView(R.layout.layout_createquizeset_manually_fillup);
                layout_selections = (LinearLayout) findViewById(R.id.layout_selections);
                layout_shortanswer = (LinearLayout) findViewById(R.id.layout_shortanswer);
                bt_manual_add_question1 = findViewById(R.id.bt_manual_add_question1);
                bt_manual_add_question2 = findViewById(R.id.bt_manual_add_question2);
                layout_selections.setVisibility(View.INVISIBLE);
                layout_shortanswer.setVisibility(View.INVISIBLE);
                bt_manual_add_question1.setVisibility(View.INVISIBLE);
                bt_manual_add_question2.setVisibility(View.INVISIBLE);
                break;
            case R.id.bt_manual_question_type_w_selections:
                layout_selections.setVisibility(View.VISIBLE);
                layout_shortanswer.setVisibility(View.INVISIBLE);
                bt_manual_add_question1.setVisibility(View.VISIBLE);
                bt_manual_add_question2.setVisibility(View.INVISIBLE);

                et_manual_selections_question = findViewById(R.id.et_manual_selections_question);

                radioGroup = findViewById(R.id.rg_question_selections);

                rb_question_selection1 = findViewById(R.id.rb_question_selection1);
                rb_question_selection2 = findViewById(R.id.rb_question_selection2);
                rb_question_selection3 = findViewById(R.id.rb_question_selection3);
                rb_question_selection4 = findViewById(R.id.rb_question_selection4);
                rb_question_selection5 = findViewById(R.id.rb_question_selection5);

                et_question_selection1 = findViewById(R.id.et_question_selection1);
                et_question_selection2 = findViewById(R.id.et_question_selection2);
                et_question_selection3 = findViewById(R.id.et_question_selection3);
                et_question_selection4 = findViewById(R.id.et_question_selection4);
                et_question_selection5 = findViewById(R.id.et_question_selection5);


                RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        EditText selection = null;
                        RadioButton radioButtonChecked = null;
                        et_manual_selections_answer.setText("");
                        if (checkedId == R.id.rb_question_selection1) {
                            radioButtonChecked = rb_question_selection1;
                            selection = et_question_selection1;
                        } else if (checkedId == R.id.rb_question_selection2) {
                            radioButtonChecked = rb_question_selection2;
                            selection = et_question_selection2;
                        } else if (checkedId == R.id.rb_question_selection3) {
                            radioButtonChecked = rb_question_selection3;
                            selection = et_question_selection3;
                        } else if (checkedId == R.id.rb_question_selection4) {
                            radioButtonChecked = rb_question_selection4;
                            selection = et_question_selection4;
                        } else if (checkedId == R.id.rb_question_selection5) {
                            radioButtonChecked = rb_question_selection5;
                            selection = et_question_selection5;
                        }
                        if (selection.getText().toString() == null | selection.getText().toString().equals("")) {
                            radioButtonChecked.setChecked(false);
                            selection.requestFocus();
                            Toast.makeText(CreateQuizsetActivity.this, "보기를 입력해주세요", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        et_manual_selections_answer.setText(selection.getText().toString());
                    }
                };
                radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);

                et_manual_selections_answer = findViewById(R.id.et_manual_selections_answer);

                bt_manual_add_question1.setOnClickListener(new View.OnClickListener() {
                    public String[] getSelectionsArray() {
                        String[] selections = null;

                        String selection1 = et_question_selection1.getText().toString();
                        String selection2 = et_question_selection2.getText().toString();
                        String selection3 = et_question_selection3.getText().toString();
                        String selection4 = et_question_selection4.getText().toString();
                        String selection5 = et_question_selection5.getText().toString();

                        if (selection5 == null | selection5.equals("")) {
                            selections = new String[]{selection1, selection2, selection3, selection4};
                        } else {
                            selections = new String[]{selection1, selection2, selection3, selection4, selection5};
                        }
                        return selections;
                    }

                    @Override
                    public void onClick(View v) {
                        String question_type = "selections";
                        String question_name = et_manual_selections_question.getText().toString();

                        String[] selections = getSelectionsArray();
                        String answer = et_manual_selections_answer.getText().toString();
                        count++;
                        Question question = new Question(count + "", question_name, question_type, selections, answer);
                        questions.add(question);
                        Log.d(TAG, "questions.toString() : " + questions.toString());
                        et_manual_selections_question.setText("");
                        et_question_selection1.setText("");
                        et_question_selection2.setText("");
                        et_question_selection3.setText("");
                        et_question_selection4.setText("");
                        et_question_selection5.setText("");
                        rb_question_selection1.setChecked(false);
                        rb_question_selection2.setChecked(false);
                        rb_question_selection3.setChecked(false);
                        rb_question_selection4.setChecked(false);
                        rb_question_selection5.setChecked(false);
                        et_manual_selections_answer.setText("");
                    }
                });
                break;
            case R.id.bt_manual_question_type_shortanswer:
                layout_shortanswer.setVisibility(View.VISIBLE);
                layout_selections.setVisibility(View.INVISIBLE);
                bt_manual_add_question2.setVisibility(View.VISIBLE);
                bt_manual_add_question1.setVisibility(View.INVISIBLE);

                et_manual_quizset_name = findViewById(R.id.et_quizset_name);
                et_manual_shortanswer_question = findViewById(R.id.et_manual_shortanswer_question);
                et_manual_shortanswer_answer = findViewById(R.id.et_manual_shortanswer_answer);

                bt_manual_add_question2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String question_type = "shortanswer";
                        String question_name = et_manual_shortanswer_question.getText().toString();
                        String answer = et_manual_shortanswer_answer.getText().toString();
                        count++;
                        Question question = new Question(count + "", question_name, question_type, null, answer);
                        questions.add(question);
                        Log.d(TAG, "questions.toString() : " + questions.toString());
                        et_manual_shortanswer_question.setText("");
                        et_manual_shortanswer_answer.setText("");
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                String path = data.getData().getPath();
                Log.d("---", path);
                setContentView(R.layout.activity_registerquizset);
                et_filepath = findViewById(R.id.et_filepath);
                et_filepath.setEnabled(false);
                et_filepath.setText(path);

            }
        }
    }


    //    public void createQuizset(View view) {
//        String read = ReadTextFile(filePath);
//        setContentView(R.layout.activity_registerquizset);
//        detail = findViewById(R.id.detail);
//        detail.setText(read);
//        pdfGo();
//
//
//    }
//
//    void pdfGo() {
//        ///권한확인
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//            /////권한 추가
//            ActivityCompat.requestPermissions(this, new String[]{
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            }, 1);
//        }
//
//        ///SDK 버전 문제
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//
//        String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        System.out.println(sdCardDir);
//        String fileName = "2_03.pdf";
//        String filePath = sdCardDir + File.separator + fileName;
//
//        Uri uri = Uri.fromFile(new File(filePath));
//        //Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(uri, "application/pdf");
//        startActivity(intent);
//
//    }
//
//    //경로의 텍스트 파일읽기
//    public String ReadTextFile(String path) {
//        StringBuffer strBuffer = new StringBuffer();
//        try {
//            InputStream is = new FileInputStream(path);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                strBuffer.append(line + "\n");
//            }
//            reader.close();
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        }
//        return strBuffer.toString();
//    }
}
