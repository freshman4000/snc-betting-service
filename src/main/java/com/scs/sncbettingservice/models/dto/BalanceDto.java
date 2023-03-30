package com.scs.sncbettingservice.models.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class BalanceDto {
    private String userId;
    private BigDecimal money;
}
