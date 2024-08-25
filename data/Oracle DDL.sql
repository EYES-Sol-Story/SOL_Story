DROP TABLE user_card_deck CASCADE CONSTRAINTS;
DROP TABLE story_cards CASCADE CONSTRAINTS;
DROP TABLE user_stories CASCADE CONSTRAINTS;
DROP TABLE story_steps CASCADE CONSTRAINTS;
DROP TABLE challenge_rewards CASCADE CONSTRAINTS;
DROP TABLE user_challenges CASCADE CONSTRAINTS;
DROP TABLE challenges CASCADE CONSTRAINTS;
DROP TABLE daily_financial_summary CASCADE CONSTRAINTS;
DROP TABLE user_accounts CASCADE CONSTRAINTS;
DROP TABLE user_details CASCADE CONSTRAINTS;
DROP TABLE users CASCADE CONSTRAINTS;

CREATE TABLE users (
	user_no	NUMBER(10) NOT NULL,
	user_id	VARCHAR2(50) NOT NULL,
	password VARCHAR2(64) NOT NULL,
	user_name VARCHAR2(100)	NOT NULL,
	email VARCHAR2(254) NOT NULL,
	gender	VARCHAR2(10) NOT NULL,
	birth DATE NOT NULL,
	join_date DATE DEFAULT SYSDATE NOT NULL,
	character_img_path VARCHAR(200) NULL,
	CONSTRAINT PK_USERS PRIMARY KEY (user_no)
);

CREATE TABLE user_details (
	detail_no NUMBER(10) NOT NULL,
	user_no	NUMBER(10) NOT NULL,
	attribute_type NUMBER(1) NOT NULL,
	attribute_value VARCHAR2(50) NOT NULL,
	CONSTRAINT PK_USER_DETAILS PRIMARY KEY (detail_no)
);

CREATE TABLE user_accounts (
	account_no VARCHAR2(34)	 NOT NULL,
	user_no	NUMBER(10) NOT NULL,
	account_type NUMBER(1) NOT NULL,
	account_name VARCHAR2(100) NOT NULL,
	target_amount NUMBER(15)	 NULL,
	reg_date DATE DEFAULT SYSDATE NOT NULL,
	end_date DATE NOT NULL,
	is_active	NUMBER(1) DEFAULT 1 NOT NULL,
	CONSTRAINT PK_USER_ACCOUNTS PRIMARY KEY (account_no)
);

CREATE TABLE daily_financial_summary (
	summary_no NUMBER(10) NOT NULL,
	user_no	NUMBER(10) NOT NULL,
	financial_date DATE NOT NULL,
	financial_type NUMBER(1) NOT NULL,
	category VARCHAR2(50) NULL,
	total_amount NUMBER(15) DEFAULT 0 NOT NULL,
	CONSTRAINT PK_DAILY_FINANCIAL_SUMMARY PRIMARY KEY (summary_no)
);

CREATE TABLE challenges (
	challenge_no NUMBER(10) NOT NULL,
	challenge_name VARCHAR2(255) NOT NULL,
	challenge_description VARCHAR2(255) NULL,
	reward_points NUMBER(5) NOT NULL,
	challenge_type NUMBER(1) NOT NULL,
	CONSTRAINT PK_CHALLENGES PRIMARY KEY (challenge_no)
);

CREATE TABLE user_challenges (
	assignment_no NUMBER(10)	 NOT NULL,
	user_no	NUMBER(10) NOT NULL,
	challenge_no NUMBER(10) NOT NULL,
	assigned_date DATE DEFAULT SYSDATE NOT NULL,
	is_selected VARCHAR2(5) DEFAULT 'N'	NOT NULL,
	complete_date DATE NULL,
	CONSTRAINT PK_USER_CHALLENGES PRIMARY KEY (assignment_no)
);

CREATE TABLE challenge_rewards (
	reward_no NUMBER(10) NOT NULL,
	user_no	NUMBER(10) NOT NULL,
	total_keys NUMBER DEFAULT 0 NOT NULL,
	remaining_points NUMBER DEFAULT 0	 NOT NULL,
	CONSTRAINT PK_CHALLENGE_REWARDS PRIMARY KEY (user_no)
);

CREATE TABLE story_steps (
	step_no	NUMBER(10) NOT NULL,
	step_name VARCHAR2(50) NOT NULL,
	keys_required NUMBER(2) NOT NULL,
	CONSTRAINT PK_STORY_STEPS PRIMARY KEY (step_no)
);

