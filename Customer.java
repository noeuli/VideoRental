import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
	private String name;

	private List<Rental> rentals = new ArrayList<Rental>();

	public Customer(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public void addRental(Rental rental) {
		rentals.add(rental);

	}

	public String getReport() {
		String result = "Customer Report for " + getName() + "\n";

		List<Rental> rentals = getRentals();

		double totalCharge = 0;
		int totalPoint = 0;

		for (Rental each : rentals) {
			int daysRented = calculateDaysRented(each);
			double eachCharge = calculateEachCharge(each, daysRented);
			int eachPoint = calculateEachPoint(each, daysRented);

			result += "\t" + each.getVideo().getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
					+ "\tPoint: " + eachPoint + "\n";

			totalCharge += eachCharge;
			totalPoint += eachPoint ;
		}

		result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";

		issueCoupon(totalPoint);

		return result ;
	}

	// extract method
	private void issueCoupon(int totalPoint) {
		if ( totalPoint >= 10 ||  totalPoint >= 30) {
			System.out.println("Congrat! You earned one free coupon");
		}
	}

	// extract method
	private int calculateEachPoint(Rental each, int daysRented) {
		int eachPoint = 0 ;
		eachPoint++;

		if ((each.getVideo().getPriceCode() == Video.NEW_RELEASE) )
			eachPoint++;

		if ( daysRented > each.getDaysRentedLimit() )
			eachPoint -= Math.min(eachPoint, each.getVideo().getLateReturnPointPenalty()) ;
		return eachPoint;
	}

	// extract method
	private double calculateEachCharge(Rental each, int daysRented) {
		double eachCharge = 0;
		switch (each.getVideo().getPriceCode()) {
			case Video.REGULAR:
				eachCharge += 2;
				if (daysRented > 2)
					eachCharge += (daysRented - 2) * 1.5;
				break;
			case Video.NEW_RELEASE:
				eachCharge = daysRented * 3;
				break;
		}
		return eachCharge;
	}

	// extract method
	private int calculateDaysRented(Rental each) {
		long diff;
		if (each.getStatus() == 1) { // returned Video
			diff = each.getReturnDate().getTime() - each.getRentDate().getTime();
		} else { // not yet returned
			diff = new Date().getTime() - each.getRentDate().getTime();
		}
		return (int) (diff / (1000 * 60 * 60 * 24)) + 1;
	}
}