package com.example.user.at;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class FileView extends AppCompatActivity {
    Skin skin;
    int color;
    ListView fileViewList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.file_viewer);

        fileViewList=(ListView)findViewById(R.id.file_viewer_list);


    }
}
