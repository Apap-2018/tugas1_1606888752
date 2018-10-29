package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
    @Autowired
    private JabatanDb jabatanDb;


    @Override
    public List<JabatanModel> getAllJabatan() {
        return jabatanDb.findAll();
    }

    @Override
	public Optional<JabatanModel> getJabatanDetailById(Long id) {
		return jabatanDb.findById(id);
	}

    @Override
    public void addJabatan(JabatanModel jabatan) {
        jabatanDb.save(jabatan);
    }

	@Override
	public void updateJabatan(JabatanModel jabatan, long id) {
		JabatanModel oldJabatan = jabatanDb.findById(id);
		oldJabatan.setNama(jabatan.getNama());
		oldJabatan.setDeskripsi(jabatan.getDeskripsi());
		oldJabatan.setGajiPokok(jabatan.getGajiPokok());
	}

	@Override
	public void deleteJabatanById(Long id) {
		jabatanDb.deleteById(id);
		
	}
}