import 'package:flutter/material.dart';
import 'login_screen.dart';

//처음에 2초간 이 화면을 띄운 후 로그인 페이지로 갈 것
class EyesIconPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // 일정 시간이 지난 후 화면 전환
    Future.delayed(Duration(seconds: 2), () {
      if (context.mounted) { // 위젯이 활성화 상태인지 확인
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => LoginScreen()),
        );
      }
    });

    return Scaffold(
      backgroundColor: Colors.blue,
      body: Center(
        child: Text(
          'SOL Story',
          style: TextStyle(
            color: Colors.white,
            fontSize: 70,
            fontFamily: 'SOL_Story_title',
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
    );
  }
}
