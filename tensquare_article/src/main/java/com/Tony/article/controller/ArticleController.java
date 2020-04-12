package com.Tony.article.controller;

import com.Tony.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;



}
