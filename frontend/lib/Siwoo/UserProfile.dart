import 'package:flutter/material.dart';
import '/Jihee/FinancialAnaly.dart'; // FinancialAnaly 페이지 import

class UserProfilePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('User Profile Page'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('This is the User Profile Page'),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                // FinancialAnaly 페이지로 이동
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => FinancialAnalyPage()),
                );
              },
              child: Text('Go to Financial Analysis'),
            ),
          ],
        ),
      ),
    );
  }
}
