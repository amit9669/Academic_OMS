package com.academic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name = "university_new")
public class UniversityNew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unv_id")
    private Long unvId;

    @Column(name="name")
    private String name;

    @Column(name="alpha_two_code")
    private String code;

    @Column(name="state-province")
    private String stateProvince;

    @Column(name="country")
    private String country;

    @Column(name="web_pages")
    private String webUrl;

    @Column(name="domains")
    private String domains;
}
