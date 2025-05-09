package com.green.acamatch.academy;

import com.green.acamatch.academy.premium.model.PremiumBannerGetRes;
import com.green.acamatch.academy.premium.model.PremiumGetRes;
import com.green.acamatch.entity.academy.PremiumAcademy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PremiumRepository extends JpaRepository<PremiumAcademy, Long> {

    Optional<PremiumAcademy> findByAcademy_AcaId(Long academyId);


    //프리미엄 학원 승인 여부
    @Transactional
    @Modifying
    @Query(" update PremiumAcademy a set a.preCheck =:preCheck where a.acaId =:acaId")
    int updateByAcaId(@Param("acaId") Long acaId, @Param("preCheck") Integer preCheck);

    //프리미엄 시작일, 종료일
    @Transactional
    @Modifying
    @Query(" update PremiumAcademy a set a.startDate =:startDate, a.endDate =:endDate where a.acaId =:acaId")
    int updateDateByAcaId(@Param("acaId") Long acaId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //프리미엄 학원 조회
    @Query("SELECT new com.green.acamatch.academy.premium.model.PremiumGetRes(" +
            "a.acaId, b.acaName, c.academyPicIds.acaPic, a.startDate, a.endDate, a.preCheck, a.createdAt, " +
            "(SELECT COUNT(p) as countPremium FROM PremiumAcademy p) ) " + // 전체 개수를 가져오는 서브쿼리
            "FROM PremiumAcademy a " +
            "JOIN Academy b ON a.acaId = b.acaId " +
            "JOIN AcademyPic c ON b.acaId = c.academyPicIds.acaId " +
            "ORDER BY a.createdAt")
    List<PremiumGetRes> findAllByPremium(Pageable pageable);

    //프리미엄 학원 조회(배너포함)
    @Query("SELECT new com.green.acamatch.academy.premium.model.PremiumBannerGetRes(" +
            "b.acaId, b.acaName, a.startDate, a.endDate, a.preCheck, c.bannerType, a.createdAt, " +
            "(SELECT COUNT(p) as countPremium FROM PremiumAcademy p) ) " +
            "FROM PremiumAcademy a " +
            "JOIN Academy b ON a.acaId = b.acaId " +
            "LEFT JOIN Banner c ON a.acaId = c.acaId " +
            "order by a.createdAt")
    List<PremiumBannerGetRes> findAllByPremiumAndBannerType(Pageable pageable);

    //프리미엄 학원 삭제
    @Transactional
    @Modifying
    @Query(" delete from PremiumAcademy a where a.acaId = :acaId")
    int deleteByAcaId(@Param("acaId") Long acaId);

    //프리미엄 종료 되기전 acaId 가져오기
    // endDate가 특정 날짜보다 이전인 acaId를 반환하는 메서드
    @Query("SELECT a.acaId FROM PremiumAcademy a WHERE a.endDate <= :now")
    List<Long> findAcaIdsByEndDateBefore(@Param("now") LocalDate now);

    //프리미엄 종료일 이후 삭제
    @Transactional
    @Modifying
    @Query(" delete from PremiumAcademy a where a.endDate <= :now")
    int deletePremiumAcademyByEndDate(@Param("now") LocalDate now);



}
