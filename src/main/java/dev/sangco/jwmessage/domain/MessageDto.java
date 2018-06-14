package dev.sangco.jwmessage.domain;

import lombok.Data;

import java.util.List;

@Data
public class MessageDto {

    @Data
    public static class Create {

        private String title;
        private String msg;
        private String contactNumb;
    }
}
