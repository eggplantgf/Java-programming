package com.example.mobprogfinal_v1.board;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.models.Post;
import com.example.mobprogfinal_v1.providers.AuthManager;
import com.example.mobprogfinal_v1.providers.PostsManager;

import java.util.Date;

public class PostFormActivity extends AppCompatActivity {

    private boolean isEditMode;
    private EditText titleInput, contentInput, bookTitleInput, bookAuthorInput;
    private Spinner bookGenreSpinner, readingStatusSpinner;
    private TextView titleLabel, contentLabel;
    private Button savePostButton;
    private ImageButton editButton, deleteButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_form);

        isEditMode = getIntent().getBooleanExtra("isEditMode", false);

        initViews();
        setupSpinners();

        if (!isEditMode) {
            loadPostDetails();
        } else {
            setupSaveButton();
        }
    }

    private void initViews() {
        titleInput = findViewById(R.id.title_input);
        contentInput = findViewById(R.id.content_input);
        bookTitleInput = findViewById(R.id.book_title_input);
        bookAuthorInput = findViewById(R.id.book_author_input);
        bookGenreSpinner = findViewById(R.id.book_genre_spinner);
        readingStatusSpinner = findViewById(R.id.reading_status_spinner);
        titleLabel = findViewById(R.id.post_title_label);
        contentLabel = findViewById(R.id.post_content_label);
        savePostButton = findViewById(R.id.save_post_button);
        editButton = findViewById(R.id.edit_button);
        deleteButton = findViewById(R.id.delete_button);
        backButton = findViewById(R.id.back_button);

        if (isEditMode) {
            titleLabel.setVisibility(View.GONE);
            contentLabel.setVisibility(View.GONE);
            bookTitleInput.setVisibility(View.VISIBLE);
            bookAuthorInput.setVisibility(View.VISIBLE);
            bookGenreSpinner.setVisibility(View.VISIBLE);
            readingStatusSpinner.setVisibility(View.VISIBLE);
        } else {
            titleInput.setVisibility(View.GONE);
            contentInput.setVisibility(View.GONE);
            bookTitleInput.setVisibility(View.GONE);
            bookAuthorInput.setVisibility(View.GONE);
            bookGenreSpinner.setVisibility(View.GONE);
            readingStatusSpinner.setVisibility(View.GONE);
            savePostButton.setVisibility(View.GONE);
        }

        // 뒤로가기 버튼 기능 추가
        backButton.setOnClickListener(v -> finish());
    }

    private void setupSpinners() {
        // 장르 스피너 설정
        String[] genres = {
            getString(R.string.genre_fiction),
            getString(R.string.genre_essay),
            getString(R.string.genre_humanities),
            getString(R.string.genre_science),
            getString(R.string.genre_self_help),
            getString(R.string.genre_other)
        };
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, genres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookGenreSpinner.setAdapter(genreAdapter);

        // 독서 상태 스피너 설정
        String[] readingStatuses = {
            getString(R.string.status_reading),
            getString(R.string.status_finished),
            getString(R.string.status_want_to_read)
        };
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, readingStatuses);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        readingStatusSpinner.setAdapter(statusAdapter);
    }

    private void setupSaveButton() {
        savePostButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String content = contentInput.getText().toString().trim();
            String bookTitle = bookTitleInput.getText().toString().trim();
            String bookAuthor = bookAuthorInput.getText().toString().trim();
            String bookGenre = bookGenreSpinner.getSelectedItem().toString();
            String readingStatus = readingStatusSpinner.getSelectedItem().toString();

            if (title.isEmpty() || content.isEmpty() || bookTitle.isEmpty() || bookAuthor.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = AuthManager.getInstance().getCurrentUserId();
            if (userId == null) {
                Toast.makeText(this, "사용자 인증이 필요합니다", Toast.LENGTH_SHORT).show();
                return;
            }

            Post post = new Post(null, title, content, new Date(), userId, 
                               bookTitle, bookAuthor, bookGenre, readingStatus);
            
            PostsManager.getInstance().addPost(post, new PostsManager.SinglePostCallback() {
                @Override
                public void onSuccess(Post newPost) {
                    Toast.makeText(PostFormActivity.this, "토론이 성공적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(PostFormActivity.this, "토론 등록에 실패했습니다: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void loadPostDetails() {
        String postId = getIntent().getStringExtra("postId");
        if (postId == null) {
            Toast.makeText(this, "게시글 ID가 제공되지 않았습니다", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        PostsManager.getInstance().fetchPostById(postId, new PostsManager.SinglePostCallback() {
            @Override
            public void onSuccess(Post post) {
                titleLabel.setText(post.getTitle());
                contentLabel.setText(post.getContents());
                
                // 책 정보 표시 (뷰모드에서는 별도 레이아웃이 필요할 수 있음)
                // 여기서는 기본적으로 제목과 내용만 표시
            }

            @Override
            public void onError(String error) {
                Toast.makeText(PostFormActivity.this, "게시글을 불러오는데 실패했습니다: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
