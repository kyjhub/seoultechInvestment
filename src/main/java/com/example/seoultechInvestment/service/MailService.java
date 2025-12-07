package com.example.seoultechInvestment.service;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage.RecipientType;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@Slf4j
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
//    @Autowired
//    private RedisUtil redisUtil;

    private int authNumber;

    //임의의 6자리 양수를 반환합니다.
    public void makeRandomNumber() {
        Random r = new Random();
        String randomNumber = "";
        for (int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }

        authNumber = Integer.parseInt(randomNumber);
    }

    //mail을 어디서 보내는지, 어디로 보내는지 , 인증 번호를 html 형식으로 어떻게 보내는지 작성합니다.
    public String joinEmail(String emailAccount) {
        makeRandomNumber();
        String setFrom = "harryjun43@naver.com"; // email-config에 설정한 자신의 이메일 주소를 입력
        String toMail = emailAccount;
        String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
        String content =
                "seoultechInvestment를 방문해주셔서 감사합니다." +    //html 형식으로 작성 !
                        "<br><br>" +
                        "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "인증번호를 제대로 입력해주세요"; //이메일 내용 삽입
        mailSend(setFrom, toMail, title, content);
        return Integer.toString(authNumber);
    }

    //이메일을 전송합니다.
    public void mailSend(String setFrom, String mailAcc, String title, String content) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.addRecipients(RecipientType.TO, mailAcc); //보내는 대상
            message.setSubject(title);
            message.setText(content, "utf-8");
            message.setFrom(new InternetAddress("harryjun43@naver.com", "관리자"));
            mailSender.send(message);

        } catch (MessagingException e) {//이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            log.error("mailSender.createMimeMessage()에서 에러 발생");
            e.printStackTrace();//e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드
        } catch (UnsupportedEncodingException e) {
            log.error("new InternetAddress()에서 에러발생");
            e.printStackTrace();
        }
//        redisUtil.setDataExpire(mailAcc, String.valueOf(athNumber), 90L);
    }
}
