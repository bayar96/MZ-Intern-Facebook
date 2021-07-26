package com.pgs.news.service;

import com.pgs.news.client.AuthClient;
import com.pgs.news.domain.News;
import com.pgs.news.payload.NewsPayload;
import com.pgs.news.payload.UserInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import software.amazon.ion.Timestamp;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NewsServiceImplTest {

    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private AmazonClient amazonClient;
    @Mock
    private AuthClient authClient;

    private UserInfo userInfo;
    private News news;
    private NewsPayload newsPayload;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        LocalDateTime localDateTime = LocalDateTime.now();
        userInfo = new UserInfo(1L, "username", "email",
                "firstname", "lastname", "phone",
                "gender", Collections.emptyList(), true, Collections.singletonList("ROLE_ADMIN"),
                localDateTime, "user", localDateTime);
        news = new News("1", 1L, "NewsTitle", "NewsContent", Timestamp.now().toString());
        newsPayload = new NewsPayload("NewsTitle", "NewsContent");
    }

    @Test
    public void shouldReturnListOfNews() {
        Mockito.when(amazonClient.getNewsFromLastXDays(14))
                .thenReturn(Collections.singletonList(news));
        List<News> out = newsService.getNewsFromLastXDays(14);
        Assert.assertEquals(news.getId(), out.get(0).getId());
        Assert.assertEquals(news.getUserId(), out.get(0).getUserId());
        Assert.assertEquals(news.getTitle(), out.get(0).getTitle());
    }

    @Test
    public void shouldReturnNewsById() {
        Mockito.when(amazonClient.getNewsById(news.getId()))
                .thenReturn(Optional.of(news));
        News out = newsService.getNewsById(news.getId());
        Assert.assertEquals(news.getId(), out.getId());
        Assert.assertEquals(news.getUserId(), out.getUserId());
        Assert.assertEquals(news.getTitle(), out.getTitle());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionDuringReturnNewsByIdWhenPostNotFound() {
        Mockito.when(amazonClient.getNewsById(news.getId()))
                .thenReturn(Optional.empty());
        newsService.getNewsById(news.getId());
    }

    @Test
    public void shouldCreateNews() {
        news.setId(null);
        Mockito.when(authClient.getCurrentUserInfo())
                .thenReturn(userInfo);
        newsService.createNews(newsPayload);
        verify(amazonClient, times(1)).createNews(Mockito.any(News.class));
    }

    @Test(expected = SecurityException.class)
    public void shouldThrowSecurityExceptionDuringCreateNewsWhenUserIsNotAdmin() {
        userInfo.setAuthorities(Collections.singletonList(""));
        Mockito.when(authClient.getCurrentUserInfo())
                .thenReturn(userInfo);
        newsService.createNews(newsPayload);
    }

    @Test
    public void shouldEditNews() {
        Mockito.when(authClient.getCurrentUserInfo())
                .thenReturn(userInfo);
        Mockito.when(amazonClient.getNewsById(news.getId()))
                .thenReturn(Optional.of(news));
        newsService.editNews(news.getId(), newsPayload);
        verify(amazonClient, times(1)).createNews(news);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionDuringEditNewsWhenNewsNotFound() {
        Mockito.when(authClient.getCurrentUserInfo())
                .thenReturn(userInfo);
        Mockito.when(amazonClient.getNewsById(news.getId()))
                .thenReturn(Optional.empty());
        newsService.editNews(news.getId(), newsPayload);
    }

    @Test(expected = SecurityException.class)
    public void shouldThrowSecurityExceptionDuringEditNewsWhenUserIsNotAdmin() {
        userInfo.setAuthorities(Collections.singletonList(""));
        Mockito.when(authClient.getCurrentUserInfo())
                .thenReturn(userInfo);
        newsService.editNews(news.getId(), newsPayload);
    }

    @Test
    public void shouldDeleteNews() {
        Mockito.when(authClient.getCurrentUserInfo())
                .thenReturn(userInfo);
        Mockito.when(amazonClient.getNewsById(news.getId()))
                .thenReturn(Optional.of(news));
        newsService.deleteNews(news.getId());
        verify(amazonClient, times(1)).deleteNews(news);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionDuringDeleteNewsWhenNewsNotFound() {
        Mockito.when(authClient.getCurrentUserInfo())
                .thenReturn(userInfo);
        Mockito.when(amazonClient.getNewsById(news.getId()))
                .thenReturn(Optional.empty());
        newsService.deleteNews(news.getId());
    }

    @Test(expected = SecurityException.class)
    public void shouldThrowSecurityExceptionDuringDeleteNewsWhenUserIsNotAdmin() {
        userInfo.setAuthorities(Collections.singletonList(""));
        Mockito.when(authClient.getCurrentUserInfo())
                .thenReturn(userInfo);
        newsService.deleteNews(news.getId());
    }
}
