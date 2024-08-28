import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:math';
import '../config/constants.dart';

import 'change_password_page.dart';

// 계정찾기 페이지
class ForgotAccountPage extends StatefulWidget {
  @override
  _ForgotAccountPageState createState() => _ForgotAccountPageState();
}

class _ForgotAccountPageState extends State<ForgotAccountPage> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _accountNameController = TextEditingController();
  final TextEditingController _accountNumberController = TextEditingController();
  final TextEditingController _verificationCodeController = TextEditingController();
  final FocusNode _emailFocusNode = FocusNode();
  final FocusNode _accountNameFocusNode = FocusNode();
  final FocusNode _accountNumberFocusNode = FocusNode();
  final FocusNode _verificationCodeFocusNode = FocusNode();

  bool _isVerificationCompleted = false;
  bool _showImage = false;
  bool _checkId = false;
  String? _userId;
  String? _generatedVerificationCode;
  String _emailMessage = '';
  String _verificationMessage = '';
  String _accountNameErrorMessage = '';
  String _accountNumberErrorMessage = '';
  String _pinErrorMessage = '';

  List<TextEditingController> _pinControllers = List.generate(4, (index) => TextEditingController());
  List<FocusNode> _pinFocusNodes = List.generate(4, (index) => FocusNode());

  @override
  void dispose() {
    _emailController.dispose();
    _accountNameController.dispose();
    _accountNumberController.dispose();
    _verificationCodeController.dispose();
    _pinControllers.forEach((controller) => controller.dispose());
    _emailFocusNode.dispose();
    _accountNameFocusNode.dispose();
    _accountNumberFocusNode.dispose();
    _verificationCodeFocusNode.dispose();
    _pinFocusNodes.forEach((node) => node.dispose());
    super.dispose();
  }

  //테스트 이메일 : rkqls8522@naver.com
  //저장할 아이디
  String userId = "";
  Future<void> _checkEmail() async {
    final String email = _emailController.text;

    if (email.isEmpty) {
      setState(() {
        _emailMessage = '이메일을 입력하세요.';
      });
      return;
    }

    final response = await http.get(
      Uri.parse(REST_API_URL+'/api/getUserIdByEmail?email=$email'),
    );
    userId = response.body;
    if (response.statusCode == 200) {
      final isExist = userId == 'User not found';
      setState(() {
        if (isExist) {
          _emailMessage = '이메일이 존재하지 않습니다.';
        } else {
          _emailMessage = '인증 링크를 이메일로 전송중입니다.';
          _sendVerificationLink();
        }
      });
    } else {
      setState(() {
        _emailMessage = '서버 오류가 발생했습니다.';
      });
    }
  }

  Future<void> _sendVerificationLink() async {
    final String email = _emailController.text;

    try {
      UserCredential userCredential;

      try {
        userCredential = await FirebaseAuth.instance.createUserWithEmailAndPassword(
          email: email,
          password: "dummyPassword", // 임시 비밀번호
        );
      } on FirebaseAuthException catch (e) {
        if (e.code == 'email-already-in-use') {
          // 이미 등록된 이메일인 경우 기존 사용자로 로그인하여 이메일 재인증
          userCredential = await FirebaseAuth.instance.signInWithEmailAndPassword(
            email: email,
            password: "dummyPassword", // 실제 비밀번호를 알고 있어야 함
          );
        } else {
          throw e;
        }
      }

      if (userCredential.user != null) {
        await userCredential.user!.sendEmailVerification();

        setState(() {
          _emailMessage = '인증 링크를 이메일로 전송하였습니다. 이메일을 확인하고 인증을 완료하세요.';
        });
      }
    } catch (e) {
      setState(() {
        if (e is FirebaseAuthException) {
          _emailMessage = '이메일 전송에 실패했습니다: ${e.message}';
        } else {
          _emailMessage = '알 수 없는 오류가 발생했습니다: $e';
        }
      });
    }
  }

  Future<void> _checkEmailVerified() async {
    User? user = FirebaseAuth.instance.currentUser;

    if (user != null) {
      await user.reload(); // 사용자 정보를 다시 불러옴
      if (user.emailVerified) {
        // 이메일 인증이 완료됨
        setState(() {
          _emailMessage = '이메일 인증이 완료되었습니다.';
          _isVerificationCompleted = true;
        });
      } else {
        // 이메일 인증이 아직 완료되지 않음
        setState(() {
          _emailMessage = '이메일 인증을 완료해주세요.';
          FocusScope.of(context).requestFocus(_emailFocusNode);
          _isVerificationCompleted = false;
        });
      }
    }
  }

  Future<void> _verifyOneWon() async {
    if (_accountNameController.text.isEmpty) {
      _showErrorDialog('계좌명을 입력하세요.');
      FocusScope.of(context).requestFocus(_accountNameFocusNode);
      return;
    }

    if (_accountNumberController.text.isEmpty) {
      _showErrorDialog('계좌번호를 입력하세요.');
      FocusScope.of(context).requestFocus(_accountNumberFocusNode);
      return;
    }

    if (!_isVerificationCompleted) {
      _checkEmailVerified();
      return;
    }

    setState(() {
      _showImage = true;
    });


  }

  void _handleChangePassword() {
    final String email = _emailController.text;
    final String pin = _pinControllers.map((controller) => controller.text).join();

    // 이메일 입력 여부 확인
    if (email.isEmpty) {
      _showErrorDialog('이메일을 입력하세요.');
      FocusScope.of(context).requestFocus(_emailFocusNode);
      return;
    }

    // 이메일 인증 여부 확인
    if (_emailMessage != '이메일이 인증되었습니다.') {
      _showErrorDialog('이메일 인증이 필요합니다.');
      return;
    }

    // 핀 번호 입력 여부 확인
    for (int i = 0; i < 4; i++) {
      if (_pinControllers[i].text.isEmpty) {
        _showErrorDialog('핀 번호를 모두 입력하세요.');
        FocusScope.of(context).requestFocus(_pinFocusNodes[i]);
        return;
      }
    }

    // 핀 번호 일치 여부 확인
    if (pin != '1234') { //dl
      _showErrorDialog('숫자가 일치하지 않습니다.');
      FocusScope.of(context).requestFocus(_pinFocusNodes[0]);
      return;
    }

    // ChangePasswordPage로 이동하면서 userId를 전달
    Navigator.pushNamed(
      context,
      '/change-password',
      arguments: {
        'userId': userId, // 여기에 실제로 가져온 유저 아이디를 전달해야 함
      },
    );
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
    for (int i = 0; i < 4; i++) {
      _pinControllers[i].addListener(() {
        if (_pinControllers[i].text.length == 1) {
          if (i < 3) {
            FocusScope.of(context).requestFocus(_pinFocusNodes[i + 1]);
          } else {
            FocusScope.of(context).unfocus();
          }
        }
      });

      _pinFocusNodes[i].addListener(() {
        if (_pinFocusNodes[i].hasFocus) {
          _pinControllers[i].clear();
        }
      });
    }
  }

  InputDecoration _pinDecoration(int i) {
    return InputDecoration(
      counterText: "",
      border: OutlineInputBorder(
        borderSide: BorderSide(color: _pinFocusNodes[i].hasFocus ? Colors.blue : Colors.grey),
        borderRadius: BorderRadius.circular(5),
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
                  bottom: 20,
                  child: Container(
                    width: double.infinity,
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(50),
                    ),
                    child: Stack(
                      children: [
                        Positioned.fill(
                          child: SingleChildScrollView(
                            child: Padding(
                              padding: EdgeInsets.all(50),
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Row(
                                    children: [
                                      Expanded(
                                        child: Column(
                                          crossAxisAlignment: CrossAxisAlignment.start,
                                          children: [
                                            TextField(
                                              controller: _emailController,
                                              focusNode: _emailFocusNode,
                                              decoration: InputDecoration(
                                                hintText: '이메일을 입력하세요.',
                                                border: OutlineInputBorder(
                                                  borderSide: BorderSide(color: Colors.blue),
                                                ),
                                                focusedBorder: OutlineInputBorder(
                                                  borderSide: BorderSide(color: Colors.blue),
                                                ),
                                              ),
                                              style: TextStyle(fontSize: 16),
                                            ),
                                            if (_emailMessage.isNotEmpty)
                                              Align(
                                                alignment: Alignment.centerLeft,
                                                child: Text(
                                                  _emailMessage,
                                                  style: TextStyle(
                                                    color: _emailMessage.contains('인증되었습니다')
                                                        ? Colors.black
                                                        : Colors.red,
                                                    fontSize: 12,
                                                  ),
                                                ),
                                              ),
                                          ],
                                        ),
                                      ),
                                      SizedBox(width: 20),
                                      SizedBox(
                                        width: 100,
                                        height: 50,
                                        child: ElevatedButton(
                                          onPressed: _checkEmail,
                                          style: ElevatedButton.styleFrom(
                                            backgroundColor: Colors.blue,
                                            shape: RoundedRectangleBorder(
                                              borderRadius: BorderRadius.circular(50),
                                            ),
                                          ),
                                          child: Text(
                                            '전송',
                                            style: TextStyle(
                                              color: Colors.white,
                                              fontSize: 14,
                                            ),
                                            textAlign: TextAlign.center,
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                  SizedBox(height: 20),
                                  if (_verificationMessage.isNotEmpty)
                                    Align(
                                      alignment: Alignment.centerLeft,
                                      child: Text(
                                        _verificationMessage,
                                        style: TextStyle(
                                          color: _verificationMessage == '인증되었습니다.'
                                              ? Colors.black
                                              : Colors.red,
                                          fontSize: 12,
                                        ),
                                      ),
                                    ),
                                  SizedBox(height: 50),
                                  Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      TextField(
                                        controller: _accountNameController,
                                        focusNode: _accountNameFocusNode,
                                        decoration: InputDecoration(
                                          hintText: '계좌명을 입력하세요.',
                                          border: OutlineInputBorder(
                                            borderSide: BorderSide(color: Colors.blue),
                                          ),
                                          focusedBorder: OutlineInputBorder(
                                            borderSide: BorderSide(color: Colors.blue),
                                          ),
                                        ),
                                        style: TextStyle(fontSize: 16),
                                      ),
                                      if (_accountNameErrorMessage.isNotEmpty)
                                        Align(
                                          alignment: Alignment.centerLeft,
                                          child: Text(
                                            _accountNameErrorMessage,
                                            style: TextStyle(
                                              color: Colors.red,
                                              fontSize: 12,
                                            ),
                                          ),
                                        ),
                                    ],
                                  ),
                                  SizedBox(height: 50),
                                  Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      TextField(
                                        controller: _accountNumberController,
                                        focusNode: _accountNumberFocusNode,
                                        keyboardType: TextInputType.number,
                                        decoration: InputDecoration(
                                          hintText: '계좌번호를 입력하세요.',
                                          border: OutlineInputBorder(
                                            borderSide: BorderSide(color: Colors.blue),
                                          ),
                                          focusedBorder: OutlineInputBorder(
                                            borderSide: BorderSide(color: Colors.blue),
                                          ),
                                        ),
                                        style: TextStyle(fontSize: 16),
                                      ),
                                      if (_accountNumberErrorMessage.isNotEmpty)
                                        Align(
                                          alignment: Alignment.centerLeft,
                                          child: Text(
                                            _accountNumberErrorMessage,
                                            style: TextStyle(
                                              color: Colors.red,
                                              fontSize: 12,
                                            ),
                                          ),
                                        ),
                                    ],
                                  ),
                                  SizedBox(height: 60),
                                  ElevatedButton(
                                    onPressed: _verifyOneWon,
                                    style: ElevatedButton.styleFrom(
                                      backgroundColor: Colors.blue,
                                      minimumSize: Size(double.infinity, 60),
                                    ),
                                    child: Text(
                                      '1원 인증하기',
                                      style: TextStyle(color: Colors.white),
                                    ),
                                  ),
                                  SizedBox(height: 30),
                                  if (_showImage)
                                    Column(
                                      children: [
                                        Center(
                                          child: Text(
                                            '1원을 보내드렸습니다.\n입금내역에 표시된 숫자 4자리를 입력해주세요',
                                            textAlign: TextAlign.center,
                                            style: TextStyle(
                                              color: Colors.black,
                                              fontSize: 16,
                                            ),
                                          ),
                                        ),
                                        SizedBox(height: 20),
                                        Center(
                                          child: Image.asset(
                                            "assets/images/1원인증.png",
                                            height: 200,
                                            fit: BoxFit.cover,
                                          ),
                                        ),
                                        SizedBox(height: 20),
                                        Row(
                                          mainAxisAlignment: MainAxisAlignment.center,
                                          children: List.generate(
                                            4,
                                                (index) => Container(
                                              margin: EdgeInsets.symmetric(horizontal: 5),
                                              width: 50,
                                              height: 50,
                                              child: TextField(
                                                controller: _pinControllers[index],
                                                focusNode: _pinFocusNodes[index],
                                                keyboardType: TextInputType.number,
                                                maxLength: 1,
                                                textAlign: TextAlign.center,
                                                decoration: _pinDecoration(index),
                                              ),
                                            ),
                                          ),
                                        ),
                                        if (_pinErrorMessage.isNotEmpty)
                                          Padding(
                                            padding: const EdgeInsets.only(top: 10.0),
                                            child: Text(
                                              _pinErrorMessage,
                                              style: TextStyle(
                                                color: Colors.red,
                                                fontSize: 12,
                                              ),
                                            ),
                                          ),
                                        SizedBox(height: 20),
                                        SizedBox(
                                          width: double.infinity,
                                          height: 50,
                                          child: ElevatedButton(
                                            onPressed: _handleChangePassword,
                                            style: ElevatedButton.styleFrom(
                                              backgroundColor: Colors.blue,
                                              shape: RoundedRectangleBorder(
                                                borderRadius: BorderRadius.circular(50),
                                              ),
                                            ),
                                            child: Text(
                                              'Change Password',
                                              style: TextStyle(
                                                color: Colors.white,
                                                fontSize: 16,
                                              ),
                                              textAlign: TextAlign.center,
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
                      ],
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
