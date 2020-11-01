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
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-left">
                            <li class="breadcrumb-item"><a href="#">Home</a></li>
                            <li class="breadcrumb-item active">资源管理</li>
                            <li class="breadcrumb-item active">应用管理</li>
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
                    <h3 class="card-title">应用列表</h3>
                </div>

                <!-- /.card-header -->
                <div class="card-body">
                    <form id="id_form_query" class="form-horizontal">
                        <div class="card-body">
                            <div class="form-group row">
                                <label for="id_application_name" class="col-form-label">应用: </label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control form-control-sm" id="id_application_name"
                                           placeholder="application name">
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

    <!-- add database modal -->
    <div class="modal fade" id="id_add_modal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title">新增应用</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label for="id_form_add_application_name" class="col-form-label">应用名称:</label>
                                    <input type="text" class="form-control form-control-sm"
                                           id="id_form_add_application_name">
                                </div>
                            </div>

                            <div class="offset-sm-1 col-sm-4">
                                <div class="form-group">
                                    <label for="id_form_add_owner_name" class="col-form-label">负责人:</label>
                                    <input type="text" class="form-control form-control-sm"
                                           id="id_form_add_owner_name">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label for="id_form_add_application_desc" class="col-form-label">应用描述:</label>
                                    <input type="text" class="form-control form-control-sm"
                                           id="id_form_add_application_desc">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" id="id_form_add_btn">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<@jsfile/>
<script src="/dist/js/pages/resources/application_index.js"></script>
</html>