layui.config({
    base: 'assets/module/'
}).extend({
    formSelects: 'formSelects/formSelects-v4',
    treetable: 'treetable-lay/treetable',
    dropdown: 'dropdown/dropdown',
    notice: 'notice/notice',
    step: 'step-lay/step',
    dtree: 'dtree/dtree',
    citypicker: 'city-picker/city-picker',
    tableSelect: 'tableSelect/tableSelect'
}).use(['layer', 'element', 'config', 'index', 'admin', 'laytpl'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var element = layui.element;
    var config = layui.config;
    var index = layui.index;
    var admin = layui.admin;
    var laytpl = layui.laytpl;

    // 检查是否登录
    if (!config.getToken()) {
        return location.replace('login.html');
    }

    // *************************************************************************************
    // 默认设置蓝色主题
    initTheme("theme-blue");
    function initTheme(themeColor){
        if(!themeColor) themeColor = "theme-blue";
        var mTheme = layui.data(config.tableName).theme;
        var theme = mTheme ? mTheme : themeColor;
        if(themeColor != mTheme) admin.changeTheme(theme);
    }
    // 默认设置关闭页脚
    initFooter(false);
    function initFooter(footer){
        var openFooter = layui.data(config.tableName).openFooter;
        var checked = openFooter == undefined ? footer : openFooter;
        if(footer != openFooter){
            $('#setFooter').prop('checked', checked);
            layui.data(config.tableName, {key: 'openFooter', value: checked});
            checked ? $('body.layui-layout-body').removeClass('close-footer') : $('body.layui-layout-body').addClass('close-footer');
        }
    }
    // *************************************************************************************

    // 获取用户信息
    layer.load();
    admin.req('user/info', {}, function (res) {
        admin.removeLoading();  // 移除页面加载动画
        layer.closeAll('loading');
        if (200 == res.code) {
            config.putUser(res.user);
            $('#huName').text(res.user.nickName);
        } else {
            layer.msg('获取用户失败', {icon: 2});
        }
    }, 'GET');

    // 加载侧边栏
    admin.req('user/menu', {}, function (res) {
        laytpl(sideNav.innerHTML).render(res.data, function (html) {
            $('.layui-layout-admin .layui-side .layui-nav').html(html);
            element.render('nav');
        });

        var menus = [];
        buildMenu(res.data, menus);
        store.set('menus', menus);

        index.regRouter(res.data);  // 注册路由
        index.loadHome({  // 加载主页
            url: '#/welcome',
            name: '<i class="layui-icon layui-icon-home"></i>'
        });
    }, 'get');

    function buildMenu(data, menus) {
        $.each(data, function (i, item) {
            if(item.url.indexOf("javascript:") < 0){
                menus.push({id: item.url, name: item.name});
                if(item.subMenus.length > 0){
                    buildMenu(item.subMenus, menus);
                }
            }else{
                if(item.subMenus.length > 0){
                    buildMenu(item.subMenus, menus);
                }
            }
        });
    }
});
