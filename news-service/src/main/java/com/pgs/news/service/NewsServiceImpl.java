package com.pgs.news.service;

import com.pgs.news.client.AuthClient;
import com.pgs.news.domain.News;
import com.pgs.news.payload.NewsPayload;
import com.pgs.news.payload.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import software.amazon.ion.Timestamp;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class NewsServiceImpl implements NewsService {

    private final AmazonClient amazonClient;
    private final AuthClient authClient;

    @Override
    public List<News> getNewsFromLastXDays(int lastDays) {
        return amazonClient.getNewsFromLastXDays(lastDays);
    }

    @Override
    public News getNewsById(String id) {
        return amazonClient.getNewsById(id).orElseThrow(() -> new EntityNotFoundException("News not found!"));
    }

    @Override
    public void createNews(NewsPayload newsPayload) {
        UserInfo userInfo = getUserInfo();
        News news = new News();
        news.setUserId(userInfo.getId());
        news.setTitle(newsPayload.getTitle());
        news.setContent(newsPayload.getContent());
        news.setCreateDate(Timestamp.now().toString());
        amazonClient.createNews(news);
        log.info("news: " + newsPayload.getTitle() + " created.");
    }

    @Override
    public void editNews(String id, NewsPayload newsPayload) {
        getUserInfo();
        News news = getNewsById(id);
        setNewsTitle(news, newsPayload.getTitle());
        setNewsContent(news, newsPayload.getContent());
        amazonClient.createNews(news);
    }

    private void setNewsTitle(News news, String newTitle) {
        if (newTitle != null) {
            news.setTitle(newTitle);
        }
    }

    private void setNewsContent(News news, String newContent) {
        if (newContent != null) {
            news.setContent(newContent);
        }
    }

    @Override
    public void deleteNews(String id) {
        getUserInfo();
        News news = getNewsById(id);
        amazonClient.deleteNews(news);
    }

    private UserInfo getUserInfo() {
        UserInfo userInfo = authClient.getCurrentUserInfo();
        if (!userInfo.getAuthorities().contains("ROLE_ADMIN")) {
            throw new SecurityException("You are not authorized for this action!");
        }
        return userInfo;
    }
}
