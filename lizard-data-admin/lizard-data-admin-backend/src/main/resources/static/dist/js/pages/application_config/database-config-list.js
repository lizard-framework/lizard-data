$(function () {

});

// init table
function initTable() {
    $("#jsGrid1").jsGrid("destroy");

    const OperField = function (config) {
        jsGrid.Field.call(this, config);
    };

    OperField.prototype = new jsGrid.Field({
        itemTemplate: function (value, item) {
            return "<button type=\"button\" class=\"btn btn-info\">详情</button>";
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

        fields: [
            {title: "Id", name: "id", type: "number", visible: false},
            {title: "Name", name: "mixedName", type: "text", width: 50, align: "center"},
            {title: "Desc", name: "mixedDesc", type: "text", width: 150, align: "center"},
            {title: "State", name: "state", type: "text", width: 200, align: "center"},
            {title: "DatebaseType", name: "dbType", type: "number", width: 50, align: "center"},
            {title: "Create User", name: "createUser", type: "text", width: 200, align: "center"},
            {title: "Create Time", name: "createTime", type: "text", width: 200, align: "center"},
            {title: "操作", type: "operField", width: 100, align: "center"}
        ]
    });
}