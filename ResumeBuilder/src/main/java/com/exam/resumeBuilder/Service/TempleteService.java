package com.exam.resumeBuilder.Service;

import com.exam.resumeBuilder.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.exam.resumeBuilder.util.AppConstans.PREMIUM;

@Service
@RequiredArgsConstructor
@Slf4j
public class TempleteService {

    private final AuthService authService;
    public Map<String, Object> getTempletes(Object principal){

        //Step1: get the current profile
       AuthResponse authResponse = authService.getProfile(principal);

        //Step2: get the available template based on the subscription
        List<String> allTemplates = List.of("01", "02", "03");
        List<String> availableTemplates;

        Boolean isPremium = PREMIUM.equalsIgnoreCase(authResponse.subscriptionPlan);

        if(isPremium==true){
            availableTemplates = List.of("01","02","03");
        }else{
            availableTemplates = List.of("01");
        }

        //Step3: Add the data into app
        Map<String, Object> restriction = new HashMap<>();
        restriction.put("availableTemplates", availableTemplates);
        restriction.put("allTemplates", List.of("01", "02", "03"));
        restriction.put("subscriptionPlan", authResponse.subscriptionPlan);
        restriction.put("isPremium", isPremium);

        //Step: return the result
        return restriction;
    }
}
