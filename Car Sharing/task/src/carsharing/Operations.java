package carsharing;


import java.sql.*;
import java.util.ArrayList;

public class Operations {

    private Connection con;
    private ArrayList<Integer> cars;

    public Operations() {
        this.cars = new ArrayList<>();
        connect();
        createTableCompany();
        createTableCars();
        createTableCustomer();
    }


    public void connect() {
        try {
            Class.forName("org.h2.Driver");
            this.con = DriverManager.getConnection("jdbc:h2:./src/carsharing/db/carsharing");
            con.setAutoCommit(true);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTableCompany() {
        String create = "create table if not exists company( id int PRIMARY KEY AUTO_INCREMENT, name varchar(255) UNIQUE not null);";
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(create);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                if (stmt != null) {
                    stmt.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void viewTables() {
        String query = "SHOW TABLES";
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean showCompanies() {
        String query = "SELECT * FROM company;";
        boolean hasContent = false;
        try {
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet result = stm.executeQuery();

            while (result.next()) {
                hasContent = true;
                System.out.print(result.getInt("ID") + ". ");
                System.out.println(result.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasContent;
    }

    public void addCompany(String name) {
        String query = "INSERT INTO company (name) VALUES (?);";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCompany(int id) {
        String query = "DELETE FROM company WHERE id = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropCarsTable() {
        String query = "drop table if exists car;";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void dropCompanyTable() {
        String query = "drop table if exists company;";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTableCars() {
        String query = "CREATE TABLE if not exists car(ID int primary key auto_increment, NAME varchar(255) UNIQUE NOT NULL, COMPANY_ID INT NOT NULL, constraint fk_comapany foreign key (COMPANY_ID) references company(id));";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void listCars(int id) {
        String query = "SELECT name FROM car WHERE COMPANY_ID = ?";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            try (resultSet) {
                int i = 1;
                while (resultSet.next()) {
                    System.out.println(i + ". " + resultSet.getString("name"));
                    i++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCar(String carName, int companyID) {
        String query = "INSERT INTO car (name, company_id) VALUES (?, ?);";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.setString(1, carName);
            statement.setInt(2, companyID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showCompany(int company) {
        String query = "SELECT name FROM company WHERE id = ?;";
        try{
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.setInt(1, company);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("'" + resultSet.getString("name") + "'" + " company");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean companyListIsEmpty() {
        String query = "SELECT * FROM company;";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            try (result) {
                if (result.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean carListIsEmpty(int id) {
        String query = "SELECT * FROM car where company_id = ?;";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            try (result) {
                if (result.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void createTableCustomer() {
        String query = "CREATE TABLE if not exists CUSTOMER (ID int primary key auto_increment, name varchar(255) unique not null, rented_car_id int, constraint fk_rented_car foreign key (rented_car_id) references CAR(id))";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCustomer(String name) {
        String query = "INSERT INTO CUSTOMER (name) values ( ? )";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean customerIsEmpty() {
        String query = "SELECT * FROM CUSTOMER;";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            try (rs) {
                if (rs.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void listCustomers() {
        String query = "SELECT name FROM CUSTOMER";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            try (rs) {
                for (int i = 1; rs.next(); i++) {
                    System.out.println(i + ". " + rs.getString("name"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void showAvailableCars(int company) {
        String query = "SELECT * FROM car LEFT JOIN customer ON car.id = customer.rented_car_id WHERE customer.name IS NULL AND car.company_id = ?;";

        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.setInt(1, company);
            ResultSet rs = statement.executeQuery();
            try (rs) {
                for (int i = 1; rs.next(); i++) {
                    this.cars.add(rs.getInt("id"));
                    System.out.println(i + ". " + rs.getString("name"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showRentedCar(int customerID) {
        String query = "SELECT * FROM car LEFT JOIN customer ON car.id = customer.rented_car_id WHERE customer.ID = ?;";
        boolean noHave = true;
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.setInt(1, customerID);
            ResultSet rs = statement.executeQuery();
            try (rs) {
                while (rs.next()) {
                    noHave = false;
                    System.out.println("\nYour rented car:");
                    System.out.println(rs.getString("name"));
                    System.out.println("Company:");
                    System.out.println(getCompany(rs.getInt("company_id")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if (noHave) {
                System.out.println("\nYou didn't rent a car!");
            }
        }
    }

    public boolean hasRentedCar(int customerID) {
        return false;

    }

    public void rentCar(int customer, int car) {
        String query = "UPDATE CUSTOMER SET rented_car_id = ? WHERE id = ?";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.setInt(1, this.cars.get(car - 1));
            statement.setInt(2, customer);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.cars.remove(car - 1);
        }


    }

    public String getCompany(int ID) {
        String query = "SELECT company.name FROM company LEFT JOIN car ON company.id = car.company_id WHERE car.company_id = ?;";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.setInt(1, ID);
            ResultSet rs = statement.executeQuery();
            try (rs) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "NO EXIST";
    }

    public void returnRentedCar(int customerID) {
        String query = "UPDATE CUSTOMER SET rented_car_id = NULL WHERE ID = ?;";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.setInt(1, customerID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean alreadyRentedCar(int customerID) {
        String query = "SELECT * FROM customer where customer.id = ? AND customer.rented_car_id IS NOT NULL";
        boolean rentedCar = false;
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.setInt(1, customerID);
            ResultSet rs = statement.executeQuery();
            try (rs) {
                if (rs.next()) {
                    rentedCar = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentedCar;
    }

    public String getCarName(int costumer) {
        String query = "SELECT car.name FROM car LEFT JOIN CUSTOMER ON car.id = customer.rented_car_id WHERE customer.id = ?;";
        try {
            PreparedStatement statement = this.con.prepareStatement(query);
            statement.setInt(1, costumer);
            ResultSet rs = statement.executeQuery();
            try (rs) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

}