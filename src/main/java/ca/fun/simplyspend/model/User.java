package ca.fun.simplyspend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonSerialize
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;
    @Column(name="age", nullable = false)
    private int age;
    @Column(name="phone", nullable = false)
    private String phone;
    @Column(name="role", nullable = false)
    private Role role;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="manager_name", nullable = false)
    private String managerName;
    @Column(name="address", nullable = false)
    private String address;
    @Column(name="tag", nullable = false)
    private String tag;


    public enum Role {
        MGR, REPORTEE, ADMIN;
    }
}
