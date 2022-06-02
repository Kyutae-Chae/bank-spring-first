package bank.kakao.controller;

import bank.kakao.domain.Account;
import bank.kakao.entity.AccountEntity;
import bank.kakao.repository.AccountRepository;
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

    /* bank.html 템플릿에 전체 계좌 정보를 list로 보내줍니다. */
    @GetMapping("bank")
    public String bank(Model model) {
        List<AccountEntity> list = accountRepository.findAll();
        for (AccountEntity e : list) { //로그 출력
            System.out.println("[LOG]" + e.getAccountName() + ", " + e.getBalance());
        }
        model.addAttribute("list", list);
        return "bank";
    }

    /* API들은 결과를 JSON으로 보내줍니다. */

    //전체 계좌 리스트
    @GetMapping("accounts")
    @ResponseBody
    public List<AccountEntity> bankApi() {
        return accountRepository.findAll();
    }

    //accoundId로 balance 조회
    @GetMapping("balance")
    @ResponseBody
    public Optional<AccountEntity> readBalance(@RequestParam("accountid") Long accountid) {
        System.out.println("[LOG]"+"balance : " + accountRepository.findById(accountid).get().getBalance());
        return accountRepository.findById(accountid);
    }

    //계좌 생성하기
    @PostMapping("account")
    @ResponseBody
    public Account accountCreate(Account account) {
        System.out.println("[LOG]"+"account name : "+account.getAccountName());
        accountRepository.save(new AccountEntity(account.getAccountName(), account.getBalance(), account.getAccountType()));
        return account;
    }

    //accountId로 입금하기
    @PatchMapping("deposit/{accountId}")
    @ResponseBody
    public void deposit(@PathVariable("accountId") long accountId, @RequestParam int amount) {
        Optional<AccountEntity> account = accountRepository.findById(accountId);
        Account ac;
        if (account.isPresent()) {
            AccountEntity ae = account.get();
            ae.setBalance(ae.getBalance() + amount);
            accountRepository.save(ae);
        }
    }

    //accountId로 출금하기
    @PatchMapping("withdraw/{accountId}")
    @ResponseBody
    public void withdraw(@PathVariable("accountId") long accountId, @RequestParam int amount) {
        Optional<AccountEntity> account = accountRepository.findById(accountId);
        Account ac;
        if (account.isPresent()) {
            AccountEntity ae = account.get();
            if (ae.getBalance() >= amount) {
                ae.setBalance(ae.getBalance() - amount);
                accountRepository.save(ae);
            }
        }
    }


}
