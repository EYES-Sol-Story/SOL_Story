import 'package:flutter/material.dart';
import 'eyes_icon_page.dart'; // 초기 로딩 페이지
import 'login_screen.dart'; // 로그인 페이지
import 'forgot_account_page.dart'; // 계정찾기 페이지
import 'sign_up_screen.dart'; // 회원가입 페이지
import 'change_password_page.dart'; // 비밀번호 변경 페이지
import 'package:firebase_core/firebase_core.dart'; // Firebase 초기화

//가빈의 메인페이지. 바로 solstory화면을 띄우러 갈 것
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  try {
    await Firebase.initializeApp(); // Firebase 초기화, 모바일용. 웹에서도 쓰려면 따로 옵션설정해줘야 함.
  } catch (e) {
    print('Firebase 초기화 오류: $e');
  }
  runApp(MyApp());
}


class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SOL_Story',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: EyesIconPage(), // 초기 로딩 페이지를 처음에 표시
      routes: {
        '/forgot-account': (context) => ForgotAccountPage(),
        '/join': (context) => SignUpScreen(),
        '/change-password': (context) {
          final args = ModalRoute.of(context)!.settings.arguments as Map<String, String>;
          return ChangePasswordPage(
            userId: args['userId']!,
          );
        },
      },
    );
  }
}
