package carsharing;

import java.util.Scanner;


public class UserInterface {

    private Scanner scanner;
    private Operations operations;

    public UserInterface() {
        operations = new Operations();
        scanner = new Scanner(System.in);
    }


    public void start() {

        while (true) {
            initialOptions();
            int command = Integer.parseInt(scanner.nextLine());
            if (command == 0) {
                break;
            }
            if (command == 1) {
                while (true) {
                    managerOptions();
                    int command1 = Integer.parseInt(scanner.nextLine());

                    if (command1 == 0) {
                        break;
                    }

                    if (command1 == 1) {
                        while (true) {
                            if (operations.companyListIsEmpty()) {
                                System.out.println("The company list is empty!");
                                break;
                            }

                            System.out.println("\nChoose the company:");
                            companiesList();
                            System.out.println("0. Back");

                            int company = Integer.parseInt(scanner.nextLine());
                            if (company == 0) {
                                break;
                            }

                            operations.showCompany(company);
                            while (true) {

                                companyOptions();
                                int command2 = Integer.parseInt(scanner.nextLine());
                                if (command2 == 0) {
                                    break;
                                }
                                if (command2 == 1) {

                                    if (operations.carListIsEmpty(company)) {
                                        System.out.println("\nThe car list is empty!\n");

                                    } else {
                                        System.out.println("\nCar list:");
                                        operations.listCars(company);
                                        System.out.println("");
                                    }

                                }
                                if (command2 == 2) {
                                    createCar(company);
                                }
                            }
                            break;
                        }
                    }

                    if (command1 == 2) {
                        createCompany();
                    }

                }

            }
            if (command == 2) {
                if (hasCustomerList()) {
                    int customer = Integer.parseInt(scanner.nextLine());
                    if (customer == 0) {
                        continue;
                    }
                    while (true) {
                        customerOptions();
                        int customerCommand = Integer.parseInt(scanner.nextLine());
                        if (customerCommand == 0) {
                            break;
                        }
                        if (customerCommand == 1) {
                            if (operations.alreadyRentedCar(customer)) {
                                System.out.println("\nYou've already rented a car!\n");
                                continue;
                            }

                            if (operations.companyListIsEmpty()) {
                                System.out.println("The company list is empty!");
                                continue;
                            }


                            while (true) {

                                System.out.println("\nChoose a company:");
                                companiesList();
                                System.out.println("0. Back");
                                int company = Integer.parseInt(scanner.nextLine());

                                if (company == 0) {
                                    break;
                                }

                                System.out.println("\nChoose a car:");
                                operations.showAvailableCars(company);
                                System.out.println("0. Back");


                                int car = Integer.parseInt(scanner.nextLine());
                                if (car == 0) {
                                    break;
                                }

                                operations.rentCar(customer, car);
                                System.out.println("You rented " + "'" + operations.getCarName(customer) + "'");
                                break;


                            }

                        }
                        if (customerCommand == 2) {
                            if (!operations.alreadyRentedCar(customer)) {
                                System.out.println("\nYou didn't rent a car!\n");
                            } else {
                                operations.returnRentedCar(customer);
                                System.out.println("\nYou've returned a rented car!\n");
                            }
                        }
                        if (customerCommand == 3) {
                            operations.showRentedCar(customer);
                        }
                    }
                }
            }
            if (command == 3) {
                createCustomer();
            }
        }
        operations.closeConnection();
    }

    void initialOptions() {
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
    }

    void createCustomer() {
        System.out.println("\nEnter the customer name:");
        this.operations.createCustomer(scanner.nextLine());
        System.out.println("The customer was added!\n");
    }

    boolean hasCustomerList() {
        if (operations.customerIsEmpty()) {
            System.out.println("\nThe customer list is empty!\n");
            return false;
        } else {
            System.out.println("\nCustomer list:");
            this.operations.listCustomers();
            System.out.println("0. Back");
            return true;
        }
    }

    void createCar(int company) {
        System.out.println("\nEnter the car name:");
        operations.createCar(scanner.nextLine(), company);
        System.out.println("The car was added!\n");
    }

    void managerOptions() {
        System.out.println("\n1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
    }

    void customerOptions() {
        System.out.println("\n1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
    }

    void companiesList() {
        this.operations.showCompanies();
    }


    void createCompany() {
        System.out.println("\nEnter the company name:");
        String name = scanner.nextLine();
        this.operations.addCompany(name);
        System.out.println("The company was created!");
    }

    void companyOptions() {
        System.out.println("1. Car list\n2. Create a car\n0. Back");
    }


}

