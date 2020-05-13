package com.applaudostudios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.applaudostudios.entities.UserInfo;

/**
 * UserInfoRepository is a rest repository and this is exposed
 * on URL http://serve-host:port/api/users
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@RepositoryRestResource(path = "users", collectionResourceRel = "users")
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

}
