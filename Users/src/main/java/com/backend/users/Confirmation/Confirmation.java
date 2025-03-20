package com.backend.users.Confirmation;

import com.backend.users.User.Domain.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Data
public class Confirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String code;
    private ZonedDateTime confirmationDate;
    private Long userId;
}
