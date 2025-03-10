package com.minhtn.bankservice.controller;

import com.minhtn.bankservice.model.user.UpdatePasswordDTO;
import com.minhtn.bankservice.model.user.UserDTO;
import com.minhtn.bankservice.model.wrapper.ObjectResponseWrapper;
import com.minhtn.bankservice.ultility.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.USER_SERVICE_URL)
@Tag(name = "User API")
public class UserController extends BaseController {

    @Operation(summary = "Lấy thông tin user", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/{username}")
    public ObjectResponseWrapper getUser(@PathVariable("username") String username) {
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(userService.getUserByUsername(username))
                .build();
    }

    @Operation(summary = "Update user", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @PostMapping("/{username}/password")
    public ObjectResponseWrapper updatePassword(@PathVariable("username") String username, @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        userService.updatePassword(username, updatePasswordDTO);
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }
}
