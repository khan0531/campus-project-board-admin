package com.campus.projectboardadmin.controller;

import com.campus.projectboardadmin.dto.response.ArticleResponse;
import com.campus.projectboardadmin.service.ArticleManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@RequestMapping("/management/articles")
@Controller
public class ArticleManagementController {

  private final ArticleManagementService articleManagementService;

  @GetMapping
  public String articles(Model model) {
    model.addAttribute(
        "articles",
        articleManagementService.getArticles().stream().map(ArticleResponse::withoutContent).toList()
    );

    return "management/articles";
  }

  @ResponseBody
  @GetMapping("/{articleId}")
  public ArticleResponse article(@PathVariable Long articleId) {
    return ArticleResponse.withContent(articleManagementService.getArticle(articleId));
  }

  @PostMapping("/{articleId}")
  public String deleteArticle(@PathVariable Long articleId) {
    articleManagementService.deleteArticle(articleId);

    return "redirect:/management/articles";
  }
}
