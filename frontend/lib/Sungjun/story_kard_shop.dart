import 'dart:async';
import 'dart:convert'; // JSON 인코딩 및 디코딩을 위해 추가
import 'dart:math';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'models/key_model.dart';
import 'models/owned_cards_model.dart';
import 'models/related_cards.dart';

class StoryKardShop extends StatefulWidget {
  @override
  _StoryKardShopState createState() => _StoryKardShopState();
}

class _StoryKardShopState extends State<StoryKardShop> {
  List<Map<String, dynamic>> availableCards = [];
  List<Map<String, dynamic>> additionalCards = [];
  Set<String> purchasedCards = {};
  final String itemNameMaxIncrease = "배달";
  final int wildCardPrice = 100;
  bool initialCardsExhausted = false;
  bool additionalCardsExhausted = false;
  Timer? _timer;
  Duration _timeUntilMidnight = Duration.zero;
  final ScrollController _scrollController = ScrollController();

  @override
  void initState() {
    super.initState();
    _loadState().then((_) {
      _resetCards(); // 초기화 후 카드 생성///////////////////////시연일때는 이거 삭제
      _startTimer();
    });
  }

  @override
  void dispose() {
    _timer?.cancel();
    _scrollController.dispose();
    super.dispose();
  }

  void _startTimer() {
    _calculateTimeUntilMidnight();
    _timer = Timer.periodic(Duration(seconds: 1), (timer) {
      setState(() {
        _calculateTimeUntilMidnight();
      });
    });
  }

  void _calculateTimeUntilMidnight() {
    final now = DateTime.now();
    final midnight = DateTime(now.year, now.month, now.day + 1);
    _timeUntilMidnight = midnight.difference(now);
  }

  void _generateInitialCards() {
    if (availableCards.isNotEmpty) return;

    final Random random = Random();
    List<Map<String, dynamic>> chosenCards = [];

    void _addCardOfType(List<String> cardList, String type) {
      String card;
      int price;
      do {
        card = cardList[random.nextInt(cardList.length)];
        price = type == 'person'
            ? random.nextInt(51) + 50
            : random.nextInt(50) + 1;
      } while (chosenCards.any((c) => c['name'] == card));

      chosenCards.add({
        'name': card,
        'price': price,
        'type': type
      });
    }

    _addCardOfType(
        keywordToPersonCards.values.expand((x) => x).toList(), 'person');
    _addCardOfType(
        keywordToObjectCards.values.expand((x) => x).toList(), 'object');
    _addCardOfType(
        keywordToPlaceCards.values.expand((x) => x).toList(), 'place');

    while (chosenCards.length < 6) {
      String card;
      int price;
      String type;
      List<String> cardList;

      if (random.nextBool()) {
        type = 'person';
        cardList = keywordToPersonCards.values.expand((x) => x).toList();
      } else if (random.nextBool()) {
        type = 'object';
        cardList = keywordToObjectCards.values.expand((x) => x).toList();
      } else {
        type = 'place';
        cardList = keywordToPlaceCards.values.expand((x) => x).toList();
      }

      card = cardList[random.nextInt(cardList.length)];
      price = type == 'person' ? random.nextInt(51) + 50 : random.nextInt(50) + 1;

      if (!chosenCards.any((c) => c['name'] == card)) {
        chosenCards.add({
          'name': card,
          'price': price,
          'type': type
        });
      }
    }

    availableCards = chosenCards;
    _saveState();
    setState(() {});

    print('Initial Cards Generated: $availableCards');
  }

