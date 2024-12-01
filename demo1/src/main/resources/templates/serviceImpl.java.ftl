package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Arrays;
/**
* ${table.comment!}
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
    open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

    }
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Autowired
    ${table.entityName}Mapper ${table.entityName?uncap_first}Mapper;

    @Override
    public ${table.entityName} get${table.entityName}(Integer id){
        return ${table.entityName?uncap_first}Mapper.selectById(id);
    }

    @Override
    public List<${table.entityName}> getAll${table.entityName}(){
        return ${table.entityName?uncap_first}Mapper.selectList(null);
    }

    @Override
    public void add(${table.entityName} ${table.entityName?uncap_first}) {
        ${table.entityName?uncap_first}Mapper.insert(${table.entityName?uncap_first});
    }

    @Override
    public int modify(${table.entityName} ${table.entityName?uncap_first}) {
        return  ${table.entityName?uncap_first}Mapper.updateById(${table.entityName?uncap_first});
    }

    @Override
    public void remove(String ids) {
        if(StringUtils.isNotEmpty(ids)){
            String[] array = ids.split(",");
            if (!CollectionUtils.isEmpty(Arrays.asList(array))) {
                ${table.entityName?uncap_first}Mapper.deleteBatchIds(Arrays.asList(array));
            }
        }
    }
}
</#if>
