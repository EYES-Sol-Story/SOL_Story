package com.eyes.solstory.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class UserRes {
    //신한은행 사용자 로그인 api 사용시 반환받을 값
    public String userId;
    public String username;
    public String institutionCode;
    public String userKey;
    public String created;
    public String modified;

    @Override
    public String toString() {
        return "UserApiResponse{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", institutionCode='" + institutionCode + '\'' +
                ", userKey='" + userKey + '\'' +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                '}';
    }
}