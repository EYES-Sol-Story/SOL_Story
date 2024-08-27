const REST_API_URL = 'http://10.0.2.2:8090';

// 카테고리별 지출금액 객체
class CategorySpendingSummary {
  final String category;
  final double totalAmount;

  CategorySpendingSummary({required this.category, required this.totalAmount});

  factory CategorySpendingSummary.fromJson(Map<String, dynamic> json) {
    return CategorySpendingSummary(
      category: json['category'],
      totalAmount: json['totalAmount'],
    );
  }
}

// 연령대별 지출금액 객체
class CategorySpendingAvgDTO {
  final String category;
  final double avgAmount;
  final String ageGroup;

  CategorySpendingAvgDTO({
    required this.category,
    required this.avgAmount,
    required this.ageGroup,
  });

  factory CategorySpendingAvgDTO.fromJson(Map<String, dynamic> json) {
    return CategorySpendingAvgDTO(
      category: json['category'],
      avgAmount: json['avgAmount'].toDouble(),
      ageGroup: json['ageGroup'],
    );
  }
}

// 지출 증감 객체
class FinancialTrendDTO {
  final String category;
  final int totalAmount;
  final int totalAmountBefore;
  final int difference;
  final double percentChange;

  FinancialTrendDTO({
    required this.category,
    required this.totalAmount,
    required this.totalAmountBefore,
    required this.difference,
    required this.percentChange,
  });

  factory FinancialTrendDTO.fromJson(Map<String, dynamic> json) {
    return FinancialTrendDTO(
      category: json['category'],
      totalAmount: json['totalAmount'],
      totalAmountBefore: json['totalAmountBefore'],
      difference: json['difference'],
      percentChange: json['percentChange'],
    );
  }
}

// 최근 7일간 가장 지출이 많았던 카테고리 소비 요약 정보 객체
class StoreSpendingSummary {
  final String storeName;
  final int visitCount;
  final int totalAmount;

  StoreSpendingSummary({
    required this.storeName,
    required this.visitCount,
    required this.totalAmount,
  });

  factory StoreSpendingSummary.fromJson(Map<String, dynamic> json) {
    return StoreSpendingSummary(
      storeName: json['storeName'],
      visitCount: json['visitCount'],
      totalAmount: json['totalAmount'],
    );
  }
}