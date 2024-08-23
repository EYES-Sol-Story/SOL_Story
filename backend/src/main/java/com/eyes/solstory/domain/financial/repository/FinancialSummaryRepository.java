package com.eyes.solstory.domain.financial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eyes.solstory.domain.financial.entity.DailyFinancialSummary;

@Repository
public interface FinancialSummaryRepository extends JpaRepository<DailyFinancialSummary, Integer> {
	
	@Query(value =  "SELECT * FROM (" + 
					"    SELECT d.category as category, SUM(d.total_amount) as total_amount " +
		            "    FROM daily_financial_summary d " +
		            "    WHERE user_no = :userNo AND financial_type = 2 " +
		            "    AND financial_date >= SYSDATE - 30 " +
		            "    GROUP BY d.category " +
		            "    ORDER BY SUM(d.total_amount) DESC " + 
		            ") " +
		            "    WHERE ROWNUM <= 5 "
		   , nativeQuery = true)
	List<Object[]> findTopCategoriesByUser(@Param("userNo") int userNo);

	
	@Query(value = "WITH " +
	        "-- 전체 사용자 연령대 " +
	        "user_age_group AS ( " +
	        "    SELECT user_no, " +
	        "           CASE " +
	        "               WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM birth) + 1 BETWEEN 0 AND 9 THEN '0대' " +
	        "               WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM birth) + 1 BETWEEN 10 AND 19 THEN '10대' " +
	        "               WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM birth) + 1 BETWEEN 20 AND 29 THEN '20대' " +
	        "               WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM birth) + 1 BETWEEN 30 AND 39 THEN '30대' " +
	        "               WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM birth) + 1 BETWEEN 40 AND 49 THEN '40대' " +
	        "               ELSE '50대이상' " +
	        "           END AS age_group " +
	        "    FROM users " +
	        "), " +
	        "-- 사용자 최근 30일 지출 상위 5개 카테고리 " +
	        "user_top5_spending_category AS ( " +
	        "    SELECT a.category " +
	        "    FROM  ( " +
	        "           SELECT d.category as category, SUM(d.total_amount) as total_amount " +
	        "           FROM daily_financial_summary d " +
	        "           WHERE d.user_no = :userNo AND d.financial_type = 2 " +
	        "           AND d.financial_date >= SYSDATE - 30 " +
	        "           GROUP BY d.category " +
	        "           ORDER BY SUM(d.total_amount) DESC " +
	        "          ) a " +
	        "    WHERE ROWNUM <= 5 " +
	        ") " +
	        "SELECT do.category AS category, uag.age_group, ROUND(AVG(do.total_amount)) AS avg_amount " +
	        "FROM daily_financial_summary do " +
	        "JOIN users uo ON uo.user_no = do.user_no " +
	        "JOIN user_age_group uag ON uo.user_no = uag.user_no " +
	        "WHERE uag.age_group = ( " +
	        "                        SELECT u.age_group " +
	        "                        FROM  user_age_group u " +
	        "                        WHERE u.user_no = :userNo " +
	        "                      ) " +
	        "AND do.category IN ( " +
	        "                     SELECT category " +
	        "                     FROM user_top5_spending_category " +
	        "                   ) " +
	        "GROUP BY do.category, uag.age_group ",
	        nativeQuery = true)
	List<Object[]> findTopCategoriesWithAvg(@Param("userNo") int userNo);
	
