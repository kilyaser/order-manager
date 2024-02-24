package com.profcut.ordermanager.controllers.rest.dto.technologist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnologistFieldsPatch {

    private String fullName;
    private String email;
    private String phone;
}
