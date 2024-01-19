package TP3San.drugProject.log;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILogRepo extends CrudRepository<Log, Long> {
}
