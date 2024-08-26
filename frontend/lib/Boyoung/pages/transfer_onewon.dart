import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class TransferOneWonPage extends StatefulWidget {
  @override
  _TransferOneWonPageState createState() => _TransferOneWonPageState();
}

class _TransferOneWonPageState extends State<TransferOneWonPage> {
  final TextEditingController _accountNoController = TextEditingController();
  final TextEditingController _userIdController = TextEditingController();

  Future<void> _transferOneWon() async {
    final String accountNo = _accountNoController.text;
    final String email = _userIdController.text;

    final Uri uri = Uri.parse('http://192.168.0.2:8090/api/transfer/one_won').replace(
      queryParameters: {
        'accountNo': accountNo,
        'email': email,
      },
    );

    final response = await http.post(uri);
    print(response.body);
    if (response.statusCode == 200) {
      // 성공적으로 1원 송금
      print(response.body);
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('1원 송금이 완료되었습니다.'),
      ));
      Navigator.pushReplacementNamed(
        context,
        '/verify/one-won',
        arguments: {
          'accountNo': accountNo,
          'email': email,
        },
      );
    } else {
      // 실패
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('송금에 실패했습니다.'),
      ));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('계좌 인증 - 1원 송금하기'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _accountNoController,
              decoration: InputDecoration(labelText: '계좌 번호'),
            ),
            TextField(
              controller: _userIdController,
              decoration: InputDecoration(labelText: '사용자 email'),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _transferOneWon,
              child: Text('1원 송금하기'),
            ),
          ],
        ),
      ),
    );
  }
}
No newline at end of file
