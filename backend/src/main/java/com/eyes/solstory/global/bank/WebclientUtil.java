package com.eyes.solstory.global.bank;

import com.eyes.solstory.global.bank.dto.Header;
import com.eyes.solstory.global.bank.dto.SavingsAccountReq;
import com.eyes.solstory.global.bank.dto.SavingsAccountRes;
import com.eyes.solstory.global.bank.dto.SearchSavingsAccountReq;
import com.eyes.solstory.global.bank.dto.SearchSavingsAccountRes;
import com.eyes.solstory.global.bank.dto.SearchSavingsProductRes;
import com.eyes.solstory.global.bank.dto.UpdateSavingsAccountReq;
import com.eyes.solstory.global.bank.dto.UpdateSavingsAccountRes;
import com.eyes.solstory.global.bank.dto.UserRes;
import com.eyes.solstory.global.bank.dto.UserReq;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//@Component
public class WebClientUtil {
    @Value("${api.key}")
    private String apiKey;
    private final WebClient webClient;

    public WebClientUtil(WebClient webClient) {
        this.webClient = webClient;
    }

    //사용자 계정 생성
    public Mono<ResponseEntity<UserRes>> creatUserAccount(String userId) {
        String url = "/member";
        UserReq request = new UserReq(apiKey, userId);

        return webClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .toEntity(UserRes.class);
    }

    //사용자 계정 조회
    public Mono<ResponseEntity<UserRes>> findUserAccount(String userId) {
        String url = "/member/search";
        UserReq request = new UserReq(apiKey, userId);
        return webClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .toEntity(UserRes.class);
    }

    //수시입출금 - 계좌 입금
    public Mono<ResponseEntity<UpdateSavingsAccountRes>> updateSavingAccountDeposit(UpdateSavingsAccountReq request) {
        String url = "/edu/demandDeposit/updateDemandDepositAccountDeposit";
        return webClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .toEntity(UpdateSavingsAccountRes.class);
    }

    //적금 상품 조회
    public Mono<ResponseEntity<SearchSavingsProductRes>> searchSavingsProduct(Header header) {
        String url = "/edu/savings/inquireSavingsProducts";
        return webClient.post()
                .uri(url)
                .bodyValue(header)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .toEntity(SearchSavingsProductRes.class);
    }

    //적금 계좌 생성
    public Mono<ResponseEntity<SavingsAccountRes>> createSavingAccount(SavingsAccountReq request) {
        String url = "/edu/savings/createAccount";

        return webClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .toEntity(SavingsAccountRes.class);
    }

    //적금 계좌 조회 - 단건
    public Mono<ResponseEntity<SearchSavingsAccountRes>> searchSavingAccount(SearchSavingsAccountReq request) {
        String url = "/edu/savings/inquireAccount";

        return webClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .toEntity(SearchSavingsAccountRes.class);
    }
}