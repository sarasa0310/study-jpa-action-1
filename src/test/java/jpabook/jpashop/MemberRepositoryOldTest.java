package jpabook.jpashop;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryOldTest {

    @Autowired
    private MemberRepositoryOld memberRepositoryOld;

    @Test
    @Transactional
    void save() {
        // given
        Member member = new Member();
        member.setName("memberA");

        // when
        memberRepositoryOld.save(member);
        Member findMember = memberRepositoryOld.findOne(member.getId());

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(member).isEqualTo(findMember);
    }

}