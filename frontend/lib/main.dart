import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:firebase_core/firebase_core.dart';
import 'Gabin/eyes_icon_page.dart'; // 초기 로딩 페이지
import 'Gabin/Login_screen.dart'; // 로그인 페이지
import 'Gabin/forgot_account_page.dart'; // 계정찾기 페이지
import 'Gabin/sign_up_screen.dart'; // 회원가입 페이지
import 'Gabin/change_password_page.dart'; // 비밀번호 변경 페이지
import 'Sungjun/models/key_model.dart';
import 'Sungjun/models/owned_cards_model.dart';
import 'Sungjun/models/story_data.dart'; // StoryDataModel 가져오기
import 'Boyoung/pages/user_challenge_list.dart';
import 'Boyoung/pages/create_useraccount.dart';
import 'Boyoung/pages/transfer_onewon.dart';
import 'Boyoung/pages/verify_onewon.dart';
import 'Siwoo/MainPage.dart';



//이 페이지는, 앱을 실행시켰을 때 sol_story페이지를 2초간 띄우며 firebase(인증구현서비스)연동을 하고
// 그 후에 로그인페이지로 넘어가기 위한 가빈의 main.dart에다가
//기존 이 프로젝트의 main.dart를 합친 것.
// 일단 기존 이 프로젝트의 main.dart를 쓰고, 이 가빈이 만든 main_gabin.dart를 써보자!
//가빈이 만든 main.dart는 가빈 폴더에 있음.
//이 파일은 가빈의 main.dart와 기존 이 프로젝트의 main.dart를 합친 코드.

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  try {
    await Firebase.initializeApp(); // Firebase 초기화, 모바일용. 웹에서도 쓰려면 따로 옵션설정해줘야 함.
  } catch (e) {
    print('Firebase 초기화 오류: $e');
  }

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
      title: 'SOL_Story',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      //home: MainPage(),
      home: EyesIconPage(), // 초기 로딩 페이지를 처음에 표시
      routes: {
        '/login': (context) => LoginScreen(),
        '/forgot-account': (context) => ForgotAccountPage(),
        '/join': (context) => SignUpScreen(),
        '/change-password': (context) {
          final args = ModalRoute.of(context)!.settings.arguments as Map<String, String>;
          return ChangePasswordPage(
            userId: args['userId']!,
          );
        },
        '/challenges': (context) => ChallengeListPage(), // '/challenges' 경로 추가
        '/create_user': (context) => CreateUserAccountPage(),
        '/transfer/one-won': (context) => TransferOneWonPage(),
        '/verify/one-won' : (context) => VerifyOneWonPage(),
      },
    );
  }
}
