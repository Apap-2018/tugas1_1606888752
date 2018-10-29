package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
    void addJabatan(JabatanModel jabatan);
    void updateJabatan(JabatanModel jabatan, long id);
    void deleteJabatanById(Long id);
    List<JabatanModel> getAllJabatan();
    Optional<JabatanModel> getJabatanDetailById(Long id);
}