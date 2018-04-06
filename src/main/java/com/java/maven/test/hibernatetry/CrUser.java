package com.java.maven.test.hibernatetry;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * <pre>
 CREATE TABLE cr_user(
 `cuserid` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 `usercode` VARCHAR(10) NOT NULL,
 `username` VARCHAR(20) NOT NULL,
 PRIMARY KEY (`cuserid`) USING BTREE,
 </pre>
 )
 */
@Entity
@Table(name="cr_user")
public class CrUser implements Serializable {
    private static final long serialVersionUID = 5345245254235252L;
    @Id
    @Column(name = "cuserid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "usercode")
    private String code;
    @Column(name = "username")
    private String name;
    @Column(name = "createdtime")
    private String createdTime;
    @OneToMany(mappedBy = "user")//,cascade ={CascadeType.ALL})
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<House> houses=new HashSet<House>();
    //提供默认的构造方法
    public CrUser(){

    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<House> getHouses() {
        return houses;
    }

    public void setHouses(Set<House> houses) {
        this.houses = houses;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
