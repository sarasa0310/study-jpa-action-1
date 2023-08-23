package jpabook.jpashop.service;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepositoryOld memberRepositoryOld;

    @Test
    @DisplayName("회원가입")
    void joinTest() {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        assertThat(member).isEqualTo(memberRepositoryOld.findOne(savedId));
    }

    @Test
    @DisplayName("같은 이름의 회원이 이미 존재할 경우 예외가 발생해야 한다.")
    void validateDuplicateMemberTest() {
        // given
        Member member1 = new Member();
        member1.setName("jimmy");

        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("jimmy");

        // when & then
        assertThatThrownBy(() -> memberService.join(member2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 존재하는 회원입니다.");
    }

}