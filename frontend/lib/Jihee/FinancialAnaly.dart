import 'package:flutter/material.dart';
import 'package:fl_chart/fl_chart.dart';
import 'dart:math';
import 'financialModels.dart';
import 'spendingAmountWithCategory.dart';
import 'spendingTrends.dart';
import 'spendingDetails.dart';

class FinancialAnalyPage extends StatelessWidget {
  const FinancialAnalyPage({super.key});

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
              FutureBuilder<List<CategorySpendingSummary>>(
                future: fetchTop5Categories(1), // 예시로 userNo = 1
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return CircularProgressIndicator();
                  } else if (snapshot.hasError) {
                    return Text('Error: ${snapshot.error}');
                  } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                    return Text('No data available');
                  } else {
                    return Column(
                      children: [
                        _buildSpendingTypeSection(snapshot.data![0].category),
                        const SizedBox(height: 20),
                        FutureBuilder<Map<String, CategorySpendingAvgDTO>>(
                          future: fetchTop5CategoriesWithAvg(1), // 예시로 userNo = 1
                          builder: (context, avgSnapshot) {
                            if (avgSnapshot.connectionState == ConnectionState.waiting) {
                              return CircularProgressIndicator();
                            } else if (avgSnapshot.hasError) {
                              return Text('Error: ${avgSnapshot.error}');
                            } else if (!avgSnapshot.hasData || avgSnapshot.data!.isEmpty) {
                              return Text('No data available');
                            } else {
                              return _buildHistogramChart(snapshot.data!, avgSnapshot.data!);
                            }
                          },
                        ),
                        const SizedBox(height: 20),
                        FutureBuilder<List<FinancialTrendDTO>>(
                          future: fetchSpendingTrends(1), // 예시로 userNo = 1
                          builder: (context, trendSnapshot) {
                            if (trendSnapshot.connectionState == ConnectionState.waiting) {
                              return CircularProgressIndicator();
                            } else if (trendSnapshot.hasError) {
                              return Text('Error: ${trendSnapshot.error}');
                            } else if (!trendSnapshot.hasData || trendSnapshot.data!.isEmpty) {
                              return Text('No data available');
                            } else {
                              return _buildAnalysisSummary(trendSnapshot.data!);
                            }
                          },
                        ),
                        const SizedBox(height: 20),
                        FutureBuilder<List<CategorySpendingSummary>>(
                          future: fetchLast7DaysSpending(1), // 예시로 userNo = 1
                          builder: (context, donutSnapshot) {
                            if (donutSnapshot.connectionState == ConnectionState.waiting) {
                              return CircularProgressIndicator();
                            } else if (donutSnapshot.hasError) {
                              return Text('Error: ${donutSnapshot.error}');
                            } else if (!donutSnapshot.hasData || donutSnapshot.data!.isEmpty) {
                              return Text('No data available');
                            } else {
                              return _buildDonutChart(donutSnapshot.data!);
                            }
                          },
                        ),
                        const SizedBox(height: 20),
                        FutureBuilder<List<StoreSpendingSummary>>(
                          future: fetchHighestSpendingDetails(1), // 예시로 userNo = 1
                          builder: (context, detailsSnapshot) {
                            if (detailsSnapshot.connectionState == ConnectionState.waiting) {
                              return CircularProgressIndicator();
                            } else if (detailsSnapshot.hasError) {
                              return Text('Error: ${detailsSnapshot.error}');
                            } else if (!detailsSnapshot.hasData || detailsSnapshot.data!.isEmpty) {
                              return Text('No data available');
                            } else {
                              return _buildCategorySpendingSummary(detailsSnapshot.data!);
                            }
                          },
                        ),
                      ],
                    );
                  }
                },
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildSpendingTypeSection(String category) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.grey.withOpacity(0.2),
        borderRadius: BorderRadius.circular(12),
      ),
      child: Text(
        '지히님의 지출타입은?\n\t\t\t\t"프로 $category러"',
        style: const TextStyle(
          fontSize: 22,
          fontWeight: FontWeight.bold,
          color: Colors.white,
        ),
      ),
    );
  }

  Widget _buildHistogramChart(
      List<CategorySpendingSummary> barData, Map<String, CategorySpendingAvgDTO> lineData) {
    return Container(
      height: 300,
      child: BarChart(
        BarChartData(
          alignment: BarChartAlignment.spaceAround,
          barGroups: _createHistogramBarGroups(barData, lineData),
          titlesData: FlTitlesData(
            bottomTitles: AxisTitles(
              sideTitles: SideTitles(
                showTitles: true,
                getTitlesWidget: (value, meta) {
                  return Text(barData[value.toInt()].category);
                },
              ),
            ),
            leftTitles: AxisTitles(
              sideTitles: SideTitles(
                showTitles: true,
                reservedSize: 30,
                getTitlesWidget: (value, meta) {
                  return Text('${value.toInt()}');
                },
              ),
            ),
          ),
          borderData: FlBorderData(show: false),
        ),
      ),
    );
  }

  List<BarChartGroupData> _createHistogramBarGroups(
      List<CategorySpendingSummary> barData, Map<String, CategorySpendingAvgDTO> lineData) {
    return List.generate(barData.length, (index) {
      final category = barData[index];
      final avgSpending = lineData[category.category]?.avgAmount ?? 0;
      return BarChartGroupData(
        x: index,
        barRods: [
          BarChartRodData(
            toY: category.totalAmount.toDouble(),
            color: const Color.fromARGB(255, 163, 214, 255),
            width: 15,
          ),
          BarChartRodData(
            toY: avgSpending.toDouble(),
            color: const Color.fromARGB(255, 255, 182, 193),
            width: 15,
          ),
        ],
        barsSpace: 10,
      );
    });
  }

  Widget _buildAnalysisSummary(List<FinancialTrendDTO> trends) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.grey.withOpacity(0.2),
        borderRadius: BorderRadius.circular(12),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: _buildTrendsSummary(trends),
      ),
    );
  }

  List<Widget> _buildTrendsSummary(List<FinancialTrendDTO> trends) {
    List<Widget> trendWidgets = [];
    for (var i = 0; i < trends.length; i += 3) {
      List<Widget> rowItems = [];
      for (var j = i; j < i + 3 && j < trends.length; j++) {
        final trend = trends[j];
        rowItems.add(
          Expanded(
            child: Text(
              '${trend.category}: ${trend.percentChange > 0 ? '+' : ''}${trend.percentChange.toStringAsFixed(1)}%',
              style: const TextStyle(
                fontSize: 18,
                color: Colors.white,
              ),
            ),
          ),
        );
      }
      trendWidgets.add(Row(children: rowItems));
    }
    return trendWidgets;
  }

  Widget _buildDonutChart(List<CategorySpendingSummary> data) {
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
    final random = Random();
    final sectionColors = List.generate(data.length, (_) => predefinedColors[random.nextInt(predefinedColors.length)]);

    return Container(
      height: 300,
      child: PieChart(
        PieChartData(
          sections: _createPieSections(data, sectionColors),
          centerSpaceRadius: 60,
        ),
      ),
    );
  }

  List<PieChartSectionData> _createPieSections(List<CategorySpendingSummary> data, List<Color> sectionColors) {
    return List.generate(data.length, (index) {
      final percentage = (data[index].totalAmount / data.map((e) => e.totalAmount).reduce((a, b) => a + b)) * 100;
      return PieChartSectionData(
        color: sectionColors[index],
        value: data[index].totalAmount.toDouble(),
        title: '${data[index].category} ${percentage.toStringAsFixed(1)}%',
      );
    });
  }

  Widget _buildCategorySpendingSummary(List<StoreSpendingSummary> details) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.grey.withOpacity(0.2),
        borderRadius: BorderRadius.circular(12),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: details.map((detail) {
          return Text(
            '${detail.storeName}(${detail.visitCount}회): ${detail.totalAmount}원',
            style: const TextStyle(
              fontSize: 18,
              color: Colors.white,
            ),
          );
        }).toList(),
      ),
    );
  }
}