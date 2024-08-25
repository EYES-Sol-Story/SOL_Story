import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'profile_screen.dart';
import 'detail_screen.dart';

class HomeScreen extends StatelessWidget {
  Future<String> _getUserName() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString('name') ?? '사용자'; // 이름이 없을 경우 '사용자'로 대체
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<String>(
      future: _getUserName(),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Center(child: CircularProgressIndicator());
        } else if (snapshot.hasError) {
          return const Center(child: Text('Error loading user name'));
        } else {
          String userName = snapshot.data ?? '사용자';
          return Scaffold(
            backgroundColor: const Color(0xFFA7B4D4), // 배경색 지정
            body: SafeArea(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.start,
                      children: [
                        Icon(Icons.arrow_back, color: Colors.white),
                      ],
                    ),
                    const SizedBox(height: 10),
                    Center(
                      child: Align(
                        alignment: Alignment.centerRight,
                        child: Image.asset(
                          'assets/logo.png',
                          width: 300,
                        ),
                      ),
                    ),
                    const SizedBox(height: 0),
                    Center(
                      child: Text(
                        "Welcome, $userName", // 저장된 이름을 사용
                        style: const TextStyle(
                          fontSize: 24,
                          color: Colors.white,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                    const SizedBox(height: 20),
                    Center(
                      child: ClipOval(
                        child: Image.asset(
                          'assets/profile.webp',
                          width: 250,
                          height: 250,
                          fit: BoxFit.cover,
                        ),
                      ),
                    ),
                    const SizedBox(height: 30),
                    Expanded(
                      child: ListView.builder(
                        itemCount: 4,
                        itemBuilder: (context, index) {
                          List<String> titles = [
                            "사용자 프로필",
                            "도전과제",
                            "나만의 스토리",
                            "선물함"
                          ];
                          List<IconData> icons = [
                            Icons.person,
                            Icons.message,
                            Icons.book,
                            Icons.local_activity_rounded
                          ];

                          return GestureDetector(
                            onTap: () {
                              if (titles[index] == "사용자 프로필") {
                                Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                      builder: (context) =>
                                          const ProfileScreen()),
                                );
                              } else {
                                Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) =>
                                        DetailScreen(title: titles[index]),
                                  ),
                                );
                              }
                            },
                            child: Padding(
                              padding:
                                  const EdgeInsets.symmetric(vertical: 8.0),
                              child: Container(
                                height: 80,
                                decoration: BoxDecoration(
                                  color: const Color(0xFF7F8FA6),
                                  borderRadius: BorderRadius.circular(20),
                                ),
                                child: Padding(
                                  padding: const EdgeInsets.symmetric(
                                      horizontal: 16.0),
                                  child: Row(
                                    children: [
                                      Icon(
                                        icons[index],
                                        size: 40,
                                        color: Colors.white,
                                      ),
                                      const SizedBox(width: 16),
                                      Text(
                                        titles[index],
                                        style: const TextStyle(
                                          fontSize: 20,
                                          color: Colors.white,
                                        ),
                                      ),
                                      const Spacer(),
                                      const Icon(
                                        Icons.arrow_forward_ios,
                                        color: Colors.white,
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                            ),
                          );
                        },
                      ),
                    ),
                  ],
                ),
              ),
            ),
          );
        }
      },
    );
  }
}
