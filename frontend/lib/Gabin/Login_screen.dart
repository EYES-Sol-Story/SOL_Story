import 'dart:convert'; // for jsonEncode
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'user_main_screen.dart';
import '/Siwoo/MainPage.dart';
import '../config/constants.dart';

//가빈의 로그인페이지
class LoginScreen extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final TextEditingController _useridController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final FocusNode _useridFocusNode = FocusNode();
  final FocusNode _passwordFocusNode = FocusNode();

  bool _obscurePassword = true;

  @override
  void dispose() {
    _useridController.dispose();
    _passwordController.dispose();
    _useridFocusNode.dispose();
    _passwordFocusNode.dispose();
    super.dispose();
  }
  //test login : solstory
  Future<void> _handleLogin() async {
    final String userid = _useridController.text;
    final String password = _passwordController.text;

    if (userid.isEmpty) {
      _showErrorDialog('아이디를 입력하세요.');
      FocusScope.of(context).requestFocus(_useridFocusNode);
      return;
    }

    if (password.isEmpty) {
      _showErrorDialog('비밀번호를 입력하세요.');
      FocusScope.of(context).requestFocus(_passwordFocusNode);
      return;
    }

    try {
      // 서버로 요청 보낼 URI
      final uri = Uri.parse(REST_API_URL+'/api/login');

      // HTTP POST 요청
      final response = await http.post(
        uri,
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          'userId': userid,
          'password': password,
        }),
      );

      //test id : solstory, test pwd : 1234
      //test gabin id : gabinId, test gabin pwd : 1234
      if (response.statusCode == 200) {
        final responseBody = jsonDecode(response.body);

        if (responseBody['userNo'] != 'noUser') {

          // 로그인 성공
          print('로그인 성공');

          final userNo = jsonDecode(response.body)['user_no']; // 서버에서 받은 user_no

          // 화면이 여전히 활성 상태인지 확인
          if (!mounted) return;

          // 메인 화면으로 이동
          Navigator.pushReplacement(
            context,
            MaterialPageRoute(
              builder: (context) => MainPage(), // user_no를 넘겨줌 main.dart나 config로 저장해뒀으면좋겠음 
            ),
          );

          // 추가 작업을 방지하기 위해 return
          return;
        } else {
          // 잘못된 로그인 정보 처리
          _showErrorDialog('아이디 또는 비밀번호가 잘못되었습니다.');
        }
      } else {
        // 서버 오류 처리
        _showErrorDialog('서버 오류가 발생했습니다.');
      }
    } catch (e) {
      // 비동기 작업 중 에러 발생 시 처리
      if (!mounted) return;
      _showErrorDialog('로그인에 실패했습니다. 다시 시도하세요.');
      print(e.toString());
    }
  }

  void _showErrorDialog(String message) {
    // 화면이 여전히 활성 상태인지 확인
    if (!mounted) return;

    // 오류 메시지 다이얼로그 표시
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(''),
        content: Container(
          height: 200,
          alignment: Alignment.center,
          child: Text(
            message,
            textAlign: TextAlign.center,
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
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            TextField(
                              controller: _useridController,
                              focusNode: _useridFocusNode,
                              decoration: InputDecoration(
                                hintText: '아이디를 입력하세요.',
                                border: OutlineInputBorder(
                                  borderSide: BorderSide(color: Colors.blue),
                                ),
                                focusedBorder: OutlineInputBorder(
                                  borderSide: BorderSide(color: Colors.blue),
                                ),
                              ),
                            ),
                            SizedBox(height: 50),
                            TextField(
                              controller: _passwordController,
                              focusNode: _passwordFocusNode,
                              obscureText: _obscurePassword,
                              decoration: InputDecoration(
                                hintText: '비밀번호를 입력하세요.',
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
                            ),
                            SizedBox(height: 60),
                            ElevatedButton(
                              onPressed: _handleLogin,
                              style: ElevatedButton.styleFrom(
                                backgroundColor: Colors.blue,
                                minimumSize: Size(double.infinity, 60),
                              ),
                              child: Text('Login',
                                  style: TextStyle(color: Colors.white)),
                            ),
                            SizedBox(height: 50),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                GestureDetector(
                                  onTap: () {
                                    Navigator.pushNamed(
                                        context, '/forgot-account');
                                  },
                                  child: Text(
                                    'Forgot Account?',
                                    style: TextStyle(
                                      color: Colors.grey,
                                      fontSize: 10,
                                    ),
                                  ),
                                ),
                                SizedBox(width: 50),
                                GestureDetector(
                                  onTap: () {
                                    Navigator.pushNamed(context, '/join');
                                  },
                                  child: Text(
                                    'join us?',
                                    style: TextStyle(
                                      color: Colors.grey,
                                      fontSize: 10,
                                    ),
                                  ),
                                ),
                              ],
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
