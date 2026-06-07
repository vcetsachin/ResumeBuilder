package com.exam.resumeBuilder.Service;

import com.exam.resumeBuilder.Document.Payment;
import com.exam.resumeBuilder.Document.User;
import com.exam.resumeBuilder.Repository.PaymentRepository;
import com.exam.resumeBuilder.Repository.UserRepository;
import com.exam.resumeBuilder.dto.AuthResponse;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.exam.resumeBuilder.util.AppConstans.PREMIUM;

@RequiredArgsConstructor
@Service
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKetSecret;

    public Payment createOrder(Object principal, String planType) throws RazorpayException {

        // Initial Step
        AuthResponse authResponse = authService.getProfile(principal);

        //Step1: Initialize the razorpay client
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKetSecret);

        //step2: prepare the json object to pass the razorpay
        int amount = 99900; //amount in paise
        String currency = "INR";
        String receipt = PREMIUM+"_"+ UUID.randomUUID().toString().substring(0,8);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount);
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", receipt);

        //step3: Call the razorpay API to create order
        Order razorpayOrder = razorpayClient.orders.create(orderRequest);
        log.info("Razorpay Order: {}", razorpayOrder);
        log.info("Order ID: {}", (Object) razorpayOrder.get("id"));
        //step4: save the order details into database
        Payment payment = Payment.builder()
                .userId(authResponse.getId())
                .razorpayOrderId(razorpayOrder.get("id").toString())
                .amount(amount)
                .currency(currency)
                .planType(planType)
                .status("created")
                .receipt(receipt)
                .build();
        //Step5: return the result
        return paymentRepository.save(payment);
    }

    public boolean verifyPayment(String razorpayPaymentId, String razorpayOrderId, String razorpaySignature) throws RazorpayException {
      try {
          JSONObject attributes = new JSONObject();
          attributes.put("razorpay_order_id", razorpayOrderId);
          attributes.put("razorpay_payment_id", razorpayPaymentId);
          attributes.put("razorpay_signature", razorpaySignature);

          boolean isValidSignature = Utils.verifyPaymentSignature(attributes, razorpayKetSecret);
          if (isValidSignature){
              Payment payment = paymentRepository.findByRazorpayOrderId(razorpayOrderId).orElseThrow(() -> new RuntimeException("Payment is not found"));
              payment.setRazorpayPaymentId(razorpayPaymentId);
              payment.setRazorpaySignature(razorpaySignature);
              payment.setStatus("paid");
              paymentRepository.save(payment);


              //Upgrade the user subscription
              upgradeUserSubscription(payment.getUserId(), payment.getPlanType());
              return true;
      }
          return false;
    }
      catch (Exception e){
          log.error("Error verifying the payment", e);
          return false;
      }
    }
    private void upgradeUserSubscription(String userId, String planType) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        existingUser.setSubscriptionPlan(planType);
        userRepository.save(existingUser);
        log.info("User {} upgraded to {} plan");

    }

    public List<Payment> getUserPayments(@Nullable Object principal) {
        //Step1: Get the current profile
        AuthResponse authResponse = authService.getProfile(principal);

        //Step2: Call the repo finder method
        return paymentRepository.findByUserIdOrderByCreateAtDesc(authResponse.getId());
    }

    public Payment getPaymentDetails(String orderId) {
       //Step1: Call the repo finder
        return paymentRepository.findByRazorpayOrderId(orderId).orElseThrow(() -> new RuntimeException("The payment is nor found"));
    }
}
