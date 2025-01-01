package com.star.altineller_kuyumcu.dto;

import com.star.altineller_kuyumcu.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private Status status;
    private Date orderDate;
    private List<OrderItemDto> items;
}
