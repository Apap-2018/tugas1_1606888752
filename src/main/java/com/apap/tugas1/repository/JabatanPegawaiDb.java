package com.apap.tugas1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.JabatanPegawaiModel;

/**
 * 
 * @author Amira Taliya
 *
 */
@Repository
public interface JabatanPegawaiDb extends JpaRepository<JabatanPegawaiModel, Long> {
	/*Optional<List<JabatanPegawaiModel>> findPegawaiByNip(String nip);
	List<JabatanPegawaiModel> findJabatanById(Long id);*/
}
