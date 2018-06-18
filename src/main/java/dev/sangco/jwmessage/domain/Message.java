package dev.sangco.jwmessage.domain;

import dev.sangco.jwmessage.support.http.HtmlFormDataBuilder;
import lombok.*;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Message {

    private final String encodingType = "utf-8";
    private final String boundary = "____boundary____";
    private final String sms_url = "https://apis.aligo.in/send/";

    private String userid;
    private String key;
    private String sender;
    private String receiver;
    private String msg;
    private String title;
    private String destination;
    private String rdate;
    private String rtime;
    private String image;
    private String testmode_yn;

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
