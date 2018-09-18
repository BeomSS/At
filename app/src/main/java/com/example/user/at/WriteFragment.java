package com.example.user.at;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class WriteFragment extends Fragment {
    View view;
    Spinner categorySpinner;
    EditText titleEdit,explainEdit;
    TextView fileTextView,explainTextView;
    Button doneBtn;
    int putCategory;
    String putTitle,putExplain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write, container,false);

        categorySpinner=(Spinner)view.findViewById(R.id.write_spinner);
        titleEdit =(EditText)view.findViewById(R.id.title_edit);
        explainTextView=(TextView)view.findViewById(R.id.explain_textview);
        explainEdit=(EditText)view.findViewById(R.id.explain_edit);
        fileTextView=(TextView) view.findViewById(R.id.file_textview);
        doneBtn=(Button)view.findViewById(R.id.done_button);


        //게시판 변경시 발생하는 이벤트
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        explainTextView.setText("내 용");
                        explainEdit.setHint("내용을 입력해주세요");
                        fileTextView.setEnabled(false);
                        break;
                    case 1:
                        explainTextView.setText("설 명");
                        explainEdit.setHint("설명을 입력해주세요");
                        fileTextView.setEnabled(true);
                        break;
                    case 2:
                        explainTextView.setText("설 명");
                        explainEdit.setHint("설명을 입력해주세요");
                        fileTextView.setEnabled(true);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        //글올리기 버튼 클릭시
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putCategory=categorySpinner.getSelectedItemPosition();
                putTitle=titleEdit.getText().toString();
                putExplain=explainEdit.getText().toString();


                Response.Listener rListener=new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Toast.makeText(getActivity(), "글 등록 완료", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), "글 등록 실패", Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){
                            Log.d("dberror",e.toString());
                        }
                    }
                };

                //회원 아이디 부분 수정 필요
                WritingRequest wRequest = new WritingRequest("test",putCategory,putTitle,putExplain,rListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(wRequest);

            }
        });
        return view;
    }
}
