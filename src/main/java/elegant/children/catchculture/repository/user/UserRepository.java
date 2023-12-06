package elegant.children.catchculture.repository.user;

import elegant.children.catchculture.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.nickname = :nickname where u.id = :userId")
    void updateNickname(@Param("nickname") String nickname,  @Param("userId") int userId);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.point = :point where u.id = :userId")
    void updateUserPoint(@Param("userId") int userId, @Param("point") int point);
}
