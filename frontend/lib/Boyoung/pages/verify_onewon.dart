import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '/../../../config/constants.dart';

class VerifyOneWonPage extends StatefulWidget {
  @override
  _VerifyOneWonPageState createState() => _VerifyOneWonPageState();
}

class _VerifyOneWonPageState extends State<VerifyOneWonPage> {
  final TextEditingController _accountNoController = TextEditingController();
  final TextEditingController _authCodeController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    final args = ModalRoute.of(context)?.settings.arguments as Map<String, String>?;

    _accountNoController.text = args?['accountNo'] ?? '0019605725437997';
    _emailController.text = args?['email'] ?? 'user@shinhan.ssafy.com';
  }

  Future<void> _verifyOneWon() async {
    final String accountNo = _accountNoController.text;
    final String authCode = _authCodeController.text;
    final String email = _emailController.text;

    final Uri uri = Uri.parse(REST_API_URL + '/api/verify/one_won').replace(
      queryParameters: {
        'accountNo': accountNo,
        'authCode': authCode,
        'email': email,
      },
    );
    final response = await http.post(uri);
    if (response.statusCode == 200) {
      // 성공적으로 인증
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('1원 인증이 완료되었습니다.'),
      ));
      Navigator.pushReplacementNamed(
        context,
        '/challenges',
        arguments: {
          'accountNo': accountNo,
          'email': email,
        },
      );
    } else {
      // 실패
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('인증에 실패했습니다.'),
      ));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('계좌 인증 - 1원 송금 인증하기'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _authCodeController,
              decoration: InputDecoration(labelText: '인증 코드'),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _verifyOneWon,
              child: Text('인증하기'),
            ),
          ],
        ),
      ),
    );
  }
}
