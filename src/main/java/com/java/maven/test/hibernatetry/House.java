package com.java.maven.test.hibernatetry;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * <pre>
 CREATE TABLE house(
 `auto_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 `address` VARCHAR(50) NOT NULL,
 `userId` INT(10) UNSIGNED NOT NULL,
 PRIMARY KEY (`auto_id`) USING BTREE,
 FOREIGN KEY (`userId`) REFERENCES cr_user ( `cuserid` )
 );
 <pre/>
 */
@Entity
@Table(name="house")
public class House implements Serializable {
    private static final long serialVersionUID = 1039543504835087L;
    @Id
    @Column(name = "auto_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "address")
    private String address;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "userId")
    private CrUser user;
    @Transient
    private String aera;

    public CrUser getUser() {
        return user;
    }

    public void setUser(CrUser user) {
        this.user = user;
    }

    /**
     *
     */
    public House() {
    }

    /**
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return
     */
    public String getAera() {
        return aera;
    }


    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @param aera
     */
    public void setAera(String aera) {
        this.aera = aera;
    }

}
