package io.dveamer.sample.login;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository
        extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    List<Member> findByEmailContaining(String email);

    List<Member> findByNameContaining(String name);
}
