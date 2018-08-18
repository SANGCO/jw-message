package dev.sangco.jwmessage.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class AccountDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create {

        @NotBlank(message = "아이디를 입력하세요.")
        @Size(min = 3, max = 20, message = "아이디는 최소4자 ~ 최대21자 입니다.")
        private String accId;

        @NotBlank(message = "패스워드를 입력해주세요.")
        private String password;

        @NotBlank(message = "패스워드 확인을 입력해주세요.")
        private String cpassword;

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @NotBlank(message = "폰번호를 입력해주세요.")
        @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리 숫자만 입력가능합니다.")
        private String phoneNumb;

        @NotBlank(message = "알리고 ID를 입력해주세요.")
        private String aligoId;

        @NotBlank(message = "알리고 키를 입력해주세요.")
        private String aligoKey;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private String accId;
        private String name;
        private String phoneNumb;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {

        @NotBlank(message = "패스워드를 입력해주세요.")
        private String password;

        @NotBlank(message = "패스워드 확인을 입력해주세요.")
        private String cpassword;

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @NotBlank(message = "폰번호를 입력해주세요.")
        @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리 숫자만 입력가능합니다.")
        private String phoneNumb;

        @NotBlank(message = "알리고 ID를 입력해주세요.")
        private String aligoId;

        @NotBlank(message = "알리고 키를 입력해주세요.")
        private String aligoKey;
    }
}
