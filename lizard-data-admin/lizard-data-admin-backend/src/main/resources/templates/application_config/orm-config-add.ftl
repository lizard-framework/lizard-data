<!DOCTYPE html>
<html>
<head>
    <#include "../marco/base.ftl"/>
    <title>Lizard-Data-Admin</title>
    <@style/>
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    <@navbar />
    <@sidebar />

    <div class="content-wrapper">
        <section class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-left">
                            <li class="breadcrumb-item"><a href="#">Home</a></li>
                            <li class="breadcrumb-item active">应用配置</li>
                            <li class="breadcrumb-item active">关系型数据库</li>
                            <li class="breadcrumb-item active">新增</li>
                        </ol>
                    </div>
                    <div class="col-sm-6">
                    </div>
                </div>
            </div><!-- /.container-fluid -->
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">新增关系型数据库应用配置</h3>
                </div>

                <div class="card-body pl-sm-5">
                    <form id="id_form_query" class="form-horizontal">
                        <div class="form-group row">
                            <div class="col-sm-1">
                                <label for="id_mixed_name" class="col-form-label">名称：</label>
                            </div>
                            <div class="col-sm-4">
                                <input class="form-control" placeholder="Mixed DataSource Name">
                            </div>

                            <div class="offset-sm-1 col-sm-1">
                                <label for="id_mixed_state"
                                       class="col-form-label offset-sm-1">状态：</label>
                            </div>
                            <div class="col-sm-4">
                                <input type="checkbox" name="my-checkbox" checked
                                       data-bootstrap-switch readonly
                                       id="id_mixed_state">
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-sm-1">
                                <label for="id_mixed_desc" class="col-form-label">描述：</label>
                            </div>
                            <div class="col-sm-4">
                                <textarea class="form-control"></textarea>
                            </div>

                            <div class="offset-sm-1 col-sm-1">
                                <label for="id_mixed_dbType" class="col-form-label">数据库：</label>
                            </div>
                            <div class="col-sm-1">
                                <select class="form-control">
                                    <option value="mysql">MYSQL</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-sm-1">
                                <label for="id_mixed_application" class="col-form-label">应用：</label>
                            </div>
                            <div class="col-sm-4">
                                <select id="id_mixed_application" class="select2"
                                        multiple="multiple" style="width: 100%;">
                                    <#if all_application_list?? && (all_application_list?size > 0)>
                                        <#list all_application_list as application>
                                            <option value="${application.applicationName}">${application.applicationName}</option>
                                        </#list>
                                    </#if>
                                </select>
                            </div>
                        </div>
                    </form>

                    <h5 class="mt-4 mb-3">分库配置(Repository Sharding):</h5>
                    <div class="row">
                        <div class="col-1">
                            <button type="button" class="btn btn-success btn-sm" id="id_btn_add">+</button>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
</body>

<@jsfile/>
</html>