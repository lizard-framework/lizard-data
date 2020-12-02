$(function () {
    const val_arr = ["Alabama", "Alaska"];
    //$("#id_mixed_application").val(val_arr).trigger("change");

    // 应用查询下拉框初始化
    $("#id_mixed_application").select2({
        placeholder: "Application Name",
        // 最少输入3个字符
        minimumInputLength: 3,
        ajax: {
            // 模糊查询url
            url: api_resources_manager_application_list_fuzzy,
            quietMillis: 250,
            method: 'get',
            dataType: 'json',
            // 请求参数
            data: function (params) {
                const query = {
                    applicationName: params.term,
                    type: 'query'
                }

                return query;
            },
            // 处理响应参数
            processResults: function (data) {
                // select2需要{'id':'', 'text':''}格式 由后端转换
                return {
                    results: data.data
                }
            }
        }
    });
});