package bank.kakao.controller;

import bank.kakao.domain.Account;
import bank.kakao.entity.AccountEntity;
import bank.kakao.repository.AccountRepository;
import bank.kakao.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BankController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    LogService logService;

    /* bank.html 템플릿에 전체 계좌 정보를 list로 보내줍니다. */
    @GetMapping("bank")
    public String bank(Model model) {
        List<AccountEntity> list = accountRepository.findAll();
        for (AccountEntity e : list) { //로그 출력
            logService.info(e.getAccountName() + ", " + e.getBalance());
        }
        model.addAttribute("list", list);
        return "bank";
    }

    /* balance.html 에 특정 계좌의 잔액을 보여줍니다. */
    @GetMapping("balance/{accountId}")
    public String balance(Model model, @PathVariable("accountId") long accountId) {
        model.addAttribute("item", accountRepository.findById(accountId).get());
        return "balance";
    }

    //계좌 생성하기
    @PostMapping("account")
    public String accountCreate(Account account) {
        logService.info("account name : "+account.getAccountName());
        accountRepository.save(new AccountEntity(account.getAccountId(), account.getAccountName(), account.getBalance(), account.getAccountType()));
        return "redirect:/bank";
    }

    //accountId로 입금하기
    @PatchMapping("deposit/{accountId}")
    public String deposit(@PathVariable("accountId") long accountId, @RequestParam int amount) {
        Optional<AccountEntity> account = accountRepository.findById(accountId);
        Account ac;
        if (account.isPresent()) {
            AccountEntity ae = account.get();
            if (ae.getAccountType() == 2) return "redirect:/balance/" + accountId;
            ae.setBalance(ae.getBalance() + amount);
            accountRepository.save(ae);
        }
        return "redirect:/balance/" + accountId;
    }

    //accountId로 출금하기
    @PatchMapping("withdraw/{accountId}")
    public String withdraw(@PathVariable("accountId") long accountId, @RequestParam int amount, Model model) {
        Optional<AccountEntity> account = accountRepository.findById(accountId);
        Account ac;
        if (account.isPresent()) {
            AccountEntity ae = account.get();
            if (ae.getAccountType() == 2) return "redirect:/balance/" + accountId;;
            if (ae.getBalance() >= amount) {
                ae.setBalance(ae.getBalance() - amount);
                accountRepository.save(ae);
            } else {
                model.addAttribute("error", "출금 가능 금액 초과");
                model.addAttribute("accountId", accountId);
                model.addAttribute("availableAmount", ae.getBalance());
                return "error";
            }
        }
        return "redirect:/balance/" + accountId;
    }


}
