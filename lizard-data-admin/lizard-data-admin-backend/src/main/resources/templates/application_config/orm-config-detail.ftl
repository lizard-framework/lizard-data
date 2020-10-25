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
            <div class="card card-primary card-outline">
                <div class="card-body">
                    <ul class="nav nav-tabs" id="custom-content-above-tab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="custom-content-above-home-tab" data-toggle="pill"
                               href="#custom-content-above-home" role="tab" aria-controls="custom-content-above-home"
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
                            .....
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