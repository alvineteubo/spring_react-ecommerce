package com.proxidevcode.spring_react_ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagedResponse<T> {
 
    private Long totalElement;
    private int totalPages;
    private int number;
    private int size;
    private int numberOfElements;
    private boolean first;
    private boolean last;
    private List<T> content;

    
}
