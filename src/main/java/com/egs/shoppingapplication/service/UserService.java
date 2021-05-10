package com.egs.shoppingapplication.service;

import com.egs.shoppingapplication.dto.UserRepresentation;
import com.egs.shoppingapplication.entity.User;

public interface UserService {

    Iterable<User> getAllUsers();

    void blockUser(long id);

    void unblockUser(long id);

    User create(UserRepresentation userRepr);

    User create(User user);

    User update(User user);

    void delete(long id);

    User find(long id);

    User findByEmail(String username);
}