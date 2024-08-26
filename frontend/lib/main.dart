import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'Sungjun/models/key_model.dart';
import 'Sungjun/models/owned_cards_model.dart';
import 'Sungjun/models/story_data.dart'; // StoryDataModel 가져오기
import 'Sungjun/story_main.dart'; // 사용되지 않는 패키지
import 'package:sol_story/Gabin/Login.dart'; // 필수

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => KeyModel()),
        ChangeNotifierProvider(create: (context) => OwnedCardsModel()),
        ChangeNotifierProvider(create: (context) => StoryDataModel()), // StoryDataModel 등록
      ],
      child: MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Sol스토리',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: LoginPage(),
    );
  }
}