	@Query(value = "WITH recent_top10_spending_category AS (" +
					"SELECT *" +
					"FROM (" +
					"		SELECT d.category, SUM(d.total_amount) AS total_amount" +
					"		FROM daily_financial_summary d" +
					"		WHERE user_no = :userNo"+
					"		  AND financial_type = 2"+
					"		  AND financial_date >= sysdate-30" +
					"		GROUP BY category" +
					"		ORDER BY SUM(d.total_amount) DESC"+ 
					"		)" +
					"WHERE ROWNUM <= 10" +
					")" +
					
		            "SELECT r.category, " + 
		            "		r.total_amount AS total_amount, " +
		            "       n.total_amount_before, " +
		            "       r.total_amount - n.total_amount_before AS difference, " +
		            "       CASE WHEN n.total_amount_before = 0 THEN 999 " + // 무한증가
		            "            ELSE ROUND(((r.total_amount - n.total_amount_before)/n.total_amount_before) * 100, 2) " +
		            "       END AS percent_change " +
		            "FROM recent_top10_spending_category r, " +
		            "     ("+
		            "		SELECT b.category, " +
		            "             SUM(b.total_amount) AS total_amount_before " +
		            "      FROM daily_financial_summary b " +
		            "      WHERE b.user_no = :userNo " +
		            "        AND b.financial_type = 2 " +
		            "        AND b.financial_date >= SYSDATE - 60 " +
		            "        AND b.financial_date < SYSDATE - 30 " +
		            "        AND b.category IN (SELECT a.category " +
		            "                           FROM recent_top10_spending_category a "+
		            "							) " +
		            "      GROUP BY b.category"+
		            "	   ) n " +
		            "WHERE r.category = n.category " +
		            "ORDER BY r.total_amount DESC"
		            , nativeQuery = true)
	List<Object[]> getSpendingTrends(@Param("userNo") int userNo);
	
	
	@Query(value = "SELECT d.category as category, SUM(d.total_amount) as total_amount " +
		           "FROM daily_financial_summary d " +
		           "WHERE user_no = :userNo AND financial_type = 2 " +
		           "AND financial_date >= SYSDATE - 7 " +
		           "GROUP BY d.category " +
		           "ORDER BY SUM(d.total_amount) DESC" 
            , nativeQuery = true)
	List<Object[]> getLast7DaysSpending(@Param("userNo") int userNo);
	
	@Query(value = "SELECT u.user_key, a.account_no, dr.category "
				 + "FROM users u, user_accounts a, ( "
				 + "                                SELECT do.user_no, do.category "
				 + "                                FROM ("
				 + "                                        SELECT d.user_no as user_no "
				 + "                                                ,d.category as category "
				 + "                                        FROM daily_financial_summary d "
				 + "                                        WHERE user_no = :userNo "
				 + "                                        AND financial_type = 2 "
				 + "                                        AND financial_date >= SYSDATE - 7 "
				 + "                                        GROUP BY d.user_no, d.category "
				 + "                                        ORDER BY SUM(d.total_amount) DESC "
				 + "                                    ) do "
				 + "                                WHERE ROWNUM = 1 "
				 + "                                ) dr "
				 + "WHERE u.user_no = dr.user_no "
				 + "AND   u.user_no = a.user_no  "
				 + "AND   a.is_active = 1 "
				 + "AND   a.account_type = 2 "
			, nativeQuery = true)
	String[] getUserMostSpendingCategory(@Param("userNo") int userNo);
	
	
	@Query(value = "WITH "
				+ "recent_spending_category AS "
				+ "("
				+ "    SELECT user_no, category, SUM(total_amount) AS total_amount "
				+ "    FROM daily_financial_summary "
				+ "    WHERE financial_type = 2 "
				+ "      AND financial_date >= SYSDATE - 30 "
				+ "    GROUP BY user_no, category "
				+ "),"
				+ "before_spending_category AS "
				+ "("
				+ "    SELECT user_no, category, SUM(total_amount) AS total_amount "
				+ "    FROM daily_financial_summary "
				+ "    WHERE financial_type = 2 "
				+ "      AND financial_date >= SYSDATE - 60 "
				+ "      AND financial_date < SYSDATE - 30 "
				+ "    GROUP BY user_no, category "
				+ ")"
				+ ""
				+ "SELECT u.user_key, a.account_no, t.category "
				+ "FROM  "
				+ "("
				+ "    SELECT r.user_no, r.category, "
				+ "           CASE WHEN NVL(b.total_amount, 0) = 0 THEN 999 "
				+ "                ELSE (r.total_amount - b.total_amount) * 100 / b.total_amount "
				+ "           END AS growth_rate "
				+ "    FROM recent_spending_category r "
				+ "    JOIN before_spending_category b ON r.user_no = b.user_no AND r.category = b.category "
				+ "    ORDER BY growth_rate DESC "
				+ ") t "
				+ "JOIN user_accounts a ON t.user_no = a.user_no "
				+ "JOIN users u ON t.user_no = u.user_no "
				+ "WHERE a.is_active = 1 "
				+ "  AND a.account_type = 2 "
				+ "  AND ROWNUM = 1 "
			, nativeQuery = true)
	String[] getCategoryWithHighestSpendingGrowth(@Param("userNo") int userNo);
	
