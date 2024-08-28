import 'package:flutter/material.dart';
import '../config/constants.dart';

//가빈이 임시로 만든 메인화면 페이지
//이 페이지는, 로그인 후 이동할 메인화면에 user_no를 함께 넘겨줄 것인데,
//user_no값이 잘 넘겨지는지 확인하기 위해 만든 메인화면 페이지!
//그러므로, 이 페이지 말고 실제 메인화면페이지로 링크 연결하여 사용하면 됨!
class user_main_screen extends StatelessWidget {
  final int userNo;

  user_main_screen({required this.userNo});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: GestureDetector(
        onTap: () => FocusScope.of(context).unfocus(),
        child: Stack(
          children: [
            Container(
              width: double.infinity,
              height: double.infinity,
              color: Colors.white,
            ),
            Container(
              width: double.infinity,
              height: 300,
              color: Colors.blue,
              child: Center(
                child: Text(
                  'SOL Story',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 70,
                    fontFamily: 'SOL_Story_title',
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
            Positioned(
              top: 250,
              left: 30,
              right: 30,
              bottom: 30,
              child: Stack(
                children: [
                  Container(
                    width: double.infinity,
                    padding: EdgeInsets.all(40),
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(50),
                    ),
                  ),
                  Positioned(
                    top: 0,
                    left: 0,
                    right: 0,
                    bottom: 0,
                    child: Container(
                      padding: EdgeInsets.all(40),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Center(
                            child: Text(
                              '로그인 완료',
                              style: TextStyle(
                                color: Colors.blue,
                                fontSize: 30,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ),
                          SizedBox(height: 50),
                          Center(
                            child: Text(
                              '성공적으로 로그인되었습니다!',
                              style: TextStyle(
                                color: Colors.black,
                                fontSize: 16,
                              ),
                            ),
                          ),
                          SizedBox(height: 20),
                          Center(
                            child: Text(
                              '회원번호: $userNo',
                              style: TextStyle(
                                color: Colors.black,
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ),
                          SizedBox(height: 50),

                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
