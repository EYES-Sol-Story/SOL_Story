import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:sol_story/Gabin/Login.dart';
import 'package:sol_story/Sungjun/models/key_model.dart';
import 'package:sol_story/Sungjun/models/owned_cards_model.dart';
import 'package:sol_story/Sungjun/models/story_data.dart'; // StoryDataModel 가져오기
import 'package:sol_story/Sungjun/story_main.dart';

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
      title: 'SOL 스토리',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: LoginPage(),
    );
  }
}
