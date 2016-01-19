<#include "base.ftl">
<#import "spring.ftl" as spring />
<#macro content>
<div class="container">
<#--<@spring.bind "model" />-->
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <br>
                <table id="data" class="table table-bordered table-stripped">
                    <thead>
                    <tr>
                        <th>
                            Username
                        </th>
                        <#list resources as resource>
                            <th class="col-xs-1">
                            ${resource.name}
                            </th>
                        </#list>
                    </tr>
                    </thead>

                    <tbody>
                        <#list data?keys as username>
                        <tr class="even">
                            <td>
                            ${username}
                            </td>
                            <#list data[username]?keys as role>
                                <td>
                                    <#if data[username][role]=="">
                                        <input type="checkbox" class=" "
                                               onclick="toggleAndSave(this,'${username}' ,'${role}')">
                                    <#elseif data[username][role]=="reader">
                                        <input type="checkbox" checked="checked" class="tristate"
                                               onclick="toggleAndSave(this,'${username}', '${role}')">
                                    <#else>
                                        <input type="checkbox" checked="checked" class=""
                                               onclick="toggleAndSave(this,'${username}', '${role}')">
                                    </#if>
                                <#--${data[username][role]}-->
                                </td>
                            </#list>

                        </tr>
                        </#list>

                    </tbody>

                </table>
            </div>
        </div>
    </div>
</div>
</#macro>

<#macro scripts>
<script>
    var containsClass = function (element, cls) {
        return (' ' + element.className + ' ').indexOf(' ' + cls + ' ') > -1;
    }

    var promote = function (role, resource, user) {
        $.ajax({
            url: "/users/promote?role=" + role + "&resource=" + resource + "&user=" + user,
            cache: false
        });
    }

    toggleAndSave = function (e, resource, user) {
        el = e;
        if (!e.checked) {
            e.className += "tristate";
            console.log("to reader" + e.checked);
            e.setAttribute("checked", "checked");
            promote("reader", resource, user);
        } else if (containsClass(e, "tristate")) {
            console.log("to admin" + e.checked);
            e.className.replace('tristate', '');
            promote("admin", resource, user);
        } else {
            console.log("to none " + e.checked);
            e.removeAttribute("checked");
            promote("", resource, user);
        }
        return false;
    }
</script>
</#macro>
<style>
    table th {
        width: auto !important;
    }

    .tristate {
        opacity: 0.5;
        filter: alpha(opacity=50);
    }

</style>
<@display/>