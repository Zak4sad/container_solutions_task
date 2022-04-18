package com.bezkoder.spring.datajpa.model;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "people")
public class People {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", updatable = false, nullable = false)
    private String uuid;

	@Column(name = "survived")
	private boolean survived;
	
	@Column(name = "passengerClass")
	private Integer passengerClass;

	@Column(name = "name")
	private String name;

	@Column(name = "sex")
	private String sex;

	@Column(name = "age")
	private Integer age;

	@Column(name = "siblingsOrSpousesAboard")
	private Integer siblingsOrSpousesAboard;

	@Column(name = "parentsOrChildrenAboard")
	private Integer parentsOrChildrenAboard;

	@Column(name = "fare")
	private Double fare;

	public People() {

	}

	public People(boolean survived,Integer passengerClass,String name,String sex,Integer age,Integer siblingsOrSpousesAboard,Integer parentsOrChildrenAboard,Double fare) {
		this.survived = survived;
		this.passengerClass = passengerClass;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.siblingsOrSpousesAboard = siblingsOrSpousesAboard;
		this.parentsOrChildrenAboard = parentsOrChildrenAboard;
		this.fare = fare;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean isSurvived() {
		return survived;
	}

	public void setSurvived(boolean isSurvived) {
		this.survived = isSurvived;
	}

	public Integer getPassengerClass() {
		return passengerClass;
	}

	public void setPassengerClass(Integer passengerClass) {
		this.passengerClass = passengerClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSiblingsOrSpousesAboard() {
		return siblingsOrSpousesAboard;
	}

	public void setSiblingsOrSpousesAboard(Integer siblingsOrSpousesAboard) {
		this.siblingsOrSpousesAboard = siblingsOrSpousesAboard;
	}

	public Integer getParentsOrChildrenAboard() {
		return parentsOrChildrenAboard;
	}

	public void setParentsOrChildrenAboard(Integer parentsOrChildrenAboard) {
		this.parentsOrChildrenAboard = parentsOrChildrenAboard;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	@Override
	public String toString() {
		return "People [ uuid=" + uuid + ",passengerClass=" + passengerClass + ",name=" + name + ", sex=" + sex + ", age=" + age + ", siblingsOrSpousesAboard=" + siblingsOrSpousesAboard + ", parentsOrChildrenAboard=" + parentsOrChildrenAboard + ",fare=" + fare + "]";
	}

}