CREATE TABLE user_stories (
	user_story_no NUMBER(10) NOT NULL,
	user_no	NUMBER(10) NOT NULL,
	step_no	NUMBER(10) NOT NULL,
	story_content CLOB NOT NULL,
	unlock_date DATE	DEFAULT SYSDATE	NOT NULL,
	CONSTRAINT PK_USER_STORIES PRIMARY KEY (user_story_no)
);

CREATE TABLE story_cards (
	card_no	NUMBER(10) NOT NULL,
	card_type VARCHAR2(50) NOT NULL,
	card_keyword VARCHAR2(100) NOT NULL,
	keys_required NUMBER(2) NOT NULL,
	CONSTRAINT PK_STORY_CARDS PRIMARY KEY (card_no)
);

CREATE TABLE user_card_deck (
	user_card_no NUMBER(10) NOT NULL,
	card_no	NUMBER(10) NOT NULL,
	user_story_no NUMBER(10) NULL,
	user_no	NUMBER(10) NOT NULL,
	acquisition_date DATE DEFAULT SYSDATE NOT NULL,
	is_active	VARCHAR2(5) DEFAULT 'Y' NOT NULL,
	CONSTRAINT PK_USER_CARD_DECK PRIMARY KEY (user_card_no)
);


COMMENT ON COLUMN users.user_no IS '사용자 일련번호';
COMMENT ON COLUMN users.user_id IS '사용자 ID';
COMMENT ON COLUMN users.password IS '사용자 비밀번호';
COMMENT ON COLUMN users.user_name IS '사용자 이름';
COMMENT ON COLUMN users.email IS '사용자 이메일';
COMMENT ON COLUMN users.gender IS '사용자 성별(MALE/FEMALE)';
COMMENT ON COLUMN users.birth IS '사용자 생년월일';
COMMENT ON COLUMN users.join_date IS '사용자 가입일자';
COMMENT ON COLUMN users.character_img_path IS '캐릭터 이미지 저장된 경로';

COMMENT ON COLUMN user_details.detail_no IS '사용자 정보 일련번호';
COMMENT ON COLUMN user_details.user_no IS '사용자 일련번호';
COMMENT ON COLUMN user_details.attribute_type IS '사용자 정보 타입 (1: MBTI, 2:취미, 3:관심사)';
COMMENT ON COLUMN user_details.attribute_value IS '사용자 정보 (MBTI타입, 취미/관심사 카테고리)';

COMMENT ON COLUMN user_accounts.account_no IS '저축 계좌번호';
COMMENT ON COLUMN user_accounts.user_no IS '사용자 일련번호';
COMMENT ON COLUMN user_accounts.account_type IS '계좌 유형(1: 저축 예적금 계좌, 2: 주거래 입출금 계좌)';
COMMENT ON COLUMN user_accounts.account_name IS '사용자 지정 목표(계좌 연동시 계좌 이름 설정)';
COMMENT ON COLUMN user_accounts.target_amount IS '저축 목표 금액(적금 최종 금액)';
COMMENT ON COLUMN user_accounts.reg_date IS '시작일자 (계좌 연동 날짜)';
COMMENT ON COLUMN user_accounts.end_date IS '종료일자';
COMMENT ON COLUMN user_accounts.is_active IS '계좌 상태(0: 비활성화, 1:활성화)';

COMMENT ON COLUMN daily_financial_summary.summary_no IS 'summary 일련번호';
COMMENT ON COLUMN daily_financial_summary.user_no IS '사용자 일련번호';
COMMENT ON COLUMN daily_financial_summary.financial_date IS '저축/소비 해당 날짜';
COMMENT ON COLUMN daily_financial_summary.financial_type IS '산출 유형(1: 저축 , 2:소비)';
COMMENT ON COLUMN daily_financial_summary.category IS '지출 카테고리';
COMMENT ON COLUMN daily_financial_summary.total_amount IS '총액(저축/지출 카테고리별)';

COMMENT ON COLUMN challenges.challenge_no IS '챌린지 일련번호';
COMMENT ON COLUMN challenges.challenge_name IS '챌린지 이름';
COMMENT ON COLUMN challenges.challenge_description IS '챌린지 설명';
COMMENT ON COLUMN challenges.reward_points IS '챌린지 과제 완료 시 획득할 포인트(1000point = 1key)';
COMMENT ON COLUMN challenges.challenge_type IS '챌린지 유형(1:저축 , 2:지출)';

