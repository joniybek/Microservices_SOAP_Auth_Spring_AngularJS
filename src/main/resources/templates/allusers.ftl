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
                                               onclick="save(this, event,'${role}', '${username}' )">
                                    <#elseif data[username][role]=="reader">
                                        <input type="checkbox" checked="checked" class="tristate"
                                               onclick="save(this, event , '${role}','${username}')">
                                    <#else>
                                        <input type="checkbox" checked="checked" class=""
                                               onclick="save(this, event, '${role}','${username}')">
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
    var getSessionId = function () {
        return document.cookie.match(/sessionId=\w+/g)[0].split('=')[1];
    }

    var promote = function (role, resource, user) {
        var s = getSessionId();
        $.post("/api/auth/users/promote",
                {
                    role: role,
                    resource: resource,
                    user: user,
                    sessionId: s
                },
                function (data, status) {
                    console.log('updated');
                });
    }

    $(function () {
        save = function (elm, event, resource, user) {
            if ($(elm).is(":checked")) {
                console.log('not checked');
                promote('reader', resource, user);
                $(elm).addClass('tristate');
            } else if ($(elm).hasClass('tristate')) {
                console.log('reader');
                promote('admin', resource, user);
                event.preventDefault();
                $(elm).removeClass('tristate');
            } else {
                console.log('admin');
                promote('', resource, user);
            }
        };
    });

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