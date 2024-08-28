package BankManagementSystem.src;

import java.sql.ResultSet;
import java.sql.SQLException;

class OnlineTransaction extends Bank {
    int maxTry;

    protected OnlineTransaction() throws SQLException {
        super();
    }

    /**
     * transfers fund from one account to another
     */
    void transfer(String src_id, int PIN) throws Exception {
        if (!idExists(src_id)) {
            System.out.println("Id Not Exist!!!");
            return;
        }
        String sql = "SELECT ID FROM account_balance WHERE ID=? AND PIN=?";
        pst = con.prepareStatement(sql);
        pst.setString(1, src_id);
        pst.setInt(2, PIN);
        ResultSet rs = pst.executeQuery();
        String d = null;
        while (rs.next()) {
            d = rs.getString("ID");
        }
        if (d != null && maxTry < 3) {
            System.out.print("Enter ID of Transfer Account : ");
            String dst_id = sc.nextLine();
            if (idExists(dst_id)) {
                System.out.print("Enter Amount you want withdraw : ");
                double w = sc.nextDouble();
                sc.nextLine();
                double t = withdraw(src_id, w, "Online");
                deposit(dst_id, t, "Online");
            }
        } else {
            System.out.println("ID or PIN is Invalid!!");
        }
    }
}