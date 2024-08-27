import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import 'pages/create_useraccount.dart';
import '/../../../config/constants.dart';

class GoalListPage extends StatelessWidget {
  Future<String?> _checkUserKey() async {
    //SharedPreferences prefs = await SharedPreferences.getInstance();
   //String email = "userKey@ssafy.com"; // 이메일을 사용해 userkey를 확인
    //지히 - 테스트용
    String email = "240827_01@ssafy.com";

    if (email.isEmpty) {
      return null;
    }

    // window 주소 uri
    final Uri uri = Uri.parse(REST_API_URL + '/api/userkey').replace(
      queryParameters: {
        'email': email,
      },
    );

    try {
      final response = await http.get(uri);
      if (response.statusCode == 200 && response.body.isNotEmpty) {
        return response.body; // userkey 반환
      }
    } catch (e) {
      print('서버와의 연결에 실패했습니다: $e');
    }
    return null; // userkey가 없거나 오류가 발생한 경우 null 반환
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<String?>(
        future: _checkUserKey(),
    builder: (context, snapshot) {
    if (snapshot.connectionState == ConnectionState.waiting) {
    return Scaffold(
    body: Center(
    child: CircularProgressIndicator(),
    ),
    );
    } else if (snapshot.hasData && snapshot.data != null) {
    // userkey가 존재하는 경우 ChallengeListPage로 이동
    WidgetsBinding.instance.addPostFrameCallback((_) {
    Navigator.pushReplacementNamed(context, '/challenges');
    });
    return SizedBox.shrink(); // 빈 화면 반환
    } else {
    // userkey가 없는 경우 CreateUserAccountPage로 이동
    return CreateUserAccountPage();
    }
    },
    );
  }
}
