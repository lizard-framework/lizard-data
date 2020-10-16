$(function () {
    // init table
    initTable();
});

// init table
function initTable() {
    $("#jsGrid1").jsGrid({
        height: "100%",
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
                 return $.ajax({
                    type: 'GET',
                    url: api_database_list,
                    data: filter
                });
            }
        },

        fields: [
            {title: "Type", name: "dbType", type: "text", width: 50, align: "center"},
            {title: "Schema", name: "dbName", type: "text", width: 150, align: "center"},
            {title: "Host", name: "dbHost", type: "text", width: 200, align: "center"},
            {title: "Port", name: "dbPort", type: "number", width: 50, align: "center"},
            {title: "Create Time", name: "createTime", type: "text", width: 200, align: "center"}
        ]
    });
}