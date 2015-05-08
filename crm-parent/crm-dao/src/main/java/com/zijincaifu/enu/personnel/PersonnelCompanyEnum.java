package com.zijincaifu.enu.personnel;

/**
 * 所属公司
 * @author Administrator
 *
 */
public enum PersonnelCompanyEnum {

	JITUAN("集团总部",0),NANJING("南京",1),SUZHOU("苏州",2),YANGZHOU("扬州",3),CHANGZHOU("常州",4);
	
	// 成员变量
    private Integer id;
    
    private String name;
    
    private PersonnelCompanyEnum(String name,Integer id){
    	this.name=name;
    	this.id=id;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
