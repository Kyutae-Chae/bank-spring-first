package bank.kakao.entity;

import lombok.*;

import javax.persistence.*;

@Getter // getter 메소드 생성
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
@NoArgsConstructor
@Table(name="account") // 테이블 명을 작성
@Entity
public class AccountEntity {
    @Id
    @Column(name="accountid", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    @Column(name="accountname", nullable = false, unique = true, length = 45)
    private String accountName;

    @Column(nullable = false)
    private int balance;

    @Column(name="accounttype", nullable = true)
    private int accountType;

    @Builder
    public AccountEntity() {

    }
    public AccountEntity(/*long accountId,*/ String accountName, int balance, int accountType) {
//        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
        this.accountType = accountType;
    }

//    public long getAccountId() {return this.accountId;}
    public int getBalance() {return this.balance;}

    public int getAccountType() {return this.accountType;}

    public String getAccountName() {return this.accountName;}
}