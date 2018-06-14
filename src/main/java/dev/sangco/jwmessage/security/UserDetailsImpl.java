package dev.sangco.jwmessage.security;

import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl extends User {
    public static final Logger log =  LoggerFactory.getLogger(UserDetailsImpl.class);

    public UserDetailsImpl(Account account) {
        super(account.getAccId(), account.getPassword(), authorities(account));
    }

    private static Collection<? extends GrantedAuthority> authorities(Account account) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.USER.getRoleName()));
        if (account.getRole().isCorrectRole(Role.ADMIN.getRoleName())) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getRoleName()));
        }

        return authorities;
    }
}
