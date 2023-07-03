package tn.ooredoo.reservation.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;



@Service
public class SmsServiceImpl implements ISmsService {
	
    @Value("${TWILIO_ACCOUNT_SID}")
    String ACCOUNT_SID;
    @Value("${TWILIO_AUTH_TOKEN}")
    String AUTH_TOKEN;
    @Value("${TWILIO_OUTGOING_SMS_NUMBER}")
    String OUTGOING_SMS_NUMBER;
    
    
    @PostConstruct
    private void setup(){
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
    }
    
    public String sendSMS(String smsMessages, String smsNumber) {
        Message message = Message.creator(
                new PhoneNumber(smsNumber),
                new PhoneNumber(OUTGOING_SMS_NUMBER),
                smsMessages).create();

            return message.getStatus().toString();
        }
   
}

/*
 *     @PostConstruct
    private void setup(){
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
    }
    public String sendSMS(String smsMessages, String smsNumber) {
        Message message = Message.creator(
                new PhoneNumber(smsNumber),
                new PhoneNumber(OUTGOING_SMS_NUMBER),
                smsMessages).create();

            return message.getStatus().toString();
        }

 */
