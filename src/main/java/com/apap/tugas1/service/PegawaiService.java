package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	void addPegawai(PegawaiModel pegawai);
	List<PegawaiModel> getAllPegawai();
	List<PegawaiModel> getTertuaTermudaByInstansi(InstansiModel instansiModel);
	String generateNip(PegawaiModel pegawai);
	void updatePegawai(PegawaiModel oldPegawai, PegawaiModel newPegawai);
	List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, Optional<JabatanModel> jabatan);
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	List<PegawaiModel> getPegawaiByListjabatan(Optional<JabatanModel> jabatan);	
}