	@Query(value = "SELECT ot.category"
				+ " FROM ("
				+ "         SELECT d.category"
				+ "         FROM daily_financial_summary d"
				+ "         WHERE d.user_no = :userNo"
				+ "         AND   financial_type = 2 --지출"
				+ "         AND   financial_date >= SYSDATE -30"
				+ "         GROUP BY d.category"
				+ "         ORDER BY NVL(SUM(d.total_amount), 0) DESC"
				+ "      ) ot"
				+ " WHERE ROWNUM = 1"
			, nativeQuery = true)
	String findTopCategoryByUserNo(@Param("userNo") int userNo);
	
	
	@Query(value = "SELECT NVL(SUM(total_amount), 0) AS toal_amount"
				+ " FROM daily_financial_summary "
				+ " WHERE financial_date >= SYSDATE - 30 "
				+ " AND user_no = :userNo"
			, nativeQuery = true)
	int deriveTotalSpendingForMonth(@Param("userNo") int userNo);
	
	@Query(value = "SELECT 50 + (( CASE WHEN r.savings_total_before = 0 THEN 100 "
				+ "                    ELSE ROUND (100 * (r.savings_total_after - r.savings_total_before) / r.savings_total_before) "
				+ "               END ) "
				+ "          +  ( CASE WHEN r.spending_total_before = 0 THEN 100"
				+ "                    ELSE ROUND (100 * (r.spending_total_after - r.spending_total_before) / r.spending_total_before) "
				+ "               END ) "
				+ "             )/4 AS financial_score "
				+ "FROM( "
				+ "    SELECT SUM( "
				+ "                CASE WHEN (financial_type = 1 AND financial_date >= SYSDATE - 30) THEN NVL(total_amount, 0) "
				+ "                     ELSE 0 "
				+ "                END "
				+ "          ) AS savings_total_after "
				+ "        , SUM( "
				+ "                CASE WHEN (financial_type = 1 AND financial_date >= SYSDATE - 60 AND financial_date < SYSDATE - 30) THEN NVL(total_amount, 0) "
				+ "                     ELSE 0 "
				+ "                END "
				+ "          ) AS savings_total_before "
				+ "        , SUM( "
				+ "                CASE WHEN (financial_type = 2 AND financial_date >= SYSDATE - 30) THEN NVL(total_amount, 0) "
				+ "                     ELSE 0 "
				+ "                END "
				+ "          ) AS spending_total_after "
				+ "        , SUM( "
				+ "                CASE WHEN (financial_type = 2 AND financial_date >= SYSDATE - 60 AND financial_date < SYSDATE - 30) THEN NVL(total_amount, 0) "
				+ "                     ELSE 0 "
				+ "                END "
				+ "          ) AS spending_total_before "
				+ "    FROM daily_financial_summary "
				+ "    WHERE user_no = :user_no "
				+ ") r "
			, nativeQuery = true)
	int deriveFinancialScore(@Param("userNo") int userNo);
	
	
	/*
	 * UserAccount에서
	@Query(value = "SELECT u.user_key, a.account_no "
			+ "FROM user_accounts a, users u "
			+ "WHERE a.user_no = u.user_no "
			+ "AND u.user_no = :userNo "
			+ "AND is_active = 1 "
			+ "AND account_type = 1 -- 저축계좌"
			, nativeQuery = true)
	String[] getTotalSavingsAmount(@Param("userNo") int userNo);
	*/
}


