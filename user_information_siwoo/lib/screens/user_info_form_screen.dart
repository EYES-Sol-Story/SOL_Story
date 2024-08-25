import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'home_screen.dart';

class UserInfoFormScreen extends StatefulWidget {
  @override
  State<UserInfoFormScreen> createState() => _UserInfoFormScreenState();
}

class _UserInfoFormScreenState extends State<UserInfoFormScreen> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _ageController = TextEditingController();
  final _genderController = TextEditingController();
  final _locationController = TextEditingController();

  final List<String> hobbies = [
    '등산',
    '낚시',
    '요리',
    '독서',
    '캠핑',
    '자전거 타기',
    '사진 촬영',
    '게임',
    '영화 감상',
    '헬스/피트니스',
    '악기 연주',
    '수영',
    '다이어리 꾸미기',
    '여행',
    '홈카페/커피 만들기',
    '가드닝',
    '보드게임',
    '공예',
    '러닝',
    '드라마/예능 시청',
  ];

  final List<String> interests = [
    '패션',
    '음악',
    'IT 기술',
    '건강 관리',
    '경제/재테크',
    '스포츠',
    '환경 보호',
    '사회 이슈',
    '교육/학습',
    '외국어 배우기',
    '음식 탐방',
    '뷰티',
    '여행지 탐방',
    '자동차',
    '영화/드라마',
    '인테리어 디자인',
    'SNS 트렌드',
    '자원봉사',
    '가족/육아',
    '창업',
  ];

  final List<String> mbtiTypes = [
    'INTJ',
    'INTP',
    'ENTJ',
    'ENTP',
    'INFJ',
    'INFP',
    'ENFJ',
    'ENFP',
    'ISTJ',
    'ISFJ',
    'ESTJ',
    'ESFJ',
    'ISTP',
    'ISFP',
    'ESTP',
    'ESFP',
  ];

  List<String> selectedHobbies = [];
  List<String> selectedInterests = [];
  String selectedMbti = ''; // 선택한 MBTI를 저장

  Future<void> _submitForm() async {
    if (_formKey.currentState!.validate()) {
      if (selectedMbti.isEmpty) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('MBTI를 선택해 주세요')),
        );
        return;
      }

      SharedPreferences prefs = await SharedPreferences.getInstance();

      await prefs.setString('name', _nameController.text);
      await prefs.setString('age', _ageController.text);
      await prefs.setString('gender', _genderController.text);
      await prefs.setString('location', _locationController.text);
      await prefs.setString('mbti', selectedMbti);
      await prefs.setStringList('hobbies', selectedHobbies);
      await prefs.setStringList('interests', selectedInterests);
      await prefs.setBool('first_run', false);

      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => HomeScreen()),
      );
    }
  }

  void _toggleSelection(List<String> list, String item, int maxSelection) {
    setState(() {
      if (list.contains(item)) {
        list.remove(item);
      } else if (list.length < maxSelection) {
        list.add(item);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('사용자 정보 등록'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text('User Information',
                    style:
                        TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
                TextFormField(
                  controller: _nameController,
                  decoration: const InputDecoration(labelText: '이름'),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return '이름을 입력해 주세요';
                    }
                    return null;
                  },
                ),
                TextFormField(
                  controller: _ageController,
                  decoration: const InputDecoration(labelText: '나이'),
                  keyboardType: TextInputType.number,
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return '나이를 입력해 주세요';
                    }
                    return null;
                  },
                ),
                TextFormField(
                  controller: _genderController,
                  decoration: const InputDecoration(labelText: '성별'),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return '성별을 입력해 주세요';
                    }
                    return null;
                  },
                ),
                TextFormField(
                  controller: _locationController,
                  decoration: const InputDecoration(labelText: '사는 곳'),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return '사는 곳을 입력해 주세요';
                    }
                    return null;
                  },
                ),
                const SizedBox(height: 20),
                const Text('나의 MBTI 선택',
                    style:
                        TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
                Wrap(
                  spacing: 8.0,
                  runSpacing: 8.0,
                  children: mbtiTypes.map((mbti) {
                    final isSelected = selectedMbti == mbti;
                    return ChoiceChip(
                      label: Text(mbti),
                      selected: isSelected,
                      onSelected: (selected) {
                        setState(() {
                          selectedMbti = mbti;
                        });
                      },
                    );
                  }).toList(),
                ),
                const SizedBox(height: 20),
                const Text('나의 취미 5가지',
                    style:
                        TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
                Wrap(
                  spacing: 8.0,
                  runSpacing: 8.0,
                  children: hobbies.map((hobby) {
                    final isSelected = selectedHobbies.contains(hobby);
                    return ChoiceChip(
                      label: Text(hobby),
                      selected: isSelected,
                      onSelected: (selected) {
                        _toggleSelection(selectedHobbies, hobby, 5);
                      },
                    );
                  }).toList(),
                ),
                const SizedBox(height: 20),
                const Text('나의 관심사 5가지',
                    style:
                        TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
                Wrap(
                  spacing: 8.0,
                  runSpacing: 8.0,
                  children: interests.map((interest) {
                    final isSelected = selectedInterests.contains(interest);
                    return ChoiceChip(
                      label: Text(interest),
                      selected: isSelected,
                      onSelected: (selected) {
                        _toggleSelection(selectedInterests, interest, 5);
                      },
                    );
                  }).toList(),
                ),
                const SizedBox(height: 20),
                Center(
                  child: ElevatedButton(
                    onPressed: _submitForm,
                    child: const Text('입력 완료!'),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
