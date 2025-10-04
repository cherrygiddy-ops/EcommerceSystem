package com.morrisco.net.eCommerceSystem.mappers;

import com.morrisco.net.eCommerceSystem.dtos.OrderDto;
import com.morrisco.net.eCommerceSystem.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto (Order order);
}
