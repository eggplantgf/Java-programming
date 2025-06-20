package com.example.mobprogfinal_v1.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {

    private String id;
    private String title;              // 토론 주제
    private String contents;           // 토론 내용
    private Date datetime;
    private String userId;
    
    // 독서 토론 관련 필드들
    private String bookTitle;          // 책 제목
    private String bookAuthor;         // 작가
    private String bookGenre;          // 장르
    private String readingStatus;      // 독서 진행 상황 (읽는 중, 완독, 읽고 싶은 책)
    private int discussionCount;       // 토론 참여자 수

    public Post(String id, String title, String contents, Date datetime, String userId,
                String bookTitle, String bookAuthor, String bookGenre, String readingStatus) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.datetime = datetime;
        this.userId = userId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookGenre = bookGenre;
        this.readingStatus = readingStatus;
        this.discussionCount = 0;
    }

    // 기존 생성자 호환성을 위해 유지 (deprecated)
    @Deprecated
    public Post(String id, String title, String contents, Date datetime, int minPeople, int maxPeople, String userId) {
        this(id, title, contents, datetime, userId, "", "", "", "");
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Date getDatetime() {
        return datetime;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public String getReadingStatus() {
        return readingStatus;
    }

    public int getDiscussionCount() {
        return discussionCount;
    }

    public void setDiscussionCount(int discussionCount) {
        this.discussionCount = discussionCount;
    }

    // 이전 호환성을 위한 메서드들 (deprecated)
    @Deprecated
    public int getMinPeople() {
        return 0;
    }

    @Deprecated
    public int getMaxPeople() {
        return 0;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("title", title);
        postMap.put("contents", contents);
        postMap.put("datetime", datetime != null ? datetime.getTime() : null);
        postMap.put("userId", userId);
        postMap.put("bookTitle", bookTitle);
        postMap.put("bookAuthor", bookAuthor);
        postMap.put("bookGenre", bookGenre);
        postMap.put("readingStatus", readingStatus);
        postMap.put("discussionCount", discussionCount);
        return postMap;
    }
}
