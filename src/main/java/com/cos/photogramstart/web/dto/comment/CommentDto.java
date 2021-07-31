package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    @NotBlank // "", null, " " 안됨, NotBlank "", null 안됨, NotNull null만안됨
    private String content;
    @NotNull
    private Integer imageId;

    // toEntity가 필요없음.
}
