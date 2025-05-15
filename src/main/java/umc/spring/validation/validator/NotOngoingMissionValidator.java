package umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.memberMissionRepository.MemberMissionRepository;
import umc.spring.validation.annotation.NotOngoingMission;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotOngoingMissionValidator implements ConstraintValidator<NotOngoingMission, Long> {

    private final MemberMissionRepository memberMissionRepository;

    @Override
    public void initialize(NotOngoingMission constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        List<MemberMission> memberMission = memberMissionRepository.findOngoingMemberMissionByMissionId(value);
        if (!memberMission.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.MISSION_ONGOING.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
