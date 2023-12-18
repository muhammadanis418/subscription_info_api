package com.subscription.api.subscription_info_api.controller;

import com.subscription.api.subscription_info_api.model.SubscriptionResponse;
import com.subscription.api.subscription_info_api.service.SubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SubController {

    private final SubService subSer;

    public SubController(SubService subSer) {
        this.subSer = subSer;
    }

    @GetMapping("/sub")
    public SubscriptionResponse getSubs(@RequestParam String msisdn){
            return subSer.getSubs(msisdn);

    }
//    @GetMapping("/sub")
//    public ResponseEntity< SubscriptionResponse> getSubs(@RequestParam String msisdn){
//            return subSer.getSubs(msisdn);
//
//    }


}
