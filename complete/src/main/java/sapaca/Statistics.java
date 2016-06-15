package sapaca;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "statistics")
public class Statistics {

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "ages")
	private ArrayList<Integer> ages;

	@Column(name = "calculation_time")
	private ArrayList<Long> calculationTime;

	@Column(name = "is_face")
	private int isFace;

	@Column(name = "is_no_face")
	private int isNoFace;

	private static final int IS_ZERO = -42;

	public void initialize() {
		setId(0);
		setAges(new ArrayList<Integer>());
		setCalculationTime(new ArrayList<Long>());
		setIsFace(0);
		setIsNoFace(0);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ArrayList<Integer> getAges() {
		return ages;
	}

	public void setAges(ArrayList<Integer> ages) {
		this.ages = ages;
	}

	public double calculateAverageAge() {
		double averageAge = 0;
		for (int age : ages) {
			averageAge += age;
		}
		if (!ages.isEmpty()) {
			averageAge = (averageAge / ages.size());
			return averageAge;
		} else {
			return IS_ZERO;
		}
	}

	public ArrayList<Long> getCalculationTime() {
		return calculationTime;
	}

	public void setCalculationTime(ArrayList<Long> calculationTime) {
		this.calculationTime = calculationTime;
	}

	public long calculateAverageCalculationTime() {
		long averageCalculationTime = 0;
		for (long calculationTimes : calculationTime) {
			averageCalculationTime += calculationTimes;
		}
		if (!calculationTime.isEmpty()) {
			averageCalculationTime = (averageCalculationTime / calculationTime.size());
			return averageCalculationTime;
		} else {
			return IS_ZERO;
		}
	}

	public int getIsFace() {
		return isFace;
	}

	public void setIsFace(int isFace) {
		this.isFace = isFace;
	}

	public int getIsNoFace() {
		return isNoFace;
	}

	public void setIsNoFace(int isNoFace) {
		this.isNoFace = isNoFace;
	}

	public double calculateAccuracyOfCalculation() {
		if (isFace > 0 || isNoFace > 0) {
			return ((double) isFace / ((double) isFace + (double) isNoFace));
		} else {
			return IS_ZERO;
		}
	}

}
