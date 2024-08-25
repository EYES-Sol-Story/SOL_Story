//이 화면도 지희 누나 화면으로 대체
import 'package:flutter/material.dart';
import 'package:fl_chart/fl_chart.dart';

class FinanceDetailsScreen extends StatelessWidget {
  const FinanceDetailsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFA7B4D4),
      appBar: AppBar(
        title: const Text("내 금융 상태"),
        backgroundColor: const Color(0xFF7F8FA6),
      ),
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: ListView(
            children: [
              _buildSpendingTypeSection(),
              const SizedBox(height: 20),
              _buildBarChart(),
              const SizedBox(height: 20),
              _buildAnalysisSummary(),
              const SizedBox(height: 20),
              _buildDonutChart(),
              const SizedBox(height: 20),
              _buildCategorySpendingSummary(),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildSpendingTypeSection() {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.grey.withOpacity(0.2),
        borderRadius: BorderRadius.circular(12),
      ),
      child: const Text(
        '지희님의 지출 타입은?\n"프로 카페러"',
        style: TextStyle(
          fontSize: 22,
          fontWeight: FontWeight.bold,
          color: Colors.white,
        ),
      ),
    );
  }

  Widget _buildBarChart() {
    return Container(
      height: 300,
      child: BarChart(
        BarChartData(
          alignment: BarChartAlignment.spaceAround,
          barGroups: _createBarGroups(),
          titlesData: FlTitlesData(
            bottomTitles: AxisTitles(
              sideTitles: SideTitles(showTitles: true),
            ),
            leftTitles: AxisTitles(
              sideTitles: SideTitles(showTitles: true),
            ),
          ),
          borderData: FlBorderData(show: false),
        ),
      ),
    );
  }

  List<BarChartGroupData> _createBarGroups() {
    final data = [120000, 90000, 80000, 60000, 50000];
    return List.generate(data.length, (index) {
      return BarChartGroupData(
        x: index,
        barRods: [
          BarChartRodData(
            toY: data[index].toDouble(),
            color: const Color.fromARGB(255, 163, 214, 255),
            width: 20,
          )
        ],
      );
    });
  }

  Widget _buildAnalysisSummary() {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.grey.withOpacity(0.2),
        borderRadius: BorderRadius.circular(12),
      ),
      child: const Text(
        '최근 30일 각 카테고리의 전월 대비 지출 금액 증감 추이:\n'
        '운동: +20%     카페: +30%     게임: 0%\n'
        '식비: -10%     가구: -40%     엔터테인먼트: +20%',
        style: TextStyle(
          fontSize: 18,
          color: Colors.white,
        ),
      ),
    );
  }

  Widget _buildDonutChart() {
    return Container(
      height: 300,
      child: PieChart(
        PieChartData(
          sections: _createPieSections(),
          centerSpaceRadius: 60,
        ),
      ),
    );
  }

  List<PieChartSectionData> _createPieSections() {
    return [
      PieChartSectionData(
        value: 40,
        color: const Color.fromARGB(255, 185, 221, 251),
        title: '카페 40%',
      ),
      PieChartSectionData(
        value: 30,
        color: const Color.fromARGB(255, 255, 232, 198),
        title: '식비 30%',
      ),
      PieChartSectionData(
        value: 20,
        color: const Color.fromARGB(255, 194, 255, 196),
        title: '운동 20%',
      ),
      PieChartSectionData(
        value: 5,
        color: const Color.fromARGB(255, 255, 192, 188),
        title: '게임 5%',
      ),
      PieChartSectionData(
        value: 5,
        color: const Color.fromARGB(255, 246, 196, 255),
        title: '엔터테인먼트 5%',
      ),
    ];
  }

  Widget _buildCategorySpendingSummary() {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.grey.withOpacity(0.2),
        borderRadius: BorderRadius.circular(12),
      ),
      child: const Text(
        '[일주일간 가장 많은 지출이 있었던 카페 카테고리의 최근 30일 간의 지출 요약]\n'
        '투썸플레이스(7회): 64,000원\n'
        '스타벅스(6회): 48,000원\n'
        '신라호텔카페(2회): 61,000원',
        style: TextStyle(
          fontSize: 18,
          color: Colors.white,
        ),
      ),
    );
  }
}
