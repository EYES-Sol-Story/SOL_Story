import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:http/http.dart' as http;
import 'login_screen.dart';
import 'user_detail_screen.dart'; // 회원가입 후 이동할 화면

//가빈의 회원가입페이지
class SignUpScreen extends StatefulWidget {
  @override
  _SignUpScreenState createState() => _SignUpScreenState();
}

class _SignUpScreenState extends State<SignUpScreen> {
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _useridController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _birthDateController = TextEditingController();
  final FocusNode _nameFocusNode = FocusNode();
  final FocusNode _useridFocusNode = FocusNode();
  final FocusNode _passwordFocusNode = FocusNode();
  final FocusNode _emailFocusNode = FocusNode();
  final FocusNode _birthDateFocusNode = FocusNode();

  bool _obscurePassword = true;
  bool _isUseridAvailable = false;
  bool _isVerificationCodeSent = false;
  bool _isVerificationCompleted = false;
  bool _isEmailAvailable = false; // 이메일 중복 확인 여부
  String _selectedGender = "남자"; // 성별 선택의 기본값은 "남자"

  String _passwordWarning = '';
  String _passwordSpecialCharWarning = '';
  String _passwordUppercaseWarning = '';
  String _passwordInvalidCharWarning = '';
  String _useridMessage = '';
  String _emailMessage = '';
  String _verificationMessage = '';
  String _birthDateWarning = '';

  @override
  void dispose() {
    _nameController.dispose();
    _useridController.dispose();
    _passwordController.dispose();
    _emailController.dispose();
    _birthDateController.dispose();
    _nameFocusNode.dispose();
    _useridFocusNode.dispose();
    _passwordFocusNode.dispose();
    _emailFocusNode.dispose();
    _birthDateFocusNode.dispose();
    super.dispose();
  }

  Future<void> _checkUserid() async {
    final String userid = _useridController.text;

    if (userid.isEmpty) {
      setState(() {
        _useridMessage = '아이디를 입력하세요.';
        _isUseridAvailable = false;
      });
      return;
    }

    final response = await http.get(
      Uri.parse('http://10.0.2.2:8090/api/check-userid?userid=$userid'),
    );

    if (response.statusCode == 200) {
      final isExist = response.body == 'true';
      setState(() {
        if (isExist) {
          _useridMessage = '아이디가 중복되었습니다.';
          _isUseridAvailable = false;
        } else {
          _useridMessage = '사용가능한 아이디입니다.';
          _isUseridAvailable = true;
        }
      });
    } else {
      setState(() {
        _useridMessage = '서버 오류가 발생했습니다.';
        _isUseridAvailable = false;
      });
    }
  }

