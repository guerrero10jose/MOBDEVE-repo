package com.mobdeve.s18.guerrero.josegerardo.mco2;

import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Friend;

import java.util.ArrayList;
import java.util.Collections;

public class DataHelper3 {
    public ArrayList<Friend> initializeData() {
        String[] usernames = {"John Doe", "Jane Walker"};
        int[] userImages = {R.drawable.person1, R.drawable.person2};

        ArrayList<Friend> data = new ArrayList<>();

        data.add(new Friend(
                userImages[0],
                usernames[0]));

        data.add(new Friend(
                userImages[1],
                usernames[1]));

        Collections.shuffle(data);

        return data;
    }
}