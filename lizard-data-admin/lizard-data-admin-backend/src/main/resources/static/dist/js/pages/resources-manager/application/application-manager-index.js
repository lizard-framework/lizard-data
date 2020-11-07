$(function () {
    // init table
    initTable();
    // id:id_btn_query onClick
    $("#id_btn_query").click(function () {
        // init table
        initTable();
    });
    $("#id_form_query").submit(function (e) {
        e.preventDefault();
        // init table
        initTable();
    });

    // add application modal: before show modal
    $("#id_add_modal").on("show.bs.modal", function () {
        $("#id_form_add_application_name").val("");
        $("#id_form_add_owner_name").val("");
        $("#id_form_add_application_desc").val("");
    });

    // add database button click
    $("#id_form_add_btn").click(function () {
        const params = {};
        params.applicationName = $("#id_form_add_application_name").val();
        params.applicationDesc = $("#id_form_add_application_desc").val();
        params.owner = $("#id_form_add_owner_name").val();

        callApi(api_resources_manager_application_add, "PUT", params, function (result) {
            alertTopEnd('添加应用信息成功', 'success', 6000);
            $("#id_add_modal").modal('hide');
            initTable();

            return null;
        }, null);
    });
});


// init table
function initTable() {
    $("#jsGrid1").jsGrid("destroy");

    $("#jsGrid1").jsGrid({
        height: "auto",
        width: "100%",

        sorting: true,
        paging: true,
        autoload: true,

        pageSize: 10,
        pageButtonCount: 5,
        pageLoading: true,
        loadMessage: '正在加载数据...',

        controller: {
            loadData: function (filter) {
                filter.applicationName = $("#id_application_name").val();

                return $.ajax({
                    type: 'GET',
                    url: api_resources_manager_application_list,
                    data: filter
                });
            }
        },

        fields: [
            {title: "Id", name: "id", type: "number", visible: false},
            {title: "Application", name: "applicationName", type: "text", width: 150, align: "center"},
            {title: "Description", name: "applicationDesc", type: "text", width: 150, align: "center"},
            {title: "Owner", name: "ownerName", type: "text", width: 100, align: "center"},
            {title: "Create Time", name: "createTime", type: "text", width: 100, align: "center"}
        ]
    });
}
