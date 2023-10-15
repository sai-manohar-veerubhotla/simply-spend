package ca.fun.simplyspend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonSerialize
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String emailId;
    private int age;
    private String phone;
    private Role role;
    private String password;
    private String managerName;
    private String address;
    private String tag;


    public enum Role {
        MGR, REPORTEE, ADMIN;
    }

    public User() {
    }

    public User(String firstName,
                String lastName,
                String emailId,
                int age,
                String phone,
                Role role,
                String password,
                String managerName,
                String address,
                String tag) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.age = age;
        this.phone = phone;
        this.role = role;
        this.password = password;
        this.managerName = managerName;
        this.address = address;
        this.tag = tag;
    }

    public User(int id, String firstName, String lastName, String emailId, int age, String phone, Role role, String password, String managerName, String address, String tag) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.age = age;
        this.phone = phone;
        this.role = role;
        this.password = password;
        this.managerName = managerName;
        this.address = address;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getAddress() {
        return address;
    }

    public String getTag() {
        return tag;
    }
}
