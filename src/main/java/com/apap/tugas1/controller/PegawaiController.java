package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;


@Controller
public class PegawaiController {
	@Autowired 
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<ProvinsiModel> daftarProvinsi = provinsiService.getAllProvinsi();
		ProvinsiModel provPertama = daftarProvinsi.get(0);
		List<InstansiModel> daftarInstansi = provPertama.getIistInstansi();
		List<JabatanModel> daftarJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listInstansi", daftarInstansi);
		model.addAttribute("listProvinsi", daftarProvinsi);
		model.addAttribute("listJabatan", daftarJabatan);
		return "home";
	}
	
	@RequestMapping("/pegawai")
	private String viewPegawai(@RequestParam(value = "nip") String nip, Model model) {
		PegawaiModel archive = pegawaiService.getPegawaiDetailByNip(nip);
		
		//hitung gaji pokok
		double gaji=0;
		for(JabatanPegawaiModel jabatan : archive.getListJabatan()) {
			if(jabatan.getJabatan().getGajiPokok()>gaji) {
				gaji+=jabatan.getJabatan().getGajiPokok();
			}
		}
		gaji+=((archive.getInstansi().getProvinsi().getPresentaseTunjangan()/100)*gaji);
		gaji=gaji/10;
		
		model.addAttribute("pegawai", archive);
		model.addAttribute("gaji", gaji);
		return "view-pegawai";
	}
	
	@RequestMapping("/pegawai/termuda-tertua")
	public String viewPegawaii (@RequestParam Long idInstansi, Model model) {
		List<PegawaiModel> listPegawai = pegawaiService.getTertuaTermudaByInstansi(instansiService.getInstansiDetailById(idInstansi).get());
		model.addAttribute("pegawaiTertua", listPegawai.get(0));
		model.addAttribute("pegawaiTermuda", listPegawai.get(listPegawai.size()-1));
		return "view-tertua-termuda";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.GET)
	private String tambahPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		ArrayList<JabatanPegawaiModel> jabatan = new ArrayList<JabatanPegawaiModel>();
		jabatan.add(new JabatanPegawaiModel());
		pegawai.setListJabatan(jabatan);
		
		List<ProvinsiModel> daftarProvinsi = provinsiService.getAllProvinsi();
		ProvinsiModel provPertama = daftarProvinsi.get(0);
		List<InstansiModel> daftarInstansi = provPertama.getIistInstansi();
		List<JabatanModel> daftarJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listInstansi", daftarInstansi);
		model.addAttribute("listProvinsi", daftarProvinsi);
		model.addAttribute("listJabatan", daftarJabatan);
		
		return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.POST)
	private String tambah(@ModelAttribute PegawaiModel pegawai, RedirectAttributes ra) {
		String nip = pegawaiService.generateNip(pegawai);
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
	
		String url = "redirect:/pegawai?nip=" + pegawai.getNip();
		return url;
	}
	

	@RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
	public String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		pegawai.getListJabatan().add(new JabatanPegawaiModel());
		List<InstansiModel> allInstansi = instansiService.getInstansiFromProvinsi(pegawai.getInstansi().getProvinsi());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listInstansi", allInstansi);
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		
	    return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"deleteRow"}, method = RequestMethod.POST)
	public String deleteRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		List<InstansiModel> allInstansi = instansiService.getInstansiFromProvinsi(pegawai.getInstansi().getProvinsi());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listInstansi", allInstansi);
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		if (pegawai.getListJabatan().size() > rowId.intValue()) {
			pegawai.getListJabatan().remove(rowId.intValue());
		}
	    model.addAttribute("pegawai", pegawai);
	    return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	public String ubahPegawai (@RequestParam(value = "nip", required = true) String nip, Model model) {
		PegawaiModel archive = pegawaiService.getPegawaiDetailByNip(nip);
		
		List<ProvinsiModel> daftarProvinsi = provinsiService.getAllProvinsi();
		ProvinsiModel provPertama = daftarProvinsi.get(0);
		List<InstansiModel> daftarInstansi = provPertama.getIistInstansi();
		List<JabatanModel> daftarJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listInstansi", daftarInstansi);
		model.addAttribute("listProvinsi", daftarProvinsi);
		model.addAttribute("listJabatan", daftarJabatan);
		
		model.addAttribute("pegawai", archive);
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	public String ubahPegawai (Model model, @ModelAttribute PegawaiModel pegawai) {
		
		return "update-pegawai-sukses";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	private String findPegawai(@RequestParam(value="idProvinsi", required = false) Optional<Long> idProvinsi, 
			@RequestParam(value="idInstansi", required = false) Optional<Long> idInstansi, 
			@RequestParam(value="idJabatan", required = false) Optional<Long> idJabatan, 
			Model model) {
		List<ProvinsiModel> listAllProv = provinsiService.getAllProvinsi();
		List<JabatanModel> listAllJabatan = jabatanService.getAllJabatan();
		List<InstansiModel> listAllInstansi = instansiService.getAllInstansi();
		
		model.addAttribute("listInstansi", listAllInstansi);
		model.addAttribute("listJabatan", listAllJabatan);
		model.addAttribute("listProvinsi", listAllProv);
		
		List<PegawaiModel> pegawai = new ArrayList<PegawaiModel>();
		if(idInstansi.isPresent()) {
			
				InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi.get()).get();
			if(idJabatan.isPresent()) {
				Optional<JabatanModel> jabatan = jabatanService.getJabatanDetailById(idJabatan.get());
				
				pegawai = pegawaiService.getPegawaiByInstansiAndJabatan(instansi, jabatan);
			}else {
				pegawai = pegawaiService.getPegawaiByInstansi(instansi);
			}
		}
		else{
			List<PegawaiModel> pegawaiTemp = new ArrayList<PegawaiModel>();
			if(idProvinsi.isPresent()) {
				ProvinsiModel provinsi = provinsiService.getProvinsiById(idProvinsi.get()).get();
				
				List<InstansiModel> listInstansi = instansiService.getInstansiFromProvinsi(provinsi);
				if(idJabatan.isPresent()) {
					Optional<JabatanModel> jabatan = jabatanService.getJabatanDetailById(idJabatan.get());
					
					for(InstansiModel instansi : listInstansi) {
						pegawaiTemp = pegawaiService.getPegawaiByInstansiAndJabatan(instansi, jabatan);
					}
					pegawai = pegawaiTemp;
					
				}else {
					for(InstansiModel instansi : listInstansi) {
						pegawaiTemp = pegawaiService.getPegawaiByInstansi(instansi);
					}
					pegawai = pegawaiTemp;
				}
			}else if(idJabatan.isPresent()){
				Optional<JabatanModel> jabatan = jabatanService.getJabatanDetailById(idJabatan.get());
				pegawai = pegawaiService.getPegawaiByListjabatan(jabatan);
			}
		}
		
		model.addAttribute("listPegawai", pegawai);
		return "search-pegawai";
	}
}
