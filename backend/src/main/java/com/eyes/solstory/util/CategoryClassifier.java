package com.eyes.solstory.util;

import java.util.HashMap;
import java.util.Map;

public class CategoryClassifier {
    
	private static final Map<String, String> keywordToCategoryMap = new HashMap<>();
    static {
        // 카테고리 키워드 맵핑
    	// 1. 식료품 (Groceries)
        keywordToCategoryMap.put("마트", "식료품");
        keywordToCategoryMap.put("식품", "식료품");
        keywordToCategoryMap.put("채소", "식료품");
        keywordToCategoryMap.put("생선", "식료품");
        keywordToCategoryMap.put("신선", "식료품");

        // 2. 외식/식사 (Dining Out)
        keywordToCategoryMap.put("식당", "외식/식사");
        keywordToCategoryMap.put("김치", "외식/식사");
        keywordToCategoryMap.put("그릴", "외식/식사");
        keywordToCategoryMap.put("도시락", "외식/식사");
        keywordToCategoryMap.put("피자", "외식/식사");
        keywordToCategoryMap.put("치킨", "외식/식사");
        keywordToCategoryMap.put("국밥", "외식/식사");
        keywordToCategoryMap.put("냉면", "외식/식사");
        keywordToCategoryMap.put("샌드위치", "외식/식사");
        keywordToCategoryMap.put("레스토랑", "외식/식사");
        keywordToCategoryMap.put("카페", "외식/식사");
        keywordToCategoryMap.put("커피", "외식/식사");
        keywordToCategoryMap.put("파스타", "외식/식사");

        // 3. 주유 (Fuel/Gasoline)
        keywordToCategoryMap.put("주유소"  , "주유");
        keywordToCategoryMap.put("오일"   , "주유");
        keywordToCategoryMap.put("고속도로", "주유");

        // 4. 교통비 (Transportation)
        keywordToCategoryMap.put("버스", "교통비");
        keywordToCategoryMap.put("지하철", "교통비");
        keywordToCategoryMap.put("택시", "교통비");
        keywordToCategoryMap.put("공항", "교통비");

        // 5. 쇼핑 (Shopping - General)
        keywordToCategoryMap.put("쇼핑몰", "쇼핑");
        keywordToCategoryMap.put("패션", "쇼핑");
        keywordToCategoryMap.put("백화점", "쇼핑");
        keywordToCategoryMap.put("이커머스", "쇼핑");

        // 6. 패션/의류 (Clothing & Accessories)
        keywordToCategoryMap.put("패션", "패션/의류");
        keywordToCategoryMap.put("의류", "패션/의류");
        keywordToCategoryMap.put("트렌드", "패션/의류");
        keywordToCategoryMap.put("잡화", "패션/의류");
        keywordToCategoryMap.put("쥬얼리", "패션/의류");
        keywordToCategoryMap.put("모자", "패션/의류");

        // 7. 가전/전자제품 (Electronics & Appliances)
        keywordToCategoryMap.put("전자", "가전/전자제품");
        keywordToCategoryMap.put("디지털", "가전/전자제품");
        keywordToCategoryMap.put("가전제품", "가전/전자제품");
        keywordToCategoryMap.put("컴퓨터", "가전/전자제품");
        keywordToCategoryMap.put("스마트기기", "가전/전자제품");

        // 8. 여행/휴가 (Travel & Vacation)
        keywordToCategoryMap.put("트래블", "여행/휴가");
        keywordToCategoryMap.put("휴가", "여행/휴가");
        keywordToCategoryMap.put("항공", "여행/휴가");
        keywordToCategoryMap.put("여행", "여행/휴가");
        keywordToCategoryMap.put("투어", "여행/휴가");

        // 9. 건강/의료비 (Healthcare & Medical)
        keywordToCategoryMap.put("메디컬", "건강/의료비");
        keywordToCategoryMap.put("병원", "건강/의료비");
        keywordToCategoryMap.put("약국", "건강/의료비");
        keywordToCategoryMap.put("검진", "건강/의료비");
        keywordToCategoryMap.put("의료기기", "건강/의료비");

        // 10. 주거비 (Housing/Rent/Mortgage)
        keywordToCategoryMap.put("부동산", "주거비");
        keywordToCategoryMap.put("월세", "주거비");
        keywordToCategoryMap.put("아파트", "주거비");
        keywordToCategoryMap.put("주택", "주거비");

        // 11. 통신비 (Phone & Internet)
        keywordToCategoryMap.put("통신", "통신비");
        keywordToCategoryMap.put("핸드폰", "통신비");
        keywordToCategoryMap.put("인터넷", "통신비");
        keywordToCategoryMap.put("모바일", "통신비");
        keywordToCategoryMap.put("와이파이", "통신비");

        // 12. 엔터테인먼트 (Entertainment - Movies, Concerts, etc.)
        keywordToCategoryMap.put("영화", "엔터테인먼트");
        keywordToCategoryMap.put("콘서트", "엔터테인먼트");
        keywordToCategoryMap.put("연극", "엔터테인먼트");
        keywordToCategoryMap.put("뮤직", "엔터테인먼트");
        keywordToCategoryMap.put("게임", "엔터테인먼트");
        keywordToCategoryMap.put("노래", "엔터테인먼트");

        // 13. 스포츠/레저 (Sports & Leisure)
        keywordToCategoryMap.put("스포츠", "스포츠/레저");
        keywordToCategoryMap.put("수영", "스포츠/레저");
        keywordToCategoryMap.put("볼링", "스포츠/레저");
        keywordToCategoryMap.put("클라이밍", "스포츠/레저");
        keywordToCategoryMap.put("헬스장", "스포츠/레저");
        keywordToCategoryMap.put("레저", "스포츠/레저");
        keywordToCategoryMap.put("피트니스", "스포츠/레저");
        keywordToCategoryMap.put("골프", "스포츠/레저");

        // 14. 교육비 (Education & Tuition)
        keywordToCategoryMap.put("학원", "교육비 ");
        keywordToCategoryMap.put("교육", "교육비 ");
        keywordToCategoryMap.put("유학원", "교육비 ");
        keywordToCategoryMap.put("강의", "교육비 ");

        // 15. 자동차 유지비 (Car Maintenance & Repairs)
        keywordToCategoryMap.put("정비소", "자동차 유지비");
        keywordToCategoryMap.put("타이어", "자동차 유지비");
        keywordToCategoryMap.put("카센터", "자동차 유지비");
        keywordToCategoryMap.put("세차장", "자동차 유지비");
        keywordToCategoryMap.put("엔진오일", "자동차 유지비");

        // 16. 가구/인테리어 (Furniture & Home Decor)
        keywordToCategoryMap.put("가구", "가구/인테리어");
        keywordToCategoryMap.put("인테리어", "가구/인테리어");
        keywordToCategoryMap.put("침대", "가구/인테리어");
        keywordToCategoryMap.put("커튼", "가구/인테리어");
        keywordToCategoryMap.put("리빙", "가구/인테리어");

        // 17. 미용/화장품 (Beauty & Cosmetics)
        keywordToCategoryMap.put("뷰티", "미용/화장품");
        keywordToCategoryMap.put("헤어", "미용/화장품");
        keywordToCategoryMap.put("코스메틱", "미용/화장품");
        keywordToCategoryMap.put("네일아트", "미용/화장품");
        keywordToCategoryMap.put("화장품", "미용/화장품");
        keywordToCategoryMap.put("스킨케어", "미용/화장품");

        // 18. 보험료 (Insurance Premiums)
        keywordToCategoryMap.put("보험", "보험료");
        keywordToCategoryMap.put("연금", "보험료");

        // 19. 유틸리티 (Utilities - Electricity, Water, etc.)
        keywordToCategoryMap.put("전기", "유틸리티");
        keywordToCategoryMap.put("수도", "유틸리티");
        keywordToCategoryMap.put("가스", "유틸리티");
        keywordToCategoryMap.put("난방", "유틸리티");

        // 20. 모임/경조사비 (Social Events & Gifts)
        keywordToCategoryMap.put("꽃배달", "모임/경조사비");
        keywordToCategoryMap.put("선물", "모임/경조사비");
        keywordToCategoryMap.put("모임", "모임/경조사비");
        keywordToCategoryMap.put("경조사", "모임/경조사비");
        keywordToCategoryMap.put("이벤트", "모임/경조사비");
    }

    public static String classify(String storeName) {
        // 키워드 기반 카테고리 분류
        for (Map.Entry<String, String> entry : keywordToCategoryMap.entrySet()) {
            if (storeName.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "기타";  // 해당하는 카테고리가 없을 경우 모두 기타로 분류
    }

}