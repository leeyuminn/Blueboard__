package com.se.blueboard;

import static com.se.blueboard.LecturePage.currentLecture;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import adapter.ContentsListAdapter;
import adapter.ContentsWeeksListAdapter;
import model.LectureContent;
import utils.Utils;

public class ContentsPage extends AppCompatActivity {
    private int lectureWeek = 16;
    private ArrayList<Integer> lectureWeeks = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contents);

        // 강의 주차 받아오기
        lectureWeek = Integer.valueOf(currentLecture.getWeeks());

        for (int idx = 1; idx <= lectureWeek; idx++) {
            lectureWeeks.add(idx);
        }

        // 강의 주차 ListView
        ListView contentsListView = findViewById(R.id.lectureContents_contentsList);
        ContentsWeeksListAdapter contentsListAdapter = new ContentsWeeksListAdapter(lectureWeeks);
        contentsListView.setAdapter(contentsListAdapter);

        // 모든 주차 열기 ToggleButton
        ToggleButton toggleButton = findViewById(R.id.weeks_button);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Utils.toastTest(getApplicationContext(), "TODO");
                }
                else {
                    Utils.toastTest(getApplicationContext(), "TODO");
                }
            }
        });

        // 출결/학습 현황 Button
        Button attendanceButton = findViewById(R.id.attendance_button);
        attendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.gotoPage(getApplicationContext(), AttendancePage.class, null);
            }
        });

        // 하단바
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationBar);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        // 홈 화면으로 이동
                        Utils.gotoPage(getApplicationContext(), HomePage.class, null);
                        return true;

                    case R.id.menu_Mail:
                        // 메시지 화면으로 이동
                        Utils.gotoPage(getApplicationContext(), MessageBoxPage.class, null);
                        return true;

                    case R.id.menu_Notification:
                        // 알림 화면으로 이동
                        Utils.gotoPage(getApplicationContext(), NotificationPage.class, null);
                        return true;

                    case R.id.menu_profile:
                        // 프로필 화면으로 이동
                        Utils.gotoPage(getApplicationContext(), ProfilePage.class, null);
                        return true;
                }
                return false;
            }
        });
    }
}
