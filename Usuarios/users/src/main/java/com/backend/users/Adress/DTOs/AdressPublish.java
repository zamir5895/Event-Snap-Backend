package com.backend.users.Adress.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class AdressPublish {

    private String street;
    private String city;
    private String state;
    private String country;
    @NotEmpty
    @NonNull
    private Double latitude;
    @NotEmpty
    @NonNull
    private Double longitude;

}
