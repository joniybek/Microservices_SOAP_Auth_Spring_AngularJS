<#include "base.ftl">
<#import "spring.ftl" as spring />
<#macro content>
<div class="container">
    <#--<@spring.bind "model" />-->
    <#if RequestParameters.info??>
        <div class="alert alert-info" role="alert"> ${RequestParameters.info}</div>
    <#--<#elseif model.danger??>-->
        <#--<div class="alert alert-danger" role="alert"> ${mode.danger}</div>-->
    </#if>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <table id="data" class="table table-bordered">
                    <thead>
                    <tr>
                        <th>
                            Name
                        </th>
                        <th>
                            Description
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list soapxmls as soap>
                            <#if soap?is_even_item>
                            <tr class="even">
                                <td>
                                ${soap.name}
                                </td>
                                <td>
                                ${soap.description}
                                </td>
                                <td class="id" style="display:none;">${soap.xml_id}</td>
                            </tr>
                            <#else>
                            <tr>
                                <td>
                                ${soap.name}
                                </td>
                                <td>
                                ${soap.description}
                                </td>
                                <td class="id" style="display:none;">${soap.xml_id}</td>
                            </tr>

                            </#if>
                        </#list>

                    </tbody>
                </table>
            </div>
            <a href="/mvc/soap/new" class="btn btn-success">New</a>
            <button onclick="editMessage();" type="button" class="btn btn-info">Edit</button>
            <button onclick="deleteMessage();" type="button" class="btn btn-danger">Delete</button>
        </div>
    </div>
</div>
</#macro>

<#macro scripts>
<script>
    $("#data tr").click(function () {
        var selected = $(this).hasClass("highlight");
        $("#data tr").removeClass("highlight");
        if (!selected)
            $(this).addClass("highlight");
    });

    findIdInHighlighted = function () {
        return $("#data tr.highlight td.id").text();
    }

    deleteMessage = function () {
        var id = findIdInHighlighted();
        if (id === "") {
            alert("Please select one of the rows!(just click on it)");
            return;
        }
        window.location = "/mvc/soap/delete?id=" + id;
    }
    var editMessage = function () {
        var id = findIdInHighlighted();
        if (id === "") {
            alert("Please select one of the rows!(just click on it)");
            return;
        }
        window.location = "/mvc/soap/edit?id=" + id;
    }
</script>
</#macro>
<style>
    .even {
        background-color: whitesmoke;
    }

    .highlight {
        background-color: #46b8da;
    }
</style>
<@display/>