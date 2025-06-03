package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name", length = 100, unique = true, nullable = false)
    private String fullName;
    @Column(name = "address", length = 100, unique = true, nullable = false)
    private String address;
    @Column(name = "phone", length = 15, unique = true, nullable = false)
    private String phone;
    @Column(name = "status")
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;
}
