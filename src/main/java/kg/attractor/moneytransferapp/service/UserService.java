package kg.attractor.moneytransferapp.service;

import kg.attractor.moneytransferapp.dto.UserRegisterDto;
import kg.attractor.moneytransferapp.model.User;

public interface UserService {

    void register(
            UserRegisterDto dto
    );

    User getByUsername(
            String username
    );

    boolean existsByUsername(
            String username
    );
}