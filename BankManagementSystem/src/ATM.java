package BankManagementSystem.src;

import java.sql.ResultSet;
import java.sql.SQLException;

class ATM extends Bank {
    int maxTry = 0;

    protected ATM() throws SQLException {
        super();
    }

    /**
     * Withdraws amount from ATM using acc_ID and PIN
     */
    void withdraw(String ID, int PIN) throws Exception {
        String sql = "SELECT ID FROM account_balance WHERE ID=? AND PIN=?";
        pst = con.prepareStatement(sql);
        pst.setString(1, ID);
        pst.setInt(2, PIN);
        ResultSet rs = pst.executeQuery();
        String id = null;
        while (rs.next()) {
            id = rs.getString("ID");
        }
        if (id != null && maxTry < 3) {
            System.out.print("Enter Amount you want withdraw : ");
            double amount = sc.nextDouble();
            sc.nextLine();
            super.withdraw(ID, amount, "ATM");
        } else {
            maxTry++;
            if (maxTry >= 3) {
                System.out.println("Maximum Try Reached");
                BMS.main(null);
            } else {
                System.out.println("ID or PIN is Invalid!!");
            }
        }
    }
}
