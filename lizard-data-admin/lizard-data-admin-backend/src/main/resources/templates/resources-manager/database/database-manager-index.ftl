<!DOCTYPE html>
<html>
<head>
    <#include "../../marco/base.ftl"/>
    <title>Lizard-Data-Admin</title>
    <@style/>
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    <@navbar />
    <@sidebar />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-left">
                            <li class="breadcrumb-item"><a href="#">Home</a></li>
                            <li class="breadcrumb-item active">资源管理</li>
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
                    <h3 class="card-title">数据库资源列表</h3>
                </div>

                <!-- /.card-header -->
                <div class="card-body">
                    <form id="id_form_query" class="form-horizontal">
                        <div class="card-body">
                            <div class="form-group row">
                                <label for="id_db_type" class="col-form-label">Type</label>
                                <div class="col-sm-2">
                                    <select class="form-control form-control-sm" id="id_db_type">
                                        <option value="mysql">MySQL</option>
                                    </select>
                                </div>

                                <label for="id_db_name" class="col-form-label">Schema</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control form-control-sm" id="id_db_name"
                                           placeholder="schema name">
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
                <!-- /.card-body -->
            </div>
            <!-- /.card -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- add database modal -->
    <div class="modal fade" id="id_add_modal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title">新增数据库</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <!-- basic info -->
                    <div class="card card-gray">
                        <div class="card-header">
                            Basic Info:
                        </div>

                        <div class="card-body">
                            <form role="form">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="id_form_add_db_type" class="col-form-label">Type</label>
                                            <div class="col-sm-4">
                                                <select class="form-control form-control-sm" id="id_form_add_db_type">
                                                    <option value="mysql">MySQL</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="id_form_add_db_name" class="col-form-label">Schema</label>
                                            <input type="text" class="form-control form-control-sm"
                                                   id="id_form_add_db_name"
                                                   placeholder="schema name">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- address info -->
                    <div class="card card-success">
                        <div class="card-header">
                            Address Info:
                        </div>

                        <div class="card-body">
                            <form role="form">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="id_form_add_db_host" class="col-form-label">Host</label>
                                            <input type="text" class="form-control form-control-sm"
                                                   id="id_form_add_db_host" placeholder="host address">
                                        </div>
                                    </div>

                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="id_form_add_db_port" class="col-form-label">Port</label>
                                            <div class="col-sm-3">
                                                <input type="number" class="form-control form-control-sm" value="3306"
                                                       id="id_form_add_db_port" placeholder="port">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- password info -->
                    <div class="card card-danger ">
                        <div class="card-header">
                            Auth Info:
                        </div>

                        <div class="card-body">
                            <form role="form">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="id_form_add_db_user" class="col-form-label">Username</label>
                                            <input type="text" class="form-control form-control-sm"
                                                   id="id_form_add_db_user" placeholder="username">
                                        </div>
                                    </div>

                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="id_form_add_db_password" class="col-form-label">Password</label>
                                            <input type="text" class="form-control form-control-sm"
                                                   id="id_form_add_db_password" placeholder="password">
                                        </div>
                                    </div>
                                </div>
                            </form>
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

    <!-- databse detail modal -->
    <div class="modal fade" id="id_detail_modal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title">数据库详情</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <dl class="row">
                        <dt class="col-sm-2">Type:</dt>
                        <dd id="id_detail_db_type" class="col-sm-10"></dd>
                        <dt class="col-sm-2">Schema:</dt>
                        <dd id="id_detail_db_name" class="col-sm-10"></dd>
                        <dt class="col-sm-2">Host:</dt>
                        <dd id="id_detail_db_host" class="col-sm-10"></dd>
                        <dt class="col-sm-2">Port:</dt>
                        <dd id="id_detail_db_port" class="col-sm-10"></dd>
                        <dt class="col-sm-2">AuthInfo:</dt>
                        <dd id="id_detail_db_auth" class="col-sm-3"></dd>
                    </dl>

                    <div class="row" id="id_detail_div">
                        <div class="callout callout-danger col-sm-12">
                            <h6><p class="text-danger">Danger!</p></h6>
                            <dl class="row">
                                <dt class="col-sm-2">Username:</dt>
                                <dd id="id_detail_db_username" class="col-sm-10"></dd>
                                <dt class="col-sm-2">Password:</dt>
                                <dd id="id_detail_db_password" class="col-sm-10"></dd>
                            </dl>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <@footer />
</div>
</body>
<@jsfile/>
<script src="/dist/js/pages/resources-manager/database/database-manager-index.js"></script>
</html>