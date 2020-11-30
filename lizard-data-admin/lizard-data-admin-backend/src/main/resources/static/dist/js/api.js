// api:应用配置-数据源配置-列表查询
const api_application_config_datasource_list = '/api/application-config/datasource/list';

// api:资源管理-应用管理-应用列表
const api_resources_manager_application_list = '/api/resources-manager/application/list';
// api:资源管理-应用管理-名称查询
const api_resources_manager_application_name = '/api/resources-manager/application/name/';
// api:资源管理-应用管理-添加应用
const api_resources_manager_application_add = '/api/resources-manager/application/save';

// api:资源管理-数据库管理-应用列表
const api_resources_manager_database_list = '/api/resources-manager/database/list';
// api:资源管理-数据库管理-添加数据库
const api_resources_manager_database_add = '/api/resources-manager/database/save';
// api:资源管理-数据库管理-数据库详情
const api_resources_manager_database_detail = "/api/resources-manager/database/detail/";
// api:资源管理-数据库管理-密码信息
const api_resources_manager_database_authinfo = '/api/resources-manager/database/authinfo/';

// 应用配置-数据源配置-详情页面
const page_application_config_datasource_detail = '/application-config/datasource/detail/';
// 应用配置-数据源配置-添加页面-基础配置
const page_application_config_datasource_add = '/application-config/datasource/addition/basic';

// call api
function callApi(api, method, params, SuccFunc, FailFunc) {
    $.ajax({
        type: method,
        contentType: "application/json;charset=UTF-8",
        url: api,
        data: JSON.stringify(params),
        success: function (result) {
            if (result.code == "000000") {
                if (SuccFunc != null) {
                    return SuccFunc(result);
                } else {
                    alertBasic('操作成功', 'success');
                }
            } else {
                if (FailFunc != null) {
                    return FailFunc(result);
                } else {
                    alertBasic('[' + result.code + ']:' + result.message, 'warning');
                }
            }
        },
        error: function (e) {
            console.log(e.status);
            alertBasic('[' + e.status + ']:' + e.responseText, 'error');
        }
    });
}

