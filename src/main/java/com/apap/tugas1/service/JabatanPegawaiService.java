package com.apap.tugas1.service;

import com.apap.tugas1.model.*;
import java.util.List;

public interface JabatanPegawaiService {
	/*Optional<List<JabatanPegawaiModel>> getJabatanByNip(String nip);

	List<JabatanPegawaiModel> getPegawaiById(Long id);*/
	
	void addJabatanPegawai(JabatanPegawaiModel jabatanPegawai);

	List<JabatanPegawaiModel> getAllJabatan();
}