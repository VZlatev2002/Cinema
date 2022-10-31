
import java.util.Scanner;

import java.util.ArrayList;

class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int rows, seats;
        int[] price = new int[]{0};

        System.out.println("Enter the number of rows: ");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        seats = scanner.nextInt();
        double[] total = new double[]{rows * seats};
        char[][] cinema = makeCinema(rows, seats);
        ArrayList<ArrayList<Integer>> bought = new ArrayList<ArrayList<Integer>>();

        while (true) {
            options();
            int request = scanner.nextInt();
            if (request == 1) {
                displayCinema(cinema);
            } else if (request == 2) {
                buyTicket(rows,seats, bought, price, cinema);
            } else if (request == 3) {
                statistics(bought, price, total, rows, seats);
            } else if (request == 0) {
                break;
            }
        }
    }

    public static void buyTicket(int rows, int seats, ArrayList<ArrayList<Integer>> bought, int[] price, char[][] cinema) {
        System.out.println("\nEnter a row number: ");
        int row = scanner.nextInt();
        System.out.println("Enter a seat number in that row: ");
        int seat = scanner.nextInt();
        ArrayList<Integer> target = new ArrayList<>();
        target.add(row);
        target.add(seat);
        while (true) {
            if (bought.contains(target)) {
                System.out.println("That ticket has already been purchased!");
                buyTicket(rows, seats, bought, price, cinema);
                break;
            } else if (row > rows || seat > seats) {
                System.out.println("Wrong input!");
                buyTicket(rows, seats, bought, price, cinema);
            } else {
                bought.add(target);
                cinema[row][seat] = 'B';
                System.out.println("\nTicket price: $" + getPrice(row, rows, seats));
                price[0] += getPrice(row, rows, seats);
                break;
            }
        }


    }
    public static void statistics(ArrayList<ArrayList<Integer>> bought, int[] price, double[] total, int rows, int seats) {
        System.out.printf("Number of purchased tickets %d \n", bought.size());
        System.out.printf("Percentage %.2f%% \n", (bought.size()/total[0])*100);
        System.out.printf("Current income $%d \n", price[0]);
        System.out.printf("Total income: $%d \n", totalIncome(rows, seats));

    }

    public static void options() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public static char[][] makeCinema(int rows, int seats) {
        char[][] cinema = new char[rows + 1][seats + 1];

        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                if (i == 0 && j != 0) {
                    cinema[i][j] = Integer.toString(j).charAt(0);
                } else if (i > 0 && j == 0) {
                    cinema[i][j] = Integer.toString(i).charAt(0);
                } else if (i > 0) {
                    cinema[i][j] = 'S';
                } else {
                    cinema[i][j] = ' ';
                }
            }
        }

        return cinema;
    }

    public static void displayCinema(char[][] cinema) {
        System.out.println("\nCinema:");
        for (char[] chars : cinema) {
            for (int j = 0; j < chars.length; j++) {
                System.out.print(chars[j] + " ");
                if (j == chars.length - 1) {
                    System.out.print("\n");
                }
            }
        }
    }

    public static int getPrice(int row, int rows, int seats) {
        int price;
        if (rows * seats < 60) {
            price = 10;
        } else {
            if (row <= (rows - rows % 2) / 2) {
                price = 10;
            } else {
                price = 8;
            }
        }
        return price;
    }

    public static int totalIncome(int rows, int seats) {
        int totalIncome = 0;
        if ( rows * seats < 60) {
            totalIncome = 10 * rows * seats;
        } else {
            totalIncome += (10 * (rows - rows % 2)/2 + 8 * ( rows / 2 + 1))*seats;
        }
        return totalIncome;
    }

}
