package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper; // qlrm라이브러리 pom.xml에 등록하여 처리 쿼리결과를 매핑해주는 라이브러
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private  final EntityManager em; // Repository는 EntityManager를 구현해서 만들어져 있는 구현

    @Transactional(readOnly = true)
    public List<SubscribeDto> 구독리스트(int principalId, int pageUserid){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT A.id, A.username, A.profileImageUrl, "); // 마지막에 반드시 한칸 띄울것!!
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserid = ? AND toUserId = A.id), 1, 0) AS subscribeState, ");
        sb.append("if ((?=A.id), 1, 0) AS equalUserState ");
        sb.append("FROM user A ");
        sb.append("INNER JOIN subscribe B ");
        sb.append("ON A.id = B.toUserId ");
        sb.append("WHERE B.fromUserId = ? "); // 세미콜론 첨부하면
        // 첫번째 두번째 principalId
        // 마지막 물음표 pageUserId
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserid)
                ;

        //쿼리 실행(qlrm 라이브러리)
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
        return subscribeDtos;
    }

    @Transactional
    public void 구독하기(int fromUserId, int toUserId) {
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        } catch (Exception e){
            throw new CustomApiException("이미 구독하고 있습니다.");
        }
    }

    @Transactional
    public void 구독취소하기(int fromUserId, int toUserId) {
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }
}
