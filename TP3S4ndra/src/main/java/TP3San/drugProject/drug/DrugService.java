package TP3San.drugProject.drug;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DrugService implements IDrugService {
	
	private final IDrugRepo drugRepo;
	
	@Autowired
	public DrugService(IDrugRepo drugRepo) {
		this.drugRepo = drugRepo;
	}

	public List<Drug> getAllDrugs() {
		return (List<Drug>)drugRepo.findAll();
	}
	
	public void addNewDrug(Drug drug) {
		if (drugRepo.existsById(drug.getDrugnumber())) {
			System.out.println("Drug existe déjà!");
		} else {
			System.out.println("New drug saved: " + drug);
			drugRepo.save(drug);
		}
	}
	
	public void deleteDrug(int drugnumber) {
		if (!drugRepo.existsById(drugnumber)) {
			throw new IllegalStateException("Drug " + drugnumber + " n'existe pas!");
		}
		drugRepo.deleteById(drugnumber);
	}

	@Transactional
	public void updateDrug(int drugnumber, Drug updatedDrug) {
		Drug drug = drugRepo.findById(drugnumber)
				.orElseThrow(() -> new IllegalStateException("Drug " + drugnumber + " n'existe pas"));

		drug.setDci(updatedDrug.getDci());
		drug.setCatnumber(updatedDrug.getCatnumber());
		drug.setDosage(updatedDrug.getDosage());
		drug.setForm(updatedDrug.getForm());
		drug.setReference(updatedDrug.getReference());
		drug.setCases(updatedDrug.getCases());
		drug.setPosts(updatedDrug.getPosts());
		drug.setCenters(updatedDrug.getCenters());
		drug.setEps1(updatedDrug.getEps1());
		drug.setEps2(updatedDrug.getEps2());
		drug.setEps3(updatedDrug.getEps3());
		drug.setCreatedby(updatedDrug.getCreatedby());
		drug.setLastupdatedby(updatedDrug.getLastupdatedby());
		drug.setStatus(updatedDrug.getStatus());

		drugRepo.save(drug);
	}

	@Transactional
	public Drug getUnDrug(int drugnumber) {
		Drug drug = drugRepo.findById(drugnumber).orElseThrow(() -> new IllegalStateException("Drug " + drugnumber + " n'existe pas"));
		return drug;
	}

	@Transactional
	public Drug getUnDrug(String dci) {
		Drug drug = drugRepo.findDrugByDci(dci).orElseThrow(()->new IllegalStateException("Drug " + dci + " n'existe pas"));
		return drug;
	}
}
