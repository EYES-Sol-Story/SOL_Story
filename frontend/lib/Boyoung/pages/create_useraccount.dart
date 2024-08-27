import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;

import '../models/user_account_model.dart';
import '/../../../config/constants.dart';

class CreateUserAccountPage extends StatefulWidget {
  @override
  _CreateUserAccountPageState createState() => _CreateUserAccountPageState();
}

class _CreateUserAccountPageState extends State<CreateUserAccountPage> {
  final TextEditingController _userIdController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  bool _isLoading = false;
  String? _responseMessage;

  Future<void> _createUserAccount() async {
    final String userId = _userIdController.text;
    final String email = _emailController.text;

    setState(() {
      _isLoading = true;
      _responseMessage = null;
    });

    final Uri uri = Uri.parse(REST_API_URL + '/api/user/account').replace(
      queryParameters: {
        'userId': userId,
        'email': email,
      },
    );

    try {
      final response = await http.post(uri);
      print(response.body);
      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        final userAccount = UserAccount.fromJson(data);

        final prefs = await SharedPreferences.getInstance();
        await prefs.setString('userEmail', email);

        setState(() {
          _responseMessage = '계정이 성공적으로 생성되었습니다: ${userAccount.username}';
        });
        // 계정 생성 성공 후, 1원 송금 페이지로 이동
        Navigator.pushReplacementNamed(context, '/transfer/one-won');
      } else {
        setState(() {
          _responseMessage = '계정 생성에 실패했습니다: ${response.reasonPhrase}';
        });
      }

      // 계정 생성 성공 후, 1원 송금 페이지로 이동
      Navigator.pushReplacementNamed(context, '/transfer/one-won');
    } catch (e) {
      setState(() {
        _responseMessage = '서버와의 연결에 실패했습니다: $e';
      });
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('사용자 계정 생성하기'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              '챌린지를 진행하기 위해서 사용자 계정 생성이 필요합니다.',
              style: TextStyle(
                color: Colors.red,
                fontSize: 16,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(height: 20), // 문구와 입력 필드 사이의 간격
            TextField(
              controller: _userIdController,
              decoration: InputDecoration(labelText: '사용자 ID'),
            ),
            TextField(
              controller: _emailController,
              decoration: InputDecoration(labelText: '이메일'),
              keyboardType: TextInputType.emailAddress,
            ),
            SizedBox(height: 20),
            _isLoading
                ? CircularProgressIndicator()
                : ElevatedButton(
              child: Text('계정 생성하기'),
              onPressed: _createUserAccount,
            ),
            if (_responseMessage != null)
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Text(
                  _responseMessage!,
                  style: TextStyle(color: Colors.red),
                ),
              ),
          ],
        ),
      ),
    );
  }
}
