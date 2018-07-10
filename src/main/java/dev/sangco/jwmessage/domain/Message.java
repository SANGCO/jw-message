package dev.sangco.jwmessage.domain;

import dev.sangco.jwmessage.support.http.HtmlFormDataBuilder;
import lombok.*;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class Message {

    private final String encodingType = "utf-8";
    private final String boundary = "____boundary____";
    private final String sms_url = "https://apis.aligo.in/send/";
    private String userid;
    private String key;
    private String sender;

    @NotBlank(message = "문자를 보낼 번호를 입력하세요.")
    private String receiver;

    @NotBlank(message = "문자 제목을 입력하세요.")
    private String title;

    @NotBlank(message = "메세지를 입력하세요.")
    private String msg;

    @NotBlank(message = "Send Mode를 입력하세요.")
    private String testmode_yn;

    private String destination;
    private String rdate;
    private String rtime;
    private String image;

    @Builder
    public Message(String userid, String key, String sender, String receiver, String msg,
                   String title, String destination, String rdate, String rtime, String image, String testmode_yn) {
        this.key = key;
        this.userid = userid;
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
        this.title = title;
        this.destination = destination;
        this.rdate = rdate;
        this.rtime = rtime;
        this.image = image;
        this.testmode_yn = testmode_yn;
    }

    public HttpEntity<MultiValueMap<String, Object>> ofEntity() {
        return HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("key", key)
                .addParameter("userid", userid)
                .addParameter("sender", sender)
                .addParameter("receiver", receiver)
                .addParameter("title", title)
                .addParameter("msg", msg)
                .addParameter("destination", destination)
                .addParameter("rdate", rdate)
                .addParameter("rtime", rtime)
                .addParameter("image", image)
                .addParameter("testmode_yn", testmode_yn)
                .build();
    }
}
