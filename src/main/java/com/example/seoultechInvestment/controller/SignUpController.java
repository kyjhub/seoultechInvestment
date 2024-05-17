package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.AthDTO;
import com.example.seoultechInvestment.DTO.MemberDTO;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.service.MailService;
import com.example.seoultechInvestment.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SignUpController {
    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/signUp")//회원가입
    public String join(Model model, @Valid MemberDTO memberDTO,
                       BindingResult bindingResult) {//errors -> bindingResult로 교체
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getFieldErrors()) {
                log.error(error.toString());
            }
            return "redirect:/login";
        }
        //멤버 등록
        if (memberDTO.getStGalleryNickname() != null) {
            Member newMember = Member.builder().stId(memberDTO.getStId()).name(memberDTO.getName()).
                    Department(memberDTO.getDepartment()).stGalleryNickname(memberDTO.getStGalleryNickname()).build();
            memberService.enroll(newMember);
        } else {
            Member newMember = Member.builder().stId(memberDTO.getStId()).name(memberDTO.getName()).
                    Department(memberDTO.getDepartment()).build();
            memberService.enroll(newMember);
        }

        return "";
    }

//    @PostMapping("/email")//이메일 입력하고 인증버튼 누르면 여기에서 인증번호 보내주면 팝업창에서 인증
//    public void getEmail(@RequestBody @Valid EmailDTO emailDTO, HttpServletResponse response) {
//        System.out.println("보낼 이메일 : " + emailDTO.getEmail());
//        String emailUUid = UUID.randomUUID().toString();
//        //redis에 email과 eamilId같이 저장 코드 작성
//
//        //클라이언트가 인증번호 입력하면 서버측에서 클라 이메일 쿠키에서 uuid랑 인증번호 대조
//        //비교해서 인증작업 완료
//
//        //쿠키 생성 후 저장
//        Cookie emailCookie = new Cookie("EMAIL_COOKIE_NAME", emailUUid);
//        response.addCookie(emailCookie);
//        //joinEmail 메소드 안에 이메일 전송 코드 있음.
//        mailService.joinEmail(emailDTO.getEmail(), emailUUid);
//        //여기서 인증번호를 위해 model.addAttribute해야하나?
//    }

    @PostMapping(value="/email", consumes =  "application/x-www-form-urlencoded")
    @ResponseBody
    public String getEmail(@Valid AthDTO athDTO) {
        String emailAcc = athDTO.getEmail();
        return mailService.joinEmail(emailAcc);
    }

    @GetMapping("/athEmail")
    public String redirectToAthEmail(@Valid AthDTO athDTO, Model model) {
        model.addAttribute("athForm", new AthDTO(athDTO.getEmail()));
        return "/athEmail";
    }
//    @PostMapping("/athEmail")   // 인증번호 오면 검증하는 단계
//    public String authenticationEmail(@RequestBody @Valid String athNumber, HttpServletRequest request) {
//        Cookie emailCookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("EMAIL_COOKIE_NAME")).findAny().orElse(null);
//        String emailUUid = emailCookie.getValue(); //getvalue가 nullPointerException 오류 날 수 있음
//        boolean checkAthNum = mailService.checkAthNum(emailUUid, athNumber);
//        if (checkAthNum) {
//            return "forward:/signUp.html";  //인증완료, forward해서 회원가입에서 입력중이던 회원정보들 유지
//        } else {
//            throw new NullPointerException("이메일 인증에서 에러 발생!");
//        }
//    }

//    @PostMapping("/athEmail")   // 인증번호 오면 검증하는 단계
//    public String authenticationEmail(@RequestBody @Valid String athNumber, HttpServletRequest request) {
//        Cookie emailCookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("EMAIL_COOKIE_NAME")).findAny().orElse(null);
//        String emailUUid = emailCookie.getValue(); //getvalue가 nullPointerException 오류 날 수 있음
//        boolean checkAthNum = mailService.checkAthNum(emailUUid, athNumber);
//        if (checkAthNum) {
//            return "forward:/signUp.html";  //인증완료, forward해서 회원가입에서 입력중이던 회원정보들 유지
//        } else {
//            throw new NullPointerException("이메일 인증에서 에러 발생!");
//        }
//    }

    @GetMapping("/athPopUp")
    public String getAthEmail(Model model) {
        log.debug("athPopUp.html is getMapped correctly");
        model.addAttribute("athForm", new AthDTO());
        return "athEmail";
    }
}
