package com.morrisco.net.eCommerceSystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    /**
     * @JsonIgnore for excluding a field
     * @JsonProperty for renaming a field
     * @JsonInclude for excluding null or empty fields
     * @JsonFormatt for formatting values especially date or numbers
     */
    private  Integer id;
    private String name;
    private String email;


}
