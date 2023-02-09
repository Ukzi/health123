package com.health.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.health.constant.ProgramSellStatus;
import com.health.dto.MainProgramDto;
import com.health.dto.ProgramSearchDto;
import com.health.entity.Program;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ProgramRepositoryCustomImpl implements ProgramRepositoryCustom  {

	private JPAQueryFactory queryFactory;
	
	public ProgramRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now(); 
		
		//현재 날짜로 부터 이전 날짜를 구해준다.
		if(StringUtils.equals("all", searchDateType) || searchDateType == null)  return null;
		else if(StringUtils.equals("1d", searchDateType)) dateTime = dateTime.minusDays(1); 
		else if(StringUtils.equals("1w", searchDateType)) dateTime = dateTime.minusWeeks(1);
		else if(StringUtils.equals("1m", searchDateType)) dateTime = dateTime.minusMonths(1);
		else if(StringUtils.equals("6m", searchDateType)) dateTime = dateTime.minusMonths(6);
		
		return QItem.item.regTime.after(dateTime); //이후의 시간
	}
	
	private BooleanExpression searchSellStatusEq(ProgramSellStatus searchSellStatus) {
		return searchSellStatus == null ? null : QItem.program.programSellStatus.eq(searchSellStatus);
	}
	
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("programNm", searchBy)) {
			return QItem.program.programNm.like("%" + searchQuery + "%"); //itemNm LIKE %청바지%
		} else if(StringUtils.equals("createdBy", searchBy)) {
			return QItem.program.createdBy.like("%" + searchQuery + "%"); //createdBy LIKE %test.com%
		}
		
		return null;
	}
	
	
	@Override
	public Page<Program> getAdminItemPage(ProgramSearchDto programSearchDto, Pageable pageable) {
		List<Program> content = queryFactory
				.selectFrom(QItem.program) //select * from item
				.where(regDtsAfter(programSearchDto.getSearchDateType()), // where reg_time = ?
					   searchSellStatusEq(programSearchDto.getSearchSellStatus()), //and sell_status = ?
					   searchByLike(programSearchDto.getSearchBy(), programSearchDto.getSearchQuery())) // and itemNm LIKE %검색어%
				.orderBy(QItem.program.programId.desc())
				.offset(pageable.getOffset()) //데이터를 가져올 시작 index
				.limit(pageable.getPageSize()) //한번에 가지고 올 최대 개수
				.fetch();
		
		//https://querydsl.com/static/querydsl/4.1.0/apidocs/com/querydsl/core/types/dsl/Wildcard.html
		// Wildcard.count = count(*)
		long total = queryFactory.select(Wildcard.count).from(QItem.item)
                .where(regDtsAfter(programSearchDto.getSearchDateType()),
                        searchSellStatusEq(programSearchDto.getSearchSellStatus()),
                        searchByLike(programSearchDto.getSearchBy(), programSearchDto.getSearchQuery()))
                .fetchOne();
		
		
		return new PageImpl<>(content, pageable, total);
	}
	
    private BooleanExpression programNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QItem.program.programNm.like("%" + searchQuery + "%");
    }
    
    @Override
    public Page<MainProgramDto> getMainProgramPage(ProgramSearchDto programSearchDto, Pageable pageable) {
        QItem program = QItem.program;
        QItemImg programImg = QItemImg.programImg;

        List<MainProgramDto> content = queryFactory
                .select(
                        new QMainItemDto(
                        		program.programId,
                        		program.programNm,
                        		program.programDetail,
                        		programImg.imgUrl,
                        		program.price)
                )
                .from(programImg)
                .join(programImg.program, program)
                .where(programImg.repimgYn.eq("Y"))
                .where(programNmLike(programSearchDto.getSearchQuery()))
                .orderBy(program.programId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(programImg)
                .join(programImg.program, program)
                .where(programImg.repimgYn.eq("Y"))
                .where(programNmLike(programSearchDto.getSearchQuery()))
                .fetchOne()
                ;

        return new PageImpl<>(content, pageable, total);
    }
	
	
}
