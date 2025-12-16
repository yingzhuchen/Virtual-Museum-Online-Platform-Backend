package org.openapitools.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_info_sub")
public class UserSub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_info_sub_id")
    private Long userSubId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "motto")
    private String motto;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "address")
    private String address;

}