  Future<void> _checkEmail() async {
    final String email = _emailController.text;

    if (email.isEmpty) {
      setState(() {
        _emailMessage = '이메일을 입력하세요.';
        _isEmailAvailable = false;
      });
      return;
    }

    final response = await http.get(
      Uri.parse('http://10.0.2.2:8090/api/check-email?email=$email'),
    );

    if (response.statusCode == 200) {
      final isExist = response.body == 'true';
      setState(() {
        if (isExist) {
          _emailMessage = '이메일이 존재합니다. 다른 이메일을 입력해주세요.';
          _isEmailAvailable = false;
        } else {
          _emailMessage = '인증 링크를 이메일로 전송중입니다.';
          _sendVerificationLink();
          _isEmailAvailable = true;
        }
      });
    } else {
      setState(() {
        _emailMessage = '서버 오류가 발생했습니다.';
        _isEmailAvailable = false;
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
            password: "dummyPassword", //
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
          print(e);
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
          _isVerificationCompleted = false;
        });
      }
    }
  }

  void _handleSignUp() async {
    final String name = _nameController.text;
    final String userid = _useridController.text;
    final String password = _passwordController.text;
    final String email = _emailController.text;
    final String birthDate = _birthDateController.text;
    final String gender = _selectedGender;

    final bool isPasswordValid = _passwordWarning.isEmpty &&
        _passwordSpecialCharWarning.isEmpty &&
        _passwordUppercaseWarning.isEmpty &&
        _passwordInvalidCharWarning.isEmpty;

    if (name.isEmpty) {
      _showErrorDialog('이름을 입력하세요.');
      FocusScope.of(context).requestFocus(_nameFocusNode);
      return;
    }

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

    if (email.isEmpty) {
      _showErrorDialog('이메일을 입력하세요.');
      FocusScope.of(context).requestFocus(_emailFocusNode);
      return;
    }

    if (birthDate.isEmpty) {
      _showErrorDialog('생년월일을 입력하세요.');
      FocusScope.of(context).requestFocus(_birthDateFocusNode);
      return;
    }

    if (_birthDateWarning.isNotEmpty) {
      _showErrorDialog('생년월일이 형식에 맞지 않습니다.');
      FocusScope.of(context).requestFocus(_birthDateFocusNode);
      return;
    }

    if (!_isUseridAvailable) {
      _showErrorDialog('아이디가 중복되었습니다.');
      return;
    }

    await _checkEmailVerified();

    if (!_isVerificationCompleted) {
      _showErrorDialog('이메일 인증을 완료해주세요.');
      return;
    }

    if (!isPasswordValid) {
      _showErrorDialog('비밀번호가 조건에 맞지 않습니다.');
      return;
    }

    // 회원가입 처리 및 user_no 조회
    try {
      final response = await http.post(
        Uri.parse('http://10.0.2.2:8090/api/signup'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          'name': name,
          'userid': userid,
          'password': password,
          'email': email,
          'birthDate': birthDate,
          'gender': gender
        }),
      );

      if (response.statusCode == 200) {
        final userNo = jsonDecode(response.body)['user_no']; // 서버에서 받은 user_no
        if (!mounted) return;
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(
            builder: (context) => user_detail_screen(userNo: userNo), // user_no를 넘겨줌
          ),
        );
      } else {
        _showErrorDialog('회원가입에 실패했습니다.');
      }
    } catch (error) {
      _showErrorDialog('오류가 발생했습니다: $error');
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

    _birthDateController.addListener(() {
      final String birthDate = _birthDateController.text;

      setState(() {
        _birthDateWarning =
        RegExp(r'^\d{4}\d{2}\d{2}$').hasMatch(birthDate) ? '' : '형식에 맞지 않습니다.';
      });
    });

    _useridController.addListener(() {
      setState(() {
        _useridMessage = '';
        _isUseridAvailable = false;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: GestureDetector(
        onTap: () => FocusScope.of(context).unfocus(),
        child: Stack(
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
              left: 30,
              right: 30,
              bottom: 30,
              child: Stack(
                children: [
                  Container(
                    width: double.infinity,
                    padding: EdgeInsets.all(40),
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(50),
                    ),
                  ),
                  Positioned(
                    top: 0,
                    left: 0,
                    right: 0,
                    bottom: 0,
                    child: SingleChildScrollView(
                      child: Container(
                        padding: EdgeInsets.all(40),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            // 이름 입력 필드와 성별 선택 라디오 버튼
                            Row(
                              children: [
                                Expanded(
                                  child: TextField(
                                    controller: _nameController,
                                    focusNode: _nameFocusNode,
                                    decoration: InputDecoration(
                                      hintText: '이름을 입력하세요.',
                                      border: OutlineInputBorder(
                                        borderSide: BorderSide(color: Colors.blue),
                                      ),
                                      focusedBorder: OutlineInputBorder(
                                        borderSide: BorderSide(color: Colors.blue),
                                      ),
                                    ),
                                    style: TextStyle(fontSize: 16),
                                  ),
                                ),
                                SizedBox(width: 20),
                                Column(
                                  children: [
                                    Row(
                                      children: [
                                        Radio(
                                          value: "남자",
                                          groupValue: _selectedGender,
                                          onChanged: (String? value) {
                                            setState(() {
                                              _selectedGender = value!;
                                            });
                                          },
                                        ),
                                        Text("남자"),
                                      ],
                                    ),
                                    Row(
                                      children: [
                                        Radio(
                                          value: "여자",
                                          groupValue: _selectedGender,
                                          onChanged: (String? value) {
                                            setState(() {
                                              _selectedGender = value!;
                                            });
                                          },
                                        ),
                                        Text("여자"),
                                      ],
                                    ),
                                  ],
                                ),
                              ],
                            ),
                            SizedBox(height: 30),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                TextField(
                                  controller: _birthDateController,
                                  focusNode: _birthDateFocusNode,
                                  keyboardType: TextInputType.number,
                                  decoration: InputDecoration(
                                    hintText: 'YYYYMMDD 형식으로 입력하세요.',
                                    border: OutlineInputBorder(
                                      borderSide: BorderSide(color: Colors.blue),
                                    ),
                                    focusedBorder: OutlineInputBorder(
                                      borderSide: BorderSide(color: Colors.blue),
                                    ),
                                  ),
                                  style: TextStyle(fontSize: 16),
                                ),
                                if (_birthDateWarning.isNotEmpty)
                                  Align(
                                    alignment: Alignment.centerLeft,
                                    child: Text(
                                      _birthDateWarning,
                                      style: TextStyle(
                                        color: Colors.black, // 검정색 글자
                                        fontSize: 12,
                                      ),
                                    ),
                                  ),
                              ],
                            ),
                            SizedBox(height: 30),
                            Row(
                              children: [
                                Expanded(
                                  child: Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
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
                                        style: TextStyle(fontSize: 16),
                                      ),
                                      if (_useridMessage.isNotEmpty)
                                        Align(
                                          alignment: Alignment.centerLeft,
                                          child: Text(
                                            _useridMessage,
                                            style: TextStyle(
                                              color: Colors.black, // 검정색 글자
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
                                    onPressed: _checkUserid,
                                    style: ElevatedButton.styleFrom(
                                      backgroundColor: Colors.blue,
                                      shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(50),
                                      ),
                                    ),
                                    child: Text(
                                      '아이디 중복확인',
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
                            SizedBox(height: 30),
                            // 비밀번호 입력 필드
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
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
                                    suffixIcon: IconButton(
                                      icon: Icon(
                                        _obscurePassword
                                            ? Icons.visibility
                                            : Icons.visibility_off,
                                      ),
                                      onPressed: () {
                                        setState(() {
                                          _obscurePassword = !_obscurePassword;
                                        });
                                      },
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
                                        color: Colors.red, // 경고 메세지 빨간색
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
                                        color: Colors.red, // 경고 메세지 빨간색
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
                                        color: Colors.red, // 경고 메세지 빨간색
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
                                        color: Colors.red, // 경고 메세지 빨간색
                                        fontSize: 12,
                                      ),
                                    ),
                                  ),
                              ],
                            ),
                            SizedBox(height: 30),
                            Row(
                              children: [
                                Expanded(
                                  child: TextField(
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
                                ),
                                SizedBox(width: 20),
                                SizedBox(
                                  width: 100,
                                  height: 50,
                                  child: ElevatedButton(
                                    onPressed: _checkEmail, // 이메일 중복확인 후 Firebase 인증 이메일 전송
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
                            SizedBox(height: 10),
                            Text(_emailMessage, style: TextStyle(color: Colors.black, fontSize: 12)),
                            SizedBox(height: 30),
                            SizedBox(
                              width: double.infinity,
                              height: 50,
                              child: ElevatedButton(
                                onPressed: _handleSignUp, // 회원가입 처리
                                style: ElevatedButton.styleFrom(
                                  backgroundColor: Colors.blue,
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(50),
                                  ),
                                ),
                                child: Text(
                                  '회원가입',
                                  style: TextStyle(color: Colors.white, fontSize: 16),
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
