package com.example.watcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.watcher.databinding.ActivityLoginBinding;
import com.example.watcher.ui.login.LoginViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;
    private LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        loginActivity = this;
        binding.buttonLogin.setOnClickListener(view -> {
            String account = binding.editAccount.getText().toString();
            String password = binding.editPassword.getText().toString();
            viewModel.setCallback(new LoginViewModel.MyCallback() {
                @Override
                public void OnLogin() {
                    Intent i = new Intent(loginActivity, MainActivity.class);
                    startActivity(i);
                    finish();
                }

                @Override
                public void OnFail() {
                    binding.editAccount.setText("");
                    binding.editPassword.setText("");
                    Toast.makeText(loginActivity, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            });
            viewModel.login(account, password);
        });
    }
}
