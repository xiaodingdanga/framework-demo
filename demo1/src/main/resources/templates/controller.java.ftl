package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
<#if restControllerStyle>
<#else>
    import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
/**
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
@RestController
<#else>
@Controller</#if>
@RequestMapping("/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>

    @Autowired
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @GetMapping("/selectOne")
<#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:list")-->
    public ${table.entityName} get${table.entityName}(@RequestParam("id") Integer id){
        ${table.entityName} ${table.entityName?uncap_first}One = ${table.entityName?uncap_first}Service.get${table.entityName}(id);
        return ${table.entityName?uncap_first}One;
    }

    @GetMapping("/listAll")
<#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:list")-->
    public List<${table.entityName}> getAll${table.entityName}(){
        List<${table.entityName}> ${table.entityName?uncap_first}List = ${table.entityName?uncap_first}Service.getAll${table.entityName}();
        return ${table.entityName?uncap_first}List;
    }

    @PostMapping("/add")
<#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:add")-->
    public Object add(@Validated @RequestBody ${table.entityName} ${table.entityName?uncap_first}) {
        ${table.entityName?uncap_first}Service.add( ${table.entityName?uncap_first});
        return null;
    }

    @PutMapping("/update")
<#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:update")-->
    public int update(@Validated @RequestBody ${table.entityName} ${table.entityName?uncap_first}) {
        int num = ${table.entityName?uncap_first}Service.modify(${table.entityName?uncap_first});
        return num;
    }

    @DeleteMapping(value = "/delete/{ids}")
<#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:delete")-->
    public Object remove(@NotBlank(message = "{required}") @PathVariable String ids) {
        ${table.entityName?uncap_first}Service.remove(ids);
        return null;
    }
}
</#if>
