package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.DTO.EndedInvDTOfromDB;
import com.example.seoultechInvestment.DTO.EnrollEarningRateDTO;
import com.example.seoultechInvestment.DTO.OnGoingInvDTO;
import com.example.seoultechInvestment.entity.Stock;
import com.example.seoultechInvestment.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
    private final StockRepository stockRepository;

}
