package com.example.easyschool.matchcourses;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.easyschool.App;
import com.example.easyschool.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Mohammed M Elkady on 07.02.2021.
 */

public class Shop {

    private static final String STORAGE = "shop";

    public static Shop get() {
        return new Shop();
    }

    private SharedPreferences storage;

    private Shop() {
        storage = App.getInstance().getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public List<Item> getData() {
        return Arrays.asList(
                new Item(1, "Arabic", "12 courses available", R.drawable.education),
                new Item(2, "English", "50 courses available", R.drawable.education_1),
                new Item(3, "Math", "265 courses available", R.drawable.education_5),
                new Item(4, "Science", "18 courses available", R.drawable.education_3),
                new Item(5, "History", "36 courses available", R.drawable.education_2),
                new Item(6, "Music", "145 courses available", R.drawable.music_icon),
                new Item(7, "Computer", "145 courses available", R.drawable.education_4)

        );
    }


}
