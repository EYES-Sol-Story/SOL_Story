package com.eyes.solstory.global.bank;


import com.eyes.solstory.global.bank.dto.UserRes;
import com.eyes.solstory.global.bank.dto.UserReq;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebClientUtil {
    @Value("${api.key}")
    private String apiKey;
    private final WebClient webClient;

    public WebClientUtil(WebClient webClient) {
        this.webClient = webClient;
    }

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
}

