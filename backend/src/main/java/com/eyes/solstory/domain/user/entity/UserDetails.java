package com.eyes.solstory.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User_details")
public class UserDetails {	

	@ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @Column(name = "attribute_type", nullable = false)
    private int attributeType;

    @Column(name = "attribute_value", nullable = false, length = 50)
    private String attributeValue;

}