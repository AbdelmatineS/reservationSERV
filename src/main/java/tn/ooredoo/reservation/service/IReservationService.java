package tn.ooredoo.reservation.service;

import java.util.List;

import tn.ooredoo.reservation.entity.Reservation;


public interface IReservationService {
	
	public Reservation retrieveReservationById(Long id);
	public List<Reservation> retrieveAllReservation();
	public Reservation addReservation(Reservation r);
	public Reservation updateReservation(Reservation r, Long id);
	public void deleteReservation(Long id);
	public Reservation getLastReservation();

}