package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import java.util.List;

/**
* @author ${author}
* @since ${date}
*/
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
    * ${table.entityName!}
    * @param
    * @return
    */
    ${table.entityName} get${table.entityName}(Integer id);

    /**
    * ${table.entityName!}
    * @param
    * @return
    */
    List<${table.entityName}> getAll${table.entityName}();

    /**
    * ${table.entityName!}
    * @param ${table.entityName?uncap_first}
    * @return
    */
    void add(${entity} ${table.entityName?uncap_first});

    /**
    * ${table.entityName!}
    * @param ${table.entityName?uncap_first}
    * @return
    */
    int modify(${entity} ${table.entityName?uncap_first});

    /**
    * ${table.entityName!}
    * @param ids
    * @return
    */
    void remove(String ids);
}

</#if>

