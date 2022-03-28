package teamexpress.velo9.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);

	Optional<Member> findByNickname(String nickname);

	Optional<Member> findByEmail(String email);

	@Modifying
	@Query("update Member m set m.password =: password")
	void changePw(@Param("password") String password);
}
