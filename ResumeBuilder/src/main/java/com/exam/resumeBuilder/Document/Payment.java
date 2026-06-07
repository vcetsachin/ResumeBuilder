package com.exam.resumeBuilder.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "payments")
public class Payment {
    @Id
    private String id;

    private String userId;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;

    private Integer amount;
    private String currency;
    private String planType;

    @Builder.Default
    private String status = "created";  //created, fail, paid
    private String receipt;

    @CreatedDate
    private LocalDateTime createAt;
    @LastModifiedDate
    private LocalDateTime updateAt;
}
