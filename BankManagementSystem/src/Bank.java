package BankManagementSystem.src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class Bank {
    PreparedStatement pst;
    static Connection con;
    static Scanner sc = new Scanner(System.in);
    // According to your Project location
    String projectPath = "C:\\Users\\KRISH CHAUHAN\\BMS";

    /**
     * default constructor for connection with database
     */
    public Bank() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
    }

    /**
     * Creates New Account in the bank
     */
    protected void createAccount() throws IOException, SQLException {
        // take details to create new account
        System.out.print("Enter Your First Name : ");
        String first_name = sc.nextLine();
        System.out.print("Enter Your last Name : ");
        String last_name = sc.nextLine();
        System.out.print("Enter Account Type: ");
        String account_type = sc.nextLine();
        String mob_no;
        while (true) {
            System.out.println("Enter Your Mobile Number : ");
            mob_no = sc.nextLine();
            int flag = 0;
            if (mob_no.length() == 10 && mob_no.charAt(0) <= '9' && mob_no.charAt(0) >= '7') {
                for (int i = 0; i < mob_no.length(); i++) {
                    if (!(mob_no.charAt(i) >= '0' && mob_no.charAt(i) <= '9')) {
                        flag = -1;
                        break;
                    }
                }
                if (flag == 0) {
                    System.out.println("Mobile Number is Valid!!!");
                    break;
                }
            } else {
                if (mob_no.length() != 10) {
                    System.out.println("Mobile Number must be 10 digits!!!");
                } else {
                    System.out.println("Mobile Number Starts with 9,8,7");
                }
                // System.out.println("Mobile number must start with 9,8,7 having 10 digits");
            }
        }
        System.out.print("Enter Your Age : ");
        int age = 0;
        try {
            age = sc.nextInt();
        } catch (Exception ignored) {
        }
        if (age < 10) {
            System.out.println("minimum Age Required is 10!!!");
            return;
        }
        sc.nextLine();
        System.out.print("Enter your Address : ");
        String address = sc.nextLine();
        // create new account
        new Account(first_name, last_name, account_type, age, mob_no, address);
    }

    /**
     * To insert details of account in the database
     */
    protected void insert(String ID, String Name, int PIN) throws SQLException {
        String sql = "INSERT INTO account_balance VALUES (?,?,?,?)";
        pst = con.prepareStatement(sql);
        pst.setString(1, Name);
        pst.setString(2, ID);
        pst.setInt(3, PIN);
        pst.setDouble(4, 0);
        pst.executeUpdate();
    }

    /**
     * Updates the balance of account having ID
     */
    private void setBalance(String ID, double balance) throws SQLException {
        String sql = "UPDATE account_balance SET balance= ? WHERE ID= ?";
        pst = con.prepareStatement(sql);
        pst.setDouble(1, balance);
        pst.setString(2, ID);
        pst.executeUpdate();
    }

    /**
     * @return balance in account of particular ID
     */
    protected double getBalance(String ID) throws SQLException {
        String sql = "SELECT balance FROM account_balance WHERE ID=?";
        pst = con.prepareStatement(sql);
        pst.setString(1, ID);
        ResultSet rs = pst.executeQuery();
        double d = 0;
        while (rs.next()) {
            d = rs.getDouble("balance");
        }
        return d;
    }

    /**
     * @return boolean value that account having ID exists in the database or not
     */
    protected boolean idExists(String ID) throws SQLException {
        String sql = "SELECT ID FROM account_balance WHERE ID=?";
        pst = con.prepareStatement(sql);
        pst.setString(1, ID);
        ResultSet rs = pst.executeQuery();
        String d = null;
        while (rs.next()) {
            d = rs.getString("ID");
        }
        return d != null;
    }

    /**
     * Withdraws given amount from account of given ID
     * @return withdraw amount
     */
    protected double withdraw(String ID, double amount, String medium) throws SQLException {
        if (idExists(ID)) {
            double balance = getBalance(ID);
            double newBalance = balance - amount;
            if (newBalance > 1000) {
                try {
                    writeToPassBook(ID, medium, "WithDraw", amount, newBalance);
                } catch (IOException ignored) {}
                setBalance(ID, newBalance);
                System.out.println(amount + " Withdrawn Successful");
                return amount;
            } else {
                System.out.println("Not Enough Balance!!!!");
            }
        } else {
            System.out.println("Invalid ID!!!!");
        }
        return 0.0;
    }

    /**
     * Deposits given amount in account of given ID
     */
    protected void deposit(String ID, double amount, String medium) throws IOException, SQLException {
        if (idExists(ID)) {
            double balance = getBalance(ID);
            double newBalance = balance + amount;
            writeToPassBook(ID, medium, "Deposit", amount, newBalance);
            setBalance(ID, newBalance);
            System.out.println(amount + " Deposited Successful");
        } else {
            System.out.println("Not Valid ID!!!!");
        }
    }

    /**
     * Writes status of Account of given ID in txt file
     */
    protected void writeToPassBook(String ID, String medium, String type, double amount, double balance) throws IOException {
        String folderPath = projectPath + "\\" + ID;
        File f = new File(folderPath, ID + ".txt");
        PrintWriter pw = new PrintWriter(new FileWriter(f, true), true);
        pw.printf("%-8s\t\t\t%-10.2f\t\t\t%-10.2f\t\t%-20s\n", type, amount, balance, medium);
        pw.close();
    }

    protected void readPassBook(String ID) throws IOException, SQLException {
        if (idExists(ID)) {
            String folderPath = projectPath + "\\" + ID;
            File f = new File(folderPath, ID + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } else {
            System.out.println("Invalid ID !!!");
        }
    }
}
