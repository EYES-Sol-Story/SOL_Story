mport 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';
import '../models/user_challenge_model.dart';

class ChallengeListPage extends StatefulWidget {
  @override
  _ChallengeListPageState createState() => _ChallengeListPageState();
}

class _ChallengeListPageState extends State<ChallengeListPage> {
  Future<List<UserChallenge>> _fetchChallenges() async {
    final prefs = await SharedPreferences.getInstance();
    final email = prefs.getString('userEmail') ?? 'user@shinhan.ssafy.com'; // 이메일을 가져오거나 기본값 설정
    print(email);
    try {
      // 서버 연동 시 이 부분에서 API 호출
      final response = await http.get(
          Uri.parse('http://192.168.0.2:8090/api/challenge/list?email=$email'));
      print(response.body);
      if (response.statusCode == 200) {
        // 서버 응답 데이터 파싱 후 리스트 반환
        final List<dynamic> challengeData = jsonDecode(response.body);
        return challengeData.map((data) => UserChallenge.fromJson(data))
            .toList();
      } else {
        throw Exception('Failed to load challenges');
      }
    } catch (e) {
      return Future.delayed(Duration(seconds: 2), () {
        return [
          UserChallenge(
              challengeType: "저축",
              category: "식비",
              days: 30,
              challengeName: "이번 달 5만원 더 저축하기",
              rewardKeys: 5,
              assignedDate: DateTime.now().subtract(Duration(days: 10)),
              completeDate: DateTime.now().add(Duration(days: 30))),
          UserChallenge(
              challengeType: "지출",
              category: "식비",
              days: 7,
              challengeName: "이번 주 3만원 더 저축하기",
              rewardKeys: 3,
              assignedDate: DateTime.now().subtract(Duration(days: 3)),
              completeDate: DateTime.now().add(Duration(days: 7)))
        ];
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('SOL Story 챌린지 리스트'),
      ),
      body: FutureBuilder<List<UserChallenge>>(
        future: _fetchChallenges(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return Center(child: Text('챌린지가 없습니다.'));
          } else {
            return ListView.builder(
              itemCount: snapshot.data!.length,
              itemBuilder: (context, index) {
                final challenge = snapshot.data![index];
                final completionPercentage = challenge
                    .getCompletionPercentage();
                return Card(
                  margin: EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                  elevation: 5,
                  child: ListTile(
                    contentPadding: EdgeInsets.all(16),
                    title: Text(challenge.challengeName),
                    subtitle: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text('카테고리: ${challenge.category}'),
                        Text('기간: ${challenge.days}일'),
                        Text('타입: ${challenge.challengeType}'),
                        Text('보상 열쇠: ${challenge.rewardKeys}'),
                        Text('할당 날짜: ${DateFormat('yy-MM-dd').format(
                            challenge.assignedDate)}'),
                        Text('완료 날짜: ${DateFormat('yy-MM-dd').format(
                            challenge.completeDate)}'),
                        SizedBox(height: 10),
                        LinearProgressIndicator(
                          value: completionPercentage,
                          backgroundColor: Colors.grey[300],
                          color: Colors.blue,
                        ),
                        SizedBox(height: 10),
                      ],
                    ),
                  ),
                );
              },
            );
          }
        },
      ),
    );
  }
}