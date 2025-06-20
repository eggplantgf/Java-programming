# BookTalk - 독서 토론 커뮤니티 앱

독서를 좋아하는 사람들이 모여 책에 대해 토론할 수 있는 커뮤니티 앱입니다.

## 프로젝트 개요

- **플랫폼**: Android (Java)
- **데이터베이스**: Firebase Realtime Database
- **인증**: Firebase Authentication
- **최소 SDK**: API 24 (Android 7.0)

## 주요 기능

- 📚 **독서 토론 게시판**: 읽고 있는 책에 대한 토론 글 작성
- 📖 **도서 정보 관리**: 책 제목, 작가, 장르, 독서 상태 표시
- 💬 **댓글 시스템**: 게시글에 대한 의견 교환
- 🔐 **사용자 인증**: 이메일 기반 로그인/회원가입
- 📱 **네비게이션 드로워**: 사용자 정보 및 앱 메뉴

## 📁 프로젝트 구조

### 🎯 Java 파일 구조

```
app/src/main/java/com/example/mobprogfinal_v1/
├── MainActivity.java                              // 메인 액티비티 (스플래시/진입점)
│
├── adapters/                                      // RecyclerView 어댑터들
│   ├── PostsAdapter.java                          // 게시글 목록 어댑터 (책 정보 포함 카드뷰)
│   └── CommentItemAdapter.java                    // 댓글 목록 어댑터 (날짜 포함)
│
├── auth/                                          // 사용자 인증 관련
│   ├── LoginActivity.java                         // 로그인 화면 (자동 로그인, 한국어 지원)
│   └── SignupActivity.java                        // 회원가입 화면 (유효성 검사 강화)
│
├── board/                                         // 게시판 관련 액티비티들
│   ├── BoardActivity.java                         // 메인 게시판 (드로워, 플로팅 버튼)
│   ├── PostDetailActivity.java                    // 게시글 상세 화면 (책 정보, 댓글)
│   └── PostFormActivity.java                      // 글 작성/수정 (도서 정보 입력)
│
├── models/                                        // 데이터 모델 클래스들
│   ├── Post.java                                  // 게시글 모델 (도서 정보 필드 추가)
│   └── Comment.java                               // 댓글 모델 (날짜 포함)
│
└── providers/                                     // Firebase 데이터 관리자들
    ├── AuthManager.java                           // 인증 관리 (로그인/로그아웃/사용자 정보)
    ├── PostsManager.java                          // 게시글 CRUD (독서 정보 파싱)
    └── CommentsManager.java                       // 댓글 CRUD (실시간 업데이트)
```

### 🎨 XML 파일 구조

```
app/src/main/res/
│
├── layout/                                        // 화면 레이아웃들
│   ├── activity_board.xml                         // 메인 게시판 (DrawerLayout, RecyclerView)
│   ├── activity_login.xml                         // 로그인 화면 (Material Design)
│   ├── activity_signup.xml                        // 회원가입 화면 (환영 메시지)
│   ├── activity_post_detail.xml                   // 게시글 상세 (댓글 목록 포함)
│   ├── activity_post_form.xml                     // 글 작성 (도서 정보 입력 폼)
│   ├── item_post.xml                              // 게시글 카드 (책 정보 태그)
│   └── item_comment.xml                           // 댓글 아이템 (날짜 표시)
│
├── menu/                                          // 메뉴 리소스
│   └── drawer_menu.xml                            // 네비게이션 드로워 메뉴
│
├── values/                                        // 리소스 값들
│   ├── strings.xml                                // 문자열 리소스 (독서 토론 관련)
│   ├── colors.xml                                 // 색상 정의 (Nordic 테마)
│   ├── styles.xml                                 // 스타일 정의
│   └── themes.xml                                 // 앱 테마
│
├── drawable/                                      // 드로어블 리소스들
│   ├── gradient_background.xml                    // 그라데이션 배경
│   ├── item_background.xml                        // 카드 배경
│   ├── border_red.xml                             //
│   └── ic_*.xml                                   //
│
└── color/                                         // 색상 리소스
    └── red.xml                                    //
```

## 🚀 시작하기

### 1. 프로젝트 설정

```bash
git clone [repository-url]
cd Java_final
```

### 2. Firebase 설정

- `app/google-services.json` 파일이 포함되어 있습니다
- Firebase Realtime Database와 Authentication이 활성화되어 있어야 합니다

### 3. 빌드 및 실행

```bash
./gradlew build
./gradlew installDebug
```

## 📱 화면별 기능

### 로그인/회원가입

- Firebase Authentication을 통한 이메일 인증
- Material Design UI 적용
- 자동 로그인 및 유효성 검사

### 게시글 목록 (BoardActivity)

- 독서 토론 게시글을 카드 형태로 표시
- 책 제목, 작가, 장르 태그 표시
- 독서 상태 및 토론 참여자 수 표시
- 네비게이션 드로워를 통한 메뉴 접근

### 글 작성 (PostFormActivity)

- 도서 정보 입력 (제목, 작가)
- 장르 선택 (스피너)
- 독서 상태 선택 (읽는 중/완독/읽고 싶은)
- 토론 주제 작성

### 게시글 상세 (PostDetailActivity)

- 토론 내용 및 책 정보 표시
- 댓글 목록 및 작성 기능
- 본인이 작성한 댓글만 삭제 가능

### 네비게이션 드로워

- 현재 로그인한 사용자 이메일 표시
- 로그아웃 기능
- 앱 정보 표시

## 🛠 기술 스택

- **언어**: Java
- **데이터베이스**: Firebase Realtime Database
- **인증**: Firebase Authentication
- **UI**: Material Design Components
- **아키텍처**: MVC 패턴
