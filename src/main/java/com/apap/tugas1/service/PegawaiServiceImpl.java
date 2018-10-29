package com.apap.tugas1.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDb;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb pegawaiDb;
	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);		
	}
	
	@Override
	public List<PegawaiModel> getAllPegawai() {
		return pegawaiDb.findAll();
	}
	
	@Override
	public List<PegawaiModel> getTertuaTermudaByInstansi(InstansiModel instansi) {
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
	}
	
	@Override
	public String generateNip(PegawaiModel pegawai) {
		String nip = "";
		nip += pegawai.getInstansi().getId();
		Date date = pegawai.getTanggalLahir();
		String[] tgl = (""+date).split("-");
		for (int i = tgl.length-1; i >= 0; i--) {
			int ukuranTgl = tgl[i].length();
			nip += tgl[i].substring(ukuranTgl-2, ukuranTgl);
		}
		nip += pegawai.getTahunMasuk();
		List<PegawaiModel> listPegawai = pegawaiDb.findByTanggalLahirAndTahunMasukAndInstansi(date, pegawai.getTahunMasuk(), pegawai.getInstansi());
		if(listPegawai.size() < 10) {
			nip += "0"+listPegawai.size();
		}
		else {
			nip += listPegawai.size();
		}
		pegawai.setNip(nip);
		System.out.println(nip);
		return nip;
	}
	
	@Override
	public void updatePegawai(PegawaiModel oldPegawai, PegawaiModel newPegawai) {
		 newPegawai.setNama(oldPegawai.getNama());
		  newPegawai.setNip(oldPegawai.getNip());
		  newPegawai.setTahunMasuk(oldPegawai.getTahunMasuk());
		  newPegawai.setTanggalLahir(oldPegawai.getTanggalLahir());
		  newPegawai.setTempatLahir(oldPegawai.getTempatLahir());
		  newPegawai.setInstansi(oldPegawai.getInstansi());
		  int jumlahList = oldPegawai.getListJabatan().size();
		  for (int i = 0; i< jumlahList; i++) {
		   oldPegawai.getListJabatan().get(i).setJabatan(newPegawai.getListJabatan().get(i).getJabatan());
		  }
		  
		  for (int i = jumlahList; i < newPegawai.getListJabatan().size(); i++) {
		   newPegawai.getListJabatan().get(i).setPegawai(oldPegawai);
		   jabatanPegawaiDb.save(newPegawai.getListJabatan().get(i));
		  }
		  pegawaiDb.save(newPegawai);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, Optional<JabatanModel> jabatan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		return pegawaiDb.findByInstansi(instansi);
	}

	@Override
	public List<PegawaiModel> getPegawaiByListjabatan(Optional<JabatanModel> jabatan) {
		// TODO Auto-generated method stub
		return null;
	}

}