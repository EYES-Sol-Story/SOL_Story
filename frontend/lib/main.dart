import 'package:flutter/material.dart';
import 'dart:async';

void main() {
  runApp(MyApp());
}

// 초기 로딩 페이지
class EyesIconPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    Future.delayed(Duration(seconds: 2), () {
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => LoginScreen()),
      );
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

// 페이지 호출 함수 영역
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SOL_Story_Login',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: EyesIconPage(), // 초기 로딩 페이지를 처음에 표시
      routes: {
        '/forgot-account': (context) => ForgotAccountPage(),
        '/join': (context) => SignUpScreen(),
        '/change-password': (context) => ChangePasswordPage(),
      },
    );
  }
}

// 로그인 페이지
class LoginScreen extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final FocusNode _usernameFocusNode = FocusNode();
  final FocusNode _passwordFocusNode = FocusNode();

  bool _obscurePassword = true;

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    _usernameFocusNode.dispose();
    _passwordFocusNode.dispose();
    super.dispose();
  }

  void _handleLogin() {
    final String username = _usernameController.text;
    final String password = _passwordController.text;

    if (username.isEmpty) {
      _showErrorDialog('아이디를 입력하세요.');
      FocusScope.of(context).requestFocus(_usernameFocusNode);
      return;
    }

    if (password.isEmpty) {
      _showErrorDialog('비밀번호를 입력하세요.');
      FocusScope.of(context).requestFocus(_passwordFocusNode);
      return;
    }

    print('아이디받아옴 : $username');
    print('비밀번호받아옴 : $password');
  }

  void _showErrorDialog(String message) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(''),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.of(context).pop();
            },
            child: Text('확인'),
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
                              controller: _usernameController,
                              focusNode: _usernameFocusNode,
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

// 회원가입 페이지
class SignUpScreen extends StatefulWidget {
  @override
  _SignUpScreenState createState() => _SignUpScreenState();
}

class _SignUpScreenState extends State<SignUpScreen> {
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _phoneController = TextEditingController();
  final TextEditingController _accountNameController = TextEditingController();
  final TextEditingController _accountNumberController = TextEditingController();
  final TextEditingController _ageController = TextEditingController();
  final TextEditingController _birthDateController = TextEditingController();
  final FocusNode _usernameFocusNode = FocusNode();
  final FocusNode _passwordFocusNode = FocusNode();
  final FocusNode _emailFocusNode = FocusNode();
  final FocusNode _phoneFocusNode = FocusNode();
  final FocusNode _accountNameFocusNode = FocusNode();
  final FocusNode _accountNumberFocusNode = FocusNode();
  final FocusNode _ageFocusNode = FocusNode();
  final FocusNode _birthDateFocusNode = FocusNode();

  bool _obscurePassword = true;
  bool _showImage = false;
  bool _isUsernameAvailable = false;
  bool _isEmailVerified = false;
  List<TextEditingController> _pinControllers =
  List.generate(4, (index) => TextEditingController());
  List<FocusNode> _pinFocusNodes =
  List.generate(4, (index) => FocusNode());

  String _passwordWarning = '';
  String _passwordSpecialCharWarning = '';
  String _passwordUppercaseWarning = '';
  String _passwordInvalidCharWarning = '';
  String _usernameMessage = '';
  String _emailMessage = '';
  String _birthDateWarning = '';
  String _pinErrorMessage = '';
  String _errorMessage = '';

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    _emailController.dispose();
    _phoneController.dispose();
    _accountNameController.dispose();
    _accountNumberController.dispose();
    _ageController.dispose();
    _birthDateController.dispose();
    _pinControllers.forEach((controller) => controller.dispose());
    _usernameFocusNode.dispose();
    _passwordFocusNode.dispose();
    _emailFocusNode.dispose();
    _phoneFocusNode.dispose();
    _accountNameFocusNode.dispose();
    _accountNumberFocusNode.dispose();
    _ageFocusNode.dispose();
    _birthDateFocusNode.dispose();
    _pinFocusNodes.forEach((node) => node.dispose());
    super.dispose();
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


