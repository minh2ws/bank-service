package com.minhtn.bankservice.controller;

import com.minhtn.bankservice.model.user.LoginDTO;
import com.minhtn.bankservice.model.user.RegUserDTO;
import com.minhtn.bankservice.model.user.UserDTO;
import com.minhtn.bankservice.model.wrapper.ObjectResponseWrapper;
import com.minhtn.bankservice.ultility.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.AUTHENTICATE_SERVICE_URL)
@Tag(name = "Authenticate API")
public class AuthenticateController extends BaseController {

    @Operation(summary = "Tạo user", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @PostMapping("/register")
    public ObjectResponseWrapper register(@RequestBody @Valid RegUserDTO regUserDTO) {
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(userService.register(regUserDTO))
                .build();
    }

    @Operation(summary = "Đăng nhập", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = ObjectResponseWrapper.class)))
    })
    @PostMapping("/login")
    public ObjectResponseWrapper login(@RequestBody LoginDTO loginDTO) {
        String token = userService.login(loginDTO);
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(token)
                .build();
    }
}
