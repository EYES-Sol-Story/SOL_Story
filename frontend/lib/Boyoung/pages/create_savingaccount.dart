import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '/../../../config/constants.dart';

class CreateSavingAccountPage extends StatefulWidget {
  @override
  _CreateSavingAccountPageState createState() => _CreateSavingAccountPageState();
}

class _CreateSavingAccountPageState extends State<CreateSavingAccountPage> {
  final TextEditingController _accountTypeUniqueNoController = TextEditingController();
  final TextEditingController _withdrawalAccountNoController = TextEditingController();
  final TextEditingController _depositBalanceController = TextEditingController();
  final TextEditingController _userIdController = TextEditingController();
  final TextEditingController _targetAmountController = TextEditingController();

  Future<void> _createSavingAccount() async {
    final String accountTypeUniqueNo = _accountTypeUniqueNoController.text;
    final String withdrawalAccountNo = _withdrawalAccountNoController.text;
    final String depositBalance = _depositBalanceController.text;
    final String userId = _userIdController.text;
    final String targetAmount = _targetAmountController.text;

    final Uri uri = Uri.parse(REST_API_URL + '/api/savings/account').replace(
      queryParameters: {
        'accountTypeUniqueNo': accountTypeUniqueNo,
        'withdrawalAccountNo': withdrawalAccountNo,
        'depositBalance': depositBalance,
        'userId': userId,
        'targetAmount': targetAmount,
      },
    );
    final response = await http.post(uri);
    print(response.body);
    if (response.statusCode == 200) { //
      // 성공적으로 계좌 생성
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('적금 계좌가 성공적으로 생성되었습니다.'),
      ));
    } else {
      // 실패
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('계좌 생성에 실패했습니다.'),
      ));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('적금 계좌 생성'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _accountTypeUniqueNoController,
              decoration: InputDecoration(labelText: '계좌 종류 고유 번호'),
            ),
            TextField(
              controller: _withdrawalAccountNoController,
              decoration: InputDecoration(labelText: '출금 계좌 번호'),
            ),
            TextField(
              controller: _depositBalanceController,
              decoration: InputDecoration(labelText: '예금 잔액'),
              keyboardType: TextInputType.number,
            ),
            TextField(
              controller: _userIdController,
              decoration: InputDecoration(labelText: '사용자 ID'),
            ),
            TextField(
              controller: _targetAmountController,
              decoration: InputDecoration(labelText: '목표 금액'),
              keyboardType: TextInputType.number,
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _createSavingAccount,
              child: Text('적금 계좌 생성하기'),
            ),
          ],
        ),
      ),
    );
  }
}
