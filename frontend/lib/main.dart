import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'models/key_model.dart';
import 'models/owned_cards_model.dart';
import 'models/story_data.dart'; // StoryDataModel 가져오기
import 'story_main.dart';

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
