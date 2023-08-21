package jpabook.jpashop.api;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.dto.request.CreateMemberRequest;
import jpabook.jpashop.dto.response.CreateMemberResponse;
import jpabook.jpashop.dto.request.UpdateMemberRequest;
import jpabook.jpashop.dto.response.MemberResponse;
import jpabook.jpashop.dto.response.MultiResponse;
import jpabook.jpashop.dto.response.UpdateMemberResponse;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOneMember(id);

        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public MultiResponse<?> membersV2() {
        List<Member> members = memberService.findMembers();

        List<MemberResponse> responses = members.stream()
                .map(MemberResponse::toResponse)
                .collect(Collectors.toList());

        return new MultiResponse<>(responses.size(), responses);
    }

}
