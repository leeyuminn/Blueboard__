package com.se.blueboard.makeLecture;

import static com.se.blueboard.makeLecture.MakeLecturePageOne.makingLecture;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.se.blueboard.HomePage;
import com.se.blueboard.MessageBoxPage;
import com.se.blueboard.NotificationPage;
import com.se.blueboard.ProfilePage;
import com.se.blueboard.R;

import java.io.File;
import java.util.ArrayList;

import utils.Utils;

public class MakeLecturePageTwo extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_lecture_page_two);

        // 뒤로 가기 버튼
        Button backButton = findViewById(R.id.makeLecture_gotoBack);
        backButton.setOnClickListener(view -> {
            Utils.gotoPage(getApplicationContext(), MakeLecturePageOne.class, null);
        });

        // Current Lecture
        setLecture(makingLecture.getId(), makingLecture.getName());

        // 학습 주차 - 양식 체크 구현 필요
        EditText weeks = (EditText) findViewById(R.id.makeLecture_weeksEditText);
        weeks.setText(makingLecture.getWeeks());

        // 수강 가능 인원 - 양식 체크 구현 필요
        EditText maxUsers = (EditText) findViewById(R.id.makeLecture_maxUsersEditText);
        maxUsers.setText(makingLecture.getMaxStudents());

        // 파일 업로드 버튼 구현 필요
        File uploadFile = null;
        Button uploadFileButton = (Button) findViewById(R.id.makeLecture_uploadFileButton);
        uploadFileButton.setOnClickListener(view -> {
            Utils.toastTest(getApplicationContext(), "uploadFile");
        });

        // 다음 버튼
        Button nextButton = findViewById(R.id.makeLecture_gotoThree);
        nextButton.setOnClickListener(view -> {
            makingLecture.setWeeks(weeks.getText().toString());
            makingLecture.setMaxStudents(maxUsers.getText().toString());
            Utils.gotoPage(getApplicationContext(), MakeLecturePageThree.class, null);
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

    public void setLecture(String lectureID, String lectureName) {
        TextView coverLectureName = findViewById(R.id.makeLecture_lectureHeader);
        coverLectureName.setText(lectureID + "\t" + lectureName);
    }
}
