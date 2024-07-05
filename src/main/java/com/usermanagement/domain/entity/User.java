package com.usermanagement.domain.entity;

import com.commonlib.domain.entity.AbstractEntity;
import com.commonlib.util.converter.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;


@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@RequiredArgsConstructor
//@JsonInclude(value = NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User /*extends AbstractEntity*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
    @SequenceGenerator(name = "userSeqGen", sequenceName = "usr_seq", allocationSize = 1)
    private Long id;
    private String name;

    @Column(name = "created_date", nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_date")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

}
