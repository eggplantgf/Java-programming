package com.example.mobprogfinal_v1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.board.PostDetailActivity;
import com.example.mobprogfinal_v1.models.Post;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private final List<Post> posts;
    private final Context context;
    private final SimpleDateFormat dateFormat;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
        this.dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        
        // 토론 주제와 내용
        String title = post.getTitle() != null ? post.getTitle() : "제목 없음";
        String content = post.getContents() != null ? post.getContents() : "";
        holder.titleView.setText(title);
        holder.contentView.setText(content);
        
        // 책 정보
        String bookTitle = post.getBookTitle() != null && !post.getBookTitle().isEmpty() 
                          ? post.getBookTitle() : "책 제목 없음";
        String bookAuthor = post.getBookAuthor() != null && !post.getBookAuthor().isEmpty() 
                           ? post.getBookAuthor() : "작가 미상";
        String bookGenre = post.getBookGenre() != null && !post.getBookGenre().isEmpty() 
                          ? post.getBookGenre() : "기타";
        String readingStatus = post.getReadingStatus() != null && !post.getReadingStatus().isEmpty() 
                              ? post.getReadingStatus() : "상태 없음";
        
        holder.bookTitleView.setText(bookTitle);
        holder.bookAuthorView.setText(bookAuthor);
        holder.bookGenreView.setText(bookGenre);
        holder.readingStatusView.setText(readingStatus);
        
        // 토론 참여자 수
        String discussionCount = "참여자 " + post.getDiscussionCount() + "명";
        holder.discussionCountView.setText(discussionCount);
        
        // 날짜 포맷팅
        if (post.getDatetime() != null) {
            holder.dateView.setText(dateFormat.format(post.getDatetime()));
        } else {
            holder.dateView.setText("날짜 없음");
        }

        // 클릭 이벤트
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("postId", post.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return posts != null ? posts.size() : 0;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, contentView;
        TextView bookTitleView, bookAuthorView, bookGenreView, readingStatusView;
        TextView discussionCountView, dateView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            // 토론 정보
            titleView = itemView.findViewById(R.id.item_post_title);
            contentView = itemView.findViewById(R.id.item_post_content);
            
            // 책 정보
            bookTitleView = itemView.findViewById(R.id.item_book_title);
            bookAuthorView = itemView.findViewById(R.id.item_book_author);
            bookGenreView = itemView.findViewById(R.id.item_book_genre);
            readingStatusView = itemView.findViewById(R.id.item_reading_status);
            
            // 기타 정보
            discussionCountView = itemView.findViewById(R.id.item_discussion_count);
            dateView = itemView.findViewById(R.id.item_post_date);
        }
    }
}
