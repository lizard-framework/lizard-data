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
                    <h3 class="card-title">关系型数据库配置</h3>
                </div>

                <div class="card-body">
                    <form id="id_form_query" class="form-horizontal">
                        <div class="card-body">
                            <div class="form-group row">
                                <label for="id_mixed_datasource" class="col-form-label">MixedName</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control form-control-sm" id="id_mixed_name"
                                           placeholder="">
                                </div>

                                <div class="col-1">
                                    <button type="button" class="btn btn-block btn-primary btn-sm" id="id_btn_query">
                                        查询
                                    </button>
                                </div>

                                <div class="col-1">
                                    <button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
                                            data-target="#id_add_modal">新增
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>

                    <div id="jsGrid1"></div>
                </div>
            </div>
        </section>
    </div>
</div>
</body>

<@jsfile/>
<script src="/dist/js/pages/application_config/orm-config-list.js"></script>
</html>