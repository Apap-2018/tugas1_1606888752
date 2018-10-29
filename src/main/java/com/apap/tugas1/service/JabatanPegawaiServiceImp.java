package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.repository.*;

@Service
@Transactional
public class JabatanPegawaiServiceImp implements JabatanPegawaiService{

	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;
	
/*	@Override
	public Optional<List<JabatanPegawaiModel>> getJabatanByNip(String nip) {
		return jabatanPegawaiDb.findPegawaiByNip(nip);
	}

	@Override
	public List<JabatanPegawaiModel> getPegawaiById(Long id) {
		return jabatanPegawaiDb.findJabatanById(id);
	}
*/
	@Override
	public void addJabatanPegawai(JabatanPegawaiModel jabatanPegawai) {
		jabatanPegawaiDb.save(jabatanPegawai);
		
	}

	@Override
	public List<JabatanPegawaiModel> getAllJabatan() {
		return jabatanPegawaiDb.findAll();
	}

}