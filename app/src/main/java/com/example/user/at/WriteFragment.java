package com.example.user.at;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
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
import com.example.user.at.request.WritingRequest;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WriteFragment extends Fragment {
    View view;
    Spinner categorySpinner;
    EditText titleEdit, explainEdit;
    TextView fileTextView, explainTextView;
    Button doneBtn;
    int putCategory;
    String putTitle, putExplain, filePath = null;
    String upLoadServerUri = null; //서버 주소를 담을 변수
    int flag = 0,maxLength;
    int serverResponseCode = 0;
    String postId;
    Skin pId=new Skin(MainActivity.context);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write, container, false);

        upLoadServerUri = LoginActivity.ipAddress + ":800/At/upload.php";

        categorySpinner = (Spinner) view.findViewById(R.id.write_spinner);
        titleEdit = (EditText) view.findViewById(R.id.title_edit);
        explainTextView = (TextView) view.findViewById(R.id.explain_textview);
        explainEdit = (EditText) view.findViewById(R.id.explain_edit);
        fileTextView = (TextView) view.findViewById(R.id.file_textview);
        doneBtn = (Button) view.findViewById(R.id.done_button);

        doneBtn.setBackgroundColor(((MainActivity) MainActivity.context).color);

        //게시판 변경시 발생하는 이벤트
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        flag = 0;
                        explainTextView.setText("내 용");
                        explainEdit.setHint("내용을 입력해주세요");
                        explainEdit.setText("");
                        //글자수 제한 변경
                        maxLength = 4000;
                        explainEdit.setFilters(new InputFilter[] {
                                new InputFilter.LengthFilter(maxLength)
                        });
                        fileTextView.setText("글게시판에서는 파일첨부가 불가능합니다.");
                        fileTextView.setEnabled(false);
                        break;
                    case 1:
                        flag = 1;
                        explainTextView.setText("설 명");
                        explainEdit.setHint("설명을 입력해주세요");
                        explainEdit.setText("");
                        maxLength = 200;
                        explainEdit.setFilters(new InputFilter[] {
                                new InputFilter.LengthFilter(maxLength)
                        });
                        fileTextView.setText("이미지를 첨부하시려면 클릭해주세요.(jpg,png,gif)");
                        fileTextView.setEnabled(true);
                        break;
                    case 2:
                        flag = 2;
                        explainTextView.setText("설 명");
                        explainEdit.setHint("설명을 입력해주세요");
                        explainEdit.setText("");
                        maxLength = 200;
                        explainEdit.setFilters(new InputFilter[] {
                                new InputFilter.LengthFilter(maxLength)
                        });
                        fileTextView.setText("음악을 첨부하시려면 클릭해주세요.");
                        fileTextView.setEnabled(true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //글올리기 버튼 클릭시
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putCategory = categorySpinner.getSelectedItemPosition();
                putTitle = titleEdit.getText().toString();
                putExplain = explainEdit.getText().toString();

                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                int tPostId = jsonResponse.getInt("postid"); //파일 url을 넣기 위해 게시글의 id를 받아온다.
                                postId = new String(String.valueOf(tPostId));
                            } else {
                                Toast.makeText(getActivity(), "글 등록 실패", Toast.LENGTH_SHORT).show();
                            }

                            //게시물의 id를 받아서 보내야되기 때문에 response에 배치
                            new Thread() {
                                @Override
                                public void run() {
                                    if (flag != 0) {
                                        upLoadFile(filePath);
                                        filePath = null;
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (flag == 1) {
                                                    fileTextView.setText("이미지를 첨부하시려면 클릭해주세요.(jpg,png,gif)");
                                                } else if (flag == 2) {
                                                    fileTextView.setText("음악을 첨부하시려면 클릭해주세요.");
                                                }
                                                titleEdit.setText(null);
                                                explainEdit.setText(null);
                                            }
                                        });
                                    }else
                                    {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                titleEdit.setText(null);
                                                explainEdit.setText(null);
                                                Toast.makeText(getActivity(),"글쓰기가 완료되었습니다.",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            }.start();

                        } catch (Exception e) {
                            Log.d("dberror", e.toString());
                        }
                    }
                };

                //회원 아이디 부분 수정 필요
                boolean checkEmpty = check(titleEdit.getText().toString(), explainEdit.getText().toString()); //제목과 내용이 빈상태인지 검사하는 메소드

                if (checkEmpty) {
                    if (flag == 1 || flag == 2) {
                        if (filePath != null) { //파일첨부가 되었는지 확인
                            WritingRequest wRequest = new WritingRequest(pId.getPreferenceString("LoginId"), putCategory, putTitle, putExplain, rListener);
                            RequestQueue queue = Volley.newRequestQueue(getActivity());
                            queue.add(wRequest);
                        } else {
                            Toast.makeText(getActivity(), "피드백 받을 파일을 첨부해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    } else { //글게시판에서는 파일첨부가 안되었어도 되므로 빈 부분이 없는지만 체크한다.
                        WritingRequest wRequest = new WritingRequest(pId.getPreferenceString("LoginId"), putCategory, putTitle, putExplain, rListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(wRequest);
                    }
                } else {
                    Toast.makeText(getActivity(), "제목이나 내용을 채워주셔야 합니다.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        fileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1) { //그림일때
                    Intent fintent = new Intent(Intent.ACTION_PICK);
                    fintent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    fintent.setType("image/*");
                    startActivityForResult(fintent, 1111);
                } else if (flag == 2) { //음악일때

                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1);

//                    startActivityForResult( Intent.createChooser( new Intent(Intent.ACTION_GET_CONTENT) .setType("image/*"), "Choose an image"), PICK_FROM_FILE);


                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //갤러리에서 첨부 파일의 경로를 읽어오는 메소드
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            filePath = getRealPathFromURI(data.getData());
            //파일 확장자 검사
            if (filePath.contains(".jpg") || filePath.contains(".png") || filePath.contains(".gif") || filePath.contains(".mp3")) {
                fileTextView.setText(filePath);
            } else {
                filePath = null;
                Toast.makeText(getActivity(), "jpg,png,gif,mp3 파일만 업로드 가능합니다.", Toast.LENGTH_SHORT);
            }

        }
    }

    private String getRealPathFromURI(Uri fileUri) { //갤러리에서 받아온 데이터를 절대 주소로 변환
        String path;
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(fileUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        Log.d("mytest", cursor.getString(column_index));
        path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    public void upLoadFile(String sourceFileUri) {

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        final String fileName = sourceFileUri;

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            Log.e("uploadFile", "Source File not exist :" + sourceFileUri);

        } else {

            try {
                //선택한 파일의 절대 경로를 이용해서 파일 입력 스트림 객체를 얻어온다.
                FileInputStream fileInputStream = new FileInputStream(sourceFile);

                //파일을 업로드할 서버의 url 주소를이용해서 URL 객체 생성하기.
                URL url = new URL(upLoadServerUri);

                //Connection 객체 얻어오기.
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);//입력할수 있도록
                conn.setDoOutput(true); //출력할수 있도록
                conn.setUseCaches(false);  //캐쉬 사용하지 않음

                //post 전송
                conn.setRequestMethod("POST");

                //파일 업로드 할수 있도록 설정하기.
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploadedfile", fileName); //파일이름을 php로 보내준다.

                //DataOutputStream 객체 생성하기.
                dos = new DataOutputStream(conn.getOutputStream());

                //이미지와 함께 문자도 보내기 위해 이미지 업로드 전에 문자를 같이 보낸다.
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"postId\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(postId);
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                //전송할 데이터의 시작임을 알린다.(이미지)
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);


                //한번에 읽어들일수있는 스트림의 크기를 얻어온다.
                bytesAvailable = fileInputStream.available();

                //byte단위로 읽어오기 위하여 byte 배열 객체를 준비한다.
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form..
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                // read file
                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                //전송할 데이터의 끝임을 알린다.
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    getActivity().runOnUiThread(new Runnable() { //프래그먼트이기 때문에 앞에 getActivity()를 붙인다.
                        public void run() {
                            String msg = "글 등록 성공!" + fileName;
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                fileInputStream.close();//스트림 닫아주기.
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);

            } catch (Exception e) {

                Log.d("Test", "exception: " + e.toString());
                Toast.makeText(getActivity(), "업로드중 에러발생!(" + e.toString() + ")", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public boolean check(String title, String explain) {
        if (title.equals("") || title.equals(null) || explain.equals("") || explain.equals(null)) {
            return false;
        } else {
            return true;
        }
    }
}
