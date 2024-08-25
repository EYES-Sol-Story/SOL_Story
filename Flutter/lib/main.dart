import 'package:flutter/material.dart';
import 'package:sol_story/Gabin/Login.dart'; // Login 페이지 import

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: LoginPage(), // LoginPage를 처음으로 보여줌
      routes: {
        '/login': (context) => LoginPage(), // 경로 설정
      },
    );
  }
}
