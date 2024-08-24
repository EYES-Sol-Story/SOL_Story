package com.eyes.solstory.global.bank.controller;

import com.eyes.solstory.global.bank.WebClientUtil;
import com.eyes.solstory.global.bank.dto.Header;
import com.eyes.solstory.global.bank.dto.SearchSavingsProductRes;
import com.eyes.solstory.domain.user.dto.UserRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class ApiTestController {
    private final WebClientUtil webClientUtil;

    @GetMapping("/test")
    public ResponseEntity<String> findUserAccount() {
        try {
            ResponseEntity<UserRes> response= webClientUtil.findUserAccount(
                    "chaehee13@naver.com").block();
            System.out.println(response.getStatusCode());
            System.out.println("status="+response.getStatusCode());
            System.out.println(response.getBody());

            return ResponseEntity.ok("테스트 요청이 성공적으로 처리되었습니다. 결과는 콘솔에서 확인할 수 있습니다.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("테스트 요청 중 에러 발생"+e.getMessage());
        }
    }

    @GetMapping("/test2")
    public ResponseEntity<String> findsaving() {
        try {

            Header header = new Header("inquireSavingsProducts", "20240821", "224012", "00100", "001",
                    "inquireSavingsProducts", "20240101121212123456", "cb6cca464d504a29a809ced072ba5aec");
            ResponseEntity<SearchSavingsProductRes> response= webClientUtil.searchSavingsProduct(header).block();
            System.out.println(response.getStatusCode());
            System.out.println("status="+response.getStatusCode());
            System.out.println(response.getBody());

            return ResponseEntity.ok("테스트 요청이 성공적으로 처리되었습니다. 결과는 콘솔에서 확인할 수 있습니다.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("테스트 요청 중 에러 발생"+e.getMessage());
        }
    }


//    @GetMapping("/savings")
//    public ResponseEntity<String> updateSavingAccountDeposit() {
//        try {
//            Header header = new Header();
//            UpdateSavingsAccountReq request = new UpdateSavingsAccountReq();
//            ResponseEntity<UpdateSavingsAccountRes> response = webClientUtil
//                    .updateSavingAccountDeposit(request).block();
//            return ResponseEntity.ok("테스트 요청이 성공적으로 처리되었습니다. 결과는 콘솔에서 확인할 수 있습니다.");
//        } catch (Exception e) {
//            System.out.println(e);
//            return ResponseEntity.status(500).body("테스트 요청 중 에러 발생"+e.getMessage());
//        }
//    }
}
