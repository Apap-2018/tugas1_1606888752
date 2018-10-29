package com.apap.tugas1.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.apap.tugas1.model.*;

/**
 * PegawaiDb
 * @author Amira Taliya
 *
 */
@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long> {
	PegawaiModel findByNip(String Nip);
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	List<PegawaiModel> findByInstansi(InstansiModel Instansi);
	List<PegawaiModel> findByTanggalLahirAndTahunMasukAndInstansi(Date date, String tahunMasuk, InstansiModel instansi);
	
	
}
