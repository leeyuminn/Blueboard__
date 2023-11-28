package com.se.blueboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.se.blueboard.makeLecture.MakeLecturePageOne;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import adapter.MainLectureAdapter;
import model.Lecture;
import model.User;
import utils.FirebaseController;
import utils.MyCallback;
import utils.Utils;

public class HomePage extends AppCompatActivity {
    FirebaseController controller = new FirebaseController();
    // 현재 유저
    public static User currentUser = User.makeUser();
    // 현재 유저의 강의 목록
    public static ArrayList<Lecture> userLectureList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // 유저 아이디를 받아서 데이터 가져옴
        controller.getUserData(currentUser.getId(), new MyCallback() {
            @Override
            public void onSuccess(Object object) {
                userLectureList = new ArrayList<>();
                currentUser = (User) object;
                Log.d("onSuccessGetUserDataHome", currentUser.toString());

                // 현재 유저가 관리자가 아니면 강의 생성 버튼 숨김
                if (currentUser.getIsManager() == 1) {
                    // 강의 생성 버튼
                    Button makeLectureButton = findViewById(R.id.main_makeLecture);
                    makeLectureButton.setOnClickListener(view -> {
                        Utils.gotoPage(getApplicationContext(), MakeLecturePageOne.class, null);
                    });
                }
                else {
                    Button makeLectureButton = findViewById(R.id.main_makeLecture);
                    makeLectureButton.setVisibility(View.INVISIBLE);
                }

                // Set GridView
                GridView lectureGridView = (GridView) findViewById(R.id.main_lectureGridView);

                // 유저가 수강하는 강의 목록 가져와서 출력
                for (String lectureID : currentUser.getLectures()) {
                    Log.d("getLectureListThread", lectureID);
                    controller.getLectureData(lectureID, new MyCallback() {
                        @Override
                        public void onSuccess(Object object) {

                            Lecture lecture = (Lecture) object;
                            boolean flag = true;

                            // 유저가 수강 중인 강의가 userLectureList에 가지고 있지 않으면 강의 목록에 추가
                            for (Lecture tempLecture : userLectureList) {
                                Log.d("getid", tempLecture.getId() + "  " + lecture.getId());
                                if (tempLecture.getId().equals(lecture.getId())){
                                    flag = false;
                                    break;
                                }
                                flag = true;
                            }
                            
                            // 없는 강의인 경우 userLectureList에 삽입
                            if (flag) { userLectureList.add(lecture); }
                            // 오름차순 정렬
                            userLectureList.sort(new LectureNameComparator());
                            // userLectureList GridView 출력
                            MainLectureAdapter mainLectureAdapter = new MainLectureAdapter(userLectureList);
                            lectureGridView.setAdapter(mainLectureAdapter);

                            // Item click 시 이벤트: 해당 강의 화면으로 이동
                            lectureGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    // Click한 강의의 이름, ID를 intent에 담아 LecturePage에 전달
                                    Lecture clickedLecture = (Lecture) mainLectureAdapter.getItem(i);
                                    Intent intent = new Intent(getApplicationContext(), LecturePage.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("lectureName", clickedLecture.getName());
                                    intent.putExtra("lectureID", clickedLecture.getId());
                                    startActivity(intent);
                                }
                            });
                            Log.d("onSuccessGetLectureList", lecture.toString());
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d("failGetLectureList", e.getMessage());
                        }
                    }); // End controller.getLectureData
                }
            }
            @Override
            public void onFailure(Exception e) {
                Log.d("GetUserDataHome", e.getMessage());
            }
        });

    } // End onCreate()

    class LectureNameComparator implements Comparator<Lecture> {
        @Override
        public int compare(Lecture lecture1, Lecture lecture2) {
            if (lecture1.getName().compareTo(lecture2.getName()) > 0)
                return 1;
            else if (lecture1.getName().compareTo(lecture2.getName()) < 0)
                return -1;
            return 0;
        }
    }
}
