package com.example.mobprogfinal_v1.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mobprogfinal_v1.MainActivity;
import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.adapters.PostsAdapter;
import com.example.mobprogfinal_v1.auth.LoginActivity;
import com.example.mobprogfinal_v1.models.Post;
import com.example.mobprogfinal_v1.providers.AuthManager;
import com.example.mobprogfinal_v1.providers.PostsManager;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView postsRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PostsAdapter postsAdapter;
    private List<Post> posts;
    
    // 드로어 관련
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        initViews();
        setupDrawer();
        setupRecyclerView();
        setupListeners();
        loadPosts();
    }

    private void initViews() {
        postsRecyclerView = findViewById(R.id.posts_list_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadPosts();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void setupDrawer() {
        // NavigationView 메뉴 아이템 선택 리스너 설정
        navigationView.setNavigationItemSelectedListener(this);

        // 햄버거 메뉴 버튼 설정
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setupRecyclerView() {
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        posts = new ArrayList<>();
        postsAdapter = new PostsAdapter(this, posts);
        postsRecyclerView.setAdapter(postsAdapter);
    }

    private void setupListeners() {
        findViewById(R.id.write_post_button).setOnClickListener(v -> {
            Intent intent = new Intent(BoardActivity.this, PostFormActivity.class);
            intent.putExtra("isEditMode", true);
            startActivity(intent);
        });
    }

    private void loadPosts() {
        Toast.makeText(this, "게시글을 불러오는 중...", Toast.LENGTH_SHORT).show();

        PostsManager.getInstance().fetchAllPosts(new PostsManager.AllPostsCallback() {
            @Override
            public void onSuccess(List<Post> fetchedPosts) {
                if (fetchedPosts.isEmpty()) {
                    Toast.makeText(BoardActivity.this, "아직 게시글이 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    posts.clear();
                    posts.addAll(fetchedPosts);
                    postsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(BoardActivity.this, "게시글을 불러오는데 실패했습니다: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.nav_home) {
            // 홈으로 이동 (현재 페이지이므로 아무것도 하지 않음)
            Toast.makeText(this, "홈 화면입니다", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_profile) {
            // 프로필 화면으로 이동 (향후 구현)
            Toast.makeText(this, "프로필 기능 준비 중입니다", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            // 로그아웃
            logout();
        } else if (id == R.id.nav_about) {
            // 앱 정보
            showAboutDialog();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        AuthManager.getInstance().logout();
        Toast.makeText(this, "로그아웃되었습니다", Toast.LENGTH_SHORT).show();
        
        // 로그인 화면으로 이동
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showAboutDialog() {
        Toast.makeText(this, "BookTalk - 독서 토론 커뮤니티 앱\n버전 1.0", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 게시글 목록 새로고침
        loadPosts();
    }
}
