package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.health.entity.Program;

//JpaRepository : 기본적인 CRUD 및 페이징 처리를 위한 메소드가 정의가 되어있다.
//JpaRepository<사용할 엔티티 클래스, 기본키 타입>
public interface ProgramRepository extends JpaRepository<Program, Long>, 
     QuerydslPredicateExecutor<Program>, ProgramRepositoryCustom {
	//select * from item where item_nm = ?
	List<Program> findByProgramNm(String programNm);
	
	//select * from item where item_nm = ? or item_detail = ?
	List<Program> findByProgramNmOrProgramDetail(String ProgramNm, String ProgramDetail);
	
	//select * from item where price < ?
	List<Program> findByPriceLessThan(Integer price);
	
	//select * from item where price < ? order by price desc
	List<Program> findByPriceLessThanOrderByPriceDesc(Integer price);
	
	@Query("select i from Program i where i.programDetail like %?1% order by i.price desc")
	List<Program> findByProgramDetail(String itemDetail);
	
	@Query(value = "select * from item i where i.program_detail like %:programDetail% order by i.price desc", nativeQuery = true)
	List<Program> findByProgramDetailByNative(@Param("programDetail") String programDetail);
	

}