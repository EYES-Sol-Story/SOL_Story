//로그인 화면은 가빈이 화면으로 대체

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'home_screen.dart'; // 홈 화면

class LoginScreen extends StatefulWidget {
  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _idController = TextEditingController();
  final _passwordController = TextEditingController();

  String correctId = "sol"; // 회원 정보의 ID
  String correctPassword = "1234"; // 회원 정보의 비밀번호

  Future<void> _login() async {
    final id = _idController.text;
    final password = _passwordController.text;

    if (id == correctId && password == correctPassword) {
      // 로그인 성공 시 홈 화면으로 이동
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => HomeScreen()),
      );
    } else {
      // 로그인 실패 시 경고 메시지
      showDialog(
        context: context,
        builder: (context) => AlertDialog(
          title: const Text('로그인 실패'),
          content: const Text('아이디 또는 비밀번호가 잘못되었습니다.'),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('확인'),
            ),
          ],
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFA7B4D4),
      body: SafeArea(
        child: Center(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Image.asset(
                  'assets/logo.png',
                  width: 200,
                ),
                const SizedBox(height: 30),
                TextFormField(
                  controller: _idController,
                  decoration: const InputDecoration(
                    labelText: '아이디',
                    border: OutlineInputBorder(),
                  ),
                ),
                const SizedBox(height: 20),
                TextFormField(
                  controller: _passwordController,
                  decoration: const InputDecoration(
                    labelText: '비밀번호',
                    border: OutlineInputBorder(),
                  ),
                  obscureText: true,
                ),
                const SizedBox(height: 20),
                ElevatedButton(
                  onPressed: _login,
                  child: const Text('로그인'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
