package com.example.youtubeproject.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.youtubeproject.DataBase.MySQLiteOpenHelper;
import com.example.youtubeproject.Model.YoutubePlayerList;
import com.example.youtubeproject.R;
import com.example.youtubeproject.app.ViewType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static MySQLiteOpenHelper mySQLiteOpenHelper;
    public static SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_youtube);

        final String DateBaseName = "youtubeplayer";

        mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext(),
                DateBaseName,
                null,
                1);

        Handler handler = new Handler();
        handler.postDelayed(

                new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);

                setDateInsert();

                finish();
            }
        }, 1000);
    }

    private void setDateInsert() {
        ArrayList<YoutubePlayerList> items = MySQLiteOpenHelper.videosSelect();

        if (youtubeSetData().size() != items.size()) {
            for (int i = 0; i < youtubeSetData().size(); i++) {
                YoutubePlayerList playerList = youtubeSetData().get(i);

                MySQLiteOpenHelper.videosInsert(playerList.url,playerList.viewtype, playerList.title, playerList.id,
                        playerList.duration, playerList.state, playerList.favoritesState);
            }
        }
    }

    public static ArrayList<YoutubePlayerList> youtubeSetData() {
        ArrayList<YoutubePlayerList> lists = new ArrayList<>();

        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"다시사랑할수있을것처럼", "http://i.ytimg.com/vi/SAp2_W14oUk/0.jpg", "SAp2_W14oUk", "4:27", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"실패없는 취향저격 사클 개띵곡", "http://i.ytimg.com/vi/h7Xu3c_5S5s/0.jpg", "h7Xu3c_5S5s", "54:30", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"With You 크러쉬", "http://i.ytimg.com/vi/z560Yz_rTjA/0.jpg", "z560Yz_rTjA", "5:22", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"별의노래(feat.유진박)", "http://i.ytimg.com/vi/uUXKRemQZ7w/0.jpg", "uUXKRemQZ7w", "3:10", false, false));
        lists.add(new YoutubePlayerList(ViewType.BB_TYPE,"땡큐땡큐 (feat.장기하,YDG,머쉬베놈)", "http://i.ytimg.com/vi/_x-8tI1V4QQ/0.jpg", "_x-8tI1V4QQ", "4:02", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"성시경 넌 감동이었어", "http://i.ytimg.com/vi/f-0kpCPpm74/0.jpg", "f-0kpCPpm74", "4:23", false, false));
        lists.add(new YoutubePlayerList(ViewType.BB_TYPE,"406 Project 없던일", "http://i.ytimg.com/vi/cPT7RWy0N7c/0.jpg", "cPT7RWy0N7c", "3:42", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"Cosmic Boy Can I Love?", "http://i.ytimg.com/vi/oHiy7-xbLmg/0.jpg", "oHiy7-xbLmg", "3:50", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"DEAN - Like A Star", "http://i.ytimg.com/vi/pD-1sK_ioHQ/0.jpg", "pD-1sK_ioHQ", "2:10", false, false));
        lists.add(new YoutubePlayerList(ViewType.BB_TYPE,"Heize No Reason", "http://i.ytimg.com/vi/Fr5JZMyxgDw/0.jpg", "Fr5JZMyxgDw", "3:36", false, false));

        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"빛과 소금 샴푸의요정", "http://i.ytimg.com/vi/cqxYufr2JrQ/0.jpg", "cqxYufr2JrQ", "3:47", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"빛과 소금 오래된친구", "http://i.ytimg.com/vi/8JZ7pD3tvQQ/0.jpg", "8JZ7pD3tvQQ", "3:49", false, false));
        lists.add(new YoutubePlayerList(ViewType.BB_TYPE,"김광석 서른 즈음에", "http://i.ytimg.com/vi/YNDGA02C2Sw/0.jpg", "YNDGA02C2Sw", "4:42", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"김광석 일어나", "http://i.ytimg.com/vi/FRP2dstf5o4/0.jpg", "FRP2dstf5o4", "4:35", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"김광석 편지", "http://i.ytimg.com/vi/EnMAHyjc9i0/0.jpg", "EnMAHyjc9i0", "4:43", false, false));
        lists.add(new YoutubePlayerList(ViewType.BB_TYPE,"브로콜리너마저 앵콜요청금지", "http://i.ytimg.com/vi/DASZ5bI0gKk/0.jpg", "DASZ5bI0gKk", "4:03", false, false));
        lists.add(new YoutubePlayerList(ViewType.BB_TYPE,"조성모 깊은 밤을 날아서", "http://i.ytimg.com/vi/PcTgxTH3hHA/0.jpg", "PcTgxTH3hHA", "3:19", false, false));
        lists.add(new YoutubePlayerList(ViewType.BB_TYPE,"이승철 작은연못", "http://i.ytimg.com/vi/N8HlCZTZD-k/0.jpg", "N8HlCZTZD-k", "3:37", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"The Beatles YesterDay", "http://i.ytimg.com/vi/jo505ZyaCbA/0.jpg", "jo505ZyaCbA", "2:05", false, false));
        lists.add(new YoutubePlayerList(ViewType.BB_TYPE,"산울림 회상", "http://i.ytimg.com/vi/0bG8lTKuRGU/0.jpg", "0bG8lTKuRGU", "3:16", false, false));

        lists.add(new YoutubePlayerList(ViewType.BB_TYPE,"조하문 눈오는밤", "http://i.ytimg.com/vi/Rt0gCvQ_n3k/0.jpg", "Rt0gCvQ_n3k", "3:27", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"정승환 그날들", "http://i.ytimg.com/vi/IuaWIR_9ui0/0.jpg", "IuaWIR_9ui0", "3:10", false, false));
        lists.add(new YoutubePlayerList(ViewType.BB_TYPE,"김범수 그댄 행복에 살텐데", "http://i.ytimg.com/vi/YwU_zI5wwnU/0.jpg", "YwU_zI5wwnU", "5:10", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"임현정 사랑은봄비처럼이별은겨울비처럼", "http://i.ytimg.com/vi/mNEvB24GlbQ/0.jpg", "mNEvB24GlbQ", "4:42", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"음악대장 매일매일기다려", "http://i.ytimg.com/vi/9kUmqiuqP5I/0.jpg", "9kUmqiuqP5I", "3:39", false, false));
        lists.add(new YoutubePlayerList(ViewType.BB_TYPE,"헤이즈 그러니까", "http://i.ytimg.com/vi/HyTbgBlnLCo/0.jpg", "HyTbgBlnLCo", "3:04", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"던 MONEY", "http://i.ytimg.com/vi/YjAVk1cR2ps/0.jpg", "YjAVk1cR2ps", "3:31", false, false));
        lists.add(new YoutubePlayerList(ViewType.BB_TYPE,"심플리선데이 사랑해요", "http://i.ytimg.com/vi/_zhUzHa6vjI/0.jpg", "_zhUzHa6vjI", "4:18", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"별 12월 32일", "http://i.ytimg.com/vi/e056T_JOB_s/0.jpg", "e056T_JOB_s", "4:21", false, false));
        lists.add(new YoutubePlayerList(ViewType.B_TYPE,"윤하 Parade", "http://i.ytimg.com/vi/oDsjrLTs9QE/0.jpg", "oDsjrLTs9QE", "3:09", false, false));

        return lists;
    }
}
