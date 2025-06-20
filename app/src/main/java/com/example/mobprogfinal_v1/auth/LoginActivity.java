package com.example.mobprogfinal_v1.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.board.BoardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        Button loginButton = findViewById(R.id.login_button);
        TextView signupLink = findViewById(R.id.signup_link);

        auth = FirebaseAuth.getInstance();

        // 로그인 버튼 클릭 리스너
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // 회원가입 링크 클릭 리스너
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 이미 로그인된 사용자가 있는지 확인
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // 이미 로그인되어 있으면 BoardActivity로 이동
            startActivity(new Intent(this, BoardActivity.class));
            finish();
        }
    }

    private void login() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "올바른 이메일 형식을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "비밀번호는 6자 이상이어야 합니다", Toast.LENGTH_SHORT).show();
            return;
        }

        // 로그인 중임을 표시
        Toast.makeText(this, "로그인 중...", Toast.LENGTH_SHORT).show();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(this, "로그인 성공! 환영합니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, BoardActivity.class));
                    finish();
                }
            } else {
                String errorMessage = "로그인에 실패했습니다.";
                if (task.getException() != null) {
                    String exception = task.getException().getMessage();
                    if (exception != null) {
                        if (exception.contains("password")) {
                            errorMessage = "비밀번호가 올바르지 않습니다.";
                        } else if (exception.contains("email")) {
                            errorMessage = "등록되지 않은 이메일입니다.";
                        } else if (exception.contains("network")) {
                            errorMessage = "네트워크 연결을 확인해주세요.";
                        }
                    }
                }
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}