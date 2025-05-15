package umc.spring.repository.memberMissionRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.spring.domain.QMission;
import umc.spring.domain.QStore;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.domain.mapping.QMemberMission;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberMissionRepositoryImpl implements MemberMissionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private final QMission m = QMission.mission;
    private final QStore s = QStore.store;
    private final QMemberMission mm = QMemberMission.memberMission;

    @Override
    public List<MemberMission> findMemberMissionsByMemberId(int offset, int limit, Long memberId) {

        BooleanBuilder predicate = new BooleanBuilder();

        predicate.and(mm.member.id.eq(memberId));
        predicate.and(mm.status.in(MissionStatus.ONGOING, MissionStatus.COMPLETED, MissionStatus.FAILED));

        return jpaQueryFactory
                .selectFrom(mm)
                .join(m.store, s).fetchJoin()
                .join(mm.mission, m).fetchJoin()
                .where(predicate)
                .orderBy(mm.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

}