package com.pe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter // getter 메소드 생성
@Builder // 빌더를 사용할 수 있게 함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_memo")
public class Memo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 200, nullable = false)
	private String memoTextselet;
	
	public Memo(Long id, String memoTextselet) {
        this.id = id;
        this.memoTextselet = memoTextselet;
    }

}
