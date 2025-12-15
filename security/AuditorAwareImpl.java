package org.bf.global.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    /**
     * 현재 스레드의 Security Context에서 인증된 사용자의 이름을 반환 <br>
     * * 엔티티의 @CreatedBy 및 @LastModifiedBy 필드에 주입 <br>
     * * @return 현재 인증된 사용자의 이름(String)을 포함하는 Optional
     */
    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("Authentication is null");
            return Optional.empty();
        }
        System.out.println("DEBUG: Authentication Principal: " + authentication.getPrincipal());

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails customUserDetails) {
            String auditorName = customUserDetails.getUsername();

            if (auditorName != null) {
                return Optional.of(auditorName);
            }
        }

        return Optional.empty();
    }

    /**
     * 익명 사용자 여부를 확인하는 헬퍼 메서드
     */
    private boolean isAnonymous(Authentication authentication) {
        return authentication.getPrincipal().equals("anonymousUser");
    }
}
