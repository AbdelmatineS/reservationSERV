package tn.ooredoo.reservation.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateCreation;
	//@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateDernièreModification;
	
	private String contractNum;
	private String boxType;
	private String abbType;
	private String civilite;
	private String nationalite;
	private String prenom;
	private String nom;
	private String idType;
	private Long numID;
	private String naissance;
	private String adresse;
	private String gouvernorat;
	private String delegation;
	private String localite;
	private String ville;
	private Integer codePostal;
	private String email;
	private Long telOne;
	private Long telTwo;
	private Long latitude;
	private Long longitude;
    
	@Lob
	private byte[] signatureImage;
	

}
