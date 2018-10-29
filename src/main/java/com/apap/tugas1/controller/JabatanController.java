package com.apap.tugas1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	public String viewJabatan (@RequestParam(value = "idJabatan") Long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan).get();
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	public String viewAllJabatan (Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		
		Map<JabatanModel, Integer> data = new HashMap<JabatanModel, Integer>();
		for(JabatanModel jabatan : listJabatan) {
			data.put(jabatan, 0);
		}
		
		List<JabatanPegawaiModel> listJabatanPegawai = jabatanPegawaiService.getAllJabatan();
		for(JabatanPegawaiModel jabatanPegawai : listJabatanPegawai) {
			if(data.containsKey(jabatanPegawai.getJabatan())) {
				data.put(jabatanPegawai.getJabatan(), data.get(jabatanPegawai.getJabatan())+1);
			}
		}
		model.addAttribute("data", data);
		return "view-all-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	public String tambahJabatan (Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	public String tambahJabatan (Model model, @ModelAttribute JabatanModel jabatan) {
		jabatanService.addJabatan(jabatan);
		String url = "redirect:/jabatan/view?idJabatan=" + jabatan.getId();
		return url;
	}	
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	public String ubahJabatan (@RequestParam(value = "idJabatan", required = true) Long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan).get();
		model.addAttribute("jabatan", jabatan);
		return "update-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	public String ubahJabatan (Model model, @ModelAttribute JabatanModel jabatan) {
		jabatanService.updateJabatan(jabatan, jabatan.getId());
		String url = "redirect:/jabatan/view?idJabatan=" + jabatan.getId();
		return url;
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	public String hapusJabatan (Model model, @RequestParam(value = "idJabatan", required = true) Long idJabatan) {
		try {
			jabatanService.deleteJabatanById(idJabatan);
			return "delete-jabatan-sukses";
		}
		catch (Exception ex) {
			return "delete-jabatan-gagal";
		}
	}
}
