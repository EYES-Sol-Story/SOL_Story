DROP TABLE "users";

CREATE TABLE "users" (
	"user_no"	NUMBER(10)		NOT NULL,
	"user_id"	VARCHAR2(50)		NOT NULL,
	"password"	VARCHAR2(64)		NOT NULL,
	"user_name"	VARCHAR2(100)		NOT NULL,
	"email"	VARCHAR2(254)		NOT NULL,
	"phone_number"	VARCHAR2(20)		NOT NULL,
	"join_date"	DATE	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "users"."user_no" IS '사용자 일련번호';

COMMENT ON COLUMN "users"."user_id" IS '사용자 ID';

COMMENT ON COLUMN "users"."password" IS '사용자 비밀번호';

COMMENT ON COLUMN "users"."user_name" IS '사용자 이름';

COMMENT ON COLUMN "users"."email" IS '사용자 이메일';

COMMENT ON COLUMN "users"."phone_number" IS '사용자 휴대폰 번호';

COMMENT ON COLUMN "users"."join_date" IS '사용자 가입일자';

DROP TABLE "savings_accounts";

CREATE TABLE "savings_accounts" (
	"account_serial_no"	NUMBER(10)		NOT NULL,
	"user_no"	NUMBER(10)		NOT NULL,
	"account_no"	VARCHAR2(34)		NOT NULL,
	"account_name"	VARCHAR2(100)		NOT NULL,
	"balance"	NUMBER(15)		NOT NULL,
	"created_at"	DATE	DEFAULT SYSDATE	NOT NULL,
	"savings_round"	NUMBER(5)	DEFAULT 1	NULL,
	"last_deposit_date"	DATE	DEFAULT SYSDATE	NOT NULL,
	"status"	NUMBER(5)	DEFAULT 1	NOT NULL,
	"account_types"	NUMBER(5)		NOT NULL
);

COMMENT ON COLUMN "savings_accounts"."account_serial_no" IS '저축 계좌 일련번호';

COMMENT ON COLUMN "savings_accounts"."user_no" IS '사용자 일련번호';

COMMENT ON COLUMN "savings_accounts"."account_no" IS '저축 계좌번호';

COMMENT ON COLUMN "savings_accounts"."account_name" IS '계좌 별명';

COMMENT ON COLUMN "savings_accounts"."balance" IS '계좌 잔액';

COMMENT ON COLUMN "savings_accounts"."created_at" IS '생성 일자';

COMMENT ON COLUMN "savings_accounts"."savings_round" IS '저축 회차';

COMMENT ON COLUMN "savings_accounts"."last_deposit_date" IS '최근 입금 일자';

COMMENT ON COLUMN "savings_accounts"."status" IS '계좌 상태(0 : 종료, 1:활성화)';

COMMENT ON COLUMN "savings_accounts"."account_types" IS '계좌 유형(1: 저축 계좌, 2: 주거래 계좌)';

DROP TABLE "savings_goals";

CREATE TABLE "savings_goals" (
	"goal_no"	NUMBER(10)		NOT NULL,
	"account_serial_no2"	NUMBER(10)		NOT NULL,
	"category_no"	NUMBER(10)		NOT NULL,
	"account_serial_no"	NUMBER(10)		NOT NULL,
	"custom_goal"	VARCHAR2(100)		NULL,
	"target_amount"	NUMBER(15)		NOT NULL,
	"current_amount"	NUMBER(15)		NOT NULL,
	"monthly_deposit_amount"	NUMBER(15)		NOT NULL,
	"start_date"	DATE	DEFAULT SYSDATE	NOT NULL,
	"end_date"	DATE		NOT NULL
);

COMMENT ON COLUMN "savings_goals"."goal_no" IS '저축 목표 일련번호';

COMMENT ON COLUMN "savings_goals"."account_serial_no2" IS '저축 계좌 일련번호';

COMMENT ON COLUMN "savings_goals"."category_no" IS '저축 목표 카테고리 일련번호';

COMMENT ON COLUMN "savings_goals"."account_serial_no" IS '저축 계좌 일련번호';

COMMENT ON COLUMN "savings_goals"."custom_goal" IS '사용자 지정 목표';

COMMENT ON COLUMN "savings_goals"."target_amount" IS '저축 목표 금액';

COMMENT ON COLUMN "savings_goals"."current_amount" IS '현재 저축 금액';

COMMENT ON COLUMN "savings_goals"."monthly_deposit_amount" IS '매달 저축 산정 금액';

COMMENT ON COLUMN "savings_goals"."start_date" IS '시작일자';

COMMENT ON COLUMN "savings_goals"."end_date" IS '종료일자';

DROP TABLE "savings_goal_categories";

CREATE TABLE "savings_goal_categories" (
	"category_no"	NUMBER(10)		NOT NULL,
	"category_name"	VARCHAR2(50)		NOT NULL
);

COMMENT ON COLUMN "savings_goal_categories"."category_no" IS '저축 목표 카테고리 일련번호';

COMMENT ON COLUMN "savings_goal_categories"."category_name" IS '저축 목표 카테고리';

DROP TABLE "deposit_history";

CREATE TABLE "deposit_history" (
	"deposit_no"	NUMBER(10)		NOT NULL,
	"account_serial_no"	NUMBER(10)		NOT NULL,
	"deposit_amount"	NUMBER(15)		NOT NULL,
	"deposit_date"	DATE	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "deposit_history"."deposit_no" IS '입금 내역 일련번호';

COMMENT ON COLUMN "deposit_history"."account_serial_no" IS '저축 계좌 일련번호';

COMMENT ON COLUMN "deposit_history"."deposit_amount" IS '저축 금액';

COMMENT ON COLUMN "deposit_history"."deposit_date" IS '입금 일자';

DROP TABLE "challenges";

CREATE TABLE "challenges" (
	"challenge_no"	NUMBER(10)		NOT NULL,
	"challenge_name"	VARCHAR2(255)		NOT NULL,
	"challenge_description"	VARCHAR2(255)		NOT NULL,
	"reward_points"	NUMBER		NOT NULL,
	"reward_eco_mileage"	NUMBER	DEFAULT 0	NOT NULL,
	"challenge_type"	NUMBER	DEFAULT 0	NOT NULL
);

COMMENT ON COLUMN "challenges"."challenge_no" IS '도전과제 일련번호';

COMMENT ON COLUMN "challenges"."challenge_name" IS '도전과제 이름';

COMMENT ON COLUMN "challenges"."challenge_description" IS '도전과제 설명';

COMMENT ON COLUMN "challenges"."reward_points" IS '도전과제 완료 시 획득할 포인트(1000point = 1key)';

COMMENT ON COLUMN "challenges"."reward_eco_mileage" IS 'ECO 미션 달성시 추가로 획득 가능한 에코 마일리지';

COMMENT ON COLUMN "challenges"."challenge_type" IS '도전과제 유형(0:일반 미션, 1: ECO 미션)';

DROP TABLE "user_challenge_assignments";

CREATE TABLE "user_challenge_assignments" (
	"assignment_no"	NUMBER(10)		NOT NULL,
	"user_no"	NUMBER(10)		NOT NULL,
	"challenge_no"	NUMBER(10)		NOT NULL,
	"assigned_date"	DATE	DEFAULT SYSDATE	NOT NULL,
	"is_selected"	VARCHAR2(5)	DEFAULT N	NOT NULL
);

COMMENT ON COLUMN "user_challenge_assignments"."assignment_no" IS '할당된 도전과제 일련번호(주간 랜덤 5개)';

COMMENT ON COLUMN "user_challenge_assignments"."user_no" IS '사용자 일련번호';

COMMENT ON COLUMN "user_challenge_assignments"."challenge_no" IS '도전과제 일련번호';

COMMENT ON COLUMN "user_challenge_assignments"."assigned_date" IS '도전과제가 사용자에게 할당된 날짜';

COMMENT ON COLUMN "user_challenge_assignments"."is_selected" IS '사용자가 해당 도전과제를 선택했는지 여부 ('Y'/ 'N')';

DROP TABLE "keys";

CREATE TABLE "keys" (
	"user_no"	NUMBER(10)		NOT NULL,
	"total_keys"	NUMBER	DEFAULT 0	NOT NULL,
	"remaining_points"	NUMBER	DEFAULT 0	NOT NULL
);

COMMENT ON COLUMN "keys"."user_no" IS '사용자 일련번호';

COMMENT ON COLUMN "keys"."total_keys" IS '사용자가 획득한 총 열쇠 수';

COMMENT ON COLUMN "keys"."remaining_points" IS '열쇠로 변환되지 않은 포인트(1000 포인트 미만)';

DROP TABLE "user_challenges";

CREATE TABLE "user_challenges" (
	"user_challenge_no"	NUMBER(10)		NOT NULL,
	"assignment_no"	NUMBER(10)		NOT NULL,
	"challenge_no"	NUMBER(10)		NOT NULL,
	"completion_date"	DATE	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "user_challenges"."user_challenge_no" IS '사용자 도전과제 기록 일련번호';

COMMENT ON COLUMN "user_challenges"."assignment_no" IS '할당된 도전과제 일련번호(주간 랜덤 5개)';

COMMENT ON COLUMN "user_challenges"."challenge_no" IS '도전과제 일련번호';

COMMENT ON COLUMN "user_challenges"."completion_date" IS '도전과제 완료일자';

DROP TABLE "eco_mileage";

CREATE TABLE "eco_mileage" (
	"user_no"	NUMBER(10)		NOT NULL,
	"total_mileage"	NUMBER	DEFAULT 0	NOT NULL
);

COMMENT ON COLUMN "eco_mileage"."user_no" IS '사용자 일련번호';

COMMENT ON COLUMN "eco_mileage"."total_mileage" IS '사용자가 적립한 총 에코 마일리지';

DROP TABLE "story_categories";

CREATE TABLE "story_categories" (
	"category_no"	NUMBER(10)		NOT NULL,
	"category_name"	VARCHAR2(50)		NOT NULL,
	"keys_required"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "story_categories"."category_no" IS '스토리 카테고리 일련번호';

COMMENT ON COLUMN "story_categories"."category_name" IS '스토리 카테고리 이름';

COMMENT ON COLUMN "story_categories"."keys_required" IS '스토리 해금에 필요한 열쇠 수';

DROP TABLE "story_keywords";

CREATE TABLE "story_keywords" (
	"keyword_no"	NUMBER(10)		NOT NULL,
	"category_no"	NUMBER(10)		NOT NULL,
	"keyword"	VARCHAR2(100)		NOT NULL
);

COMMENT ON COLUMN "story_keywords"."keyword_no" IS '스토리 키워드 일련번호';

COMMENT ON COLUMN "story_keywords"."category_no" IS '스토리 카테고리 일련번호';

COMMENT ON COLUMN "story_keywords"."keyword" IS '스토리 키워드';

DROP TABLE "user_stories";

CREATE TABLE "user_stories" (
	"user_story_no"	NUMBER(10)		NOT NULL,
	"user_no"	NUMBER(10)		NOT NULL,
	"keyword_no"	NUMBER(10)		NOT NULL,
	"unlock_date"	DATE	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "user_stories"."user_story_no" IS '사용자가 해금한 스토리 키워드 일련번호';

COMMENT ON COLUMN "user_stories"."user_no" IS '사용자 일련번호';

COMMENT ON COLUMN "user_stories"."keyword_no" IS '선택된 스토리 키워드 일련번호';

COMMENT ON COLUMN "user_stories"."unlock_date" IS '스토리 해금일자';

DROP TABLE "character_cards";

CREATE TABLE "character_cards" (
	"character_no"	NUMBER(10)		NOT NULL,
	"character_type"	VARCHAR2(50)		NOT NULL,
	"character_name"	VARCHAR2(100)		NOT NULL,
	"keys_required"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "character_cards"."character_no" IS '인물 카드 일련번호';

COMMENT ON COLUMN "character_cards"."character_type" IS '인물 카드 종류';

COMMENT ON COLUMN "character_cards"."character_name" IS '인물 이름';

COMMENT ON COLUMN "character_cards"."keys_required" IS '인물 카드를 얻기 위해 필요한 열쇠 수';

DROP TABLE "user_character_deck";

CREATE TABLE "user_character_deck" (
	"user_character_no"	NUMBER(10)		NOT NULL,
	"user_no"	NUMBER(10)		NOT NULL,
	"character_no2"	NUMBER(10)		NOT NULL,
	"acquisition_date"	DATE	DEFAULT SYSDATE	NOT NULL,
	"is_active"	VARCHAR2(5)	DEFAULT Y	NOT NULL
);

COMMENT ON COLUMN "user_character_deck"."user_character_no" IS '사용자가 보유한 인물 카드 일련번호';

COMMENT ON COLUMN "user_character_deck"."user_no" IS '사용자 일련번호';

COMMENT ON COLUMN "user_character_deck"."character_no2" IS '인물 카드 일련번호';

COMMENT ON COLUMN "user_character_deck"."acquisition_date" IS '인물 카드 획득일자';

COMMENT ON COLUMN "user_character_deck"."is_active" IS '사용자가 획득한 인물 카드 중 보유 여부('Y' : 보유중,  'N' : 제거됨)';

DROP TABLE "transactions";

CREATE TABLE "transactions" (
	"transaction_no"	NUMBER(10)		NOT NULL,
	"account_serial_no"	NUMBER(10)		NOT NULL,
	"transaction_date"	DATE	DEFAULT SYSDATE	NOT NULL,
	"amount"	NUMBER(15, 2)		NOT NULL,
	"category_no"	NUMBER(10)		NOT NULL,
	"description"	VARCHAR2(255)		NULL
);

COMMENT ON COLUMN "transactions"."transaction_no" IS '계좌 거래 일련번호';

COMMENT ON COLUMN "transactions"."account_serial_no" IS '저축 계좌 일련번호';

COMMENT ON COLUMN "transactions"."transaction_date" IS '거래 날짜';

COMMENT ON COLUMN "transactions"."amount" IS '거래 금액';

COMMENT ON COLUMN "transactions"."category_no" IS '거래 카테고리 일련번호';

COMMENT ON COLUMN "transactions"."description" IS '거래 설명';

DROP TABLE "transaction_categories";

CREATE TABLE "transaction_categories" (
	"category_no"	NUMBER(10)		NOT NULL,
	"category_name"	VARCHAR2(50)		NOT NULL
);

COMMENT ON COLUMN "transaction_categories"."category_no" IS '거래 카테고리 일련번호';

COMMENT ON COLUMN "transaction_categories"."category_name" IS '거래 카테고리 이름';

DROP TABLE "spending_analysis";

CREATE TABLE "spending_analysis" (
	"analysis_no"	NUMBER(10)		NOT NULL,
	"user_no"	NUMBER(10)		NOT NULL,
	"account_serial_no"	NUMBER(10)		NOT NULL,
	"analysis_date"	DATE	DEFAULT SYSDATE	NOT NULL,
	"most_frequent_merchant"	VARCHAR2(100)		NOT NULL,
	"highest_spending_category"	VARCHAR2(100)		NOT NULL,
	"recommended_savings"	VARCHAR2(500)		NOT NULL
);

COMMENT ON COLUMN "spending_analysis"."analysis_no" IS '소비 분석 일련번호';

COMMENT ON COLUMN "spending_analysis"."user_no" IS '사용자 일련번호';

COMMENT ON COLUMN "spending_analysis"."account_serial_no" IS '저축 계좌 일련번호';

COMMENT ON COLUMN "spending_analysis"."analysis_date" IS '분석 날짜';

COMMENT ON COLUMN "spending_analysis"."most_frequent_merchant" IS '가장 큰 소비 카테고리';

COMMENT ON COLUMN "spending_analysis"."highest_spending_category" IS '가장 큰 낭비 카테고리';

COMMENT ON COLUMN "spending_analysis"."recommended_savings" IS '절약할 수 있는 사항';

ALTER TABLE "users" ADD CONSTRAINT "PK_USERS" PRIMARY KEY (
	"user_no"
);

ALTER TABLE "savings_accounts" ADD CONSTRAINT "PK_SAVINGS_ACCOUNTS" PRIMARY KEY (
	"account_serial_no"
);

ALTER TABLE "savings_goals" ADD CONSTRAINT "PK_SAVINGS_GOALS" PRIMARY KEY (
	"goal_no"
);

ALTER TABLE "savings_goal_categories" ADD CONSTRAINT "PK_SAVINGS_GOAL_CATEGORIES" PRIMARY KEY (
	"category_no"
);

ALTER TABLE "deposit_history" ADD CONSTRAINT "PK_DEPOSIT_HISTORY" PRIMARY KEY (
	"deposit_no"
);

ALTER TABLE "challenges" ADD CONSTRAINT "PK_CHALLENGES" PRIMARY KEY (
	"challenge_no"
);

ALTER TABLE "user_challenge_assignments" ADD CONSTRAINT "PK_USER_CHALLENGE_ASSIGNMENTS" PRIMARY KEY (
	"assignment_no"
);

ALTER TABLE "keys" ADD CONSTRAINT "PK_KEYS" PRIMARY KEY (
	"user_no"
);

ALTER TABLE "user_challenges" ADD CONSTRAINT "PK_USER_CHALLENGES" PRIMARY KEY (
	"user_challenge_no"
);

ALTER TABLE "eco_mileage" ADD CONSTRAINT "PK_ECO_MILEAGE" PRIMARY KEY (
	"user_no"
);

ALTER TABLE "story_categories" ADD CONSTRAINT "PK_STORY_CATEGORIES" PRIMARY KEY (
	"category_no"
);

ALTER TABLE "story_keywords" ADD CONSTRAINT "PK_STORY_KEYWORDS" PRIMARY KEY (
	"keyword_no"
);

ALTER TABLE "user_stories" ADD CONSTRAINT "PK_USER_STORIES" PRIMARY KEY (
	"user_story_no"
);

ALTER TABLE "character_cards" ADD CONSTRAINT "PK_CHARACTER_CARDS" PRIMARY KEY (
	"character_no"
);

ALTER TABLE "user_character_deck" ADD CONSTRAINT "PK_USER_CHARACTER_DECK" PRIMARY KEY (
	"user_character_no"
);

ALTER TABLE "transactions" ADD CONSTRAINT "PK_TRANSACTIONS" PRIMARY KEY (
	"transaction_no"
);

ALTER TABLE "transaction_categories" ADD CONSTRAINT "PK_TRANSACTION_CATEGORIES" PRIMARY KEY (
	"category_no"
);

ALTER TABLE "spending_analysis" ADD CONSTRAINT "PK_SPENDING_ANALYSIS" PRIMARY KEY (
	"analysis_no"
);

ALTER TABLE "savings_accounts" ADD CONSTRAINT "FK_users_TO_savings_accounts_1" FOREIGN KEY (
	"user_no"
)
REFERENCES "users" (
	"user_no"
);

ALTER TABLE "savings_goals" ADD CONSTRAINT "FK_savings_accounts_TO_savings_goals_1" FOREIGN KEY (
	"account_serial_no2"
)
REFERENCES "savings_accounts" (
	"account_serial_no"
);

ALTER TABLE "savings_goals" ADD CONSTRAINT "FK_savings_goal_categories_TO_savings_goals_1" FOREIGN KEY (
	"category_no"
)
REFERENCES "savings_goal_categories" (
	"category_no"
);

ALTER TABLE "deposit_history" ADD CONSTRAINT "FK_savings_accounts_TO_deposit_history_1" FOREIGN KEY (
	"account_serial_no"
)
REFERENCES "savings_accounts" (
	"account_serial_no"
);

ALTER TABLE "user_challenge_assignments" ADD CONSTRAINT "FK_users_TO_user_challenge_assignments_1" FOREIGN KEY (
	"user_no"
)
REFERENCES "users" (
	"user_no"
);

ALTER TABLE "user_challenge_assignments" ADD CONSTRAINT "FK_challenges_TO_user_challenge_assignments_1" FOREIGN KEY (
	"challenge_no"
)
REFERENCES "challenges" (
	"challenge_no"
);

ALTER TABLE "keys" ADD CONSTRAINT "FK_users_TO_keys_1" FOREIGN KEY (
	"user_no"
)
REFERENCES "users" (
	"user_no"
);

ALTER TABLE "user_challenges" ADD CONSTRAINT "FK_user_challenge_assignments_TO_user_challenges_1" FOREIGN KEY (
	"assignment_no"
)
REFERENCES "user_challenge_assignments" (
	"assignment_no"
);

ALTER TABLE "eco_mileage" ADD CONSTRAINT "FK_users_TO_eco_mileage_1" FOREIGN KEY (
	"user_no"
)
REFERENCES "users" (
	"user_no"
);

ALTER TABLE "story_keywords" ADD CONSTRAINT "FK_story_categories_TO_story_keywords_1" FOREIGN KEY (
	"category_no"
)
REFERENCES "story_categories" (
	"category_no"
);

ALTER TABLE "user_stories" ADD CONSTRAINT "FK_users_TO_user_stories_1" FOREIGN KEY (
	"user_no"
)
REFERENCES "users" (
	"user_no"
);

ALTER TABLE "user_stories" ADD CONSTRAINT "FK_story_keywords_TO_user_stories_1" FOREIGN KEY (
	"keyword_no"
)
REFERENCES "story_keywords" (
	"keyword_no"
);

ALTER TABLE "user_character_deck" ADD CONSTRAINT "FK_users_TO_user_character_deck_1" FOREIGN KEY (
	"user_no"
)
REFERENCES "users" (
	"user_no"
);

ALTER TABLE "user_character_deck" ADD CONSTRAINT "FK_character_cards_TO_user_character_deck_1" FOREIGN KEY (
	"character_no2"
)
REFERENCES "character_cards" (
	"character_no"
);

ALTER TABLE "transactions" ADD CONSTRAINT "FK_savings_accounts_TO_transactions_1" FOREIGN KEY (
	"account_serial_no"
)
REFERENCES "savings_accounts" (
	"account_serial_no"
);

ALTER TABLE "transactions" ADD CONSTRAINT "FK_transaction_categories_TO_transactions_1" FOREIGN KEY (
	"category_no"
)
REFERENCES "transaction_categories" (
	"category_no"
);

ALTER TABLE "spending_analysis" ADD CONSTRAINT "FK_users_TO_spending_analysis_1" FOREIGN KEY (
	"user_no"
)
REFERENCES "users" (
	"user_no"
);

ALTER TABLE "spending_analysis" ADD CONSTRAINT "FK_savings_accounts_TO_spending_analysis_1" FOREIGN KEY (
	"account_serial_no"
)
REFERENCES "savings_accounts" (
	"account_serial_no"
);

