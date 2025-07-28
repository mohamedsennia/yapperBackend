package com.example.MessengerAppp.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPostDTO {
    private String content;
    private PostType postType;
    private int parent;

    public AddPostDTO(String content, PostType postType) {
        this.content = content;
        this.postType = postType;

    }
}
