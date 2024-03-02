package com.profcut.ordermanager.domain.exceptions;

public class OmRoleNotFoundException extends EntityNotFoundException{

    public OmRoleNotFoundException(String message) {
        super(message);
    }

    public static OmRoleNotFoundException byRoleName(String roleName) {
        return new OmRoleNotFoundException("Не найдена роль с наименованием: %s".formatted(roleName));
    }
}
