const api_database_list = '/operator/database/list';
const api_database_add = '/operator/database';
const api_database_detail = '/operator/database/';
const api_database_auth = '/operator/database/authinfo/';

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

