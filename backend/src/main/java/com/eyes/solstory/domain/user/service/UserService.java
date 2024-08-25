package com.eyes.solstory.domain.user.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eyes.solstory.constants.OpenApiUrls;
import com.eyes.solstory.domain.challenge.entity.ChallengeReward;
import com.eyes.solstory.domain.challenge.repository.ChallengeRewardRepository;
import com.eyes.solstory.domain.financial.entity.UserAccount;
import com.eyes.solstory.domain.financial.repository.UserAccountRepository;
import com.eyes.solstory.domain.financial.service.DemandDepositCollector;
import com.eyes.solstory.domain.user.dto.LoginRes;
import com.eyes.solstory.domain.user.dto.LoginUser;
import com.eyes.solstory.domain.user.dto.UserDto;
import com.eyes.solstory.domain.user.dto.UserRes;
import com.eyes.solstory.domain.user.entity.User;
import com.eyes.solstory.domain.user.repository.UserRepository;
import com.eyes.solstory.global.bank.WebClientUtil;
import com.eyes.solstory.global.bank.dto.SavingsAccountReq;
import com.eyes.solstory.global.bank.dto.SavingsAccountRes;
import com.eyes.solstory.util.OpenApiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${api.key}")
    private String apiKey;

    private final WebClientUtil webClientUtil;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final ChallengeRewardRepository challengeRewardRepository;

    //사용자 계정 - 계 생성
    public ResponseEntity<UserRes> createUserAccount(String userId, String email) {
        ResponseEntity<UserRes> response = webClientUtil.creatUserAccount(email)
                .onErrorMap(e -> new RuntimeException("사용자 계정 생성 중 오류 발생", e))
                .block();

        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        UserRes userRes = response.getBody();
        if (userRes != null && userRes.getUserKey() != null) {
            user.updateUserKey(userRes.getUserKey());
            userRepository.save(user);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

        return ResponseEntity.ok(response.getBody());
    }

    // 1원 송금
    public ResponseEntity<String> transferOneWon(
            String accountNo, String email) throws URISyntaxException {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(email);
        User user = userRepository.findUserByEmail(email);
        System.out.println(user.getUserId());

        System.out.println("1111");
        
        String transmissionDate = LocalDate.now().format(OpenApiUtil.DATE_FORMATTER);
        String transmissionTime = LocalDateTime.now().format(OpenApiUtil.TIME_FORMATTER);
        
        /*
        TransferOneWonReq.Header header = TransferOneWonReq.Header.builder()
                .apiName("openAccountAuth")
                .transmissionDate(transmissionDate)
                .transmissionTime(transmissionTime)
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("openAccountAuth")
                .institutionTransactionUniqueNo("20240723152345666098")
                .apiKey(apiKey)
                .userKey(user.getUserKey())
                .build();
*/
        Map<String, String> header = OpenApiUtil.createHeaders("04e988f2-d086-495a-aa2f-67b0e911782f", OpenApiUrls.OPEN_ACCOUNT_AUTH);
        System.out.println("2222");
        
        Map<String, Object> request = new HashMap<>();
        request.put("Header", header);
        request.put("accountNo", accountNo);
        request.put("authText", "SSAFY");
        
        //TransferOneWonReq request = TransferOneWonReq.builder()
        //        .header(header)
        //        .accountNo(accountNo)
        //        .authText("SSAFY")
        //        .build();
        System.out.println("333333");
        
        ResponseEntity<String> response = OpenApiUtil.callApi(new URI(OpenApiUrls.ACCOUNT_AUTH_URL + OpenApiUrls.OPEN_ACCOUNT_AUTH), request);
        ObjectMapper objectMapper = new ObjectMapper();
        
        //System.out.println(objectMapper.toString());
        //ResponseEntity<TransferOneWonRes> response = webClientUtil.transferOneWon(request)
        //        .onErrorMap(e -> new RuntimeException("1원 송금 요청 중 오류 발생", e))
        //        .block();
        System.out.println("44444");
        //if (response == null || response.getStatusCode() != HttpStatus.OK) {
         //   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          //          .body(null);
        //}
        
        String transactionUniqueNo = "";
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            transactionUniqueNo = rootNode.path("REC").path("transactionUniqueNo").asText();
            System.out.println("transactionUniqueNo :"+transactionUniqueNo);
        } catch (Exception e) {
            throw new RuntimeException("err", e);
        }
        
        header = OpenApiUtil.createHeaders("04e988f2-d086-495a-aa2f-67b0e911782f", OpenApiUrls.INQUIRE_TRANSACTION_HISTORY);
        System.out.println("2222");
        
        request = new HashMap<>();
        request.put("Header", header);
        request.put("accountNo", accountNo);
        request.put("transactionUniqueNo", transactionUniqueNo);
        
        response = OpenApiUtil.callApi(new URI(OpenApiUrls.DEMAND_DEPOSIT_URL + OpenApiUrls.INQUIRE_TRANSACTION_HISTORY), request);
        
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            String summary = rootNode.path("REC").path("transactionSummary").asText();
            int code = Integer.parseInt(summary.substring(6));
            System.out.println("summary :"+code);
        } catch (Exception e) {
            throw new RuntimeException("err", e);
        }
        
        
        return ResponseEntity.ok(response.getBody());
    }

    @Autowired
    DemandDepositCollector demandDepositCollector;
    // 1원 검증
    public ResponseEntity<String> verifyOneWon(String accountNo, String authCode, String email) throws URISyntaxException {
        User user = userRepository.findUserByEmail(email);
        Map<String, String> header = OpenApiUtil.createHeaders(user.getUserKey(), OpenApiUrls.CHECK_ACCOUNT_AUTH);
        
        Map<String, Object> request = new HashMap<>();
        request.put("Header", header);
        request.put("accountNo", accountNo);
        request.put("authText", "SSAFY");
        request.put("authCode", authCode);
        
        ResponseEntity<String> response = OpenApiUtil.callApi(new URI(OpenApiUrls.ACCOUNT_AUTH_URL + OpenApiUrls.CHECK_ACCOUNT_AUTH), request);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            String status = rootNode.path("REC").path("status").asText(); //SUCCESS
        } catch (Exception e) {
            throw new RuntimeException("err", e);
        }
        return ResponseEntity.ok(response.getBody());
    }

    // 적금 계좌 생성
    public ResponseEntity<SavingsAccountRes> createSavingAccount(String accountTypeUniqueNo, String withdrawalAccountNo, 
    		long depositBalance, String userId, int targetAmount) {
        User user = userRepository.findUserByUserId(userId);
        String transmissionDate = LocalDate.now().format(OpenApiUtil.DATE_FORMATTER);
        String transmissionTime = LocalDateTime.now().format(OpenApiUtil.TIME_FORMATTER);
        
        SavingsAccountReq.Header header = SavingsAccountReq.Header.builder()
                .apiName("createAccount")
                .transmissionDate(transmissionDate)
                .transmissionTime(transmissionTime)
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("createAccount")
                .institutionTransactionUniqueNo("20240101121212123456")
                .apiKey(apiKey)
                .userKey(user.getUserKey())
                .build();
        SavingsAccountReq request = SavingsAccountReq.builder()
                .header(header)
                .accountTypeUniqueNo(accountTypeUniqueNo)
                .withdrawalAccountNo(withdrawalAccountNo)
                .depositBalance(depositBalance)
                .build();

        ResponseEntity<SavingsAccountRes> response = webClientUtil.createSavingAccount(request)
                .onErrorMap(e -> new RuntimeException("적금 계좌 생성 중 오류 발생", e))
                .block();

        if (response == null || response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

        SavingsAccountRes.Rec rec = response.getBody().getRec();
        UserAccount userAccount = UserAccount.builder()
                .accountNo(rec.getAccountNo())
                .user(user)
                .accountType(1)
                .accountName(rec.getAccountName())
                .targetAmount(targetAmount)
                .regDate(LocalDate.now())
                .endDate(LocalDate.parse(rec.getAccountExpiryDate()))
                .isActive(1)
                .build();

        userAccountRepository.save(userAccount);
        ChallengeReward challengeReward = ChallengeReward.builder().userNo(user.getUserNo()).keys(0).build();
        challengeRewardRepository.save(challengeReward);

        return ResponseEntity.ok(response.getBody());
    }

    public ResponseEntity<String> searchUserkey(String email) {
        return ResponseEntity.ok(userRepository.findUserByEmail(email).getUserKey());
    }
    
    
    
    
    /////////////gabin
    public void saveUser(UserDto userDto) {
    	// UserEntity로 변환 후 저장
    	User user = User.builder()
    			.userId(userDto.getId())
    			.password(userDto.getPassword())
    	        .userName(userDto.getName())
    	        .email(userDto.getEmail())
    	        .gender(userDto.getGender())
    	        .birth(userDto.transDateFormatyyyyMMdd(userDto.getBirthdate())) //변환필요
    	        .joinDate(LocalDate.now())
    			.build();
        // 나머지 매핑 작업
        userRepository.save(user);
    }
    
    public String findUserIdByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return user != null ? user.getUserId() : null;
    }
    
    public LoginRes authenticate(String username, String password) {
    	System.out.println(username + ", " + password);
        
    	LoginUser user = userRepository.login(username, password);
        LoginRes result = new LoginRes();

        if(user == null) {
        	result.setLoginResult(false);
        }else {
        	result.setLoginResult(true);
        	result.setLoginUser(user);
        }
        
        return result;
	}
}