import 'package:flutter/material.dart';
import 'package:sol_story/Siwoo/UserProfile.dart'; // UserProfile 페이지 import
import 'package:sol_story/Boyoung/GoalList.dart'; // GoalList 페이지 import
import 'package:sol_story/Sungjun/Scenario.dart'; // Scenario 페이지 import

class MainPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Main Page'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () {
                // UserProfile 페이지로 이동
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => UserProfilePage()),
                );
              },
              child: Text('User Profile'),
            ),
            ElevatedButton(
              onPressed: () {
                // GoalList 페이지로 이동
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => GoalListPage()),
                );
              },
              child: Text('Goal List'),
            ),
            ElevatedButton(
              onPressed: () {
                // Scenario 페이지로 이동
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => ScenarioPage()),
                );
              },
              child: Text('Scenario'),
            ),
            SizedBox(height: 20), // 여백 추가
            ElevatedButton(
              onPressed: () {
                // 앱 종료
                Navigator.of(context).pop(); // 현재 화면 종료
              },
              child: Text('Exit'),
            ),
          ],
        ),
      ),
    );
  }
}
