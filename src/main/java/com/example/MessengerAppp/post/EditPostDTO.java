package com.example.MessengerAppp.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EditPostDTO {
    private int id;
    private String content;
}
