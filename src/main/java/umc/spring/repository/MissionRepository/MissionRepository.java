package umc.spring.repository.MissionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Mission;
import umc.spring.repository.MemberMissionRepository.MemberMissionRepositoryCustom;

public interface MissionRepository extends JpaRepository<Mission, Long>, MissionRepositoryCustom {
}
