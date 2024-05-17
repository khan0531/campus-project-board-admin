package com.campus.projectboardadmin.controller;

import com.campus.projectboardadmin.dto.response.ArticleCommentResponse;
import com.campus.projectboardadmin.service.ArticleCommentManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@RequestMapping("/management/article-comments")
@Controller
public class ArticleCommentManagementController {

  private final ArticleCommentManagementService articleCommentManagementService;

  @GetMapping
  public String articleComments(Model model) {
    model.addAttribute(
        "comments",
        articleCommentManagementService.getArticleComments().stream().map(ArticleCommentResponse::of).toList()
    );

    return "management/article-comments";
  }

  @ResponseBody
  @GetMapping("/{articleCommentId}")
  public ArticleCommentResponse articleComment(@PathVariable Long articleCommentId) {
    return null;
  }

  @PostMapping("/{articleCommentId}")
  public String deleteArticleComment(@PathVariable Long articleCommentId) {

    return "redirect:/management/article-comments";
  }

}
