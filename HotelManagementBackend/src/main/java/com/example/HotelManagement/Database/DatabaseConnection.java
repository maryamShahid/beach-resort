package com.example.HotelManagement.Database;

import org.apache.coyote.http11.filters.SavedRequestInputFilter;

import java.sql.*;

public class DatabaseConnection {

    public final static int FETCH = 0;
    public final static int UPDATE = 1;

    private final String url = "jdbc:mysql://dijkstra.ug.bcc.bilkent.edu.tr:3306/bora_cun";
    private final String user = "bora.cun";
    private final String pass = "DPZ3a7Km";

    public DatabaseConnection() {}

    /**
     * This method creates a connection, and executes a query or update.
     * @param query The desired SQL update or query as string
     * @param type determines the type of the execution: FETCH for query, UPDATE for update
     * @return The result of the query as a ResultSet. null if type is UPDATE.
     */
    public ResultSet execute(String query, int type ) {
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ResultSet resultSet = null;
            //Connection
            System.out.println("Connecting to the database...");
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection successful.");

            stmt = con.createStatement();

            if( type == FETCH ){
                resultSet = stmt.executeQuery(query);
                con.close();
                return resultSet;
            }
            else if( type == UPDATE) {
                stmt.executeUpdate(query);
                con.close();
                return null;
            }
        }
        catch (Exception e) {

            try {
                con.close();
            }catch ( Exception e1 ){
                System.out.println(e1.getMessage());
            }

            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
    ============================DROP TABLE METHODS============================
     */

    /**
     * Drops the given table if it exists.
     * @param tableName The name of the table
     * @return 1 if successful, 0 if failure
     */
    public int dropTable(String tableName) {
        String update = "DROP TABLE IF EXISTS " + tableName + ";";
        try {
            execute(update, UPDATE);
            System.out.println("Dropped " + tableName + ".");
            return 1;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Drops all the tables in the database. Be careful when using this, every table will be gone.
     * @return 1 if successful, 0 if failed
     */
    public int dropAllTables() {
        if (dropTable("Receptionist") == 0) {return 0;}
        if (dropTable("Security_Staff") == 0) {return 0;}
        if (dropTable("Manager") == 0) {return 0;}
        if (dropTable("Housekeeper") == 0) {return 0;}
        if (dropTable("Recruiter") == 0) {return 0;}
        if (dropTable("Employee") == 0) {return 0;}
        if (dropTable("Candidate") == 0) {return 0;}
        if (dropTable("Guests") == 0) {return 0;}
        if (dropTable("Users") == 0) {return 0;}
        if (dropTable("Room") == 0) {return 0;}
        if (dropTable("Room_Type") == 0) {return 0;}
        if (dropTable("Reservation") == 0) {return 0;}
        if (dropTable("Comment") == 0) {return 0;}
        if (dropTable("QnA") == 0) {return 0;}
        if (dropTable("Building") == 0) {return 0;}
        if (dropTable("Location") == 0) {return 0;}
        return 1;
    }

    /*
    ============================CREATE TABLE METHODS============================
     */

    /**
     * Creates the given table.
     * @param query SQL Code of table definition
     * @return 1 for success, 0 for failure
     */
    public int createTable(String query) {
        try {
            execute(query, UPDATE);
            return 1;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Initializes the database with empty tables.
     * @return 1 for success, 0 for failure
     */
    public int createAllTables() {
        if (createTable(CREATE_USERS) == 0) {return 0;}
        if (createTable(CREATE_EMPLOYEE) == 0) {return 0;}
        if (createTable(CREATE_RECEPTIONIST) == 0) {return 0;}
        if (createTable(CREATE_CANDIDATE) == 0) {return 0;}
        if (createTable(CREATE_RECRUITER) == 0) {return 0;}
        if (createTable(CREATE_SECURITY_STAFF) == 0) {return 0;}
        if (createTable(CREATE_MANAGER) == 0) {return 0;}
        if (createTable(CREATE_GUESTS) == 0) {return 0;}
        if (createTable(CREATE_HOUSEKEEPER) == 0) {return 0;}
        if (createTable(CREATE_LOCATION) == 0) {return 0;}
        if (createTable(CREATE_BUILDING) == 0) {return 0;}
        if (createTable(CREATE_ROOM_TYPE) == 0) {return 0;}
        if (createTable(CREATE_ROOM) == 0) {return 0;}
        if (createTable(CREATE_RESERVATION) == 0) {return 0;}
        if (createTable(CREATE_COMMENT) == 0) {return 0;}
        if (createTable(CREATE_QNA) == 0) {return 0;}
        return 1;
    }

    /*
    ============================USER TABLE DEFINITIONS============================
     */
    public static final String CREATE_USERS = "CREATE TABLE IF NOT EXISTS Users(\n" +
            "                      id INT,\n" +
            "                      firstname VARCHAR(50) NOT NULL,\n" +
            "                      lastname VARCHAR(50) NOT NULL,\n" +
            "                      email VARCHAR(75) NOT NULL UNIQUE,\n" +
            "                      password VARCHAR(20) NOT NULL,\n" +
            "                      phone VARCHAR(20),\n" +
            "                      address VARCHAR(100),\n" +
            "                      gender VARCHAR(20),\n" +
            "                      date_of_birth DATE,\n" +
            "                      PRIMARY KEY (id),\n" +
            "                      CHECK (date_of_birth < CURDATE() AND id > 0 ) ) ENGINE=InnoDB;";

    public static final String CREATE_EMPLOYEE = "CREATE TABLE IF NOT EXISTS Employee(\n" +
            "    id INT,\n" +
            "    salary NUMERIC(18, 2) NOT NULL,\n" +
            "    employment_date DATE NOT NULL,\n" +
            "    annual_leave INT NOT NULL,\n" +
            "    PRIMARY KEY (id),\n" +
            "    FOREIGN KEY (id) REFERENCES Users(id)\n" +
            "        ON DELETE CASCADE \n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (salary >= 0 AND id > 0 AND annual_leave > 0)) ENGINE=InnoDB;";

    public static final String CREATE_RECEPTIONIST = "CREATE TABLE IF NOT EXISTS Receptionist(\n" +
            "    id INT,\n" +
            "    PRIMARY KEY (id),\n" +
            "    FOREIGN KEY (id) REFERENCES Employee(id)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (id > 0)) ENGINE=InnoDB;";

    public static final String CREATE_CANDIDATE = "CREATE TABLE IF NOT EXISTS Candidate(\n" +
            "    id INT,\n" +
            "    cover_letter VARCHAR(5000) NOT NULL,\n" +
            "    PRIMARY KEY (id),\n" +
            "    FOREIGN KEY (id) REFERENCES Users(id)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (id > 0)) ENGINE=InnoDB;";

    public static final String CREATE_RECRUITER = "CREATE TABLE IF NOT EXISTS Recruiter(\n" +
            "    id INT,\n" +
            "    PRIMARY KEY (id),\n" +
            "    FOREIGN KEY (id) REFERENCES Employee(id)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (id > 0)) ENGINE=InnoDB;";

    public static final String CREATE_SECURITY_STAFF = "CREATE TABLE IF NOT EXISTS Security_Staff(\n" +
            "    id INT,\n" +
            "    security_rank VARCHAR(20) NOT NULL,\n" +
            "    weapon VARCHAR(20),\n" +
            "    PRIMARY KEY (id),\n" +
            "    FOREIGN KEY (id) REFERENCES Employee(id)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (id > 0)) ENGINE=InnoDB;";

    public static final String CREATE_MANAGER = "CREATE TABLE IF NOT EXISTS Manager(\n" +
            "    id INT,\n" +
            "    office_no VARCHAR(10) NOT NULL,\n" +
            "    PRIMARY KEY (id),\n" +
            "    FOREIGN KEY (id) REFERENCES Employee(id)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (id > 0) ) ENGINE=InnoDB;";

    public static final String CREATE_GUESTS = "CREATE TABLE IF NOT EXISTS Guests(\n" +
            "    id INT,\n" +
            "    money_spent NUMERIC(18, 2) NOT NULL,\n" +
            "    PRIMARY KEY (id),\n" +
            "    FOREIGN KEY (id) REFERENCES Users(id)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (id > 0 AND money_spent > 0)) ENGINE=InnoDB;";

    public static final String CREATE_HOUSEKEEPER = "CREATE TABLE IF NOT EXISTS Housekeeper(\n" +
            "    id INT,\n" +
            "    PRIMARY KEY (id),\n" +
            "    FOREIGN KEY (id) REFERENCES Employee(id)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (id > 0)) ENGINE=InnoDB;";

    /*
    ============================ROOM TABLE DEFINITIONS============================
     */
    public static final String CREATE_ROOM = "CREATE TABLE IF NOT EXISTS Room (\n" +
            "    room_no INT,\n" +
            "    building_no VARCHAR(50),\n" +
            "    type VARCHAR(50) NOT NULL,\n" +
            "    description VARCHAR(5000),\n" +
            "    PRIMARY KEY (room_no, building_no),\n" +
            "    FOREIGN KEY (type) REFERENCES Room_Type(type)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    FOREIGN KEY (building_no) REFERENCES Building(building_no)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (room_no > 0 AND  LENGTH(building_no) > 0 AND LENGTH(type) > 0)) ENGINE=InnoDB;";      //LENGTH(type) produces warning

    public static final String CREATE_ROOM_TYPE = "CREATE TABLE IF NOT EXISTS Room_Type (\n" +
            "    type VARCHAR(50),\n" +
            "    price NUMERIC(18, 2) NOT NULL,\n" +
            "    no_of_people INT NOT NULL,\n" +
            "    PRIMARY KEY (type),\n" +
            "    CHECK (LENGTH(type) > 0)) ENGINE=InnoDB;";   //LENGTH(type) produces warning

    public static final String CREATE_RESERVATION = "CREATE TABLE IF NOT EXISTS Reservation(\n" +
            "    reservation_id INT,\n" +
            "    guest_id INT NOT NULL,\n" +
            "    room_no INT,\n" +
            "    building_no VARCHAR(50),\n" +
            "    check_in_date DATE NOT NULL,\n" +
            "    check_out_date DATE NOT NULL,\n" +
            "    PRIMARY KEY (reservation_id),\n" +
            "    FOREIGN KEY (guest_id) REFERENCES Guests(id)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    FOREIGN KEY (room_no,building_no) REFERENCES Room(room_no, building_no)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (reservation_id > 0 AND guest_id > 0 AND check_in_date < check_out_date AND room_no > 0 AND  LENGTH(building_no) > 0) ) ENGINE=InnoDB;";

    public static final String CREATE_COMMENT = "CREATE TABLE IF NOT EXISTS Comment(\n" +
            "    comment_id INT,\n" +
            "    reservation_id INT NOT NULL,\n" +
            "    text VARCHAR(5000) NOT NULL,\n" +
            "    date DATE NOT NULL,\n" +
            "    topic VARCHAR(50),\n" +
            "    PRIMARY KEY (comment_id),\n" +
            "    FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (comment_id > 0 AND reservation_id > 0)) ENGINE=InnoDB;";

    public static final String CREATE_QNA = "CREATE TABLE IF NOT EXISTS QnA(\n" +
            "    thread_id INT,\n" +
            "    guest_id INT NOT NULL,\n" +
            "    receptionist_id INT,\n" +
            "    question_text VARCHAR(5000) NOT NULL,\n" +
            "    question_date DATE NOT NULL,\n" +
            "    question_time TIME NOT NULL,\n" +
            "    answer_text VARCHAR(5000),\n" +
            "    answer_date DATE,\n" +
            "    answer_time TIME,\n" +
            "    PRIMARY KEY (thread_id),\n" +
            "    FOREIGN KEY (guest_id) REFERENCES Guests(id)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    FOREIGN KEY (receptionist_id) REFERENCES Receptionist(id)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (thread_id > 0 AND guest_id > 0 AND receptionist_id > 0)) ENGINE=InnoDB;";

    /*
    ============================PLACE TABLE DEFINITIONS============================
     */

    public static final String CREATE_BUILDING = "CREATE TABLE IF NOT EXISTS Building(\n" +
            "    building_no VARCHAR(50),\n" +
            "    location_name VARCHAR(50) NOT NULL,\n" +
            "    name VARCHAR(50) NOT NULL,\n" +
            "    no_of_rooms INT NOT NULL,\n" +
            "    no_of_floors INT NOT NULL,\n" +
            "    area INT NOT NULL,\n" +
            "    type VARCHAR(50) NOT NULL,\n" +
            "    PRIMARY KEY (building_no),\n" +
            "    FOREIGN KEY (location_name) REFERENCES Location(name)\n" +
            "        ON DELETE CASCADE\n" +
            "        ON UPDATE CASCADE,\n" +
            "    CHECK (no_of_rooms > 0 AND no_of_floors > 0 AND area > 0 AND  CHAR_LENGTH(building_no) > 0 AND CHAR_LENGTH(location_name) > 0)) ENGINE=InnoDB;";

    public static final String CREATE_LOCATION = "CREATE TABLE IF NOT EXISTS Location(\n" +
            "    name VARCHAR(50),\n" +
            "    opening TIME,\n" +
            "    closing TIME,\n" +
            "    min_age INT,\n" +
            "    PRIMARY KEY (name),\n" +
            "    CHECK ( CHAR_LENGTH(name) > 0 AND opening < closing AND min_age > 0)) ENGINE=InnoDB;";


}
