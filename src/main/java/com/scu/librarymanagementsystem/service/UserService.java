package com.scu.librarymanagementsystem.service;

import com.scu.librarymanagementsystem.common.enums.UserType;
import com.scu.librarymanagementsystem.common.utils.RedisUtil;
import com.scu.librarymanagementsystem.model.User;
import com.scu.librarymanagementsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisUtil redisUtil;

    private final Long USER_REDIS_TIMEOUT = 30L;

    private final TimeUnit USER_REDIS_TIMEOUT_UNIT = TimeUnit.MINUTES;

    public int addUser(String userName, String passWord, UserType userType) {
        try {
            User user = new User();
            user.setUsername(userName);
            user.setPassword(passWord);
            user.setUserType(userType);

            userRepository.save(user);

            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    @Transactional
    public int deleteUserById(Long id) {
        try {
            User oldUser = userRepository.findById(id).get();
            if (oldUser != null) {
                userRepository.deleteById(id);
                redisUtil.del(generateAllCacheKeys(oldUser.getUsername(), oldUser.getUserType().equals(UserType.ADMIN)?"admin":"user").toArray(new String[0]));
            }

            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }


    @Transactional
    public int updateUser(Long id, String newUsername, String newPassword, UserType newUserType) {
        try {
            /**
             *先更新数据库，再删缓存，保证数据一致性（极少极少的情况下可能会出现不一致，可以通过超时时间兜底）
             *先写再删只能保证并发场景下的数据一致性，但是没法保证整个操作的一致性
             *网上常见做法通过监听数据库binlog来删缓存，这里简化一下通过spring的事务来保证
             */
            User oldUser = userRepository.findById(id).get();
            if (oldUser != null) {
                userRepository.updateUser(id, newUsername, newPassword, newUserType);
                redisUtil.del(generateAllCacheKeys(oldUser.getUsername(), oldUser.getUserType().equals(UserType.ADMIN)?"admin":"user").toArray(new String[0]));
            }

            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    public List<User> findUsersByMultiConditions(String username, String userType) {
        try {
            String cacheKey = generateCacheKey(username, userType);
            List<User> cacheResult = (List<User>) redisUtil.get(cacheKey);
            if (cacheResult != null && !cacheResult.isEmpty()) {
                return cacheResult;
            }

            List<User> dbResult = findUsersByDB(username, userType);
            redisUtil.setWithExpiration(cacheKey, dbResult, USER_REDIS_TIMEOUT, USER_REDIS_TIMEOUT_UNIT);

            return dbResult;
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<String> generateAllCacheKeys(String username, String userType) {
        return new ArrayList<String>(){{
            add(generateCacheKey(username, null));
            add(generateCacheKey(null, userType));
            add(generateCacheKey(username, userType));
        }};
    }

    private String generateCacheKey(String username, String userType) {
        String key = "user:";
        if (username != null) {
            key += "username_" + username;
        }
        if (userType != null) {
            key += "userType_" + userType;
        }

        return key;
    }

    private List<User> findUsersByDB(String username, String userType) {
        Specification<User> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (username != null) {
                predicates.add(criteriaBuilder.equal(root.get("username"), username));
            }
            if (userType != null) {
                predicates.add(criteriaBuilder.equal(root.get("userType"), userType));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return userRepository.findAll(spec);
    }
}
