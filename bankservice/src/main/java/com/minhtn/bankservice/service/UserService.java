package com.minhtn.bankservice.service;

import com.minhtn.bankservice.model.user.LoginDTO;
import com.minhtn.bankservice.model.user.RegUserDTO;
import com.minhtn.bankservice.model.user.UpdatePasswordDTO;
import com.minhtn.bankservice.model.user.UserDTO;

public interface UserService {
    UserDTO getUserByUsername(String userName);
    UserDTO register(RegUserDTO regUserDTO);
    String login(LoginDTO loginDTO);
    void updatePassword(String userId, UpdatePasswordDTO updatePasswordDTO);
    String getUsernameLogin();
}
