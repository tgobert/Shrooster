package com.revature.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private int prodIdDto;
    private int prodDtoQuantity;

    //Constructor
    public ProductDTO(int prodIdDto, int prodDtoQuantity) {
        this.prodIdDto = prodIdDto;
        this.prodDtoQuantity = prodDtoQuantity;
    }
}
