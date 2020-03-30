package com.cooper.article.setup;

import com.cooper.article.data.feed.ArticleDao;
import com.cooper.article.data.feed.ArticleFeed;
import com.cooper.article.service.ArticleService;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class DataModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ArticleDao.class)
                .annotatedWith(Names.named("DefaultArticleFeed"))
                .to(ArticleFeed.class);

        bind(ArticleService.class);
    }
}