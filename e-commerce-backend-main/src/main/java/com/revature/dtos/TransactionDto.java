package com.revature.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransactionDto {

    private int userId;
    private double total;
    private long datePlaced;
    private List<TransactionQtyDTO> orderQuantityBoughts;
}
