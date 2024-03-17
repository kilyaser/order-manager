package com.profcut.ordermanager.security.service;

import java.util.Optional;

public interface CurrentUserSecurityService {

    Optional<String> getLogin();
}
