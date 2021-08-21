package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.s18.guerrero.josegerardo.mco2.dao.UserDAO;
import com.mobdeve.s18.guerrero.josegerardo.mco2.dao.UserDAOFirebaseImpl;
import com.mobdeve.s18.guerrero.josegerardo.mco2.databinding.RegisterBinding;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.User;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    private RegisterBinding binding;
    private ArrayList<User> userArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UserDAO userDAO = new UserDAOFirebaseImpl(getApplicationContext());
        userArrayList = userDAO.getUsers();

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.etUsername.getText().toString();
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();
                String confirm_password = binding.etConfirmPassword.getText().toString();

                if(username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() < 8){
                    Toast.makeText(getApplicationContext(), "Passwords should contain at least 8 characters", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(confirm_password)){
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else {

                    boolean flag = false;

                    for(int i = 0; i < userArrayList.size(); i++) {
                        if(userArrayList.get(i).getUsername().equals(username) ||
                            userArrayList.get(i).getEmail().equals(email)) {
                            Toast.makeText(getApplicationContext(), "Username and/or email is already taken", Toast.LENGTH_SHORT).show();
                            flag = true;
                            break;
                        }
                    }
                    if(flag == false) {
                        User user = new User();
                        user.setUsername(username);
                        user.setEmail(email);
                        user.setPassword(password);

                        userDAO.addUser(user);
                        Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        });
    }
}