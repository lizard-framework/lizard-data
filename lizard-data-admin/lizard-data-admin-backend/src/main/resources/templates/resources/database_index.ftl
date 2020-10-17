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
                            <li class="breadcrumb-item active">数据库资源</li>
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
                    <form class="form-horizontal">
                        <div class="card-body">
                            <div class="form-group row">
                                <label for="id_db_type" class="col-form-label">Type</label>
                                <div class="col-sm-1">
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
                                    <button type="button" class="btn btn-block btn-primary btn-sm" id="id_btn_query">查询</button>
                                </div>

                                <div class="col-1">
                                    <button type="button" class="btn btn-danger btn-sm">新增</button>
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

    <@footer />
</div>
</body>
<@jsfile/>
<script src="/dist/js/pages/resources/database_index.js"></script>
</html>