package TP3San.drugProject.drug;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDrugRepo extends CrudRepository<Drug, Integer> {
	
	//SELECT * FROM drug WHERE dci == ?
	@Query("SELECT d FROM Drug d WHERE d.dci = ?1")
	Optional<Drug> findDrugByDci(String dci);
}
