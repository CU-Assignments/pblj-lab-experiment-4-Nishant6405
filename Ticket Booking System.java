import java.util.concurrent.locks.ReentrantLock;

class TicketBookingSystem {
    private boolean[] seats;
    private final ReentrantLock lock = new ReentrantLock();

    public TicketBookingSystem(int numSeats) {
        seats = new boolean[numSeats];
    }

    public synchronized void bookSeat(int seatNumber, String userName, boolean isVIP) {
        if (seatNumber < 1 || seatNumber > seats.length) {
            System.out.println(userName + ": Invalid seat number!");
            return;
        }

        if (seats[seatNumber - 1]) {
            System.out.println(userName + ": Seat " + seatNumber + " is already booked!");
        } else {
            seats[seatNumber - 1] = true;
            System.out.println(userName + " booked seat " + seatNumber);
        }
    }
}

class User extends Thread {
    private TicketBookingSystem bookingSystem;
    private int seatNumber;
    private String userName;
    private boolean isVIP;

    public User(TicketBookingSystem system, int seatNumber, String userName, boolean isVIP) {
        this.bookingSystem = system;
        this.seatNumber = seatNumber;
        this.userName = userName;
        this.isVIP = isVIP;
    }

    @Override
    public void run() {
        bookingSystem.bookSeat(seatNumber, userName, isVIP);
    }
}

public class TicketBookingApp {
    public static void main(String[] args) {
        int numSeats = 5;
        TicketBookingSystem system = new TicketBookingSystem(numSeats);

        User u1 = new User(system, 1, "Anish (VIP)", true);
        User u2 = new User(system, 2, "Bobby (Regular)", false);
        User u3 = new User(system, 3, "Charlie (VIP)", true);
        User u4 = new User(system, 4, "Anish (VIP)", true);
        User u5 = new User(system, 4, "Bobby (Regular)", false);
        User u6 = new User(system, 6, "David (Regular)", false);
        User u7 = new User(system, 0, "Eve (Regular)", false);

        u1.setPriority(Thread.MAX_PRIORITY);
        u2.setPriority(Thread.NORM_PRIORITY);
        u3.setPriority(Thread.MAX_PRIORITY);
        u4.setPriority(Thread.MAX_PRIORITY);
        u5.setPriority(Thread.NORM_PRIORITY);
        u6.setPriority(Thread.NORM_PRIORITY);
        u7.setPriority(Thread.NORM_PRIORITY);

        u1.start();
        u2.start();
        u3.start();
        u4.start();
        u5.start();
        u6.start();
        u7.start();
    }
}
