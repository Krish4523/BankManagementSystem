package BankManagementSystem.src;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;

class InstantCash extends Bank {
    private int ic_id;
    protected double ic_amount, interest, payable_amount;
    protected final double rate = 2.5;

    protected InstantCash() throws SQLException {
        super();
    }

    /**
     * Tries to approve Instant Cash for account Holder of given acc_id
     */
    protected void getIC(String acc_id) throws SQLException {
        if (idExists(acc_id) && !instantCashExists(acc_id)) {
            double maxAllowedIC = getBalance(acc_id) * 0.6;
            System.out.println("Max Instant Cash : " + maxAllowedIC);
            System.out.print("Enter Amount You Want : ");
            try {
                ic_amount = sc.nextDouble();
                sc.nextLine();
                if (ic_amount > maxAllowedIC) {
                    System.out.println("You are not allowed to get Instant Cash");
                    return;
                }
                System.out.println("Interest Rate is : " + rate + " Per Month");
                System.out.print("Enter Duration in month : ");
                double duration = sc.nextDouble();
                sc.nextLine();
                interest = ((ic_amount * rate * duration) / 100);
                payable_amount = ic_amount + interest;
                System.out.println("Total Amount to Pay is : " + payable_amount);

                System.out.println("Enter \n1. Accept\n2. Reject");
                int i = sc.nextInt();
                sc.nextLine();
                if (i == 1) {
                    while (true) {
                        ic_id = (int) (1000 * Math.random());
                        Statement s = con.createStatement();
                        ResultSet rs = s.executeQuery("SELECT * FROM instantcash WHERE ic_id=" + ic_id);
                        if (!rs.next()) {
                            break;
                        }
                    }

                    deposit(acc_id, ic_amount, "Instant Cash Approval");
                    pst = con.prepareStatement("INSERT INTO instantcash VALUES (?,?,?,?)");
                    pst.setInt(1, ic_id);
                    pst.setString(2, acc_id);
                    pst.setDouble(3, ic_amount);
                    pst.setDouble(4, payable_amount);
                    pst.executeUpdate();
                    System.out.println("Instant Cash Approved successfully");
                    System.out.println("ic_ID : " + ic_id + "\nAccount ID : " + acc_id + "\nAmount : " + ic_amount + "\nPayable Amount : " + payable_amount);

                } else {
                    System.out.println("Thanks to visit");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Enter Valid Number!!!");
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Error In Get Instant Cash!!!");
            }
        } else {
            if (instantCashExists(acc_id)) {
                System.out.println("Instant Cash already taken on this account.\nFirst pay existing IC!!");
            } else {
                System.out.println("Sorry!!!,Try Again ID not exist!!!");
            }
        }
    }

    /**
     * @return boolean value that instant cash loan for acc_id exists or not
     */
    private boolean instantCashExists(String acc_id) throws SQLException {
        String sql = "SELECT ic_id FROM instantcash WHERE acc_id=?";
        pst = con.prepareStatement(sql);
        pst.setString(1, acc_id);
        ResultSet rs = pst.executeQuery();
        String d = null;
        while (rs.next()) {
            d = rs.getString(1);
        }
        return d != null;
    }

    /**
     * get payment of IC of given loan_id
     */
    protected void payIC(String acc_id) throws SQLException {
        if (idExists(acc_id) && instantCashExists(acc_id)) {
            pst = con.prepareStatement("SELECT * FROM instantcash WHERE acc_id=?");
            pst.setString(1, acc_id);
            ResultSet rst = pst.executeQuery();
            // String acc_id = null;
            double pay_amount = 0;
            while (rst.next()) {
                ic_id = rst.getInt(1);
                pay_amount = rst.getDouble(4);
            }

            double amount = withdraw(acc_id, pay_amount, "Instant Cash Payed");
            if (amount != 0.0) {
                System.out.println("Instant Cash Payed SuccessFully!!!");
                PreparedStatement pst = con.prepareStatement("delete from instantcash where ic_id=?");
                pst.setInt(1, ic_id);
                pst.executeUpdate();
            } else {
                System.out.println("Not Valid Balance In Your Account!!!");
            }
        } else {
            if (!instantCashExists(acc_id)) {
                System.out.println("No Instant Cash Exists for acc_id : " + acc_id);
            } else {
                System.out.println("Sorry, Try Again Acc_ID not exists!!!");
            }
        }
    }
}
