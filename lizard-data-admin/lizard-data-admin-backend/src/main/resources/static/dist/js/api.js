const api_database_list = '/operator/database/list';
const api_database_add = '/operator/database';

// call api
function callApi(api, method, params, SuccFunc, FailFunc) {
    $.ajax({
        type: method,
        contentType: "application/json;charset=UTF-8",
        url: api,
        data: params,
        success: function (result) {
            if (result.code == "000000") {
                if (SuccFunc != null)
                    return SuccFunc(result);
                else
                    $.Toasts('create', {
                        class: 'bg-success',
                        body: '成功'
                    });
            } else {
                if (FailFunc != null)
                    return FailFunc(result);
                else
                    $.Toasts('create', {
                        class: 'bg-warning',
                        title: 'warning',
                        subtitle: api,
                        body: '[' + result.code + ']:' + result.message
                    });
            }
        },
        error: function (e) {
            console.log(e.status);
            $.Toasts('create', {
                class: 'bg-danger',
                title: '请求失败',
                subtitle: api,
                body: '[' + e.status + ']:' + e.responseText
            });
        }
    });
}