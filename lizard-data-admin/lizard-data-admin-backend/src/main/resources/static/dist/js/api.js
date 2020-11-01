const api_resources_database_list = '/operator/resources/database/list';
const api_resources_database_add = '/operator/resources/database';
const api_resources_database_detail = '/operator/resources/database/';
const api_resources_database_auth = '/operator/resources/database/authinfo/';
const api_resources_application_list = '/operator/resources/application/list';
const api_resources_application_add = '/operator/resources/application';

const api_application_orm_list = '/operator/application-config/orm/list';

const page_application_orm_detail = '/application-config/orm/detail/';
const page_application_orm_add = '/application-config/orm/add';

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

