package com.twillioMessaging.messageTwilio.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twillioMessaging.messageTwilio.Payload.SMSRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SMSController {
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twiliophoneNumber;
//url-> http://localhost:8080/sms-sending, with the body-> (to, message) parameter
    @PostMapping("/sms-sending")
    public ResponseEntity<String> sendSMS(@RequestBody SMSRequest smsRequest) {
        try {
            Twilio.init(accountSid, authToken);
            Message message=Message.creator(
                    new PhoneNumber(smsRequest.getTo()),
                    new PhoneNumber(twiliophoneNumber),
                    smsRequest.getMessage())
                    .create();
            return ResponseEntity.ok("SMS send Successfully SID:" + message.getSid());

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to send SMS"+e.getMessage());
        }

    }
}