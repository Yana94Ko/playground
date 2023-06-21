package yana.playground.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public class MemberDTO {

    @Getter
    @Setter
    @Builder
    public static class Post {

        @NotNull
        @Email
        private String email;

        private String nickname;

        @NotBlank
        @Size(min = 8)
        private String password;

        //임베더블 확인 하기
        private String postcode;

        private String address;

        private String detailAddress;

        private String realName;

        private String phone;
    }
}
