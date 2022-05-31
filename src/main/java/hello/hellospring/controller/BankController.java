package hello.hellospring.controller;

import hello.hellospring.entity.Account;
import hello.hellospring.entity.AccountEntity;
import hello.hellospring.repository.AccountRepository;
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

    @GetMapping("bank")
    public String bank(Model model) {
        List<AccountEntity> list = accountRepository.findAll();
        for (AccountEntity e : list) {
            System.out.println("[LOG]"+/*e.getAccountId() + ", " +*/ e.getAccountName() + ", " + e.getBalance());
        }
        model.addAttribute("list", list);
//        model.addAttribute("data", "hello world!!!");
        return "bank";
    }

    @GetMapping("accounts")
    @ResponseBody
    public List<AccountEntity> bankApi() {
        return accountRepository.findAll();
    }

    @GetMapping("balance")
    @ResponseBody
    public Optional<AccountEntity> readBalance(@RequestParam("accountid") Long accountid) {
        System.out.println("[LOG]"+"balance : " + accountRepository.findById(accountid).get().getBalance());
        return accountRepository.findById(accountid);
    }

    @PostMapping("account")
    @ResponseBody
    public Account accountCreate(Account account) {
        System.out.println("[LOG]"+"account name : "+account.getAccountName());
        accountRepository.save(new AccountEntity(/*account.getAccountId(),*/ account.getAccountName(), account.getBalance(), account.getAccountType()));
        return account;
    }

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
