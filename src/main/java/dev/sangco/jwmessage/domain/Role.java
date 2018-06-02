package dev.sangco.jwmessage.domain;

import dev.sangco.jwmessage.common.RoleDuplicatedException;
import dev.sangco.jwmessage.common.RoleNotFoundException;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.BinaryOperator;

@Getter
public enum Role {

    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public boolean isCorrectRole(String paramRole) {
        return paramRole.equalsIgnoreCase(this.roleName);
    }

    public static Role getRoleByName(String roleName) {
        return Arrays.stream(Role.values()).filter(r -> r.isCorrectRole(roleName))
                .reduce(thereCanBeOnlyOne(roleName)).orElseThrow(() -> new RoleNotFoundException(roleName));
    }

    private static <T> BinaryOperator<T> thereCanBeOnlyOne(String roleName)
    {
        return (a, b) -> {throw new RoleDuplicatedException(roleName);};
    }
}
