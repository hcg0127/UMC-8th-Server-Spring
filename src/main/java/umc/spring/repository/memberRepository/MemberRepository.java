package umc.spring.repository.memberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
}
