package com.atguigu.gmall.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class UmsMember implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String  id;
    private Long member_level_id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String status;
    private Date create_time;
    private String icon;
    private String gender;
    private Date birthday;
    private String city;
    private String job;
    private String personalized_signature;
    private Long source_uid;
    private String source_type;
    private String integration;
    private String growth;

    public UmsMember() {
    }

    public UmsMember(String id, Long member_level_id, String username, String password, String nickname, String phone, String status, Date create_time, String icon, String gender, Date birthday, String city, String job, String personalized_signature, Long source_uid, String source_type, String integration, String growth) {
        this.id = id;
        this.member_level_id = member_level_id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.status = status;
        this.create_time = create_time;
        this.icon = icon;
        this.gender = gender;
        this.birthday = birthday;
        this.city = city;
        this.job = job;
        this.personalized_signature = personalized_signature;
        this.source_uid = source_uid;
        this.source_type = source_type;
        this.integration = integration;
        this.growth = growth;
    }

    public String getId() {
        return id;
    }

    public Long getMember_level_id() {
        return member_level_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public String getIcon() {
        return icon;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getCity() {
        return city;
    }

    public String getJob() {
        return job;
    }

    public String getPersonalized_signature() {
        return personalized_signature;
    }

    public Long getSource_uid() {
        return source_uid;
    }

    public String getSource_type() {
        return source_type;
    }

    public String getIntegration() {
        return integration;
    }

    public String getGrowth() {
        return growth;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMember_level_id(Long member_level_id) {
        this.member_level_id = member_level_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPersonalized_signature(String personalized_signature) {
        this.personalized_signature = personalized_signature;
    }

    public void setSource_uid(Long source_uid) {
        this.source_uid = source_uid;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public void setIntegration(String integration) {
        this.integration = integration;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }
}
