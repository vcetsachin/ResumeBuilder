package com.exam.resumeBuilder.Repository;

import com.exam.resumeBuilder.Document.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface PaymentRepository extends MongoRepository<Payment, String> {


    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);

    Optional<Payment> findByRazorpayPaymentId(String razorpayPaymentId);

    List<Payment> findByUserIdOrderByCreateAtDesc(String userId);
    List<Payment> findByStatus(String status);
}
