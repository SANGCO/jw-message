package dev.sangco.jwmessage.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class MessageDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create {

        private String title;
        private String msg;
        private String contactNumb;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private String result_code;
        private String message;
        private String msg_id;
        private String error_cnt;
        private String success_cnt;
        private String msg_type;
    }
}
