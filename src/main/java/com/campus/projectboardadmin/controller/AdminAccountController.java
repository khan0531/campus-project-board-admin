package com.campus.projectboardadmin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AdminAccountController {


  @GetMapping("/admin/members")
  public String members() {
    return "admin/members";
  }
}
