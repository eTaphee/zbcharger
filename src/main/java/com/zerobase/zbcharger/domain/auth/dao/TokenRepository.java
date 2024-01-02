package com.zerobase.zbcharger.domain.auth.dao;

import com.zerobase.zbcharger.domain.auth.entity.Token;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {

    Optional<Token> findByRefreshToken(String refreshToken);
}
