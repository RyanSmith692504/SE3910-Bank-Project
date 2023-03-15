package com.example.SpringReact.controller;

import com.example.SpringReact.domain.BankUser;
import com.example.SpringReact.domain.Book;
import com.example.SpringReact.domain.ExpenseIncome;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


import java.sql.*;

@Controller
public class LoginController {

    Statement statement;

    public LoginController() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver Loaded");

        Connection connection = DriverManager.getConnection("" +
                "jdbc:mysql://localhost/bank", "root", "root");

        System.out.println("Database connected");
        statement = connection.createStatement();


    }

    @CrossOrigin
    @PutMapping("/userLogin")
    public ResponseEntity<?> login(@RequestBody BankUser user) throws SQLException {

        String username = "";
        String password = "";

        username = user.getUsername();
        password = user.getPassword();

        System.out.println(username);
        System.out.println(password);

        if(checkLogin(username, password).getUsername().isEmpty()){
            return new ResponseEntity<>(checkLogin(username, password), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(checkLogin(username, password), HttpStatus.OK);
    }



    public BankUser checkLogin (String username, String password) throws SQLException {

        ResultSet user = statement.executeQuery("SELECT * FROM bank.users WHERE username = '" + username +
                                                            "' && password = '" + password + "'");
        BankUser bankUser = new BankUser();
        if(user.next()){
            bankUser.setUsername(user.getString("username"));
            bankUser.setName(user.getString("name"));
            bankUser.setTotal_balance(user.getDouble("total_balance"));
            bankUser.setSavings_goal(user.getDouble("savings_goal"));
            bankUser.setLoan_amount(user.getDouble("loan_amount"));
        }

        return bankUser;
    }

    @CrossOrigin
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") String id) throws SQLException {


        if(getUserHelper(id).getUsername().isEmpty()) {
            return new ResponseEntity<>(getUserHelper(id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getUserHelper(id), HttpStatus.OK);
    }
    public BankUser getUserHelper (String username) throws SQLException {

        ResultSet user = statement.executeQuery("Select * FROM bank.users WHERE username = '" + username + "'");
        BankUser bankUser = new BankUser();

        if(user.next()) {
            bankUser.setUsername(user.getString("username"));
            bankUser.setName(user.getString("name"));
            bankUser.setTotal_balance(user.getDouble("total_balance"));
            bankUser.setSavings_goal(user.getDouble("savings_goal"));
            bankUser.setLoan_amount(user.getDouble("loan_amount"));
        }
        return bankUser;

    }

    @CrossOrigin
    @RequestMapping(value = "/userLoanAmount/{id}/{amount}", method = RequestMethod.GET)
    public ResponseEntity<?> setLoanAmount(@PathVariable("id") String id, @PathVariable("amount")double amount) throws SQLException {
        BankUser user = setLoanAmountHelper(id,amount);
        if(user.getUsername()==null){
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public BankUser setLoanAmountHelper (String username, double amount) throws SQLException {
        int user = statement.executeUpdate("UPDATE bank.users SET loan_amount = " + amount + " WHERE username = '" + username + "'" );
        ResultSet updatedUser = statement.executeQuery("SELECT * FROM bank.users WHERE username = '" + username + "'");
        BankUser bankUser = new BankUser();

        while(updatedUser.next()) {
            String name = updatedUser.getString("name");
            String password = updatedUser.getString("password");
            double total_balance = Double.parseDouble(updatedUser.getString("total_balance"));
            double savings_goal = Double.parseDouble(updatedUser.getString("savings_goal"));
            double loan_amount = Double.parseDouble(updatedUser.getString("loan_amount"));
            bankUser.setUsername(username);
            bankUser.setName(name);
            bankUser.setPassword(password);
            bankUser.setTotal_balance(total_balance);
            bankUser.setSavings_goal(savings_goal);
            bankUser.setLoan_amount(loan_amount);
        }
        return bankUser;
    }

}