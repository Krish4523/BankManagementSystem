package BankManagementSystem.src;

import java.util.Scanner;

public class BMS {
	static Scanner sc = new Scanner(System.in);

	/**
	 * To run the program of Bank Management System
	 */
	public static void main(String[] args) {
		int i = 0, PIN;
		String ID;
		boolean bank_loop, loop, mainLoop = true;
		System.out.println("Welcome to BANK");
		while (mainLoop) {
			bank_loop = true;
			loop = true;
			System.out.println("""
					\nEnter What you want to do?
					1 - BANK
					2 - ATM
					3 - ONLINE TRANSFER
					4 - Instant Cash
					5 - EXIT
					""");
			System.out.print("Choice : ");
			try {
				int x = Integer.parseInt(sc.nextLine());
				switch (x) {
					case 1:
						Bank bank = new Bank();
						while (bank_loop) {
							System.out.println("1. Add Account \n2. Withdraw\n3. Deposit\n4. Read PassBook\n5. Exit");
							System.out.print("Enter Choice : ");
							try {
								i = sc.nextInt();
							} catch (Exception ignored) {
							}
							sc.nextLine();
							switch (i) {
								case 1:
									bank.createAccount();
									break;
								case 2:
									System.out.print("Enter Account ID: ");
									ID = sc.next();
									System.out.print("Enter Amount you want withdraw : ");
									double w;
									try {
										w = sc.nextDouble();
										sc.nextLine();
										bank.withdraw(ID, w, "Cash");
									} catch (Exception e) {
										sc.nextLine();
										System.out.println("Enter Number!!!");
									}
									break;
								case 3:
									System.out.print("Enter Account ID: ");
									ID = sc.nextLine();
									System.out.print("Enter amount to deposit: ");
									double d;
									try {
										d = sc.nextDouble();
										sc.nextLine();
										bank.deposit(ID, d, "Cash");
									} catch (Exception e) {
										sc.nextLine();
										System.out.println("Enter Number!!!");
									}
									break;
								case 4:
									System.out.print("Enter Account ID: ");
									ID = sc.nextLine();
									bank.readPassBook(ID);
									break;
								case 5:
									bank_loop = false;
									break;
								default:
									System.out.println("Enter Valid Number");
									break;
							}
						}
						break;
					case 2:
						ATM atm = new ATM();
						while (loop) {
							System.out.println("1. Withdraw Amount\n2. exit");
							System.out.print("Enter Choice : ");
							try {
								i = sc.nextInt();
							} catch (Exception e) {
								// System.out.println("Please enter Integer Choice");
							}
							sc.nextLine();
							switch (i) {
								case 1:
									System.out.print("Enter Account ID: ");
									ID = sc.nextLine();
									System.out.print("Enter PIN : ");
									try {
										PIN = sc.nextInt();
										sc.nextLine();
										atm.withdraw(ID, PIN);
									} catch (Exception e) {
										sc.nextLine();
										System.out.println("Enter PIN in Integer!!!");
									}

									break;
								case 2:
									loop = false;
									break;
								default:
									System.out.println("Please enter valid choice");
									break;
							}
						}
						break;
					case 3:
						OnlineTransaction a1 = new OnlineTransaction();
						while (loop) {
							System.out.println("1. Transfer\n2. exit");
							System.out.print("Enter Choice : ");
							try {
								i = sc.nextInt();
							} catch (Exception e) {
								// System.out.println("Please enter Integer Choice");
							}
							sc.nextLine();
							switch (i) {
								case 1:
									System.out.print("Enter Account ID: ");
									ID = sc.nextLine();
									System.out.print("Enter PIN : ");
									try {
										PIN = sc.nextInt();
										sc.nextLine();
										a1.transfer(ID, PIN);
									} catch (Exception e) {
										sc.nextLine();
										System.out.println("Enter PIN in Integer!!!");
									}
									break;
								case 2:
									loop = false;
									break;
								default:
									System.out.println("Please enter valid choice");
									break;
							}
						}
						break;

					case 4:
						InstantCash ic = new InstantCash();
						System.out.println("Enter \n1. Apply for Instant Cash\n2. Pay Instant Cash");
						System.out.print("Enter Choice : ");
						try {
							i = sc.nextInt();
						} catch (Exception ignored) {

						}
						sc.nextLine();
						switch (i) {
							case 1:
								System.out.print("Enter Account ID : ");
								String Id = sc.nextLine();
								ic.getIC(Id);
								break;

							case 2:
								System.out.print("Enter Account ID to PAY : ");
								try {
									String acc_id = sc.nextLine();
									ic.payIC(acc_id);
								} catch (Exception e) {
									sc.nextLine();
									System.out.println("Enter Cash ID in Integer!!!");
								}
								break;

							default:
								System.out.println("Enter Valid Choice!!!");
								break;
						}
						break;

					case 5:
						mainLoop = false;
						System.exit(0);
					default:
						System.out.println("Enter valid choice!!");
						break;
				}
			} catch (Exception e) {
				System.out.println("Invalid Choice!!!");
			}
		}
	}
}
