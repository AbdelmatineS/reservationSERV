package tn.ooredoo.reservation.controller;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.exception.TwilioException;

import tn.ooredoo.reservation.entity.Reservation;
import tn.ooredoo.reservation.service.IEmailService;
import tn.ooredoo.reservation.service.IReservationService;
import tn.ooredoo.reservation.service.ISmsService;

@CrossOrigin(origins = {"http://localhost:8100", "http://172.19.3.38:8101"})
@RestController
@RequestMapping("Reservation")
public class ReservationController {
	
	@Autowired
	IReservationService rService;
	
	@Autowired
	ISmsService smsService;
	
	@Autowired
	IEmailService emailService;
	

    @GetMapping("/getLastReservation")
    public ResponseEntity<?> getLastReservation() {
        Reservation lastReservation = rService.getLastReservation();
        return ResponseEntity.ok(lastReservation);
    }
	
	@PostMapping("/addReservation")
	@ResponseBody
	ResponseEntity<Long> addReservation(@RequestBody Reservation r) throws MessagingException {
		Reservation response = rService.addReservation(r);
		
        if (response != null && response.getId() != null) {
            sendEmailAsync(r);
            sendSMSAsync(r);

            return ResponseEntity.ok(response.getId());
        } else {
            // Reservation addition failed
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

	}
	
	@PostMapping("/addSignature")
	public ResponseEntity<Reservation> updateReservation(@RequestBody Map<String, Object> requestData) {
	    try {
	        Long resID = Long.parseLong(requestData.get("res").toString()); // Parse resID to Long
	        Reservation r = retrieveReservationById(resID);
	        String signatureDataURL = (String) requestData.get("signature");
	        String base64Image = signatureDataURL.replace("data:image/png;base64,", "");
	        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
	        r.setSignatureImage(imageBytes);
	        // Copy other properties from requestData to reservation (if needed)
	        rService.addReservation(r);
	        return ResponseEntity.ok().body(r);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	
	
	@GetMapping("/retrieveallreservations")
	@ResponseBody
	public List<Reservation> retrieveAll() {
	List<Reservation> listReservations = rService.retrieveAllReservation();
	return listReservations;
	}
	
	
	@GetMapping("retrieveReservation/{id}")
	@ResponseBody
	Reservation retrieveReservationById(@PathVariable Long id ) {	
		return rService.retrieveReservationById(id);
	}
	
	@DeleteMapping("deleteReservation/{id}")
	@ResponseBody
	void deleteReservation(@PathVariable("id") Long id) {	
		 rService.deleteReservation(id);
	}

	

	@PutMapping("modifyReservation/{id}")
	@ResponseBody
	Reservation updateReservation(@RequestBody Reservation r , @PathVariable Long id ) {	
		return  rService.updateReservation(r,id);
	}
	
	
	
    @Async
    public void sendEmailAsync(Reservation r) throws MessagingException {
        // Send email
		String to = r.getEmail();
		String subject = "Succès de la réservation";
		String body = "Votre réservation a été ajoutée avec succès sous le numéro "+r.getContractNum();
		emailService.sendEmail(to, subject, body);
    }

    @Async
    public void sendSMSAsync(Reservation r) {
        try {
            // Send SMS
            String smsMessage = "Votre réservation N° " + r.getContractNum() + " a été ajoutée avec succès.";
            smsService.sendSMS(smsMessage, "+216" + r.getTelOne().toString());
        } catch (TwilioException e) {
            // Handle SMS sending failure
            e.printStackTrace();
        }
    }
    
}


/*@PostMapping("/addReservation")
@ResponseBody
Reservation addReservation(@RequestBody Reservation r) {
	return rService.addReservation(r);	
}*/

