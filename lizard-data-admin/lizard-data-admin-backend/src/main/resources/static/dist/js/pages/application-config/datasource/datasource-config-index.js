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

    $("#id_btn_add").click(function () {
        window.location.href = page_application_config_datasource_add;
    });
});

// init table
function initTable() {
    $("#jsGrid1").jsGrid("destroy");

    const OperField = function (config) {
        jsGrid.Field.call(this, config);
    };

    OperField.prototype = new jsGrid.Field({
        itemTemplate: function (value, item) {
            return "<button type=\"button\" class=\"btn btn-info btn-sm\" onclick=\"openDetailPage('" + item.mixedName + "')\">详情</button>";
        }
    });
    jsGrid.fields.operField = OperField;

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
                filter.mixedName = $("#id_mixed_name").val();

                return $.ajax({
                    type: 'GET',
                    url: api_application_orm_list,
                    data: filter
                });
            }
        },

        fields: [
            {title: "Id", name: "id", type: "number", visible: false},
            {title: "MixedName", name: "mixedName", type: "text", width: 200, align: "center"},
            {title: "Desc", name: "mixedDesc", type: "text", width: 200, align: "center"},
            {title: "State", name: "state", type: "text", width: 50, align: "center"},
            {title: "DbType", name: "dbType", type: "number", width: 80, align: "center"},
            {title: "Create User", name: "createUser", type: "text", width: 80, align: "center"},
            {title: "Create Time", name: "createTime", type: "text", width: 100, align: "center"},
            {title: "操作", type: "operField", width: 80, align: "center"}
        ]
    });
}


function openDetailPage(mixedName) {
    window.location.href = page_application_config_datasource_detail + mixedName;
}