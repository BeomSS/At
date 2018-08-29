package com.example.user.at;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BaseExpandableAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> groupList = null;
    private ArrayList<ArrayList<String>> childList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    public BaseExpandableAdapter(Context c, ArrayList<String> groupList, ArrayList<ArrayList<String>> childList){   //생성자
        super();
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public String getGroup(int groupPosition){  //내가 클릭한 리스트 아이템을 groupList 에서 가져옴
        return groupList.get(groupPosition);
    }

    @Override
    public int getGroupCount(){                 //groupList 의 사이즈를 구함
        return groupList.size();
    }

    @Override
    public long getGroupId(int groupPosition){  //클릭한 그룹의 position 반환
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            viewHolder.tv_groupName = (TextView)convertView.findViewById(R.id.tv_group);
            viewHolder.iv_image = (ImageView)convertView.findViewById(R.id.iv_image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(isExpanded){             //상위 리스트가 열려있으면
            viewHolder.iv_image.setImageResource(R.drawable.arrow_right);   //화살표 이미지를 방향이 아래로 향하게 로테이션
            viewHolder.iv_image.setRotation(90);
        }else{                      //닫히면
            viewHolder.iv_image.setImageResource(R.drawable.arrow_right);   //화살표를 오른쪽으로 향하도록 로테이션
            viewHolder.iv_image.setRotation(0);
        }

        viewHolder.tv_groupName.setText(getGroup(groupPosition));   //position 에 맞는 그룹의 텍스트를 가져와 설정

        return convertView;
    }

    @Override
    public String getChild(int groupPosition, int childPosition){   //위와 동일. 다만, 하위(자식) 리스트
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition){
        return childList.get(groupPosition).size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition){
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_row, null);
            viewHolder.tv_childName = (TextView) convertView.findViewById(R.id.tv_child);
            viewHolder.iv_child = (ImageView) convertView.findViewById(R.id.iv_child);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.iv_child.setImageResource(R.drawable.play_arrow_right);
        viewHolder.tv_childName.setText(getChild(groupPosition, childPosition));

        return convertView;
    }

    @Override
    public boolean hasStableIds(){
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition){
        return true;
    }

    class ViewHolder{               //list_row 에 있는 view 들을 가져와 저장
        ImageView iv_image;
        ImageView iv_child;
        TextView tv_groupName;
        TextView tv_childName;
    }
}