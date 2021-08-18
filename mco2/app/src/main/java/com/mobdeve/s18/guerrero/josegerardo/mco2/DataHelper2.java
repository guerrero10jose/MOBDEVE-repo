package com.mobdeve.s18.guerrero.josegerardo.mco2;

import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Message;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Post;

import java.util.ArrayList;
import java.util.Collections;

public class DataHelper2 {
    public ArrayList<Message> initializeData() {
        String[] usernames = {"John Doe", "Jane Walker"};
        int[] userImages = {R.drawable.person1, R.drawable.person2};

        ArrayList<Message> data = new ArrayList<>();

        data.add(new Message(
                usernames[0],
                "Hi! How are you?",
                userImages[0]));

        data.add(new Message(
                usernames[1],
                "You should stop procrastinating good sir...",
                userImages[1]));

        Collections.shuffle(data);

        return data;
    }
}