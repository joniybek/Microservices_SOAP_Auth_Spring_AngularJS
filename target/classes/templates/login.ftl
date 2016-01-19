<#include "base.ftl">
<#macro headscripts>
<style>
    body {
        background: url(http://images1.fanpop.com/images/image_uploads/Golden-Gate-Bridge-san-francisco-1020074_1024_768.jpg) no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }

    .panel-default {
        opacity: 0.9;
        margin-top: 30px;
    }

    .form-group.last {
        margin-bottom: 0px;
    }
</style>
</#macro>

<#macro content>

<div class="container">

    <div class="row">
        <div class="col-md-4">
            <h1>Login</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4 col-md-offset-7">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span class="glyphicon glyphicon-lock"></span> Login
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" action="/login" method="post">
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">
                                Username</label>

                            <div class="col-sm-9">
                                <input type="text" name="username" class="form-control" id="username"
                                       placeholder="Username or Email" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label">
                                Password</label>

                            <div class="col-sm-9">
                                <input type="password" class="form-control" name="password" id="password"
                                       placeholder="Password"
                                       required>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox"/>
                                        Remember me
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group last">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button type="submit" class="btn btn-success btn-sm">
                                    Sign in
                                </button>
                                <button type="reset" class="btn btn-default btn-sm">
                                    Reset
                                </button>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </div>
                <div class="panel-footer">
                    Not Registred? <a href="">Register here</a></div>
            </div>
        </div>
    </div>
</div>

</#macro>
<@display/>