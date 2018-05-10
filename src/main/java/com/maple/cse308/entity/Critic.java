package com.maple.cse308.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Critic")
public class Critic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "criticId")
    private Integer criticId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "middleName")
    private String middleName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "website")
    private String website;

    @Column(name = "photo")
    private String photo;

    @Column(name = "topCritic")
    private Boolean isTopCritic;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "criticId")
    private Set<Publications> publishers = new HashSet();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "criticGroupsTable", joinColumns = @JoinColumn(name = "criticId"), inverseJoinColumns = @JoinColumn(name = "groupId"))
    private Set<Groups> groups;

    public Integer getCriticId() {
        return criticId;
    }

    public void setCriticId(Integer criticId) {
        this.criticId = criticId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Set<Publications> getPublisher() {
        return publishers;
    }

    public void setPublishers(Set<Publications> publishers) {
        this.publishers = publishers;
    }

    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    public Boolean getIsTopCritic() {
        return this.isTopCritic;
    }

    public void setIsTopCritic(Boolean isTopCritic) {
        this.isTopCritic = isTopCritic;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
