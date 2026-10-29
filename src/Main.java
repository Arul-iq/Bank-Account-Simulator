import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Bank Account Simulator ===");
        String holderName = readRequiredText("Account holder name: ");
        BankAccount account = new BankAccount(holderName);
        boolean running = true;
        while (running) {
            printMenu();
            switch (readInt("Choose an option: ")) {
                case 1 -> account.deposit(readPositiveAmount("Deposit amount: "));
                case 2 -> account.withdraw(readPositiveAmount("Withdrawal amount: "));
                case 3 -> System.out.printf("Current balance: Rs. %.2f%n", account.getBalance());
                case 4 -> account.printTransactions();
                case 0 -> running = false;
                default -> System.out.println("Please choose a valid option.");
            }
        }
        System.out.println("Thank you for using the simulator, " + holderName + "!");
    }

    private static void printMenu() { System.out.println("\n1. Deposit\n2. Withdraw\n3. Check balance\n4. View transactions\n0. Exit"); }

    private static int readInt(String prompt) {
        while (true) { try { System.out.print(prompt); return Integer.parseInt(SCANNER.nextLine().trim()); } catch (NumberFormatException exception) { System.out.println("Enter a whole number."); } }
    }

    private static double readPositiveAmount(String prompt) {
        while (true) {
            try { System.out.print(prompt); double amount = Double.parseDouble(SCANNER.nextLine().trim()); if (amount > 0) return amount; System.out.println("Amount must be greater than zero."); }
            catch (NumberFormatException exception) { System.out.println("Enter a valid amount."); }
        }
    }

    private static String readRequiredText(String prompt) {
        while (true) { System.out.print(prompt); String value = SCANNER.nextLine().trim(); if (!value.isEmpty()) return value; System.out.println("This field cannot be empty."); }
    }

    private static class BankAccount {
        private final String holderName;
        private double balance;
        private final ArrayList<String> transactions = new ArrayList<>();

        private BankAccount(String holderName) { this.holderName = holderName; transactions.add("Account created for " + holderName); }

        private void deposit(double amount) {
            balance += amount; transactions.add(String.format("Deposited Rs. %.2f", amount));
            System.out.printf("Deposit successful. Balance: Rs. %.2f%n", balance);
        }

        private void withdraw(double amount) {
            if (amount > balance) { System.out.println("Withdrawal declined: insufficient balance."); return; }
            balance -= amount; transactions.add(String.format("Withdrew Rs. %.2f", amount));
            System.out.printf("Withdrawal successful. Balance: Rs. %.2f%n", balance);
        }

        private double getBalance() { return balance; }
        private void printTransactions() { System.out.println("\nTransaction history for " + holderName + ":"); transactions.forEach(System.out::println); }
    }
}
