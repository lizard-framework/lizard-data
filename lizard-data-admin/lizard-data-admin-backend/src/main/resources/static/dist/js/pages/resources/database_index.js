$(function () {

    // id:id_btn_query onClick
    $("#id_btn_query").click(function() {
        // init table
        initTable();
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
            return "<div class=\"btn-group\">"
                + "<button type=\"button\" class=\"btn btn-info\">操作</button>"
                + "<button type=\"button\" class=\"btn btn-info dropdown-toggle dropdown-icon\" data-toggle=\"dropdown\">"
                + "<span class=\"sr-only\">Toggle Dropdown</span>"
                + "<div class=\"dropdown-menu\" role=\"menu\">"
                + "<a class=\"dropdown-item\" href=\"#\">详情</a>"
                + "<a class=\"dropdown-item\" href=\"#\">依赖详情</a>"
                + "</div>"
                + "</button>"
                + "</div>";
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
                filter.dbType = $("#id_db_type").val();
                filter.dbName = $("#id_db_name").val();

                return $.ajax({
                    type: 'GET',
                    url: api_database_list,
                    data: filter
                });
            }
        },

        fields: [
            {title: "Id", name: "id", type: "number", visible: false},
            {title: "Type", name: "dbType", type: "text", width: 50, align: "center"},
            {title: "Schema", name: "dbName", type: "text", width: 150, align: "center"},
            {title: "Host", name: "dbHost", type: "text", width: 200, align: "center"},
            {title: "Port", name: "dbPort", type: "number", width: 50, align: "center"},
            {title: "Create Time", name: "createTime", type: "text", width: 200, align: "center"},
            {title: "操作", type: "operField", width: 100, align: "center"}
        ]
    });
}