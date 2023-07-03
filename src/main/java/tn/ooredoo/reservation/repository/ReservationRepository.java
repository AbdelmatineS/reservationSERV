package tn.ooredoo.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.ooredoo.reservation.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	Reservation findTopByOrderByIdDesc();

}
