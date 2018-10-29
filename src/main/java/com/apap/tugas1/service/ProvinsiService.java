package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiService {
    void addProvinsi(ProvinsiModel provinsi);
    List<ProvinsiModel> getAllProvinsi();
    Optional<ProvinsiModel> getProvinsiById(Long id);
    ProvinsiModel getProvinsiByName(String nama);
}