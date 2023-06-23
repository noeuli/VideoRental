import java.util.List;
import java.util.Scanner;

public class VRUI {
	private static Scanner scanner = new Scanner(System.in) ;
	private VideoRentalControl videoRentalControl = new VideoRentalControl();

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
		String name = readInput();

		videoRentalControl.clearCustomerRental(name);
	}

	public void returnVideo() {
		writeOutput("Enter customer name: ");
		String customerName = readInput();

		writeOutput("Enter video title to return: ");
		String videoTitle = readInput();

		videoRentalControl.returnVideo(customerName, videoTitle);
	}

	private void init() {
		videoRentalControl.init();
	}

	public void listVideos() {
		writeOutput("List of videos");

		List<Video> videos = videoRentalControl.getVideoList();
		for (Video video : videos) {
			writeOutput("Price code: " + video.getPriceCode() + "\tTitle: " + video.getTitle());
		}
		writeOutput("End of list");
	}

	public static void writeOutput(String List_of_videos) {
		System.out.println(List_of_videos);
	}

	public void listCustomers() {
		videoRentalControl.listCustomers();
	}

	public void getCustomerReport() {
		String customerName = readInput();
		videoRentalControl.getCustomerReport(customerName);
	}

	public static String readInput() {
		return scanner.next();
	}

	public void rentVideo() {
		VRUI.writeOutput("Enter customer name: ");
		String customerName = readInput();

		VRUI.writeOutput("Enter video title to rent: ");
		String videoTitle = readInput();

		videoRentalControl.rentVideo(customerName, videoTitle);
	}

	public void register(String object) {
		if ( object.equals("customer") ) {
			writeOutput("Enter customer name: ") ;
			String name = readInput();
			videoRentalControl.registerCustomer(name);
		}
		else {
			writeOutput("Enter video title to register: "); ;
			String title = readInput();

			writeOutput("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):") ;
			int videoType = readNum();

			writeOutput("Enter price code( 1 for Regular, 2 for New Release ):") ;
			int priceCode = readNum();

			videoRentalControl.registerVideo(title, videoType, priceCode);
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

		int command = readNum();

		return command ;

	}

	public static int readNum() {
		return scanner.nextInt();
	}
}
