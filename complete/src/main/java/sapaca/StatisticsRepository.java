package sapaca;

import org.springframework.data.repository.CrudRepository;

public interface StatisticsRepository extends CrudRepository<Statistics, Double> {
	
	Statistics findById(long id);
	
}
