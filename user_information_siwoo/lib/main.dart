//스프링부트 사용안하고 플러터에 있는 자체 서버로 일단 구동 가능하게 만들었어

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'screens/user_info_form_screen.dart';
import 'screens/home_screen.dart';
import 'screens/login_screen.dart'; // 로그인 화면 추가

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  SharedPreferences prefs = await SharedPreferences.getInstance();

  // 예외 처리: null 값이 나올 경우 기본값을 true로 설정
  bool isFirstRun = prefs.getBool('first_run') ?? true;

  runApp(MyApp(isFirstRun: isFirstRun));
}

class MyApp extends StatelessWidget {
  final bool isFirstRun;

  const MyApp({Key? key, required this.isFirstRun}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: isFirstRun ? UserInfoFormScreen() : LoginScreen(), // 로그인 화면으로 변경
    );
  }
}
