package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.s18.guerrero.josegerardo.mco2.dao.UserDAO;
import com.mobdeve.s18.guerrero.josegerardo.mco2.dao.UserDAOFirebaseImpl;
import com.mobdeve.s18.guerrero.josegerardo.mco2.databinding.RegisterBinding;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.User;

public class Register extends AppCompatActivity {

    private RegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UserDAO userDAO = new UserDAOFirebaseImpl(getApplicationContext());

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                user.setUsername(binding.etUsername.getText().toString());
                user.setEmail(binding.etEmail.getText().toString());
                user.setPassword(binding.etPassword.getText().toString());

                userDAO.addUser(user);

                Intent intent = new Intent(getApplicationContext(), MainView.class);
                startActivity(intent);
            }
        });
    }
}