COMMENT ON COLUMN user_challenges.assignment_no IS '제시된 도전과제 일련번호';
COMMENT ON COLUMN user_challenges.user_no IS '사용자 일련번호';
COMMENT ON COLUMN user_challenges.challenge_no IS '도전과제 일련번호';
COMMENT ON COLUMN user_challenges.assigned_date IS '도전과제가 사용자에게 할당된 날짜';
COMMENT ON COLUMN user_challenges.is_selected IS '사용자가 해당 도전과제를 선택했는지 여부 (Y/ N)';
COMMENT ON COLUMN user_challenges.complete_date IS '도전과제 완료일자';

COMMENT ON COLUMN challenge_rewards.reward_no IS '사용자 리워드 일련번호';
COMMENT ON COLUMN challenge_rewards.user_no IS '사용자 일련번호';
COMMENT ON COLUMN challenge_rewards.total_keys IS '사용자가 획득한 총 열쇠 수';
COMMENT ON COLUMN challenge_rewards.remaining_points IS '열쇠로 변환되지 않은 포인트(1000 포인트 미만)';

COMMENT ON COLUMN story_steps.step_no IS '스토리 단계 일련번호';
COMMENT ON COLUMN story_steps.step_name IS '스토리 단계 이름';
COMMENT ON COLUMN story_steps.keys_required IS '스토리 해금에 필요한 열쇠 수';

COMMENT ON COLUMN user_stories.user_story_no IS '생성된 사용자 스토리 일련번호';
COMMENT ON COLUMN user_stories.step_no IS '스토리 단계 일련번호';
COMMENT ON COLUMN user_stories.user_no IS '사용자 일련번호';
COMMENT ON COLUMN user_stories.story_content IS '스토리 내용';
COMMENT ON COLUMN user_stories.unlock_date IS '스토리 해금일자';

COMMENT ON COLUMN story_cards.card_no IS '카드 일련번호';
COMMENT ON COLUMN story_cards.card_type IS '카드 종류( 인물/아이템/상황)';
COMMENT ON COLUMN story_cards.card_keyword IS '카드 상세 키워드';
COMMENT ON COLUMN story_cards.keys_required IS '카드를 얻기 위해 필요한 열쇠 수';

COMMENT ON COLUMN user_card_deck.user_card_no IS '사용자가 보유한 카드 일련번호';
COMMENT ON COLUMN user_card_deck.card_no IS '카드 일련번호';
COMMENT ON COLUMN user_card_deck.user_story_no IS '카드가 사용된 스토리 일련번호';
COMMENT ON COLUMN user_card_deck.user_no IS '사용자 일련번호';
COMMENT ON COLUMN user_card_deck.acquisition_date IS '인물 카드 획득일자';
COMMENT ON COLUMN user_card_deck.is_active IS '사용자가 획득한 인물 카드 중 보유 여부(Y : 보유중,  N : 사용완료)';


ALTER TABLE user_details ADD CONSTRAINT FK_user_details_users FOREIGN KEY (user_no) REFERENCES users (user_no);
ALTER TABLE user_accounts ADD CONSTRAINT FK_user_accounts_users FOREIGN KEY (user_no) REFERENCES users (user_no);
ALTER TABLE daily_financial_summary ADD CONSTRAINT FK_summary_users FOREIGN KEY (user_no) REFERENCES users (user_no);
ALTER TABLE user_challenges ADD CONSTRAINT FK_user_challenges_users FOREIGN KEY (user_no) REFERENCES users (user_no);
ALTER TABLE user_challenges ADD CONSTRAINT FK_user_challenges_challenges FOREIGN KEY (challenge_no) REFERENCES challenges (challenge_no);
ALTER TABLE challenge_rewards ADD CONSTRAINT FK_challenge_rewards_users FOREIGN KEY (user_no) REFERENCES users (user_no);
ALTER TABLE user_stories ADD CONSTRAINT FK_user_stories_users FOREIGN KEY (user_no) REFERENCES users (user_no);
ALTER TABLE user_stories ADD CONSTRAINT FK_user_stories_story_steps FOREIGN KEY (step_no) REFERENCES story_steps (step_no);
ALTER TABLE user_card_deck ADD CONSTRAINT FK_user_card_deck_users FOREIGN KEY (user_no) REFERENCES users (user_no);
ALTER TABLE user_card_deck ADD CONSTRAINT FK_user_card_deck_story_cards FOREIGN KEY (card_no) REFERENCES story_cards (card_no);
ALTER TABLE user_card_deck ADD CONSTRAINT FK_user_card_deck_user_stories FOREIGN KEY (user_story_no) REFERENCES user_stories (user_story_no);
