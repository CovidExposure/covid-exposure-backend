package com.justsayyes.record.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentDTO {
    private String email;
    private String body;
    private String timestamp;
}
