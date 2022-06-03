package bank.kakao.controller;

import bank.kakao.domain.Account;
import bank.kakao.entity.AccountEntity;
import bank.kakao.repository.AccountRepository;
import bank.kakao.service.LogService;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BankRestController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    LogService logService;

    /* API들은 결과를 JSON으로 보내줍니다. */

    //전체 계좌 리스트
    @GetMapping("accounts")
    public List<AccountEntity> bankApi() {
        return accountRepository.findAll();
    }

    //accoundId로 balance 조회
    @GetMapping("balance")
    public Optional<AccountEntity> readBalance(@RequestParam("accountid") Long accountid) {
        logService.info("balance : " + accountRepository.findById(accountid).get().getBalance());
        return accountRepository.findById(accountid);
    }

    @GetMapping("log-test")
    public String log() {
        logService.log();
        return "console log-test";
    }

}
