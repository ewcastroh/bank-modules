package com.ewch.modules.application;

import com.ewch.modules.jmp.bank.api.Bank;
import com.ewch.modules.jmp.cloud.bank.impl.BankImpl;
import com.ewch.modules.jmp.cloud.service.impl.CloudServiceImpl;
import com.ewch.modules.jmp.cloud.service.impl.database.DBConfig;
import com.ewch.modules.jmp.dto.BankCard;
import com.ewch.modules.jmp.dto.BankCardType;
import com.ewch.modules.jmp.dto.Subscription;
import com.ewch.modules.jmp.dto.User;
import com.ewch.modules.jmp.service.api.CloudService;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static CloudService cloudService = new CloudServiceImpl(new DBConfig());
    private static Bank bank = new BankImpl();

    private static void getAllUsers() {
        cloudService.getAllUsers().forEach(System.out::println);
    }

    private static void getAllBankCards() {
        cloudService.getAllBankCards().forEach(System.out::println);
    }

    private static void getAllSubscriptions() {
        cloudService.getAllSubscriptions().forEach(System.out::println);
    }

    private static void getSubscriptionByBankCardNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type bank Card Number to get subscription: ");
        String bankCardNumber = scanner.nextLine();
        System.out.println(cloudService.getSubscriptionByBankCardNumber(bankCardNumber));;
    }

    private static User createUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name of new user: ");
        String name = scanner.nextLine();
        System.out.print("Surname of new user: ");
        String surname = scanner.nextLine();
        System.out.print("Birthday of new user: ");
        String birthday = scanner.nextLine();
        User user = new User(name, surname, LocalDate.parse(birthday));
        cloudService.createUser(user);
        return user;
    }

    private static void createNewBankCard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Fill new user's bank card:");
        User user = createUser();
        System.out.println("Select type of bank card (Default DEBIT card):");
        System.out.println("1. CREDIT");
        System.out.println("2. DEBIT");
        BankCard newBankCard;
        int bankCardType = scanner.nextInt();
        if (bankCardType == 1) {
            newBankCard = bank.createBankCard(user, BankCardType.CREDIT);
        } else {
            newBankCard = bank.createBankCard(user, BankCardType.DEBIT);
        }
        cloudService.createBankCard(newBankCard);
    }

    private static void createSubscription() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Fill bank card number: ");
        String bankcard = scanner.nextLine();
        System.out.print("Fill start date(YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        Subscription subscription = new Subscription(bankcard, LocalDate.parse(startDate));
        cloudService.createSubscription(subscription);
    }

    private static void displayMenu() {

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        int option;
        System.out.println("### Welcome to Bank System ###");
        while (!exit) {
            System.out.println("Select desired action:");
            System.out.println("1. Create a new bank card.");
            System.out.println("2. Create a new subscription.");
            System.out.println("3. Get all users.");
            System.out.println("4. Get all bank cards.");
            System.out.println("5. Get all subscriptions.");
            System.out.println("6. Get subscription by bank card number.");
            System.out.println("0. Exit.");

            try {
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        createNewBankCard();
                        break;
                    case 2:
                        createSubscription();
                        break;
                    case 3:
                        getAllUsers();
                        break;
                    case 4:
                        getAllBankCards();
                        break;
                    case 5:
                        getAllSubscriptions();
                        break;
                    case 6:
                        getSubscriptionByBankCardNumber();
                        break;
                    case 0:
                        System.out.println("Thanks for using our system.");
                        exit = true;
                        break;
                    default:
                        System.out.println("Type just numbers.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Type a valid number.");
                scanner.next();
            }
        }
    }

    public static void main(String[] args) {
        DBConfig.init();
        displayMenu();
    }

}