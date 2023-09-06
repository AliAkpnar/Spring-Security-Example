package com.app.securityexample.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest
{
    private String name;
    private String description;
}
