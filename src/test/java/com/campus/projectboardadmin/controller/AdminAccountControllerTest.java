package com.campus.projectboardadmin.controller;

import static org.mockito.BDDMockito.anySet;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.campus.projectboardadmin.config.SecurityConfig;
import com.campus.projectboardadmin.config.GlobalControllerConfig;
import com.campus.projectboardadmin.domain.constant.RoleType;
import com.campus.projectboardadmin.dto.AdminAccountDto;
import com.campus.projectboardadmin.service.AdminAccountService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("컨트롤러 - 어드민 회원")
@Import({SecurityConfig.class, GlobalControllerConfig.class})
@WebMvcTest(AdminAccountController.class)
class AdminAccountControllerTest {

    private final MockMvc mvc;

    @MockBean private AdminAccountService adminAccountService;

    public AdminAccountControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @BeforeTestMethod
    public void securitySetup() {
        given(adminAccountService.searchUser(anyString()))
                .willReturn(Optional.of(createAdminAccountDto()));
        given(adminAccountService.saveUser(anyString(), anyString(), anySet(), anyString(), anyString(), anyString()))
                .willReturn(createAdminAccountDto());
    }

    @WithMockUser(username = "tester", roles = "USER")
    @DisplayName("[view][GET] 어드민 회원 페이지 - 정상 호출")
    @Test
    void givenAuthorizedUser_whenRequestingAdminMembersView_thenReturnsAdminMembersView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/admin/members"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("admin/members"));
    }

    @WithMockUser(username = "tester", roles = "USER")
    @DisplayName("[data][GET] 어드민 회원 리스트 - 정상 호출")
    @Test
    void givenAuthorizedUser_whenRequestingAdminMembers_thenReturnsAdminMembers() throws Exception {
        // Given
        given(adminAccountService.users()).willReturn(List.of());

        // When & Then
        mvc.perform(get("/api/admin/members"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        then(adminAccountService).should().users();
    }

    @WithMockUser(username = "tester", roles = "MANAGER")
    @DisplayName("[data][DELETE] 어드민 회원 삭제 - 정상 호출")
    @Test
    void givenAuthorizedUser_whenDeletingAdminMember_thenDeletesAdminMember() throws Exception {
        // Given
        String username = "uno";
        willDoNothing().given(adminAccountService).deleteUser(username);

        // When & Then
        mvc.perform(
                delete("/api/admin/members/" + username)
                        .with(csrf())
        )
                .andExpect(status().isNoContent());
        then(adminAccountService).should().deleteUser(username);
    }


    private AdminAccountDto createAdminAccountDto() {
        return AdminAccountDto.of(
                "unoTest",
                "pw",
                Set.of(RoleType.USER),
                "uno-test@email.com",
                "uno-test",
                "test memo"
        );
    }

}
