package com.example.spring_code.domian;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    private String fileName;

    private int ord; // 파일의 순번

    public void setOrd(int ord){
        this.ord = ord;
    }


}
