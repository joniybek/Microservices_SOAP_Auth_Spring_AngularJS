<#include "base.ftl">
<#macro scripts>
<script src="/assets/js/vendor/vkbeautify.js"></script>
<script>(function () {
    formatTextArea = function (element) {
        var xml = vkbeautify.xml(element.value, "   ");
        element.value = xml;
        highlight(element, '{{\w+\d*}}', 'highlited');
    }

    highlight = function(container,what,spanClass) {
        var content = container.innerHTML,
                pattern = new RegExp('(>[^<.]*)(' + what + ')([^<.]*)','g'),
                replaceWith = '$1<span ' + ( spanClass ? 'class="' + spanClass + '"' : '' ) + '">$2</span>$3',
                highlighted = content.replace(pattern,replaceWith);
        return (container.innerHTML = highlighted) !== content;
    }
})();</script>
</#macro>
<#macro headerscripts>
<style>
    .form-group {
        margin-bottom: 15px;
    }
</style>
</#macro>
<#macro content>
<div class="container">
    <div class="row">
        <div class="container">
            <h3>
                <center>${title}</center>
            </h3>
            <form class="form" action="/mvc/soap/submit" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <fieldset>
                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-10 control-label" for="name">Message name</label>

                        <div class="col-md-10">
                            <input id="name" name="name" placeholder="" class="form-control input-md"
                                   required="" type="text" value="${msg.name}">

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-10 control-label" for="description">Description</label>

                        <div class="col-md-10">
                        <textarea class="form-control" rows="1" id="description" name="description"
                                  placeholder="">${msg.description}</textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-10 control-label" for="soapAction">Soap action</label>

                        <div class="col-md-10">
                        <textarea class="form-control" rows="1" id="soapAction" name="soapAction"
                                  placeholder="">${msg.soapAction}</textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-10 control-label" for="xml">XML</label>

                        <div class="col-md-10">
                        <textarea onkeyup='formatTextArea(this);' class="form-control" rows="15" id="xml" name="xml">${msg.xml}</textarea>
                        </div>
                    </div>
                    <!-- Button -->
                    <div class="form-group">
                        <label class="col-md-10 control-label" for="submit"></label>

                        <div class="col-md-10">
                            <button id="submit" name="submit" class="btn btn-success">Submit</button>
                        </div>

                    </div>

                </fieldset>
            </form>
        </div>
    </div>
</div>
</#macro>


<#macro content1>

</#macro>
<@display/>
