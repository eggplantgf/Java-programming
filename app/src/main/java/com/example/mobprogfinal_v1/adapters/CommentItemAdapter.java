package com.example.mobprogfinal_v1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.models.Comment;
import com.example.mobprogfinal_v1.providers.AuthManager;
import com.example.mobprogfinal_v1.providers.CommentsManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.CommentViewHolder> {

    private final Context context;
    private final List<Comment> comments;
    private final SimpleDateFormat dateFormat;

    public CommentItemAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
        this.dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);

        // 사용자 ID와 댓글 내용 설정
        String userId = comment.getUserId() != null ? comment.getUserId() : "익명";
        String content = comment.getContent() != null ? comment.getContent() : "";
        
        holder.userIdTextView.setText(userId);
        holder.contentTextView.setText(content);

        // 날짜 설정
        if (comment.getDatetime() != null) {
            holder.dateTextView.setText(dateFormat.format(comment.getDatetime()));
        } else {
            holder.dateTextView.setText("날짜 없음");
        }

        String currentUserId = AuthManager.getInstance().getCurrentUserId();
        boolean isAuthor = currentUserId != null && currentUserId.equals(comment.getUserId());

        // 작성자만 수정/삭제 버튼 표시
        holder.editButton.setVisibility(isAuthor ? View.VISIBLE : View.GONE);
        holder.deleteButton.setVisibility(isAuthor ? View.VISIBLE : View.GONE);

        holder.editButton.setOnClickListener(v -> {
            // 댓글 수정 기능
            Toast.makeText(context, "댓글 수정 기능은 준비 중입니다!", Toast.LENGTH_SHORT).show();
            // 추가 구현 필요
        });

        holder.deleteButton.setOnClickListener(v -> {
            CommentsManager.getInstance().deleteComment(comment.getPostId(), comment.getId(), new CommentsManager.CompletionCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(context, "댓글이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                    comments.remove(position);
                    notifyItemRemoved(position);
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(context, "댓글 삭제에 실패했습니다: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView userIconImageView;
        TextView userIdTextView, contentTextView, dateTextView;
        ImageButton editButton, deleteButton;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            userIconImageView = itemView.findViewById(R.id.comment_user_icon);
            userIdTextView = itemView.findViewById(R.id.comment_user_id);
            contentTextView = itemView.findViewById(R.id.comment_content);
            dateTextView = itemView.findViewById(R.id.comment_date);
            editButton = itemView.findViewById(R.id.edit_comment_button);
            deleteButton = itemView.findViewById(R.id.delete_comment_button);
        }
    }
}
