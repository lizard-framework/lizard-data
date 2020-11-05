const api_resources_database_list = '/operator/resources/database/list';
const api_resources_database_add = '/operator/resources/database';
const api_resources_database_detail = '/operator/resources/database/';
const api_resources_database_auth = '/operator/resources/database/authinfo/';
const api_resources_application_list = '/operator/resources/application/list';
const api_resources_application_add = '/operator/resources/application';

const api_application_orm_list = '/operator/application-config/orm/list';

// 应用配置-数据源配置-详情页面
const page_application_config_datasource_detail = '/application-config/datasource/detail/';
const page_application_config_datasource_add = '/application-config/datasource/addition';

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

