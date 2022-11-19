package com.example.mcheck;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class schedule_prof extends AppCompatActivity implements OnItemListener{

    private long backpresstime = 0;

    TextView monthyear;
    LocalDate selecteddate; //년월 변수

    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_prof);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout2);
        View drawerview2 = findViewById(R.id.drawerview2);

        TextView list_sbmenu2 = findViewById(R.id.list_sbmenu2);
        list_sbmenu2.setOnClickListener(new View.OnClickListener() {//강의목록 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(schedule_prof.this, main_prof.class);
                startActivity(intent);
                finish();
            }
        });

        TextView home_sbmenu2 = findViewById(R.id.home_sbmenu2);
        home_sbmenu2.setOnClickListener(new View.OnClickListener() {//홈페이지 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(schedule_prof.this, webview_prof.class);
                startActivity(intent);
                finish();
            }
        });

        TextView info_sbmenu2 = findViewById(R.id.info_sbmenu2);
        info_sbmenu2.setOnClickListener(new View.OnClickListener() {//강의자료 공지 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(schedule_prof.this, notice_prof.class);
                startActivity(intent);
                finish();
            }
        });

        TextView setting_sbmenu2 = findViewById(R.id.setting_sbmenu2);
        setting_sbmenu2.setOnClickListener(new View.OnClickListener() {//환경설정 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(schedule_prof.this, setting_prof.class);
                startActivity(intent);
                finish();
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerview2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });


        //초기화
        monthyear = findViewById(R.id.monthyear);
        ImageButton prebtn = findViewById(R.id.pre_btn);
        ImageButton nextbtn = findViewById(R.id.next_btn);
        recyclerView = findViewById(R.id.recyclerview);

        //현재 날짜
        selecteddate = LocalDate.now();

        //화면 설정
        setMonthView();

        //이전달 버튼 이벤트
        prebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecteddate = selecteddate.minusMonths(1);
                setMonthView();
            }
        });
        //다음달 버튼 이벤트
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecteddate = selecteddate.plusMonths(1);
                setMonthView();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthyearFromdate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월");

        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(){
        //년월 텍스트뷰 세팅
        monthyear.setText(monthyearFromdate(selecteddate));

        //해당 월 날짜 가져오기
        ArrayList<String> daylist = daysinMontharray(selecteddate);

        //어댑터 데이터 적용
        calander_adapter adapter = new calander_adapter(daylist, schedule_prof.this);

        //레이아웃 설정(열 7개)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 7);

        //레이아웃 적용
        recyclerView.setLayoutManager(manager);

        //어댑터 적용
        recyclerView.setAdapter(adapter);

    }
    //날짜 생성
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysinMontharray(LocalDate date){

        ArrayList<String> daylist = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(date);

        int lastdate = yearMonth.lengthOfMonth();//해당 월 마지막 일 가져오기

        LocalDate firstdate = selecteddate.withDayOfMonth(1);//해당 월 첫번째 일 가져오기

        int dayofweek = firstdate.getDayOfWeek().getValue();//첫번째 날 요일 가져오기(월:1, 일:7)

        //날짜 생성
        for(int i = 1; i < 42; i++){
            if(i <= dayofweek || i > lastdate + dayofweek){
                daylist.add("");
            }else {
                daylist.add(String.valueOf(i - dayofweek));
            }
        }
        return daylist;
    }
    //인터페이스 구현(날짜 어댑터에서 넘겨준 날짜 받음)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(String dayText) {
        String ymDay = monthyearFromdate(selecteddate) + " " + dayText + "일";
    }


    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > backpresstime + 2000){
            backpresstime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(System.currentTimeMillis() <= backpresstime + 2000){
            finish();
        }
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };
}
