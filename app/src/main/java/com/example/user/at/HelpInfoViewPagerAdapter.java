package com.example.user.at;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class HelpInfoViewPagerAdapter extends PagerAdapter {
    ArrayList arrayList;
    LayoutInflater inflater;

    public HelpInfoViewPagerAdapter(LayoutInflater inflater, ArrayList arrayList) {
        this.inflater = inflater;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //container -> ViewPager, position -> 화면당 해당 위치

        View view = inflater.inflate(R.layout.help_view_pager_layout, null);
        ImageView img = (ImageView) view.findViewById(R.id.ivHelp);

        int image = (int) arrayList.get(position);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageResource(image);

        //ViewPager에 만들어 낸 View 추가
        container.addView(view);

        //Image가 세팅된 View를 리턴
        return view;
    }

    //화면에 보이지 않은 View는 파괴를 해서 메모리를 관리함.
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //ViewPager에서 보이지 않는 View 제거
        //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object. 형변환 실시
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((View) obj);
    }

}
