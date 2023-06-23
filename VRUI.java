import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VRUI {
	private static Scanner scanner = new Scanner(System.in) ;

	private List<Customer> customers = new ArrayList<Customer>() ;

	private List<Video> videos = new ArrayList<Video>() ;

	public static void main(String[] args) {
		VRUI ui = new VRUI() ;

		boolean quit = false ;
		while ( ! quit ) {
			int command = ui.showCommand() ;
			switch ( command ) {
				case 0: quit = true ; break ;
				case 1: ui.listCustomers() ; break ;
				case 2: ui.listVideos() ; break ;
				case 3: ui.register("customer") ; break ;
				case 4: ui.register("video") ; break ;
				case 5: ui.rentVideo() ; break ;
				case 6: ui.returnVideo() ; break ;
				case 7: ui.getCustomerReport() ; break;
				case 8: ui.clearRentals() ; break ;
				case -1: ui.init() ; break ;
				default: break ;
			}
		}
		writeOutput("Bye");
	}

	public void clearRentals() {
		writeOutput("Enter customer name: ");
		String customerName = readInput();

		Customer foundCustomer = null ;
		foundCustomer = findCustomer(customerName);

		if ( foundCustomer == null ) {
			writeOutput("No customer found");
		} else {
			writeOutput("Name: " + foundCustomer.getName() +
					"\tRentals: " + foundCustomer.getRentals().size());
			for ( Rental rental: foundCustomer.getRentals() ) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
			}

			List<Rental> rentals = new ArrayList<Rental>() ;
			foundCustomer.setRentals(rentals);
		}
	}

	private Customer findCustomer(String customerName) {
		for ( Customer customer: customers ) {
			if ( customer.getName().equals(customerName)) {
				return customer ;
			}
		}
		return null;
	}

	public void returnVideo() {
		writeOutput("Enter customer name: ");
		String customerName = readInput();

		Customer foundCustomer = null ;
		foundCustomer = findCustomer(customerName);
		if ( foundCustomer == null ) return ;

		writeOutput("Enter video title to return: ");
		String videoTitle = readInput();

		List<Rental> customerRentals = foundCustomer.getRentals() ;
		for ( Rental rental: customerRentals ) {
			if ( rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented() ) {
				rental.returnVideo();
				rental.getVideo().setRented(false);
				break ;
			}
		}
	}

	private void init() {
		Customer james = new Customer("James") ;
		Customer brown = new Customer("Brown") ;
		customers.add(james) ;
		customers.add(brown) ;

		Video v1 = new Video("v1", Video.CD, Video.REGULAR, new Date()) ;
		Video v2 = new Video("v2", Video.DVD, Video.NEW_RELEASE, new Date()) ;
		videos.add(v1) ;
		videos.add(v2) ;

		Rental r1 = new Rental(v1) ;
		Rental r2 = new Rental(v2) ;

		james.addRental(r1) ;
		james.addRental(r2) ;
	}

	public void listVideos() {
		writeOutput("List of videos");

		for ( Video video: videos ) {
			writeOutput("Price code: " + video.getPriceCode() + "\tTitle: " + video.getTitle());
		}
		writeOutput("End of list");
	}

	private static void writeOutput(String List_of_videos) {
		System.out.println(List_of_videos);
	}

	public void listCustomers() {
		writeOutput("List of customers");
		for ( Customer customer: customers ) {
			writeOutput("Name: " + customer.getName() +
					"\tRentals: " + customer.getRentals().size());
			for ( Rental rental: customer.getRentals() ) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
			}
		}
		writeOutput("End of list");
	}

	public void getCustomerReport() {
		writeOutput("Enter customer name: ");
		String customerName = readInput();

		Customer foundCustomer = null ;
		foundCustomer = findCustomer(customerName);

		if ( foundCustomer == null ) {
			writeOutput("No customer found");
		} else {
			String result = foundCustomer.getReport() ;
			writeOutput(result);
		}
	}

	private static String readInput() {
		return scanner.next();
	}

	public void rentVideo() {
		writeOutput("Enter customer name: ");
		String customerName = readInput();

		Customer foundCustomer = null ;
		foundCustomer = findCustomer(customerName);

		if ( foundCustomer == null ) return ;

		writeOutput("Enter video title to rent: ");
		String videoTitle = readInput();

		Video foundVideo = null ;
		for ( Video video: videos ) {
			if ( video.getTitle().equals(videoTitle) && video.isRented() == false ) {
				foundVideo = video ;
				break ;
			}
		}

		if ( foundVideo == null ) return ;

		Rental rental = new Rental(foundVideo) ;
		foundVideo.setRented(true);

		List<Rental> customerRentals = foundCustomer.getRentals() ;
		customerRentals.add(rental);
		foundCustomer.setRentals(customerRentals);
	}

	public void register(String object) {
		if ( object.equals("customer") ) {
			writeOutput("Enter customer name: ");
			String name = readInput();
			Customer customer = new Customer(name) ;
			customers.add(customer) ;
		}
		else {
			writeOutput("Enter video title to register: ");
			String title = readInput();

			writeOutput("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
			int videoType = scanner.nextInt();

			writeOutput("Enter price code( 1 for Regular, 2 for New Release ):");
			int priceCode = scanner.nextInt();

			Date registeredDate = new Date();
			Video video = new Video(title, videoType, priceCode, registeredDate) ;
			videos.add(video) ;
		}
	}

	public int showCommand() {
		writeOutput("\nSelect a command !");
		writeOutput("\t 0. Quit");
		writeOutput("\t 1. List customers");
		writeOutput("\t 2. List videos");
		writeOutput("\t 3. Register customer");
		writeOutput("\t 4. Register video");
		writeOutput("\t 5. Rent video");
		writeOutput("\t 6. Return video");
		writeOutput("\t 7. Show customer report");
		writeOutput("\t 8. Show customer and clear rentals");

		int command = scanner.nextInt() ;

		return command ;

	}
}
