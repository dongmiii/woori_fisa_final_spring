package com.example.finalProject.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
public class PaymentEntity {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @Column(name = "datetime")
    private LocalDateTime datetime;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_name", length = 100)
    private String categoryName;

    @Column(name = "store_name", length = 100)
    private String storeName;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "card_name", length = 100)
    private String cardName;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(name = "card_type")
    private CardType cardType;

    @Column(name = "transaction_type", length = 100)
    private String transactionType;

    @Column(name = "member_id")
    private Integer memberId;

    // Enum 정의
    public enum CardType {
        체크카드,
        신용카드
    }

    // Getters and Setters
//    public Integer getTransactionId() {
//        return transactionId;
//    }
//
//    public void setTransactionId(Integer transactionId) {
//        this.transactionId = transactionId;
//    }
//
//    public LocalDateTime getDatetime() {
//        return datetime;
//    }
//
//    public void setDatetime(LocalDateTime datetime) {
//        this.datetime = datetime;
//    }
//
//    public Integer getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Integer categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }
//
//    public String getStoreName() {
//        return storeName;
//    }
//
//    public void setStoreName(String storeName) {
//        this.storeName = storeName;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    public String getCardName() {
//        return cardName;
//    }
//
//    public void setCardName(String cardName) {
//        this.cardName = cardName;
//    }
//
//    public CardType getCardType() {
//        return cardType;
//    }
//
//    public void setCardType(CardType cardType) {
//        this.cardType = cardType;
//    }
//
//    public String getTransactionType() {
//        return transactionType;
//    }
//
//    public void setTransactionType(String transactionType) {
//        this.transactionType = transactionType;
//    }
//
//    public Integer getMemberId() {
//        return memberId;
//    }
//
//    public void setMemberId(Integer memberId) {
//        this.memberId = memberId;
//    }
}