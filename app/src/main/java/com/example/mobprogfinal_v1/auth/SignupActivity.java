package com.example.mobprogfinal_v1.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobprogfinal_v1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signupButton;
    private ImageButton backButton;
    private TextView loginLink;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Firebase Auth 초기화
        mAuth = FirebaseAuth.getInstance();

        initViews();
        setupListeners();
    }

    private void initViews() {
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        signupButton = findViewById(R.id.signup_button);
        backButton = findViewById(R.id.back_button);
        loginLink = findViewById(R.id.login_link);
    }

    private void setupListeners() {
        // 회원가입 버튼
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // 뒤로가기 버튼
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 로그인 링크
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // 입력 유효성 검사
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 이메일 형식 검사
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "올바른 이메일 형식을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 비밀번호 길이 검사
        if (password.length() < 6) {
            Toast.makeText(this, "비밀번호는 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 비밀번호 일치 검사
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 회원가입 진행 중 표시
        Toast.makeText(this, "회원가입 중...", Toast.LENGTH_SHORT).show();
        signupButton.setEnabled(false);

        // Firebase Auth를 사용한 회원가입
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        signupButton.setEnabled(true);
                        
                        if (task.isSuccessful()) {
                            // 회원가입 성공
                            Toast.makeText(SignupActivity.this, "회원가입이 완료되었습니다! 환영합니다.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // 회원가입 실패 - 더 구체적인 오류 메시지
                            String errorMessage = "회원가입에 실패했습니다.";
                            if (task.getException() != null) {
                                String exception = task.getException().getMessage();
                                if (exception != null) {
                                    if (exception.contains("already in use")) {
                                        errorMessage = "이미 사용 중인 이메일입니다.";
                                    } else if (exception.contains("password")) {
                                        errorMessage = "비밀번호가 너무 약합니다. 더 강한 비밀번호를 사용해주세요.";
                                    } else if (exception.contains("email")) {
                                        errorMessage = "유효하지 않은 이메일 주소입니다.";
                                    } else if (exception.contains("network")) {
                                        errorMessage = "네트워크 연결을 확인해주세요.";
                                    }
                                }
                            }
                            Toast.makeText(SignupActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
