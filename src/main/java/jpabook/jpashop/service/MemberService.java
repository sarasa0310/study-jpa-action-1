package jpabook.jpashop.service;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

//    private final MemberRepositoryOld memberRepositoryOld;
    private final MemberRepository springDataJpaMemberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 가입 방지
        springDataJpaMemberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = springDataJpaMemberRepository.findByName(member.getName());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return springDataJpaMemberRepository.findAll();
    }

    // 단일 회원 조회
    public Member findOneMember(Long memberId) {
        return springDataJpaMemberRepository.findById(memberId)
                .orElse(null);
    }

    @Transactional
    public void update(Long memberId, String name) {
        Member findMember = findOneMember(memberId);
        findMember.setName(name);
    }

}
