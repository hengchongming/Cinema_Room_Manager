package cinema;

import java.util.Scanner;

public class Cinema {

    public static final int TICKET_SMALL = 10;
    public static final int TICKET_LARGE_FRONT = 10;
    public static final int TICKET_LARGE_BACK = 8;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        String input = scanner.nextLine();
        int numberOfRows = Integer.parseInt(input);
        System.out.println("Enter the number of seats in each row:");
        input = scanner.nextLine();
        int numberOfSeats = Integer.parseInt(input);

        char[][] cinemaSeats = new char[numberOfRows][numberOfSeats];
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfSeats; j++) {
                cinemaSeats[i][j] = 'S';
            }
        }

        EXIT:
        while (true) {
            System.out.println();
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println(("3. Statistics"));
            System.out.println("0. Exit");
            String choice = scanner.nextLine();

            switch(choice) {
                case "1":
                    System.out.println();
                    displayCinemaLayout(cinemaSeats);
                    break;
                case "2":
                    int seatPrice = -1;
                    while (seatPrice < 0) {
                        System.out.println();
                        System.out.println("Enter a row number:");
                        input = scanner.nextLine();
                        int rowNo = Integer.parseInt(input);
                        System.out.println("Enter a seat number in that row:");
                        input = scanner.nextLine();
                        int seatNo = Integer.parseInt(input);

                        if (rowNo > cinemaSeats.length || seatNo > cinemaSeats[0].length) {
                            System.out.println();
                            System.out.println("Wrong input!");
                            continue;
                        }

                        seatPrice = bookSeat(cinemaSeats, rowNo, seatNo);
                        if (seatPrice == -1) {
                            System.out.println();
                            System.out.println("That ticket has already been purchased!");
                            continue;
                        }

                        System.out.printf("\nTicket price: $%d\n", seatPrice);
                    }
                    break;
                case "3":
                    showStatistics(cinemaSeats);
                    break;
                case "0":
                    break EXIT;
                default:
                    System.out.println("Invalid menu option!");
                    System.exit(1);
            }
        }
    }

    private static void showStatistics(char[][] cinemaSeats) {
        int frontRowTickets = 0;
        int backRowTickets = 0;
        int totalRow = cinemaSeats.length;
        int totalSeatsPerRow = cinemaSeats[0].length;
        int numberOfFrontRows = totalRow / 2;
        int numberOfBackRows = totalRow - numberOfFrontRows;
        for (int i = 0; i < totalRow; i++) {
            for (int j = 0; j < totalSeatsPerRow; j++) {
                if (cinemaSeats[i][j] == 'B') {
                    if ( i + 1 <= totalRow / 2) {
                        frontRowTickets++;
                    } else {
                        backRowTickets++;
                    }
                }
            }
        }

        int purchasedTickets = frontRowTickets + backRowTickets;
        int totalTickets = totalRow * totalSeatsPerRow;

        boolean isSmallCinema = false;
        if (totalTickets <= 60) {
            isSmallCinema = true;
        }

        double percentPurchased = (double) purchasedTickets / totalTickets;

        int currentIncome;
        if (isSmallCinema) {
            currentIncome = frontRowTickets * TICKET_SMALL + backRowTickets * TICKET_SMALL;
        } else {
            currentIncome = frontRowTickets * TICKET_LARGE_FRONT + backRowTickets * TICKET_LARGE_BACK;
        }

        int totalIncome;
        if (isSmallCinema) {
            totalIncome = totalRow * totalSeatsPerRow * TICKET_SMALL;
        } else {
            totalIncome = numberOfFrontRows * totalSeatsPerRow * TICKET_LARGE_FRONT
                    + numberOfBackRows * totalSeatsPerRow * TICKET_LARGE_BACK;
        }

        System.out.println();
        System.out.printf("Number of purchased tickets: %d\n", purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", percentPurchased * 100);
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n", totalIncome);

    }

    private static int bookSeat(char[][] cinemaSeats, int rowNo, int seatNo) {
        if (cinemaSeats[rowNo -1][seatNo - 1] == 'B') {
            return -1;
        }

        int seatPrice;
        int totalSeats = cinemaSeats.length * cinemaSeats[0].length;
        if (totalSeats <= 60) {
            seatPrice = TICKET_SMALL;
        } else {
            int frontRows = cinemaSeats.length / 2;
            if (rowNo <= frontRows) {
                seatPrice = TICKET_LARGE_FRONT;
            } else {
                seatPrice = TICKET_LARGE_BACK;
            }
        }
        cinemaSeats[rowNo -1][seatNo - 1] = 'B';
        return seatPrice;
    }

    private static void displayCinemaLayout(char[][] cinemaSeats) {
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 0; i < cinemaSeats[1].length; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();

        for (int i = 0; i < cinemaSeats.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < cinemaSeats[i].length; j++){
                System.out.print(cinemaSeats[i][j] + " ");
            }
            System.out.println();
        }
    }
}