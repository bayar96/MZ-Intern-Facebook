package com.pgs.news.service;

import com.pgs.news.domain.News;
import com.pgs.news.payload.NewsPayload;

import java.util.List;

public interface NewsService {
    List<News> getNewsFromLastXDays(int lastWeeks);

    News getNewsById(String id);

    void createNews(NewsPayload newsPayload);

    void editNews(String id, NewsPayload newsPayload);

    void deleteNews(String id);
}
