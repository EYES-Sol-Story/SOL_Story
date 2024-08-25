import 'package:flutter/material.dart';
import 'package:sol_story/Siwoo/MainPage.dart'; // MainPage import

class LoginPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Login Page'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: () {
            // 로그인 버튼을 누르면 MainPage로 이동
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => MainPage()),
            );
          },
          child: Text('Login'),
        ),
      ),
    );
  }
}