  void _generateAdditionalCards(String itemName) {
    final Random random = Random();
    List<Map<String, dynamic>> keywordCardsList = [];

    List<String> persons = keywordToPersonCards[itemName] ?? [];
    List<String> objects = keywordToObjectCards[itemName] ?? [];
    List<String> places = keywordToPlaceCards[itemName] ?? [];

    if (persons.isNotEmpty) {
      String personCard = persons[random.nextInt(persons.length)];
      keywordCardsList.add({
        'name': personCard,
        'price': random.nextInt(51) + 50,
        'type': 'person',
        'isAvailable': true
      });
    }

    if (objects.isNotEmpty) {
      String objectCard = objects[random.nextInt(objects.length)];
      keywordCardsList.add({
        'name': objectCard,
        'price': random.nextInt(50) + 1,
        'type': 'object',
        'isAvailable': true
      });
    }

    if (places.isNotEmpty) {
      String placeCard = places[random.nextInt(places.length)];
      keywordCardsList.add({
        'name': placeCard,
        'price': random.nextInt(50) + 1,
        'type': 'place',
        'isAvailable': true
      });
    }

    while (keywordCardsList.length < 3) {
      String card;
      int price;
      String type;
      List<String> cardList;

      if (keywordCardsList.where((c) => c['type'] == 'person').length < 1) {
        type = 'person';
        cardList = persons.isNotEmpty ? persons : keywordToPersonCards.values.expand((x) => x).toList();
      } else if (keywordCardsList.where((c) => c['type'] == 'object').length < 1) {
        type = 'object';
        cardList = objects.isNotEmpty ? objects : keywordToObjectCards.values.expand((x) => x).toList();
      } else if (keywordCardsList.where((c) => c['type'] == 'place').length < 1) {
        type = 'place';
        cardList = places.isNotEmpty ? places : keywordToPlaceCards.values.expand((x) => x).toList();
      } else {
        break;
      }

      card = cardList[random.nextInt(cardList.length)];
      price = type == 'person' ? random.nextInt(51) + 50 : random.nextInt(50) + 1;

      if (!keywordCardsList.any((c) => c['name'] == card) &&
          !availableCards.any((c) => c['name'] == card)) {
        keywordCardsList.add({
          'name': card,
          'price': price,
          'type': type,
          'isAvailable': true
        });
      }
    }

    additionalCards = keywordCardsList;
    _saveState();
    setState(() {});

    print('Additional Cards Generated: $additionalCards');
  }

  void _resetCards() {
    setState(() {
      availableCards.clear();
      additionalCards.clear();
      purchasedCards.clear();
      initialCardsExhausted = false;
      additionalCardsExhausted = false;
    });
    _generateInitialCards();
    _generateAdditionalCards(itemNameMaxIncrease);
  }

