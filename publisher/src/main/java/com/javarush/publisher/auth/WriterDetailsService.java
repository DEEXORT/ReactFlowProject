package com.javarush.publisher.auth;

import com.javarush.publisher.model.writer.Writer;
import com.javarush.publisher.repository.hibernate.WriterHibernateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WriterDetailsService implements UserDetailsService {
    private final WriterHibernateRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Writer writer = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(
                writer.getLogin(),
                writer.getPassword(),
                List.of(new SimpleGrantedAuthority(writer.getRole().toString()))
        );
    }
}
