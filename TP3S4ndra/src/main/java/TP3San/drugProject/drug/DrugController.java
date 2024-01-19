package TP3San.drugProject.drug;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/drug")
public class DrugController {

	private final DrugService drugService;

	@Autowired //magically instantiates drugService
	public DrugController(DrugService drugService) {
		this.drugService = drugService;
	}
	
	@GetMapping
	public List<Drug> getAllDrugs() {
		return drugService.getAllDrugs();
	}

	@GetMapping (path = "{drugnumber}")
	public Drug getUnDrug(@PathVariable("drugnumber") int drugnumber) {
		return drugService.getUnDrug(drugnumber);
	}

	@GetMapping (path = "/searchByDci/{dci}")
	public Drug getUnDrug(@PathVariable("dci") String dci) {
		return drugService.getUnDrug(dci);
	}
		
	@PostMapping
	public void registerNewDrug(@RequestBody Drug drug) {
		drugService.addNewDrug(drug);
	}

	@DeleteMapping(path = "{drugnumber}")
	public void deleteDrug(@PathVariable("drugnumber") int drugnumber) {
		drugService.deleteDrug(drugnumber);
	}

	@PutMapping(path = "{drugnumber}")
	public void updateDrug(@PathVariable("drugnumber") int drugnumber, @RequestBody Drug updatedDrug) {
		drugService.updateDrug(drugnumber, updatedDrug);
	}

}