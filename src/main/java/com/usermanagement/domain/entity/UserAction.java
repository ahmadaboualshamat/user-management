package com.usermanagement.domain.entity;

import com.commonlib.domain.entity.AbstractEntity;
import com.commonlib.util.converter.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.usermanagement.domain.converter.UserConverter;
import com.commonlib.domain.enumration.ActionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;


@Data
@Entity
@Table(name = "act_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = NON_EMPTY)
public class UserAction /*extends AbstractEntity*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actUserSeqGen")
    @SequenceGenerator(name = "actUserSeqGen", sequenceName = "actusr_seq", allocationSize = 1)
    private Long id;
    private Long userId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ActionType userActionType;


    @Column(name = "user_info")
//    @Convert(converter = UserConverter.class)
    private String userInfo;

    @Column(name = "technical_remarks")
    private String technicalRemarks;

//    @CreatedDate
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

}
