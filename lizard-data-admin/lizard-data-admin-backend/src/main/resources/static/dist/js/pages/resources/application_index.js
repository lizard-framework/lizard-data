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
                    url: api_resources_application_list,
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
