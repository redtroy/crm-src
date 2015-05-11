package com.zijincaifu.crm.enu.personnel;

/**
 * 是否冻结状态(0:冻结,1:未冻结)
 * @author Administrator
 *
 */
public enum PersonnelFreezeStatusEnum {

	FREEZE("冻结",0),UNFREEZE("未冻结",1);
	
	 // 成员变量
    private Integer id;
    
    private String name;
    
    private PersonnelFreezeStatusEnum(String name, Integer id)
    {
        this.name = name;
        this.id = id;
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
