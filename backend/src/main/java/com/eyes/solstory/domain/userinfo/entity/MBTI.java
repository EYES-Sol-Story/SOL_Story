
package com.eyes.solstory.domain.userinfo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_info")
@SequenceGenerator(
    name = "user_info_seq_generator",
    sequenceName = "user_info_seq",
    allocationSize = 1
)
public class MBTI {

    @Id
    @Column(name="user_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_info_seq_generator")
    private int userNo;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "mbti", nullable = false, length = 4)
    private String mbti;

    // 기타 필요한 필드들 추가 가능
}
