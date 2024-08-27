// charts.dart
import 'dart:math';
import 'package:flutter/material.dart';
import 'package:fl_chart/fl_chart.dart';
import 'financialModels.dart'; // 모델 파일 import

// 구별되는 25개의 색상 팔레트 정의
final List<Color> predefinedColors = [
  Colors.red,
  Colors.blue,
  Colors.green,
  Colors.amber,
  Colors.purple,
  Colors.orange,
  Colors.pink,
  Colors.teal,
  Colors.cyan,
  Colors.lime,
  Colors.indigo,
  Colors.brown,
  Colors.deepPurple,
  Colors.blueGrey,
  Colors.lightBlue,
  Colors.lightGreen,
  Colors.deepOrange,
  Colors.yellow,
  Colors.grey,
  Colors.blueAccent,
  Colors.redAccent,
  Colors.greenAccent,
  Colors.purpleAccent,
  Colors.orangeAccent,
  Colors.pinkAccent,
];

// 랜덤 색상을 선택하는 함수 (도넛 차트에서만 사용)
Color getRandomPredefinedColor(Random random) {
  return predefinedColors[random.nextInt(predefinedColors.length)];
}

// 통합 차트 위젯 정의
class CombinedChart extends StatelessWidget {
  final List<CategorySpendingSummary> barData;
  final Map<String, CategorySpendingAvgDTO> lineData;
  final List<Color> barColors; // 막대 색상 목록

  CombinedChart({required this.barData, required this.lineData})
      : barColors = List.generate(barData.length, (_) => Colors.blue); // 막대 색상은 기본적으로 파란색

  @override
  Widget build(BuildContext context) {
    // 막대 그래프와 꺾은선 그래프의 최대 y 값을 계산
    final double maxBarY = barData.map((e) => e.totalAmount).reduce((a, b) => a > b ? a : b).toDouble();
    final double maxLineY = lineData.values.map((e) => e.avgAmount).reduce((a, b) => a > b ? a : b);
    final double maxY = (maxBarY > maxLineY ? maxBarY : maxLineY) * 1.2; // 여유 공간을 두기 위해 1.2를 곱함

    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: Text(
            '지히님의 지출 타입은? 프로 카페러~',
            style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
          ),
        ),
        Expanded(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0),
            child: Stack(
              children: [
                BarChart(
                  BarChartData(
                    alignment: BarChartAlignment.spaceAround,
                    barTouchData: BarTouchData(enabled: true),
                    barGroups: barData.asMap().entries.map((entry) {
                      return BarChartGroupData(
                        x: entry.key * 2, // 막대의 중앙을 기준으로 x값을 두 배로 설정하여 중앙에 맞춤
                        barRods: [
                          BarChartRodData(
                            toY: entry.value.totalAmount.toDouble(),
                            color: barColors[entry.key], // 막대 색상 사용
                            width: 15, // 막대 너비 조절
                            borderRadius: BorderRadius.circular(10), // 막대 모서리 둥글게
                            backDrawRodData: BackgroundBarChartRodData(
                              show: true,
                              toY: 0,
                              color: Colors.grey.shade200,
                            ),
                          ),
                        ],
                      );
                    }).toList(),
                    titlesData: FlTitlesData(
                      bottomTitles: AxisTitles(
                        sideTitles: SideTitles(
                          showTitles: true,
                          reservedSize: 42, // 여유 공간을 줌
                          getTitlesWidget: (value, meta) {
                            return Padding(
                              padding: const EdgeInsets.only(top: 8.0),
                              child: Text(
                                barData[(value.toInt() / 2).toInt()].category,
                                style: TextStyle(fontSize: 12),
                              ),
                            );
                          },
                        ),
                      ),
                      leftTitles: AxisTitles(
                        sideTitles: SideTitles(
                          showTitles: false,
                          reservedSize: 40,
                        ),
                      ),
                      rightTitles: AxisTitles(
                        sideTitles: SideTitles(
                          showTitles: false,
                          reservedSize: 40,
                        ),
                      ),
                      topTitles: AxisTitles(
                        sideTitles: SideTitles(showTitles: false),
                      ),
                    ),
                    borderData: FlBorderData(
                      show: true,
                      border: Border.all(color: Colors.grey),
                    ),
                    gridData: FlGridData(show: true),
                    minY: 0, // Y축 최소값 설정
                    maxY: maxY, // 막대 그래프와 꺾은선 그래프의 최대 y 값을 동일하게 설정
                  ),
                ),
                LineChart(
                  LineChartData(
                    lineBarsData: [
                      LineChartBarData(
                        spots: barData.asMap().entries.map((entry) {
                          return FlSpot(
                            entry.key * 2 + 1, // 막대의 중앙을 기준으로 FlSpot의 x값을 설정
                            lineData[entry.value.category]?.avgAmount ?? 0,
                          );
                        }).toList(),
                        isCurved: false,
                        color: Colors.red, // 꺾은선 그래프 색상
                        barWidth: 2,
                        belowBarData: BarAreaData(show: false),
                        dotData: FlDotData(show: true),
                      ),
                    ],
                    titlesData: FlTitlesData(
                      bottomTitles: AxisTitles(
                        sideTitles: SideTitles(
                          showTitles: false,
                        ),
                      ),
                      leftTitles: AxisTitles(
                        sideTitles: SideTitles(
                          showTitles: false,
                        ),
                      ),
                      rightTitles: AxisTitles(
                        sideTitles: SideTitles(
                          showTitles: false,
                        ),
                      ),
                      topTitles: AxisTitles(
                        sideTitles: SideTitles(showTitles: false),
                      ),
                    ),
                    gridData: FlGridData(show: false),
                    borderData: FlBorderData(show: false),
                    minY: 0,
                    maxY: maxY,
                  ),
                ),
              ],
            ),
          ),
        ),
      ],
    );
  }
}

// 도넛 차트 위젯 정의
class DonutChart extends StatelessWidget {
  final List<CategorySpendingSummary> data;
  final List<Color> sectionColors; // 섹션 색상 목록

  DonutChart({required this.data})
      : sectionColors = List.generate(
      data.length, (_) => getRandomPredefinedColor(Random())); // 팔레트에서 랜덤 색상 생성

  @override
  Widget build(BuildContext context) {
    return PieChart(
      PieChartData(
        sections: data.asMap().entries.map((entry) {
          final percentage = (entry.value.totalAmount / data.map((e) => e.totalAmount).reduce((a, b) => a + b)) * 100;
          return PieChartSectionData(
            color: sectionColors[entry.key], // 랜덤 팔레트 색상 사용
            value: entry.value.totalAmount.toDouble(),
            title: '${entry.value.category}\n${percentage.toStringAsFixed(1)}%',
            radius: 100,
            titleStyle: TextStyle(fontSize: 12, fontWeight: FontWeight.bold, color: Colors.white),
          );
        }).toList(),
        sectionsSpace: 2,
        centerSpaceRadius: 50,
      ),
    );
  }
}