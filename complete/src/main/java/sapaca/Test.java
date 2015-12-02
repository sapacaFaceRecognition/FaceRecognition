package sapaca;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test")
public class Test {

	@Id
	private long id;

	@Column(name = "test")
	private String test;


}