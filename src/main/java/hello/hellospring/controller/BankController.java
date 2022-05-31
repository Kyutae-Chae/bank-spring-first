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
            System.out.println(/*e.getAccountId() + ", " +*/ e.getAccountName() + ", " + e.getBalance());
        }
        model.addAttribute("list", list);
//        model.addAttribute("data", "hello world!!!");
        return "bank";
    }

    @GetMapping("bank-api")
    @ResponseBody
    public List<AccountEntity> bankApi() {
        return accountRepository.findAll();
    }

    @GetMapping("balance")
    @ResponseBody
    public Optional<AccountEntity> readBalance(@RequestParam("accountid") Long accountid) {
        System.out.println("\n\n\n\nbalance : " + accountRepository.findById(accountid).get().getBalance());
        return accountRepository.findById(accountid);
    }

    @PostMapping("account")
    @ResponseBody
    public Account accountCreate(Account account) {
        System.out.println("account name : "+account.getAccountName());
        accountRepository.save(new AccountEntity(/*account.getAccountId(),*/ account.getAccountName(), account.getBalance(), account.getAccountType()));
        return account;
    }


}
