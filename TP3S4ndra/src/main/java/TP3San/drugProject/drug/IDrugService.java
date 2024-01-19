package TP3San.drugProject.drug;

import java.util.List;

public interface IDrugService {
	
	List<Drug> getAllDrugs();
	void addNewDrug(Drug drug);
	void deleteDrug(int drugnumber);
	void updateDrug(int drugnumber, Drug updatedDrug);
	Drug getUnDrug(int drugnumber);
	Drug getUnDrug(String dci);

}
