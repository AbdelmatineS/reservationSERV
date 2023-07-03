package tn.ooredoo.reservation.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tn.ooredoo.reservation.entity.Reservation;
import tn.ooredoo.reservation.repository.ReservationRepository;

@Service
public class ReservationServiceImp implements IReservationService {
	
	
	@Autowired
	ReservationRepository rRepo;

	@Override
	public Reservation retrieveReservationById(Long id) {
		return rRepo.findById(id).get();
	}

	@Override
	public List<Reservation> retrieveAllReservation() {
		return (List<Reservation>) rRepo.findAll();
	}

	@Override
	public Reservation addReservation(Reservation r) {
		r.setDateCreation(LocalDate.now());
		r.setDateDernièreModification(LocalDate.now());
		return rRepo.save(r);
	}

	@Override
	public Reservation updateReservation(Reservation r, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteReservation(Long id) {
		rRepo.deleteById(id);		
	}

	@Override
	public Reservation getLastReservation() {
		return rRepo.findTopByOrderByIdDesc();
	}

}





