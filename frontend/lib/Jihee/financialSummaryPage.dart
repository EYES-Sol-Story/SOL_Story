import 'package:flutter/material.dart';
import 'financialModels.dart'; // 모델 파일 import
import 'spendingAmount.dart';
import 'spendingAmountWithCategory.dart';
import 'spendingTrends.dart';
import 'spendingDetails.dart';
import 'financialChart.dart';

class FinancialSummaryPage extends StatefulWidget {
  @override
  _FinancialSummaryPageState createState() => _FinancialSummaryPageState();
}

var userNo = 1;

class _FinancialSummaryPageState extends State<FinancialSummaryPage> {
  Future<List<CategorySpendingSummary>>? futureBarData;
  Future<Map<String, CategorySpendingAvgDTO>>? futureLineData;
  Future<List<CategorySpendingSummary>>? futureDonutData;

  @override
  void initState() {
    super.initState();
    futureBarData = fetchTop5Categories(userNo);
    futureLineData = fetchTop5CategoriesWithAvg(userNo);
    futureDonutData = fetchLast7DaysSpending(userNo);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('지출 요약'),
      ),
      body: Center(
        child: FutureBuilder<List<CategorySpendingSummary>>(
          future: futureBarData,
          builder: (context, barSnapshot) {
            if (barSnapshot.connectionState == ConnectionState.waiting) {
              return CircularProgressIndicator();
            } else if (barSnapshot.hasError) {
              return Text('Error: ${barSnapshot.error}');
            } else if (!barSnapshot.hasData || barSnapshot.data!.isEmpty) {
              return Text('No data available');
            } else {
              // BarChart 데이터 로딩 완료
              return FutureBuilder<Map<String, CategorySpendingAvgDTO>>(
                future: futureLineData,
                builder: (context, lineSnapshot) {
                  if (lineSnapshot.connectionState == ConnectionState.waiting) {
                    return CircularProgressIndicator();
                  } else if (lineSnapshot.hasError) {
                    return Text('Error: ${lineSnapshot.error}');
                  } else if (!lineSnapshot.hasData || lineSnapshot.data!.isEmpty) {
                    return Text('No data available');
                  } else {
                    // LineChart 데이터 로딩 완료
                    return Column(
                      children: [
                        Align(
                          alignment: Alignment.topCenter,
                          child: SizedBox(
                            height: MediaQuery.of(context).size.height * 0.4,
                            width: MediaQuery.of(context).size.width * 0.9,
                            child: Padding(
                              padding: const EdgeInsets.all(8.0),
                              child: CombinedChart(
                                barData: barSnapshot.data!,
                                lineData: lineSnapshot.data!,
                              ),
                            ),
                          ),
                        ),
                        FutureBuilder<List<CategorySpendingSummary>>(
                          future: futureDonutData,
                          builder: (context, donutSnapshot) {
                            if (donutSnapshot.connectionState == ConnectionState.waiting) {
                              return CircularProgressIndicator();
                            } else if (donutSnapshot.hasError) {
                              return Text('Error: ${donutSnapshot.error}');
                            } else if (!donutSnapshot.hasData || donutSnapshot.data!.isEmpty) {
                              return Text('No data available');
                            } else {
                              // DonutChart 데이터 로딩 완료
                              return Align(
                                alignment: Alignment.bottomCenter,
                                child: SizedBox(
                                  height: MediaQuery.of(context).size.height * 0.4,
                                  width: MediaQuery.of(context).size.width * 0.8,
                                  child: Padding(
                                    padding: const EdgeInsets.all(8.0),
                                    child: DonutChart(data: donutSnapshot.data!),
                                  ),
                                ),
                              );
                            }
                          },
                        ),
                      ],
                    );
                  }
                },
              );
            }
          },
        ),
      ),
    );
  }
}