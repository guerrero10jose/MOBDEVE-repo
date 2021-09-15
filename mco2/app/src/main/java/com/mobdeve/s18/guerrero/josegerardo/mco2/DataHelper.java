package com.mobdeve.s18.guerrero.josegerardo.mco2;

import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Subtask;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Post;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Task;

import java.util.ArrayList;
import java.util.Collections;

public class DataHelper {
    public ArrayList<Post> initializeData() {
        String[] usernames = {"John Doe", "Jane Walker"};
        int[] userImages = {R.drawable.person1, R.drawable.person2};

        ArrayList<Post> data = new ArrayList<>();

        data.add(new Post(
                R.drawable.running,
                58,
                2,
                "Really proud of actually getting around to doing it!",
                "Run 5km exercise",
                true,
                usernames[0],
                userImages[0]));
        data.add(new Post(
                R.drawable.work_desk,
                10,
                4,
                "It was quite exhausting, I need a break...",
                "Finish workload",
                false,
                usernames[1],
                userImages[1]));
        Collections.shuffle(data);

        return data;
    }

    public ArrayList<Task> initializeTaskData() {

        ArrayList<Task> data = new ArrayList<>();
   //     String[] notes = {"wake up early", "don't forget to bring money to eat breakfast later!"};
        ArrayList<Subtask> subtasks = new ArrayList<>();
    //    subtask.getSubtaskArrayList().add("wake up early");
   //     subtask.getSubtaskArrayList().add("don't forget to bring money to eat breakfast later!");

        String date = "Thu, Aug 26 2021";


        data.add(new Task( "Jog for 5km starting 5am",
                "Exercise",
                subtasks,
                false, date,
                "10:00", "1", ""

        ));

        data.add(new Task( "MC02 Beta Demo",
                "MOBDEVE",
                subtasks,
                false,
                date,
                "10:00", "1", ""
        ));

        data.add(new Task( "MC01 Proposal Submission",
                "MOBDEVE",
                subtasks,
                true,
                date,
                "10:00", "1", ""

        ));


        return data;
    }
}