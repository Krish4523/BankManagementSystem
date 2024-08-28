package BankManagementSystem.src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class Account extends Bank {
    String name, ID, mob_no, account_type;
    private final String address;
    private final int age;
    protected int PIN;
    double balance;

    /**
     * parameterized constructor to fill up Account Details
     */
    public Account(String first_name, String last_name, String account_type, int age, String mob_no, String address) throws IOException, SQLException {
        this.name = first_name + " " + last_name;
        this.account_type = account_type;
        this.address = address;
        this.age = age;
        this.mob_no = mob_no;
        this.ID = first_name + (int) (Math.random() * 1000);
        this.balance = 0.0;
        while (true) {
            int randomPIN = (int) (Math.random() * 10000);
            String temp = randomPIN + "";
            if (temp.length() == 4) {
                this.PIN = randomPIN;
                break;
            }
        }
        File a = new File(projectPath, "BankDetails.txt");
        BufferedWriter bw1 = new BufferedWriter(new FileWriter(a, true));
        bw1.write(toString());
        bw1.newLine();
        bw1.close();

        File f1 = new File(projectPath, ID);
        //noinspection ResultOfMethodCallIgnored
        f1.mkdir();
        File f2 = new File(f1, ID + ".txt");
        //noinspection ResultOfMethodCallIgnored
        f2.createNewFile();
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(f2, true));
        bw2.write(toString());
        bw2.newLine();
        bw2.write("Cause\t\t\t\tAmount\t\t\t\tBalance\t\t\tMedium\n");
        bw2.write("--------------------------------------------------------------------\n");
        bw2.close();
        insert(ID, name, PIN);
        double amount = 0.0;
        while (amount < 1000) {
            System.out.print("Minimum Opening balance is 1000 : ");
            try {
                amount = sc.nextDouble();
            } catch (Exception e) {
                System.out.println("Enter Number!!!");
            }
            sc.nextLine();
        }
        deposit(ID, amount, "Opening");

        System.out.println("Account Has been Created");
        System.out.println(this);

    }

    /**
     * @return details of particular account
     * @Override
     */
    public String toString() {
        return  "ID       : " + ID + "\n" +
                "NAME     : " + name + "\n" +
                "ACC_TYPE : " + account_type + "\n" +
                "PIN      : " + PIN + "\n" +
                "AGE      : " + age + "\n" +
                "MOBILE   : " + mob_no + "\n" +
                "ADDRESS  : " + address + "\n";
    }
}
