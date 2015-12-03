package sapaca;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FacesRepository
// extends CrudRepository
<Face, Double> {
	List<Face> findById(long id);

	List<Face> findByFirstName(String firstName);

	List<Face> findByLastName(String lastName);

	List<Face> findByNationality(String nationality);

	List<Face> findByAge(String age);

	List<Face> findByFirstNameAndLastName(String firstName, String lastName);
}
