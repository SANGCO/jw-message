package dev.sangco.jwmessage.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountDto {

    @ToString
    @EqualsAndHashCode
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class Create {

        @NotBlank(message = "아이디를 입력하세요.")
        @Size(min = 3, max = 20, message = "아이디는 최소4자 ~ 최대21자 입니다.")
        private String accountId;

        @NotBlank(message = "패스워드를 입력해주세요.")
        // password에 @Size 길이를 걸면 암호화해서 넣을 때 에러가 난다.
        private String password;

        // TODO 패스워드 동일하게 들어 왔는지 확인하는 로직
        @NotBlank(message = "패스워드 확인을 입력해주세요.")
        private String cPassword;

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

    @ToString
    @EqualsAndHashCode
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class Response {

        private String accountId;
        private String name;
        private String phoneNumb;
    }

    @ToString
    @EqualsAndHashCode
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class Update {

        @NotBlank(message = "패스워드를 입력해주세요.")
        // password에 @Size 길이를 걸면 암호화해서 넣을 때 에러가 난다.
        private String password;

        // TODO 패스워드 동일하게 들어 왔는지 확인하는 로직
        @NotBlank(message = "패스워드 확인을 입력해주세요.")
        private String cPassword;

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
