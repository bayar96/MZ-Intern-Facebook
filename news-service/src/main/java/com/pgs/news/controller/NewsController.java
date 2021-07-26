package com.pgs.news.controller;

import com.pgs.news.dto.NewsDTO;
import com.pgs.news.payload.NewsPayload;
import com.pgs.news.service.NewsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "NewsController")
@RequestMapping("/api/news")
@RestController
@AllArgsConstructor
@Log4j2
public class NewsController {

    private final NewsServiceImpl newsService;
    private final ModelMapper modelMapper;

    @ApiOperation(value = "Get all news endpoint")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved news.")
    })
    @GetMapping
    public List<NewsDTO> getAllNews(@RequestParam(value = "lastDays", defaultValue = "14") int lastDays) {
        return newsService.getNewsFromLastXDays(lastDays).stream()
                .map(news -> modelMapper.map(news, NewsDTO.class)).collect(Collectors.toList());
    }

    @ApiOperation(value = "Get one news endpoint")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved news."),
            @ApiResponse(code = 400, message = "News not found!")
    })
    @GetMapping("/{newsId}")
    public NewsDTO getNewsById(@PathVariable("newsId") String newsId) {
        return modelMapper.map(newsService.getNewsById(newsId), NewsDTO.class);
    }

    @ApiOperation(value = "Create news endpoint")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created news.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public void createNews(@RequestBody NewsPayload newsPayload) {
        newsService.createNews(newsPayload);
    }

    @ApiOperation(value = "Edit news endpoint")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully edited news."),
            @ApiResponse(code = 400, message = "News not found!")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{newsId}")
    public void editNews(@PathVariable("newsId") String newsId, @RequestBody NewsPayload newsPayload) {
        newsService.editNews(newsId, newsPayload);
    }

    @ApiOperation(value = "Delete news endpoint")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted news."),
            @ApiResponse(code = 400, message = "News not found!")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{newsId}")
    public void deleteNews(@PathVariable("newsId") String newsId) {
        newsService.deleteNews(newsId);
    }


}
