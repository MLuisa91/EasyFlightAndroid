package com.example.flightextrem.service.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaExtraPK implements Serializable {

    private Integer reservaId;
    private Integer extraId;

}
