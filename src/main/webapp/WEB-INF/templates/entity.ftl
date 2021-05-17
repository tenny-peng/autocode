package com.starcare.ecg.${entityName?uncap_first};

import java.util.Date;
import com.starcare.ecg.base.BaseEntity;

public class ${entityName}Entity extends BaseEntity {
    
<#list params as param>
	// ${param.fieldNote}
    private ${param.fieldType} ${param.fieldName};
    
</#list>
<#list params as param>
	public void set${param.fieldName?cap_first}(${param.fieldType} ${param.fieldName}){
        this.${param.fieldName} = ${param.fieldName};
    }
    
    public ${param.fieldType} get${param.fieldName?cap_first}(){
        return this.${param.fieldName};
    }
    
</#list>
}