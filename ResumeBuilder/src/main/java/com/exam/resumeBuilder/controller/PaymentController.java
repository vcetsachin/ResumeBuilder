package com.exam.resumeBuilder.controller;

import com.exam.resumeBuilder.Document.Payment;
import com.exam.resumeBuilder.Service.PaymentService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.exam.resumeBuilder.util.AppConstans.PREMIUM;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, String> request,
                                         Authentication authentication) throws RazorpayException {
        //Step1: Validate the request
        String planType = request.get("planType");
        if(!PREMIUM.equalsIgnoreCase(planType)){
          return ResponseEntity.badRequest().body(Map.of("message", authentication));
        }
        //Step2: call the service method
        Payment payment = paymentService.createOrder(authentication.getPrincipal(), planType);

        //Step3: Prepare the response object
        Map<String, Object> response = Map.of(
                "orderId", payment.getRazorpayOrderId(),
                "amount", payment.getAmount(),
                "currency", payment.getCurrency(),
                "receipt", payment.getReceipt()
        );
        //Step4: return the response
        return ResponseEntity.ok(response);

    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> request) throws RazorpayException {

        //Step1: Validate the request
        String razorpayOrderId = request.get("razorpay_order_id");
        String razorpayPaymentId = request.get("razorpay_payment_id");
        String razorpaySignature = request.get("razorpay_signature");

        if (Objects.isNull(razorpayOrderId) || Objects.isNull(razorpayPaymentId) || Objects.isNull(razorpaySignature)){
            return  ResponseEntity.badRequest().body(Map.of("message","Missing required payment parameters"));
        }

        //Step2: Call the service method
        boolean isValid = paymentService.verifyPayment(razorpayPaymentId,razorpayOrderId,razorpaySignature);

        //Step3: Return the response
        if (isValid){
            return ResponseEntity.ok(Map.of("message", "Payment verified successfully",
                    "status","success"));
        }
        else {
            return ResponseEntity.badRequest().body(Map.of("message", "Payment verification failed"));
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> getPaymentHistory(Authentication authentication){
        //Step1: call the service
        List<Payment> payments = paymentService.getUserPayments(authentication.getPrincipal());
        return  ResponseEntity.ok(payments);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable String orderId){
            //Step1: Call the service method
        Payment paymentDetails = paymentService.getPaymentDetails(orderId);

        //Step2: return response
        return ResponseEntity.ok(paymentDetails);
    }
}
