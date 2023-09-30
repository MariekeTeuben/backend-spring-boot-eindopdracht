package nl.novi.backendgarageservice.dto;

import java.time.LocalDate;
import java.util.ArrayList;


public class UserDto {

    public String username;

    public String password;

    public ArrayList<String> roles = new ArrayList<>();

    public ArrayList<String> cars = new ArrayList<>();

    public ArrayList<LocalDate> appointments = new ArrayList<>();

    public ArrayList<Long> invoices = new ArrayList<>();
}
