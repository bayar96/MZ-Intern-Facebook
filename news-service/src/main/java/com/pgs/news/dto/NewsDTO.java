package com.pgs.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO {
    private String id;
    private Long userId;
    private String title;
    private String content;
    private String createDate;
}
