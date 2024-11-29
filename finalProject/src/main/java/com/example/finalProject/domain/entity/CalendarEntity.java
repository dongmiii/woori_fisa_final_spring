package com.example.finalProject.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Entity
@Table(name = "f_20_241115") // 테이블 이름 매핑
@Data
public class CalendarEntity {

    @Id
    @Column(name = "transaction_id", length = 50) // varchar(50)
    private String transactionId;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime datetime;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "category_name", length = 255, nullable = false) // varchar(255)
    private String categoryName;

    @Column(name = "merchant_name", length = 255) // varchar(255)
    private String merchantName;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2) // decimal(15,2)
    private DecimalFormat amount;

    @Column(name = "payment_method", length = 50) // varchar(50)
    private String paymentMethod;

    @Column(name = "transaction_type", length = 50) // varchar(50)
    private String transactionType;
}