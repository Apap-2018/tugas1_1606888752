package com.apap.tugas1.repository;

import com.apap.tugas1.model.JabatanModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JabatanDb extends JpaRepository<JabatanModel, Long> {
    JabatanModel findById(long id);
}