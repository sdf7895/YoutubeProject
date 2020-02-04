package com.example.youtubeproject.app;

import android.content.Context;
import android.widget.Toast;

public class CommonUtility {

     public static void showToast(Context context,String contents){
         Toast.makeText(context, contents, Toast.LENGTH_SHORT).show();
    }

}
