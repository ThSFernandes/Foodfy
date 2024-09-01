package br.foodfy.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.foodfy.auth.domain.user.User;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
}
