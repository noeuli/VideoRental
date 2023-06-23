import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoRentalControl {
    List<Customer> customers = new ArrayList<Customer>();
    List<Video> videos = new ArrayList<Video>();

    public VideoRentalControl() {
    }

    public void clearCustomerRental(String customerName) {
        Customer foundCustomer = null;
        foundCustomer = findCustomer(customerName);

        if (foundCustomer == null) {
            VRUI.writeOutput("No customer found");
        } else {
            VRUI.writeOutput("Name: " + foundCustomer.getName() +
                    "\tRentals: " + foundCustomer.getRentals().size());
            for (Rental rental : foundCustomer.getRentals()) {
                System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
                System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
            }

            List<Rental> rentals = new ArrayList<Rental>();
            foundCustomer.setRentals(rentals);
        }
    }

    Customer findCustomer(String customerName) {
        for (Customer customer : customers) {
            if (customer.getName().equals(customerName)) {
                return customer;
            }
        }
        return null;
    }

    public void returnVideo(String customerName, String videoTitle) {
        Customer foundCustomer = null;
        foundCustomer = findCustomer(customerName);
        if (foundCustomer == null) return;

        List<Rental> customerRentals = foundCustomer.getRentals();
        for (Rental rental : customerRentals) {
            if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                break;
            }
        }
    }

    void init() {
        Customer james = new Customer("James");
        Customer brown = new Customer("Brown");
        customers.add(james);
        customers.add(brown);

        Video v1 = new Video("v1", Video.CD, Video.REGULAR, new Date());
        Video v2 = new Video("v2", Video.DVD, Video.NEW_RELEASE, new Date());
        videos.add(v1);
        videos.add(v2);

        Rental r1 = new Rental(v1);
        Rental r2 = new Rental(v2);

        james.addRental(r1);
        james.addRental(r2);
    }

    public void listVideos() {
        VRUI.writeOutput("List of videos");

        for (Video video : videos) {
            VRUI.writeOutput("Price code: " + video.getPriceCode() + "\tTitle: " + video.getTitle());
        }
        VRUI.writeOutput("End of list");
    }

    public void listCustomers() {
        VRUI.writeOutput("List of customers");
        for (Customer customer : customers) {
            VRUI.writeOutput("Name: " + customer.getName() +
                    "\tRentals: " + customer.getRentals().size());
            for (Rental rental : customer.getRentals()) {
                System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
                System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
            }
        }
        VRUI.writeOutput("End of list");
    }

    public void getCustomerReport(String customerName) {
        VRUI.writeOutput("Enter customer name: ");

        Customer foundCustomer = null;
        foundCustomer = findCustomer(customerName);

        if (foundCustomer == null) {
            VRUI.writeOutput("No customer found");
        } else {
            String result = foundCustomer.getReport();
            VRUI.writeOutput(result);
        }
    }

    public void rentVideo(String customerName, String videoTitle) {

        Customer foundCustomer = null;
        foundCustomer = findCustomer(customerName);
        if (foundCustomer == null) return;

        Video foundVideo = null;
        foundVideo = findVideo(videoTitle, foundVideo);
        if (foundVideo == null) return;

        Rental rental = new Rental(foundVideo);
        foundVideo.setRented(true);

        List<Rental> customerRentals = foundCustomer.getRentals();
        customerRentals.add(rental);
        foundCustomer.setRentals(customerRentals);
    }

    private Video findVideo(String videoTitle, Video foundVideo) {
        for (Video video : videos) {
            if (video.getTitle().equals(videoTitle) && video.isRented() == false) {
                return video;
            }
        }
        return null;
    }

    void registerCustomer(String name) {
        Customer customer = new Customer(name) ;
        customers.add(customer) ;
    }

    void registerVideo(String title, int videoType, int priceCode) {

        Date registeredDate = new Date();
        Video video = new Video(title, videoType, priceCode, registeredDate) ;
        videos.add(video) ;
    }
}