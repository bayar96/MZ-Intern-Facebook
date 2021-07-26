package com.pgs.news.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsPayload {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