    setState(() {
      _showImage = true;
    });
  }

  void _handleSignUp() {
    final String username = _usernameController.text;
    final String password = _passwordController.text;
    final String email = _emailController.text;
    final String phone = _phoneController.text;
    final String accountName = _accountNameController.text;
    final String accountNumber = _accountNumberController.text;
    final String age = _ageController.text;
    final String birthDate = _birthDateController.text;
    final String pin = _pinControllers.map((controller) => controller.text).join();

    final bool isPasswordValid = _passwordWarning.isEmpty &&
        _passwordSpecialCharWarning.isEmpty &&
        _passwordUppercaseWarning.isEmpty &&
        _passwordInvalidCharWarning.isEmpty;

    if (username.isEmpty) {
      _showErrorDialog('아이디를 입력하세요.');
      FocusScope.of(context).requestFocus(_usernameFocusNode);
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

    if (phone.isEmpty) {
      _showErrorDialog('휴대폰 번호를 입력하세요.');
      FocusScope.of(context).requestFocus(_phoneFocusNode);
      return;
    }

    if (accountName.isEmpty) {
      _showErrorDialog('계좌명을 입력하세요.');
      FocusScope.of(context).requestFocus(_accountNameFocusNode);
      return;
    }

    if (accountNumber.isEmpty) {
      _showErrorDialog('계좌번호를 입력하세요.');
      FocusScope.of(context).requestFocus(_accountNumberFocusNode);
      return;
    }

    if (age.isEmpty) {
      _showErrorDialog('나이를 입력하세요.');
      FocusScope.of(context).requestFocus(_ageFocusNode);
      return;
    }

    if (birthDate.isEmpty) {
      _showErrorDialog('생년월일을 입력하세요.');
      FocusScope.of(context).requestFocus(_birthDateFocusNode);
      return;
    }

    if (pin.isEmpty) {
      _showErrorDialog('핀 번호를 입력하세요.');
      FocusScope.of(context).requestFocus(_pinFocusNodes[0]);
      return;
    }

    if (!_isUsernameAvailable) {
      _showErrorDialog('아이디가 중복되었습니다.');
    } else if (!_isEmailVerified) {
      _showErrorDialog('이메일이 인증되지 않았습니다.');
    } else if (!isPasswordValid) {
      _showErrorDialog('비밀번호가 조건에 맞지 않습니다.');
    } else if (_birthDateWarning.isNotEmpty) {
      _showErrorDialog('생년월일이 형식에 맞지 않습니다.');
    } else if (pin.length != 4 || pin != '1234') {
      _showErrorDialog('숫자가 일치하지 않습니다.');
    } else {
      _showSuccessDialog();
    }
  }

  void _showSuccessDialog() {
    showDialog(
      context: context,
      builder: (context) => Center(
        child: Container(
          width: MediaQuery.of(context).size.width * 0.8,
          height: 300,
          child: AlertDialog(
            title: Center(child: Text('회원가입 성공')),
            content: Center(
              child: Text(
                '가입을 환영합니다!\n이제 드라마를 만들 시간입니다!',
                textAlign: TextAlign.center,
              ),
            ),
            actions: [
              Center(
                child: ElevatedButton(
                  onPressed: () {
                    print("아이디: ${_usernameController.text}");
                    print("비밀번호: ${_passwordController.text}");
                    print("이메일: ${_emailController.text}");
                    print("휴대폰 번호: ${_phoneController.text}");
                    print("계좌명: ${_accountNameController.text}");
                    print("계좌번호: ${_accountNumberController.text}");
                    print("나이: ${_ageController.text}");
                    print("생년월일: ${_birthDateController.text}");
                    Navigator.pushAndRemoveUntil(
                      context,
                      MaterialPageRoute(builder: (context) => LoginScreen()),
                          (Route<dynamic> route) => false,
                    );
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.blue,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                  ),
                  child: Text(
                    '확인',
                    style: TextStyle(color: Colors.white),
                  ),
                ),
              ),
            ],
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10),
              side: BorderSide(color: Colors.blue, width: 2),
            ),
          ),
        ),
      ),
    );
  }

  void _showErrorDialog(String message) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('오류'),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.of(context).pop();
            },
            child: Text('확인'),
          ),
        ],
      ),
    );
  }

  void _checkUsername() {
    final String username = _usernameController.text;
    if (username == "testuser") {
      setState(() {
        _usernameMessage = '아이디가 중복되었습니다.';
        _isUsernameAvailable = false;
      });
    } else {
      setState(() {
        _usernameMessage = '사용가능한 아이디입니다.';
        _isUsernameAvailable = true;
      });
    }
  }

  void _verifyEmail() {
    final String email = _emailController.text;
    if (email == "test@example.com") {
      setState(() {
        _emailMessage = '이메일이 인증되었습니다.';
        _isEmailVerified = true;
      });
    } else {
      setState(() {
        _emailMessage = '이메일이 인증되지 않았습니다.';
        _isEmailVerified = false;
      });
    }
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
    }
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
                            Row(
                              children: [
                                Expanded(
                                  child: TextField(
                                    controller: _ageController,
                                    focusNode: _ageFocusNode,
                                    keyboardType: TextInputType.number,
                                    decoration: InputDecoration(
                                      hintText: '나이를 입력하세요.',
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
                                  Text(
                                    _birthDateWarning,
                                    style: TextStyle(
                                      color: Colors.red,
                                      fontSize: 12,
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
                                        controller: _usernameController,
                                        focusNode: _usernameFocusNode,
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
                                      if (_usernameMessage.isNotEmpty)
                                        Text(
                                          _usernameMessage,
                                          style: TextStyle(
                                            color: _usernameMessage.contains('사용가능')
                                                ? Colors.black
                                                : Colors.red,
                                            fontSize: 12,
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
                                    onPressed: _checkUsername,
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
                                  Text(
                                    _passwordWarning,
                                    style: TextStyle(
                                      color: Colors.black,
                                      fontSize: 12,
                                    ),
                                  ),
                                if (_passwordSpecialCharWarning.isNotEmpty)
                                  Text(
                                    _passwordSpecialCharWarning,
                                    style: TextStyle(
                                      color: Colors.black,
                                      fontSize: 12,
                                    ),
                                  ),
                                if (_passwordUppercaseWarning.isNotEmpty)
                                  Text(
                                    _passwordUppercaseWarning,
                                    style: TextStyle(
                                      color: Colors.black,
                                      fontSize: 12,
                                    ),
                                  ),
                                if (_passwordInvalidCharWarning.isNotEmpty)
                                  Text(
                                    _passwordInvalidCharWarning,
                                    style: TextStyle(
                                      color: Colors.red,
                                      fontSize: 12,
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
                                        Text(
                                          _emailMessage,
                                          style: TextStyle(
                                            color: _emailMessage.contains('인증되었습니다')
                                                ? Colors.black
                                                : Colors.red,
                                            fontSize: 12,
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
                                    onPressed: _verifyEmail,
                                    style: ElevatedButton.styleFrom(
                                      backgroundColor: Colors.blue,
                                      shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(50),
                                      ),
                                    ),
                                    child: Text(
                                      '이메일 인증',
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
                            Row(
                              children: [
                                Expanded(
                                  child: TextField(
                                    controller: _phoneController,
                                    focusNode: _phoneFocusNode,
                                    keyboardType: TextInputType.number,
                                    decoration: InputDecoration(
                                      hintText: '전화번호를 입력하세요.',
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
                              ],
                            ),
                            SizedBox(height: 30),
                            Row(
                              children: [
                                Expanded(
                                  child: TextField(
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
                                ),
                              ],
                            ),
                            SizedBox(height: 30),
                            Row(
                              children: [
                                Expanded(
                                  child: TextField(
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
                                ),
                              ],
                            ),
                            SizedBox(height: 30),
                            SizedBox(
                              width: double.infinity,
                              height: 50,
                              child: ElevatedButton(
                                onPressed: _verifyOneWon,
                                style: ElevatedButton.styleFrom(
                                  backgroundColor: Colors.blue,
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(50),
                                  ),
                                ),
                                child: Text(
                                  '1원 인증하기',
                                  style: TextStyle(
                                      color: Colors.white,
                                      fontSize: 16),
                                  textAlign: TextAlign.center,
                                ),
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
                                          decoration: InputDecoration(
                                            counterText: "",
                                            border: OutlineInputBorder(
                                              borderSide:
                                              BorderSide(color: Colors.blue),
                                              borderRadius:
                                              BorderRadius.circular(5),
                                            ),
                                          ),
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
                                      onPressed: _handleSignUp,
                                      style: ElevatedButton.styleFrom(
                                        backgroundColor: Colors.blue,
                                        shape: RoundedRectangleBorder(
                                          borderRadius: BorderRadius.circular(50),
                                        ),
                                      ),
                                      child: Text(
                                        'join',
                                        style: TextStyle(
                                            color: Colors.white,
                                            fontSize: 16),
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
          ],
        ),
      ),
    );
  }
}

// 계정찾기 페이지
class ForgotAccountPage extends StatefulWidget {
  @override
  _ForgotAccountPageState createState() => _ForgotAccountPageState();
}

class _ForgotAccountPageState extends State<ForgotAccountPage> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _accountNameController = TextEditingController();
  final TextEditingController _accountNumberController = TextEditingController();
  final FocusNode _emailFocusNode = FocusNode();
  final FocusNode _accountNameFocusNode = FocusNode();
  final FocusNode _accountNumberFocusNode = FocusNode();

  bool _showImage = false;
  List<TextEditingController> _pinControllers =
  List.generate(4, (index) => TextEditingController());
  List<FocusNode> _pinFocusNodes =
  List.generate(4, (index) => FocusNode());

  String _pinErrorMessage = '';
  String _emailMessage = '';
  String _accountNameErrorMessage = '';
  String _accountNumberErrorMessage = '';

  @override
  void dispose() {
    _emailController.dispose();
    _accountNameController.dispose();
    _accountNumberController.dispose();
    _pinControllers.forEach((controller) => controller.dispose());
    _emailFocusNode.dispose();
    _accountNameFocusNode.dispose();
    _accountNumberFocusNode.dispose();
    _pinFocusNodes.forEach((node) => node.dispose());
    super.dispose();
  }

  void _verifyEmail() {
    final String email = _emailController.text;
    if (email == "test@example.com") {
      setState(() {
        _emailMessage = '이메일이 인증되었습니다.';
      });
    } else {
      setState(() {
        _emailMessage = '이메일이 인증되지 않았습니다.';
      });
    }
  }

  Future<void> _verifyOneWon() async {
    if (_accountNameController.text.isEmpty) {
      setState(() {
        _accountNameErrorMessage = '입력이 되지 않았습니다.';
      });
      FocusScope.of(context).requestFocus(_accountNameFocusNode);
    } else {
      setState(() {
        _accountNameErrorMessage = '';
      });
    }

    if (_accountNumberController.text.isEmpty) {
      setState(() {
        _accountNumberErrorMessage = '입력이 되지 않았습니다.';
      });
      FocusScope.of(context).requestFocus(_accountNumberFocusNode);
    } else {
      setState(() {
        _accountNumberErrorMessage = '';
      });
    }

    if (_accountNameErrorMessage.isEmpty &&
        _accountNumberErrorMessage.isEmpty) {
      setState(() {
        _showImage = true;
      });
    }
  }

  void _handleChangePassword() {
    final String email = _emailController.text;
    final String accountName = _accountNameController.text;
    final String accountNumber = _accountNumberController.text;
    final String pin = _pinControllers.map((controller) => controller.text).join();

    if (email.isEmpty) {
      _showErrorDialog('이메일을 입력하세요.');
      FocusScope.of(context).requestFocus(_emailFocusNode);
      return;
    } else if (accountName.isEmpty) {
      _showErrorDialog('계좌명을 입력하세요.');
      FocusScope.of(context).requestFocus(_accountNameFocusNode);
      return;
    } else if (accountNumber.isEmpty) {
      _showErrorDialog('계좌번호를 입력하세요.');
      FocusScope.of(context).requestFocus(_accountNumberFocusNode);
      return;
    } else if (pin.isEmpty) {
      _showErrorDialog('핀 번호를 입력하세요.');
      FocusScope.of(context).requestFocus(_pinFocusNodes[0]);
      return;
    }

    if (!_emailMessage.contains('인증되었습니다')) {
      _showErrorDialog('이메일이 인증되지 않았습니다.');
    } else if (pin.length != 4 || pin != '1234') {
      _showErrorDialog('숫자가 일치하지 않습니다.');
    } else {
      Navigator.pushNamed(context, '/change-password');
    }
  }

  void _showErrorDialog(String message) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('오류'),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.of(context).pop();
            },
            child: Text('확인'),
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
    }
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
                                                  borderSide:
                                                  BorderSide(color: Colors.blue),
                                                ),
                                                focusedBorder: OutlineInputBorder(
                                                  borderSide:
                                                  BorderSide(color: Colors.blue),
                                                ),
                                              ),
                                              style: TextStyle(fontSize: 16),
                                            ),
                                            if (_emailMessage.isNotEmpty)
                                              Text(
                                                _emailMessage,
                                                style: TextStyle(
                                                  color: _emailMessage.contains('인증되었습니다')
                                                      ? Colors.black
                                                      : Colors.red,
                                                  fontSize: 12,
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
                                          onPressed: _verifyEmail,
                                          style: ElevatedButton.styleFrom(
                                            backgroundColor: Colors.blue,
                                            shape: RoundedRectangleBorder(
                                              borderRadius: BorderRadius.circular(50),
                                            ),
                                          ),
                                          child: Text(
                                            '이메일 인증',
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
                                            borderSide:
                                            BorderSide(color: Colors.blue),
                                          ),
                                          focusedBorder: OutlineInputBorder(
                                            borderSide:
                                            BorderSide(color: Colors.blue),
                                          ),
                                        ),
                                        style: TextStyle(fontSize: 16),
                                      ),
                                      if (_accountNameErrorMessage.isNotEmpty)
                                        Text(
                                          _accountNameErrorMessage,
                                          style: TextStyle(
                                            color: Colors.red,
                                            fontSize: 12,
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
                                            borderSide:
                                            BorderSide(color: Colors.blue),
                                          ),
                                          focusedBorder: OutlineInputBorder(
                                            borderSide:
                                            BorderSide(color: Colors.blue),
                                          ),
                                        ),
                                        style: TextStyle(fontSize: 16),
                                      ),
                                      if (_accountNumberErrorMessage.isNotEmpty)
                                        Text(
                                          _accountNumberErrorMessage,
                                          style: TextStyle(
                                            color: Colors.red,
                                            fontSize: 12,
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
                                                decoration: InputDecoration(
                                                  counterText: "",
                                                  border: OutlineInputBorder(
                                                    borderSide:
                                                    BorderSide(color: Colors.blue),
                                                    borderRadius:
                                                    BorderRadius.circular(5),
                                                  ),
                                                ),
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

// 비밀번호 변경 페이지
class ChangePasswordPage extends StatefulWidget {
  @override
  _ChangePasswordPageState createState() => _ChangePasswordPageState();
}

class _ChangePasswordPageState extends State<ChangePasswordPage> {
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final FocusNode _usernameFocusNode = FocusNode();
  final FocusNode _passwordFocusNode = FocusNode();

  String _passwordWarning = '';
  String _passwordSpecialCharWarning = '';
  String _passwordUppercaseWarning = '';
  String _passwordInvalidCharWarning = '';
  String _usernameMessage = '';
  bool _isUsernameAvailable = false;
  bool _obscurePassword = true;

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    _usernameFocusNode.dispose();
    _passwordFocusNode.dispose();
    super.dispose();
  }

  void _handleChangePassword() {
    final String username = _usernameController.text;
    final String password = _passwordController.text;

    final bool isPasswordValid = _passwordWarning.isEmpty &&
        _passwordSpecialCharWarning.isEmpty &&
        _passwordUppercaseWarning.isEmpty &&
        _passwordInvalidCharWarning.isEmpty;

    if (username.isEmpty) {
      _showErrorDialog('아이디를 입력하세요.');
      FocusScope.of(context).requestFocus(_usernameFocusNode);
      return;
    } else if (password.isEmpty) {
      _showErrorDialog('비밀번호를 입력하세요.');
      FocusScope.of(context).requestFocus(_passwordFocusNode);
      return;
    }

    if (!isPasswordValid) {
      _showErrorDialog('비밀번호가 조건에 맞지 않습니다.');
    } else {
      Navigator.pushAndRemoveUntil(
        context,
        MaterialPageRoute(builder: (context) => LoginScreen()),
            (Route<dynamic> route) => false,
      );
    }
  }

  void _showErrorDialog(String message) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('오류'),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.of(context).pop();
            },
            child: Text('확인'),
          ),
        ],
      ),
    );
  }

  void _checkUsername() {
    final String username = _usernameController.text;
    if (username == "testuser") {
      setState(() {
        _usernameMessage = '아이디가 중복되었습니다.';
        _isUsernameAvailable = false;
      });
    } else {
      setState(() {
        _usernameMessage = '사용가능한 아이디입니다.';
        _isUsernameAvailable = true;
      });
    }
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
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Row(
                              children: [
                                Expanded(
                                  child: TextField(
                                    controller: _usernameController,
                                    focusNode: _usernameFocusNode,
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
                                ),
                                SizedBox(width: 20),
                                SizedBox(
                                  width: 100,
                                  height: 50,
                                  child: ElevatedButton(
                                    onPressed: _checkUsername,
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
                            if (_usernameMessage.isNotEmpty)
                              Text(
                                _usernameMessage,
                                style: TextStyle(
                                  color: _usernameMessage.contains('사용가능')
                                      ? Colors.black
                                      : Colors.red,
                                  fontSize: 12,
                                ),
                              ),
                            SizedBox(height: 50),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
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
                                  Text(
                                    _passwordWarning,
                                    style: TextStyle(
                                      color: Colors.black,
                                      fontSize: 12,
                                    ),
                                  ),
                                if (_passwordSpecialCharWarning.isNotEmpty)
                                  Text(
                                    _passwordSpecialCharWarning,
                                    style: TextStyle(
                                      color: Colors.black,
                                      fontSize: 12,
                                    ),
                                  ),
                                if (_passwordUppercaseWarning.isNotEmpty)
                                  Text(
                                    _passwordUppercaseWarning,
                                    style: TextStyle(
                                      color: Colors.black,
                                      fontSize: 12,
                                    ),
                                  ),
                                if (_passwordInvalidCharWarning.isNotEmpty)
                                  Text(
                                    _passwordInvalidCharWarning,
                                    style: TextStyle(
                                      color: Colors.red,
                                      fontSize: 12,
                                    ),
                                  ),
                              ],
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
