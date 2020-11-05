<!DOCTYPE html>
<html>
<head>
    <#include "../../marco/base.ftl"/>
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
                                   href="#custom-content-code-springboot-home" role="tab"
                                   aria-controls="custom-content-code-springboot-home"
                                   aria-selected="false">代码示例(Spring Boot)</a>
                            </li>
                        </ul>
                        <div class="tab-custom-content">
                            <p class="lead mb-0">MixedName:${detail.mixedName}</p>
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
                                                   id="id_mixed_name">${detail.mixedName}</p>
                                            </div>

                                            <div class="offset-sm-1 col-sm-1">
                                                <label for="id_mixed_state"
                                                       class="col-form-label offset-sm-1">状态：</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <#if detail.state == "ONLINE">
                                                    <input type="checkbox" name="my-checkbox" checked
                                                           data-bootstrap-switch readonly
                                                           id="id_mixed_state">
                                                <#else>
                                                    <input type="checkbox" name="my-checkbox"
                                                           data-bootstrap-switch readonly
                                                           id="id_mixed_state">
                                                </#if>
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <div class="col-sm-1">
                                                <label for="id_mixed_desc" class="col-form-label">描述：</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <p class="text-muted" class="form-control form-control-sm"
                                                   id="id_mixed_desc">${detail.mixedDesc}</p>
                                            </div>

                                            <div class="offset-sm-1 col-sm-1">
                                                <label for="id_mixed_dbType" class="col-form-label">数据库：</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <p class="text-muted" class="form-control form-control-sm"
                                                   id="id_mixed_dbType">${detail.type}</p>
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <div class="col-sm-1">
                                                <label for="id_mixed_application" class="col-form-label">应用：</label>
                                            </div>
                                            <div class="col-sm-5">
                                                <#if detail.applicationList?? && (detail.applicationList?size>0)>
                                                    <select id="id_mixed_application" class="select2"
                                                            multiple="multiple" style="width: 100%;">
                                                        <#list detail.applicationList as application>
                                                            <option value="${application}"
                                                                    selected>${application}</option>
                                                        </#list>
                                                    </select>
                                                </#if>
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <div class="col-sm-1">
                                                <label for="id_mixed_create_user" class="col-form-label">创建人：</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <p class="text-info" class="form-control form-control-sm"
                                                   id="id_mixed_create_user">${detail.createUser}</p>
                                            </div>

                                            <div class="offset-sm-1 col-sm-1">
                                                <label for="id_mixed_create_time" class="col-form-label">创建时间：</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <p class="text-info" class="form-control form-control-sm"
                                                   id="id_mixed_create_time">${detail.createTime}</p>
                                            </div>
                                        </div>
                                    </form>

                                    <h5 class="mt-4 mb-3">分库配置(Repository Sharding):</h5>
                                    <#if detail.repositories?? && (detail.repositories?size > 0)>
                                        <#list detail.repositories as repository>
                                            <div id="accordion_${repository.repositoryName}">
                                                <div class="card card-default">
                                                    <div class="card-header">
                                                        <h4 class="card-title">
                                                            <a data-toggle="collapse"
                                                               data-parent="#accordion_${repository.repositoryName}"
                                                               href="#collapseOne_${repository.repositoryName}">
                                                                Repository Name: ${repository.repositoryName}
                                                            </a>
                                                        </h4>
                                                    </div>

                                                    <div id="collapseOne_${repository.repositoryName}"
                                                         class="panel-collapse collapse in">
                                                        <h6 class="mt-4 mb-1 ml-4">属性信息(Repository Config):</h6>
                                                        <div class="card-body">
                                                            <form class="form-horizontal ml-2">
                                                                <div class="form-group row">
                                                                    <div class="col-sm-1">
                                                                        <label class="col-form-label">名称：</label>
                                                                    </div>

                                                                    <div class="col-sm-3">
                                                                        <p class="text-success"
                                                                           class="form-control form-control-sm">${repository.repositoryName}</p>
                                                                    </div>

                                                                    <div class="col-sm-1">
                                                                        <label class="col-form-label">状态：</label>
                                                                    </div>

                                                                    <div class="col-sm-2">
                                                                        <#if repository.state == "ONLINE">
                                                                            <p class="text-success"
                                                                               class="form-control form-control-sm">${repository.state}</p>
                                                                        <#else >
                                                                            <p class="text-danger"
                                                                               class="form-control form-control-sm">${repository.state}</p>
                                                                        </#if>

                                                                    </div>

                                                                    <div class="col-sm-2">
                                                                        <label class="col-form-label">负载均衡策略：</label>
                                                                    </div>

                                                                    <div class="col-sm-3">
                                                                        <p class="text-success"
                                                                           class="form-control form-control-sm">${repository.loadBalance}</p>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                        <h6 class="mt-1 mb-1 ml-4">资源配置(Atom Config):</h6>
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
                                                                <#list repository.atoms as atom>
                                                                    <tr>
                                                                        <td>${atom.atomName}</td>
                                                                        <td>${atom.database}</td>
                                                                        <td>${atom.masterSlaveType}</td>
                                                                        <td>${atom.state}</td>
                                                                        <td>${atom.weight}</td>
                                                                        <td>${atom.dataSourcePoolType}</td>
                                                                        <td><textarea class="form-control" rows="3"
                                                                                      cols="30"
                                                                                      readonly>${atom.params}</textarea>
                                                                        </td>
                                                                        <td><textarea class="form-control" rows="3"
                                                                                      cols="35"
                                                                                      readonly>${atom.poolConfig}</textarea>
                                                                        </td>
                                                                    </tr>
                                                                </#list>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </#list>
                                    </#if>
                                </div>
                            </div>

                            <div class="tab-pane fade" id="custom-content-code-spring-home" role="tabpanel"
                                 aria-labelledby="custom-content-code-spring-tab">
                                <p class="mt-4 mb-0 ml-1">应用配置(applicationContext.xml):</p>
                                <pre class="pt-0 pl-0 pr-0 pb-0">
                                    <code class="xml mt-0">${detail.springXmlCode}</code>
                                </pre>
                            </div>

                            <div class="tab-pane fade" id="custom-content-code-springboot-home" role="tabpanel"
                                 aria-labelledby="custom-content-code-springboot-tab">
                                <p class="mt-4 mb-0 ml-1">应用配置(application.properties):</p>
                                <pre class="pt-0 pl-0 pr-0 pb-0">
                                    <code class="properties mt-0">${detail.springBootPropertiesCode}</code>
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
<script src="/dist/js/pages/application-config/datasource/datasource-config-detail.js"></script>
</html>