package com.eyes.solstory.domain.financial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eyes.solstory.domain.financial.dto.CategorySpendingSummaryDTO;
import com.eyes.solstory.domain.financial.entity.DailyFinancialSummary;

@Repository
public interface FinancialSummaryRepository extends JpaRepository<DailyFinancialSummary, Integer> {
	
	@Query(value =  "SELECT * FROM (" + 
					"    SELECT category, SUM(total_amount) as total_amount " +
		            "    FROM daily_financial_summary " +
		            "    WHERE user_no = :userNo AND financial_type = 2" +
		            "    AND financial_date >= SYSDATE - 30" +
		            "    GROUP BY category " +
		            "    ORDER BY SUM(total_amount) DESC" + 
		            ") " +
		            "    WHERE ROWNUM <= 5"
		   , nativeQuery = true)
	List<CategorySpendingSummaryDTO> findTopCategoriesByUser(@Param("userNo") int userNo);

	/*
	 *
select user_no, category, sum(total_amount) as total_amoount
from daily_financial_summary 
where financial_type = 2 
and financial_date >= sysdate-30
group by category, user_no
order by sum(total_amount) desc
;

WITH UserAgeGroup AS (
    SELECT 
        user_no,
        CASE 
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 0 AND 9 THEN '0대'
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 10 AND 19 THEN '10대'
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 20 AND 29 THEN '20대'
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 30 AND 39 THEN '30대'
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 40 AND 49 THEN '40대'
            ELSE '50대이상'
        END AS age_group
    FROM Users
    WHERE user_no = :targetUserNo
),
CategorySpendingByAgeGroup AS (
    SELECT 
        CASE 
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 0 AND 9 THEN '0대'
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 10 AND 19 THEN '10대'
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 20 AND 29 THEN '20대'
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 30 AND 39 THEN '30대'
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 40 AND 49 THEN '40대'
            ELSE '50대이상'
        END AS age_group,
        dfs.category,
        AVG(dfs.total_amount) AS avg_spending
    FROM daily_financial_summary dfs
    JOIN Users u ON dfs.user_no = u.user_no
    WHERE dfs.financial_type = 2
    AND dfs.financial_date >= SYSDATE - 30
    GROUP BY 
        CASE 
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 0 AND 9 THEN '0대'
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 10 AND 19 THEN '10대'
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 20 AND 29 THEN '20대'
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 30 AND 39 THEN '30대'
            WHEN EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE(birth)) +1 BETWEEN 40 AND 49 THEN '40대'
            ELSE '50대이상'
        END,
        dfs.category
)
SELECT age_group, category, avg_spending
FROM (
    SELECT 
        c.age_group,
        c.category,
        c.avg_spending,
        RANK() OVER (ORDER BY c.avg_spending DESC) as rnk
    FROM CategorySpendingByAgeGroup c
    JOIN UserAgeGroup uag ON c.age_group = uag.age_group
)
WHERE rnk <= 5;	 
	 
	 */
}
