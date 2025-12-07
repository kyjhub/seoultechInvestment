package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.*;
import com.example.seoultechInvestment.config.TelegramConfig;
import com.example.seoultechInvestment.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class InvestController {

    private final InvestmentService investmentService;
    private final TelegramConfig telegramConfig;

    @GetMapping("/admin/items/add")
    public String enrollStock(Model model) {
        model.addAttribute("newStock", new EnrollDTO());
        return "enrollStock";
    }

    /** 신규 추천 종목 등록 **/
    @PostMapping("/admin/stock/enroll")
    public String enrollStock(@Valid EnrollDTO enrollDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getFieldErrors()) {
                log.error(error.toString());
            }
            log.info("enrollStock.html 로 연결");
            return "/enrollStock";
        }

        EnrollDTO enrollDTO1 = enrollDTO.toBuilder().enrollDate(LocalDate.now()).build();
        investmentService.enroll(enrollDTO1);

        /* 종목등록 알람을 텔레그램으로 전송 */
        try {
            JSONObject jsonTelegramMessage = new JSONObject();
            jsonTelegramMessage.put("chat_id", telegramConfig.getChatId());

            JSONObject enrollDTOJson = new JSONObject();

            enrollDTOJson.put("최소 목표가", enrollDTO.getTp());
            enrollDTOJson.put("진입가", enrollDTO.getEntryPrice());
            enrollDTOJson.put("주식명", enrollDTO.getTickerName());

            jsonTelegramMessage.put("text", enrollDTOJson);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");

            String url = "https://api.telegram.org/bot"
                    + telegramConfig.getToken()
                    + "/sendMessage";

            log.info("url : " + url);

            HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonTelegramMessage, httpHeaders);
            restTemplate.postForEntity(url, httpEntity, String.class);

            log.info("send Telegram message");
            log.info("jsonTelegramMessage = " + jsonTelegramMessage.toString());

        } catch (Exception e) {
            log.error("텔레그램 코드 에러 발생");
            log.error(e.getMessage());
        }

        return "home";
    }

    /** 진행중인 투자 종목 조회 **/
    @GetMapping("/stock/onGoing")
    @ResponseBody
    public List<OnGoingInvDTO> presentStock() {
        log.info("/stock/enroll is getMapped");
        List<OnGoingInvDTO> onGoingInvestments = investmentService.findOnGoingInvestments();
        return onGoingInvestments;
    }

    @GetMapping("/stock/result")
    @ResponseBody
    public List<EndedInvDTOfromDB> resultList() {
        log.info("추천종목 성과 기록 요청");
        return investmentService.findEndedInvestments();
    }


    @GetMapping("/admin/returns/add")
    public String getEnrollResultForm(Model model) {
        model.addAttribute("endedInv", new EndedInvDTOfromFront());
        return "enrollResult";
    }

    @PostMapping("/admin/stock/result/enroll")
    public String EnrollResult(@ModelAttribute EndedInvDTOfromFront endedInvDTOfromFront) {
        investmentService.modify(endedInvDTOfromFront);
        return "performanceList";
    }
}
