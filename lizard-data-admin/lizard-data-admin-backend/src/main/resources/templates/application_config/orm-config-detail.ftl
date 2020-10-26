<!DOCTYPE html>
<html>
<head>
    <#include "../marco/base.ftl"/>
    <title>Lizard-Data-Admin</title>
    <@style/>
    <!-- highlight -->
    <link rel="stylesheet" href="/plugins/highlight/styles/solarized-dark.css">
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
                            <li class="breadcrumb-item active">配置详情</li>
                        </ol>
                    </div>
                    <div class="col-sm-6">
                    </div>
                </div>
            </div><!-- /.container-fluid -->
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <div class="card">
                    <div class="card-body">
                        <ul class="nav nav-tabs" id="custom-content-above-tab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="custom-content-above-home-tab" data-toggle="pill"
                                   href="#custom-content-above-home" role="tab"
                                   aria-controls="custom-content-above-home"
                                   aria-selected="true">详情</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="custom-content-above-profile-tab" data-toggle="pill"
                                   href="#custom-content-code-spring-home" role="tab"
                                   aria-controls="custom-content-code-spring-home"
                                   aria-selected="false">代码示例(Spring)</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="custom-content-code-spring-tab" data-toggle="pill"
                                   href="#custom-content-above-profile" role="tab"
                                   aria-controls="custom-content-above-profile"
                                   aria-selected="false">代码示例(Spring Boot)</a>
                            </li>
                        </ul>
                        <div class="tab-custom-content">
                            <p class="lead mb-0">MixedName:XXX</p>
                        </div>
                        <div class="tab-content" id="custom-content-above-tabContent">
                            <div class="tab-pane fade show active" id="custom-content-above-home" role="tabpanel"
                                 aria-labelledby="custom-content-above-home-tab">
                                <div class="card-body">
                                    <form id="id_form_query" class="form-horizontal">
                                        <div class="form-group row">
                                            <div class="col-sm-1">
                                                <label for="id_mixed_name" class="col-form-label">名称：</label>
                                            </div>

                                            <div class="col-sm-4">
                                                <p class="text-success" class="form-control form-control-sm"
                                                   id="id_mixed_name">TestMixedDataSource</p>
                                            </div>

                                            <div class="offset-sm-1 col-sm-1">
                                                <label for="id_mixed_state"
                                                       class="col-form-label offset-sm-1">状态：</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <input type="checkbox" name="my-checkbox" data-on-label="Online" checked
                                                       data-bootstrap-switch readonly
                                                       id="id_mixed_state">
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <div class="col-sm-1">
                                                <label for="id_mixed_desc" class="col-form-label">描述：</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <p class="text-muted" class="form-control form-control-sm"
                                                   id="id_mixed_desc">
                                                    text-infotext-infotext-infotext-infotext-infotext-infotext-infotext-infotext-infotext-infotext-infotext-info</p>
                                            </div>

                                            <div class="offset-sm-1 col-sm-1">
                                                <label for="id_mixed_dbType" class="col-form-label">数据库：</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <p class="text-muted" class="form-control form-control-sm"
                                                   id="id_mixed_dbType">MySQL</p>
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <div class="col-sm-1">
                                                <label for="id_mixed_application" class="col-form-label">应用：</label>
                                            </div>
                                            <div class="col-sm-5">
                                                <select id="id_mixed_application" class="select2" multiple="multiple"
                                                        data-placeholder="Select a State" style="width: 100%;">
                                                    <option value="Alabama">Alabama</option>
                                                    <option value="Alaska">Alaska</option>
                                                    <option>California</option>
                                                    <option>Delaware</option>
                                                    <option>Tennessee</option>
                                                    <option>Texas</option>
                                                    <option>Washington</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <div class="col-sm-1">
                                                <label for="id_mixed_create_user" class="col-form-label">创建人：</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <p class="text-info" class="form-control form-control-sm"
                                                   id="id_mixed_create_user">xueqi</p>
                                            </div>

                                            <div class="offset-sm-1 col-sm-1">
                                                <label for="id_mixed_create_time" class="col-form-label">创建时间：</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <p class="text-info" class="form-control form-control-sm"
                                                   id="id_mixed_create_time">yyyy-mm-dd hh:mm:ss</p>
                                            </div>
                                        </div>
                                    </form>

                                    <h5 class="mt-4 mb-3">分库配置(Repository Sharding):</h5>
                                    <div id="accordion">
                                        <div class="card card-default">
                                            <div class="card-header">
                                                <h4 class="card-title">
                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                       href="#collapseOne">
                                                        Repository Name: db_test
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseOne" class="panel-collapse collapse in">
                                                <h6 class="mt-4 mb-1 ml-4">资源配置(Atom Config):</h6>
                                                <div class="card-body">
                                                    <table class="table table-head-fixed text-nowrap">
                                                        <thead>
                                                        <tr>
                                                            <th>Atom名称</th>
                                                            <th>数据库名称</th>
                                                            <th>主从类型</th>
                                                            <th>状态</th>
                                                            <th>权重</th>
                                                            <th>连接池类型</th>
                                                            <th>JDBC配置</th>
                                                            <th>连接池配置</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                            <td>master01</td>
                                                            <td>db_test</td>
                                                            <td>master</td>
                                                            <td>online</td>
                                                            <td>100</td>
                                                            <td>druid</td>
                                                            <td>useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&allowMultiQueries=true</td>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="tab-pane fade" id="custom-content-code-spring-home" role="tabpanel"
                                 aria-labelledby="custom-content-code-spring-tab">
                            <pre>
                                <code class="java">
public class Test {
    public static void main() {
    }
}
                                </code>
                            </pre>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
</body>

<@jsfile/>
<!-- highlight -->
<script src="/plugins/highlight/highlight.pack.js"></script>
<script>hljs.initHighlightingOnLoad();</script>
<script src="/dist/js/pages/application_config/orm-config-detail.js"></script>
</html>