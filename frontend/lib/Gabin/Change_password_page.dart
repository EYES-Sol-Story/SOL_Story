import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'login_screen.dart';
//가빈의 비밀번호 변경 페이지. userId값을 받아서 띄울 것임!
class ChangePasswordPage extends StatefulWidget {
  final String userId;

  ChangePasswordPage({required this.userId});

  @override
  _ChangePasswordPageState createState() => _ChangePasswordPageState();
}

class _ChangePasswordPageState extends State<ChangePasswordPage> {
  final TextEditingController _passwordController = TextEditingController();
  final FocusNode _passwordFocusNode = FocusNode();

  String _passwordWarning = '';
  String _passwordSpecialCharWarning = '';
  String _passwordUppercaseWarning = '';
  String _passwordInvalidCharWarning = '';
  bool _obscurePassword = true;

  @override
  void dispose() {
    _passwordController.dispose();
    _passwordFocusNode.dispose();
    super.dispose();
  }

  Future<void> _handleChangePassword() async {
    final String password = _passwordController.text;

    final bool isPasswordValid = _passwordWarning.isEmpty &&
        _passwordSpecialCharWarning.isEmpty &&
        _passwordUppercaseWarning.isEmpty &&
        _passwordInvalidCharWarning.isEmpty;

    if (!isPasswordValid) {
      _showErrorDialog('비밀번호가 조건에 맞지 않습니다.');
    } else {
      try {
        final response = await http.post(
          Uri.parse('http://10.0.2.2:8090/api/change-password'),
          headers: {'Content-Type': 'application/json'},
          body: json.encode({
            'userId': widget.userId,
            'password': password,
          }),
        );

        if (response.statusCode == 200) {
          Navigator.pushAndRemoveUntil(
            context,
            MaterialPageRoute(builder: (context) => LoginScreen()),
                (Route<dynamic> route) => false,
          );
        } else {
          _showErrorDialog('비밀번호 변경에 실패했습니다. 다시 시도해 주세요.');
        }
      } catch (e) {
        _showErrorDialog('서버와의 통신에 실패했습니다. 인터넷 연결을 확인해 주세요.');
      }
    }
  }

  void _showErrorDialog(String message) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(''), // 제목을 빈 문자열로 변경
        content: Container(
          height: 200, // 높이를 200px로 고정
          alignment: Alignment.center, // 내용 가운데 정렬
          child: Text(
            message,
            textAlign: TextAlign.center, // 텍스트 가운데 정렬
          ),
        ),
        actions: [
          Center(
            child: TextButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: Text('확인'),
            ),
          ),
        ],
      ),
    );
  }

  @override
  void initState() {
    super.initState();
    _passwordController.addListener(() {
      final String password = _passwordController.text;

      setState(() {
        _passwordWarning = password.length < 8 ? '8자리 이상이어야 합니다.' : '';

        _passwordSpecialCharWarning =
        RegExp(r'[!@#$%^&*(),.?":{}|<>]').hasMatch(password)
            ? ''
            : '특수문자는 최소 1개 이상 포함되어야 합니다.';

        _passwordUppercaseWarning = RegExp(r'[A-Z]').hasMatch(password)
            ? ''
            : '대문자는 최소 1개 이상 포함되어야 합니다.';

        _passwordInvalidCharWarning =
        RegExp(r'^[a-zA-Z0-9!@#$%^&*(),.?":{}|<>]+$').hasMatch(password)
            ? ''
            : '영어, 특수문자, 숫자만 입력할 수 있습니다.';
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: GestureDetector(
        onTap: () => FocusScope.of(context).unfocus(),
        child: LayoutBuilder(
          builder: (context, constraints) {
            return Stack(
              children: [
                Container(
                  width: double.infinity,
                  height: double.infinity,
                  color: Colors.white,
                ),
                Container(
                  width: double.infinity,
                  height: 300,
                  color: Colors.blue,
                  child: Center(
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
                ),
                Positioned(
                  top: 250,
                  left: 20,
                  right: 20,
                  child: ConstrainedBox(
                    constraints: BoxConstraints(
                      minHeight: constraints.maxHeight - 250,
                    ),
                    child: Container(
                      decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(50),
                      ),
                      child: Padding(
                        padding: EdgeInsets.all(50),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              '아이디: ${widget.userId}',
                              style: TextStyle(
                                fontSize: 20,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            SizedBox(height: 50),
                            TextField(
                              controller: _passwordController,
                              focusNode: _passwordFocusNode,
                              obscureText: _obscurePassword,
                              decoration: InputDecoration(
                                hintText: '새 비밀번호를 입력하세요.',
                                border: OutlineInputBorder(
                                  borderSide: BorderSide(color: Colors.blue),
                                ),
                                focusedBorder: OutlineInputBorder(
                                  borderSide: BorderSide(color: Colors.blue),
                                ),
                                suffixIcon: GestureDetector(
                                  onTapDown: (_) {
                                    setState(() {
                                      _obscurePassword = false;
                                    });
                                  },
                                  onTapUp: (_) {
                                    setState(() {
                                      _obscurePassword = true;
                                    });
                                  },
                                  child: Icon(
                                    _obscurePassword
                                        ? Icons.visibility_off
                                        : Icons.visibility,
                                  ),
                                ),
                              ),
                              style: TextStyle(fontSize: 16),
                            ),
                            if (_passwordWarning.isNotEmpty)
                              Align(
                                alignment: Alignment.centerLeft,
                                child: Text(
                                  _passwordWarning,
                                  style: TextStyle(
                                    color: Colors.black,
                                    fontSize: 12,
                                  ),
                                ),
                              ),
                            if (_passwordSpecialCharWarning.isNotEmpty)
                              Align(
                                alignment: Alignment.centerLeft,
                                child: Text(
                                  _passwordSpecialCharWarning,
                                  style: TextStyle(
                                    color: Colors.black,
                                    fontSize: 12,
                                  ),
                                ),
                              ),
                            if (_passwordUppercaseWarning.isNotEmpty)
                              Align(
                                alignment: Alignment.centerLeft,
                                child: Text(
                                  _passwordUppercaseWarning,
                                  style: TextStyle(
                                    color: Colors.black,
                                    fontSize: 12,
                                  ),
                                ),
                              ),
                            if (_passwordInvalidCharWarning.isNotEmpty)
                              Align(
                                alignment: Alignment.centerLeft,
                                child: Text(
                                  _passwordInvalidCharWarning,
                                  style: TextStyle(
                                    color: Colors.red,
                                    fontSize: 12,
                                  ),
                                ),
                              ),
                            SizedBox(height: 60),
                            ElevatedButton(
                              onPressed: _handleChangePassword,
                              style: ElevatedButton.styleFrom(
                                backgroundColor: Colors.blue,
                                minimumSize: Size(double.infinity, 60),
                              ),
                              child: Text(
                                'Change',
                                style: TextStyle(color: Colors.white),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),
                ),
              ],
            );
          },
        ),
      ),
    );
  }
}
