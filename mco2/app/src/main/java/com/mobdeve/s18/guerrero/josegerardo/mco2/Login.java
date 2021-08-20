package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.s18.guerrero.josegerardo.mco2.dao.UserDAO;
import com.mobdeve.s18.guerrero.josegerardo.mco2.dao.UserDAOFirebaseImpl;
import com.mobdeve.s18.guerrero.josegerardo.mco2.databinding.LoginBinding;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.User;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private LoginBinding binding;
    private ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UserDAO userDAO = new UserDAOFirebaseImpl(getApplicationContext());
        userArrayList = userDAO.getUsers();

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();

                if(username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {

                    boolean flag = true;

                    for(int i = 0; i < userArrayList.size(); i++) {
                        if(userArrayList.get(i).getUsername().equals(username)) {
                            if(userArrayList.get(i).getPassword().equals(password)) {
                                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainView.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }
                            flag = false;
                            break;
                        }
                    }
                    if(flag) {
                        Toast.makeText(getApplicationContext(), "Username is not registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}