  void _buyCard(String cardName, int price, String cardType) {
    final keyModel = Provider.of<KeyModel>(context, listen: false);
    final ownedCardsModel = Provider.of<OwnedCardsModel>(context, listen: false);

    if (keyModel.key >= price) {
      setState(() {
        keyModel.useKey(price);
        purchasedCards.add(cardName);
        availableCards.removeWhere((card) => card['name'] == cardName);
        additionalCards.removeWhere((card) => card['name'] == cardName);
        ownedCardsModel.addCard(cardName, cardType);

        if (availableCards.isEmpty) {
          initialCardsExhausted = true;
        }

        if (additionalCards.isEmpty) {
          additionalCardsExhausted = true;
        }
      });
      _saveState();
      ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('$cardName 카드를 구매했습니다!'))
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('열쇠가 부족합니다'))
      );
    }
  }

  void _buyWildCard() {
    final keyModel = Provider.of<KeyModel>(context, listen: false);

    if (keyModel.key >= wildCardPrice) {
      setState(() {
        keyModel.useKey(wildCardPrice);
        _showWildCardDialog();
      });
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('열쇠가 부족합니다'))
      );
    }
  }

  void _showWildCardDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        final nameController = TextEditingController();
        String selectedType = 'person';

        return AlertDialog(
          title: Text('와일드 카드 생성'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              TextField(
                controller: nameController,
                decoration: InputDecoration(labelText: '카드 이름'),
              ),
              DropdownButton<String>(
                value: selectedType,
                onChanged: (String? newValue) {
                  setState(() {
                    selectedType = newValue!;
                  });
                },
                items: <String>['person', 'object', 'place']
                    .map<DropdownMenuItem<String>>((String value) {
                  return DropdownMenuItem<String>(
                    value: value,
                    child: Text(value),
                  );
                }).toList(),
              ),
            ],
          ),
          actions: <Widget>[
            TextButton(
              child: Text('취소'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text('확인'),
              onPressed: () {
                if (nameController.text.isNotEmpty) {
                  final cardName = nameController.text;
                  final cardPrice = Random().nextInt(50) + 1;

                  setState(() {
                    // 보유 카드 목록에 와일드 카드 추가
                    final ownedCardsModel = Provider.of<OwnedCardsModel>(context, listen: false);
                    ownedCardsModel.addCard(cardName, selectedType);

                    // 상태를 저장합니다.
                    _saveState();
                  });

                  Navigator.of(context).pop();
                }
              },
            ),
          ],
        );
      },
    );
  }

  Future<void> _saveState() async {
    final prefs = await SharedPreferences.getInstance();
    final keyModel = Provider.of<KeyModel>(context, listen: false);
    final ownedCardsModel = Provider.of<OwnedCardsModel>(context, listen: false);

    await prefs.setInt('key', keyModel.key);
    await prefs.setStringList('purchasedCards', purchasedCards.toList());

    await prefs.setString('availableCards', jsonEncode(availableCards));
    await prefs.setString('additionalCards', jsonEncode(additionalCards));
    await prefs.setBool('initialCardsExhausted', initialCardsExhausted);
    await prefs.setBool('additionalCardsExhausted', additionalCardsExhausted);
  }

  Future<void> _loadState() async {
    final prefs = await SharedPreferences.getInstance();
    final keyModel = Provider.of<KeyModel>(context, listen: false);

    setState(() {
      // 카드 및 기타 상태 로드
      availableCards = (jsonDecode(prefs.getString('availableCards') ?? '[]') as List)
          .map((item) => Map<String, dynamic>.from(item))
          .toList();
      additionalCards = (jsonDecode(prefs.getString('additionalCards') ?? '[]') as List)
          .map((item) => Map<String, dynamic>.from(item))
          .toList();
      purchasedCards = Set<String>.from(prefs.getStringList('purchasedCards') ?? []);
      initialCardsExhausted = prefs.getBool('initialCardsExhausted') ?? false;
      additionalCardsExhausted = prefs.getBool('additionalCardsExhausted') ?? false;
    });
  }

  @override
  Widget build(BuildContext context) {
    final keyModel = Provider.of<KeyModel>(context);
    final ownedCardsModel = Provider.of<OwnedCardsModel>(context);

    print('Available Cards: $availableCards');
    print('Additional Cards: $additionalCards');

    return Scaffold(
      appBar: AppBar(
        title: Text('스토리 카드 상점'),
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            // 열쇠 정보 표시
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Text('보유 열쇠: ${keyModel.key}개',
                style: TextStyle(fontSize: 18),
              ),
            ),
            // 이 달의 카드 제목
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Text('이 달의 카드', style: TextStyle(fontSize: 24)),
            ),
            // 이 달의 카드 목록
            Expanded(
              child: initialCardsExhausted
                  ? Center(
                child: Text(
                  '모든 초기 카드를 구매하셨습니다.\n다음 카드 리필까지 남은 시간: ${_timeUntilMidnight.toString().split('.').first}',
                  style: TextStyle(fontSize: 18, color: Colors.red),
                  textAlign: TextAlign.center,
                ),
              )
                  : ListView.builder(
                itemCount: availableCards.length,
                itemBuilder: (BuildContext context, int index) {
                  final card = availableCards[index];
                  final isPurchased = purchasedCards.contains(card['name']);

                  return ListTile(
                    title: Text(
                      card['name'],
                      style: TextStyle(
                        decoration: isPurchased ? TextDecoration.lineThrough : null,
                      ),
                    ),
                    subtitle: Text('가격: ${card['price']} 열쇠'),
                    trailing: ElevatedButton(
                      onPressed: isPurchased ? null : () => _buyCard(card['name'], card['price'], card['type']),
                      child: Text('구매하기'),
                    ),
                  );
                },
              ),
            ),
            // 추가 카드 제목
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Text('$itemNameMaxIncrease 추가 카드', style: TextStyle(fontSize: 24)),
            ),
            // 추가 카드 목록
            Expanded(
              child: additionalCardsExhausted
                  ? Center(
                child: Text(
                  '모든 추가 카드를 구매하셨습니다.\n다음 카드 리필까지 남은 시간: ${_timeUntilMidnight.toString().split('.').first}',
                  style: TextStyle(fontSize: 18, color: Colors.red),
                  textAlign: TextAlign.center,
                ),
              )
                  : ListView.builder(
                itemCount: additionalCards.length,
                itemBuilder: (BuildContext context, int index) {
                  final card = additionalCards[index];
                  final isPurchased = purchasedCards.contains(card['name']);

                  return ListTile(
                    title: Text(
                      card['name'],
                      style: TextStyle(
                        decoration: isPurchased ? TextDecoration.lineThrough : null,
                      ),
                    ),
                    subtitle: Text('가격: ${card['price']} 열쇠'),
                    trailing: ElevatedButton(
                      onPressed: isPurchased ? null : () => _buyCard(card['name'], card['price'], card['type']),
                      child: Text('구매하기'),
                    ),
                  );
                },
              ),
            ),
            SizedBox(height: 20),
            // 와일드 카드 구매 버튼
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: ElevatedButton(
                onPressed: _buyWildCard,
                child: Text('와일드 카드 구매하기 (가격: $wildCardPrice 열쇠)'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
