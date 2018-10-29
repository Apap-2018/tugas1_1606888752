package com.apap.tugas1.repository;
import com.apap.tugas1.model.ProvinsiModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinsiDb extends JpaRepository<ProvinsiModel, Long> {
    ProvinsiModel findById(long id);
	List<ProvinsiModel> findByNama(String nama);
}