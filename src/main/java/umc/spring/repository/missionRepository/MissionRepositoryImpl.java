package umc.spring.repository.missionRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.spring.domain.Mission;
import umc.spring.domain.QFood;
import umc.spring.domain.QMission;
import umc.spring.domain.QStore;
import umc.spring.domain.mapping.QFoodCategory;
import umc.spring.domain.mapping.QMemberMission;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private final QMission m = QMission.mission;
    private final QStore s = QStore.store;
    private final QFoodCategory fc = QFoodCategory.foodCategory;
    private final QFood f = QFood.food;
    private final QMemberMission mm = QMemberMission.memberMission;

    @Override
    public List<Mission> findMissionsUnchallengedByMemberId(String address, Long memberId, int offset, int limit) {

        BooleanBuilder predicate = new BooleanBuilder();

        predicate.and(s.address.eq(address));
        predicate.and(m.id.notIn(
                JPAExpressions
                        .select(mm.mission.id)
                        .from(mm)
                        .where(mm.member.id.eq(memberId))
        ));

        return jpaQueryFactory
                .selectFrom(m)
                .join(m.store, s).fetchJoin()
                .join(fc.store, s).fetchJoin()
                .join(fc.food, f).fetchJoin()
                .where(predicate)
                .orderBy(m.deadline.asc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }
}
